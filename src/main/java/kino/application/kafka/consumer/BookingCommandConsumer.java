package kino.application.kafka.consumer;

import kino.application.data.*;
import kino.application.kafka.events.BookingCommand;
import kino.application.kafka.events.BookingEvent;
import kino.application.kafka.events.SitzplatzInfo;
import kino.application.kafka.producer.EventProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Consumer für Buchungs-Kommandos.
 * Empfängt Buchungsanfragen von Kafka und speichert sie in PostgreSQL.
 */
@Service
public class BookingCommandConsumer {

    private final BuchungRepository buchungRepository;
    private final AuffuehrungRepository auffuehrungRepository;
    private final KundeRepository kundeRepository;
    private final SitzplatzRepository sitzplatzRepository;
    private final BuchungSitzplatzRepository buchungSitzplatzRepository;
    private final EventProducer eventProducer;

    public BookingCommandConsumer(
            BuchungRepository buchungRepository,
            AuffuehrungRepository auffuehrungRepository,
            KundeRepository kundeRepository,
            SitzplatzRepository sitzplatzRepository,
            BuchungSitzplatzRepository buchungSitzplatzRepository,
            EventProducer eventProducer) {
        this.buchungRepository = buchungRepository;
        this.auffuehrungRepository = auffuehrungRepository;
        this.kundeRepository = kundeRepository;
        this.sitzplatzRepository = sitzplatzRepository;
        this.buchungSitzplatzRepository = buchungSitzplatzRepository;
        this.eventProducer = eventProducer;
    }

    @KafkaListener(
            topics = "${kino.kafka.topic.bookings}",
            groupId = "kino-booking-worker"
    )
    @Transactional
    public void handleBookingCommand(BookingCommand command) {
        System.out.println(">>> [BookingConsumer] Buchung erhalten: " + command);

        try {
            // 1. Aufführung laden
            Auffuehrung auffuehrung = auffuehrungRepository.findById(command.getAuffuehrungId())
                    .orElseThrow(() -> new RuntimeException("Aufführung nicht gefunden: " + command.getAuffuehrungId()));

            // 2. Kunde laden
            Kunde kunde = kundeRepository.findById(command.getKundeId())
                    .orElseThrow(() -> new RuntimeException("Kunde nicht gefunden: " + command.getKundeId()));

            // 3. Buchung erstellen
            Buchung buchung = new Buchung();
            buchung.setBuchungsnummer(generateBuchungsnummer());
            buchung.setBuchungsZeitstempel(new Date());
            buchung.setKunde(kunde);
            buchung.setAuffuehrung(auffuehrung);
            buchung = buchungRepository.save(buchung);

            // 4. Sitzplätze buchen
            List<BuchungSitzplatz> gebuchterPlaetze = new ArrayList<>();
            double gesamtpreis = 0.0;
            
            for (SitzplatzInfo info : command.getSitzplaetze()) {
                Sitzplatz sitzplatz = sitzplatzRepository.findById(info.getSitzplatzId())
                        .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden: " + info.getSitzplatzId()));

                // Prüfen, ob Sitzplatz bereits gebucht ist
                if (sitzplatz.getBuchung() != null) {
                    throw new RuntimeException("Sitzplatz bereits gebucht: Reihe " + 
                            info.getReiheNummer() + ", Platz " + info.getPlatzNummer());
                }

                // Wenn Sitzplatz reserviert war, Reservierung entfernen
                if (sitzplatz.getReservierung() != null) {
                    sitzplatz.setReservierung(null);
                }

                // BuchungSitzplatz Join-Entity erstellen
                BuchungSitzplatz bs = new BuchungSitzplatz();
                bs.setBuchung(buchung);
                bs.setSitzplatz(sitzplatz);
                bs.setPreis(info.getPreis());
                bs = buchungSitzplatzRepository.save(bs);
                
                gebuchterPlaetze.add(bs);
                gesamtpreis += info.getPreis();
                
                // Sitzplatz als gebucht markieren
                sitzplatz.setBuchung(buchung);
                sitzplatzRepository.save(sitzplatz);
            }

            buchung.setBuchungSitzplaetze(gebuchterPlaetze);

            // 5. Einnahmen der Aufführung aktualisieren
            auffuehrung.setAktuelleEinnahmen(auffuehrung.getAktuelleEinnahmen() + gesamtpreis);
            auffuehrungRepository.save(auffuehrung);

            System.out.println(">>> Buchung erfolgreich gespeichert: " + buchung.getId() + 
                             " (Gesamtpreis: " + gesamtpreis + " €)");

            // 6. Event verschicken
            BookingEvent event = new BookingEvent(
                    buchung.getId(),
                    buchung.getBuchungsnummer(),
                    auffuehrung.getId(),
                    kunde.getId(),
                    gesamtpreis,
                    "COMPLETED"
            );
            eventProducer.sendBookingEvent(event);

        } catch (Exception e) {
            System.err.println(">>> [BookingConsumer] Fehler beim Verarbeiten: " + e.getMessage());
            e.printStackTrace();
            // Hier könntest du ein FAILED-Event senden
        }
    }

    private String generateBuchungsnummer() {
        // Generiere eine eindeutige Buchungsnummer
        return "BU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
