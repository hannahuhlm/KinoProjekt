package kino.application.kafka.consumer;

import kino.application.data.*;
import kino.application.kafka.events.ReservationCommand;
import kino.application.kafka.events.ReservationEvent;
import kino.application.kafka.events.SitzplatzInfo;
import kino.application.kafka.producer.EventProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Consumer für Reservierungs-Kommandos.
 * Empfängt Reservierungsanfragen von Kafka und speichert sie in PostgreSQL.
 */
@Service
public class ReservationCommandConsumer {

    private final ReservierungRepository reservierungRepository;
    private final AuffuehrungRepository auffuehrungRepository;
    private final KundeRepository kundeRepository;
    private final SitzplatzRepository sitzplatzRepository;
    private final ReservierungSitzplatzRepository reservierungSitzplatzRepository;
    private final EventProducer eventProducer;
    
    private final Random random = new Random();

    public ReservationCommandConsumer(
            ReservierungRepository reservierungRepository,
            AuffuehrungRepository auffuehrungRepository,
            KundeRepository kundeRepository,
            SitzplatzRepository sitzplatzRepository,
            ReservierungSitzplatzRepository reservierungSitzplatzRepository,
            EventProducer eventProducer) {
        this.reservierungRepository = reservierungRepository;
        this.auffuehrungRepository = auffuehrungRepository;
        this.kundeRepository = kundeRepository;
        this.sitzplatzRepository = sitzplatzRepository;
        this.reservierungSitzplatzRepository = reservierungSitzplatzRepository;
        this.eventProducer = eventProducer;
    }

    @KafkaListener(
            topics = "${kino.kafka.topic.reservations}",
            groupId = "kino-reservation-worker"
    )
    @Transactional
    public void handleReservationCommand(ReservationCommand command) {
        System.out.println(">>> [ReservationConsumer] Reservierung erhalten: " + command);

        try {
            // 1. Aufführung laden
            Auffuehrung auffuehrung = auffuehrungRepository.findById(command.getAuffuehrungId())
                    .orElseThrow(() -> new RuntimeException("Aufführung nicht gefunden: " + command.getAuffuehrungId()));

            // 2. Kunde laden oder neu erstellen
            Kunde kunde = null;
            if (command.getKundeId() != null) {
                kunde = kundeRepository.findById(command.getKundeId()).orElse(null);
            }
            
            if (kunde == null) {
                // Neuen Kunden erstellen
                kunde = new Kunde();
                kunde.setName(command.getKundeName());
                kunde.setEmail(command.getKundeName() + "@example.com"); // Platzhalter
                kunde = kundeRepository.save(kunde);
                System.out.println(">>> Neuer Kunde erstellt: " + kunde.getId());
            }

            // 3. Reservierung erstellen
            Reservierung reservierung = new Reservierung();
            reservierung.setReservierungsnummer(generateReservierungsnummer());
            reservierung.setStartZeitstempel(new Date());
            reservierung.setKunde(kunde);
            reservierung.setAuffuehrung(auffuehrung);
            reservierung = reservierungRepository.save(reservierung);

            // 4. Sitzplätze reservieren
            List<ReservierungSitzplatz> reserviertePlaetze = new ArrayList<>();
            for (SitzplatzInfo info : command.getSitzplaetze()) {
                Sitzplatz sitzplatz = sitzplatzRepository.findById(info.getSitzplatzId())
                        .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden: " + info.getSitzplatzId()));

                // Prüfen, ob Sitzplatz bereits reserviert ist
                if (sitzplatz.getReservierung() != null || sitzplatz.getBuchung() != null) {
                    throw new RuntimeException("Sitzplatz bereits belegt: Reihe " + 
                            info.getReiheNummer() + ", Platz " + info.getPlatzNummer());
                }

                // ReservierungSitzplatz Join-Entity erstellen
                ReservierungSitzplatz rs = new ReservierungSitzplatz();
                rs.setReservierung(reservierung);
                rs.setSitzplatz(sitzplatz);
                rs.setPreis(info.getPreis());
                rs = reservierungSitzplatzRepository.save(rs);
                
                reserviertePlaetze.add(rs);
                
                // Sitzplatz als reserviert markieren
                sitzplatz.setReservierung(reservierung);
                sitzplatzRepository.save(sitzplatz);
            }

            reservierung.setReservierungSitzplaetze(reserviertePlaetze);

            System.out.println(">>> Reservierung erfolgreich gespeichert: " + reservierung.getId());

            // 5. Event verschicken
            ReservationEvent event = new ReservationEvent(
                    reservierung.getId(),
                    reservierung.getReservierungsnummer(),
                    auffuehrung.getId(),
                    kunde.getId(),
                    "CREATED"
            );
            eventProducer.sendReservationEvent(event);

        } catch (Exception e) {
            System.err.println(">>> [ReservationConsumer] Fehler beim Verarbeiten: " + e.getMessage());
            e.printStackTrace();
            // Hier könntest du ein FAILED-Event senden
        }
    }

    private int generateReservierungsnummer() {
        // Einfache Zufallsnummer - in Produktion würdest du eine Sequenz verwenden
        return 10000 + random.nextInt(90000);
    }
}
