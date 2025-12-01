package kino.application.aggregation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import kino.application.data.Buchung;
import kino.application.data.AuffuehrungRepository;
import kino.application.kafka.events.AggregationResultEvent;
import kino.application.kafka.producer.EventProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class AggregationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationService.class);
    
    private final EntityManager em;
    private final MongoRevenueRepository mongoRepo;
    private final AuffuehrungRepository auffRepo;
    private final EventProducer eventProducer;

    public AggregationService(EntityManager em, MongoRevenueRepository mongoRepo, AuffuehrungRepository auffRepo, EventProducer eventProducer) {
        this.em = em;
        this.mongoRepo = mongoRepo;
        this.auffRepo = auffRepo;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public void aggregateDay(LocalDate day) {
        aggregateDay(day, null);
    }

    @Transactional
    public void aggregateDay(LocalDate day, String correlationId) {
        LOGGER.info(">>> Starte Aggregation für Tag: {}", day);
        
        // Falls es bereits Aggregate für diesen Tag gibt: löschen und ersetzen
        List<RevenueAggregate> existing = mongoRepo.findByDay(day);
        if (!existing.isEmpty()) {
            try {
                long removed = mongoRepo.deleteByDay(day);
                LOGGER.info(">>> {} bestehende Aggregate für Tag {} gefunden - alte Einträge gelöscht ({} entfernt)", existing.size(), day, removed);
                eventProducer.sendAggregationEvent(new AggregationResultEvent(
                        day,
                        correlationId,
                        AggregationResultEvent.Operation.DELETE,
                        AggregationResultEvent.Status.SUCCESS,
                        (int) removed,
                        "Alte Tagesaggregate gelöscht",
                        LocalDateTime.now()
                ));
            } catch (Exception ex) {
                LOGGER.error(">>> Fehler beim Löschen bestehender Aggregate für {}: {}", day, ex.getMessage(), ex);
                eventProducer.sendAggregationEvent(new AggregationResultEvent(
                        day,
                        correlationId,
                        AggregationResultEvent.Operation.DELETE,
                        AggregationResultEvent.Status.FAILURE,
                        0,
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
                throw ex;
            }
        }
        
        // calculate range for day
        Date start = Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(day.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Load bookings for the day
        TypedQuery<Buchung> bq = em.createQuery(
                "SELECT b FROM Buchung b WHERE b.buchungsZeitstempel >= :start AND b.buchungsZeitstempel < :end",
                Buchung.class);
        bq.setParameter("start", start);
        bq.setParameter("end", end);
        List<Buchung> bookings = bq.getResultList();
        LOGGER.info(">>> Gefundene Buchungen: {}", bookings.size());

        // If no bookings found, skip aggregation
        if (bookings.isEmpty()) {
            LOGGER.info(">>> Keine Buchungen für Tag {} vorhanden - überspringe Aggregation", day);
            return;
        }

        LocalDateTime aggregatedAt = LocalDateTime.now();
        String currentTime = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        
        // Group bookings by Aufführung and aggregate per Aufführung
        var bookingsByAuffuehrung = bookings.stream()
                .collect(java.util.stream.Collectors.groupingBy(Buchung::getAuffuehrung));
        
        int savedCount = 0;
        try {
            for (var entry : bookingsByAuffuehrung.entrySet()) {
                var auffuehrung = entry.getKey();
                var auffBookings = entry.getValue();

                double revenue = auffBookings.stream().mapToDouble(Buchung::getGesamtpreis).sum();
                int bookingsCount = auffBookings.size();

                Long filmId = auffuehrung.getFilm() != null ? auffuehrung.getFilm().getId() : null;
                Long auffuehrungId = auffuehrung.getId();

                // Sitzplätze berechnen
                int totalSeats = 0;
                int occupiedSeats = 0;
                if (auffuehrung.getSaal() != null && auffuehrung.getSaal().getReihen() != null) {
                    totalSeats = auffuehrung.getSaal().getReihen().stream()
                        .mapToInt(r -> r.getPlaetze() != null ? r.getPlaetze().size() : 0)
                        .sum();
                }
                // belegte Sitzplätze = Summe der Plätze in Buchungen (Join-Entity) ODER Sitzplaetze in Sitzplaetze, hier über BuchungSitzplatz-Liste
                occupiedSeats = auffBookings.stream()
                    .mapToInt(b -> b.getBuchungSitzplaetze() != null ? b.getBuchungSitzplaetze().size() : 0)
                    .sum();
                double occPercent = (totalSeats > 0) ? (occupiedSeats * 100.0 / totalSeats) : 0.0;

                RevenueAggregate agg = new RevenueAggregate(day, aggregatedAt, filmId, auffuehrungId,
                    revenue, bookingsCount, 0, occupiedSeats, totalSeats, occPercent);
                mongoRepo.save(agg);
                savedCount++;

                LOGGER.info(">>> Aggregat gespeichert: Film={}, Aufführung={}, Einnahmen={}, Buchungen={}",
                        filmId, auffuehrungId, revenue, bookingsCount);
            }

            LOGGER.info(">>> {} Aggregate gespeichert um {} für Tag {}", savedCount, currentTime, day);
                eventProducer.sendAggregationEvent(new AggregationResultEvent(
                    day,
                    correlationId,
                    AggregationResultEvent.Operation.INSERT,
                    AggregationResultEvent.Status.SUCCESS,
                    savedCount,
                    "Neue Tagesaggregate gespeichert",
                    LocalDateTime.now()
            ));
            LOGGER.info(">>> ✅ Aggregation für {} erfolgreich abgeschlossen", day);
            LOGGER.info(">>> ℹ️  Buchungen bleiben in PostgreSQL erhalten");
        } catch (Exception ex) {
            LOGGER.error(">>> ❌ Fehler beim Speichern der Aggregate für {}: {}", day, ex.getMessage(), ex);
                eventProducer.sendAggregationEvent(new AggregationResultEvent(
                    day,
                    correlationId,
                    AggregationResultEvent.Operation.INSERT,
                    AggregationResultEvent.Status.FAILURE,
                    savedCount,
                    ex.getMessage(),
                    LocalDateTime.now()
            ));
            throw ex;
        }
    }
}
