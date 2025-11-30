package kino.application.aggregation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import kino.application.data.Auffuehrung;
import kino.application.data.Buchung;
import kino.application.data.Reservierung;
import kino.application.data.AuffuehrungRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class AggregationService {
    private final EntityManager em;
    private final MongoRevenueRepository mongoRepo;
    private final AuffuehrungRepository auffRepo;

    public AggregationService(EntityManager em, MongoRevenueRepository mongoRepo, AuffuehrungRepository auffRepo) {
        this.em = em;
        this.mongoRepo = mongoRepo;
        this.auffRepo = auffRepo;
    }

    @Transactional
    public void aggregateDay(LocalDate day) {
        // calculate range for day
        Date start = Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(day.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Load bookings for the day
        TypedQuery<Buchung> bq = em.createQuery(
                "SELECT b FROM Buchung b WHERE b.zeitpunkt >= :start AND b.zeitpunkt < :end",
                Buchung.class);
        bq.setParameter("start", start);
        bq.setParameter("end", end);
        List<Buchung> bookings = bq.getResultList();

        // Load expired reservations for the day (assumes 'ablaufzeit' indicates expiration)
        TypedQuery<Reservierung> rq = em.createQuery(
                "SELECT r FROM Reservierung r WHERE r.ablaufzeit >= :start AND r.ablaufzeit < :end",
                Reservierung.class);
        rq.setParameter("start", start);
        rq.setParameter("end", end);
        List<Reservierung> reservations = rq.getResultList();

        // Aggregate by Auffuehrung
        bookings.stream().map(Buchung::getAuffuehrung).distinct().forEach(a -> {
            double revenue = bookings.stream().filter(b -> b.getAuffuehrung().equals(a))
                    .mapToDouble(Buchung::getGesamtpreis).sum();
            int bookingsCount = (int) bookings.stream().filter(b -> b.getAuffuehrung().equals(a)).count();
            int expiredCount = (int) reservations.stream().filter(r -> r.getAuffuehrung().equals(a)).count();

            RevenueAggregate agg = new RevenueAggregate(day,
                    a.getFilm().getId(), a.getId(), revenue, bookingsCount, expiredCount);
            mongoRepo.save(agg);
        });

        // Also aggregate films without bookings that day but with expired reservations
        reservations.stream().map(Reservierung::getAuffuehrung).distinct().forEach(a -> {
            boolean alreadyAggregated = mongoRepo.findByDay(day).stream()
                    .anyMatch(x -> x.getAuffuehrungId().equals(a.getId()));
            if (!alreadyAggregated) {
                int expiredCount = (int) reservations.stream().filter(r -> r.getAuffuehrung().equals(a)).count();
                RevenueAggregate agg = new RevenueAggregate(day,
                        a.getFilm().getId(), a.getId(), 0.0, 0, expiredCount);
                mongoRepo.save(agg);
            }
        });

        // Delete aggregated bookings and expired reservations
        em.createQuery("DELETE FROM Buchung b WHERE b.zeitpunkt >= :start AND b.zeitpunkt < :end")
                .setParameter("start", start)
                .setParameter("end", end)
                .executeUpdate();

        em.createQuery("DELETE FROM Reservierung r WHERE r.ablaufzeit >= :start AND r.ablaufzeit < :end")
                .setParameter("start", start)
                .setParameter("end", end)
                .executeUpdate();
    }
}
