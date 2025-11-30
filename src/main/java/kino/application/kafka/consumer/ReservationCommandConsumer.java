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
        System.out.println(">>> [ReservationConsumer] Command erhalten: " + command);

        // Prüfen ob CREATE, DELETE oder QUERY
        if ("DELETE".equals(command.getAction())) {
            handleDeleteReservation(command);
            return;
        }
        if ("QUERY".equals(command.getAction())) {
            handleQueryReservations(command);
            return;
        }

        // CREATE Logik
        try {
            // 1. Aufführung laden
            Auffuehrung auffuehrung = auffuehrungRepository.findById(command.getAuffuehrungId())
                    .orElseThrow(() -> new RuntimeException("Aufführung nicht gefunden: " + command.getAuffuehrungId()));

            // 2. Kunde laden oder idempotent anhand Email erstellen
            Kunde kunde = null;
            if (command.getKundeId() != null) {
                kunde = kundeRepository.findById(command.getKundeId()).orElse(null);
            }
            if (kunde == null && command.getKundeEmail() != null && !command.getKundeEmail().isBlank()) {
                kunde = kundeRepository.findByEmail(command.getKundeEmail());
            }
            if (kunde == null) {
                // Neuen Kunden erstellen – Email bevorzugt, ansonsten Minimalfallback
                kunde = new Kunde();
                kunde.setName(command.getKundeName());
                String email = command.getKundeEmail();
                if (email == null || email.isBlank()) {
                    email = (command.getKundeName() != null ? command.getKundeName() : "kunde") + "@example.com";
                }
                kunde.setEmail(email);
                kunde = kundeRepository.save(kunde);
                System.out.println(">>> Neuer Kunde erstellt: " + kunde.getId());
            }

            // 3. Reservierung erstellen
            Reservierung reservierung = new Reservierung();
            reservierung.setReservierungsnummer(generateReservierungsnummer());
            reservierung.setStartZeitstempel(new Date());
            reservierung.setKunde(kunde);
            reservierung.setAuffuehrung(auffuehrung);
            reservierung.setReservierungSitzplaetze(new ArrayList<>()); // Liste vorab initialisieren
            reservierung = reservierungRepository.save(reservierung);

            // 4. Sitzplätze reservieren
            System.out.println(">>> [ReservationConsumer] Anzahl Sitzplätze im Command: " + (command.getSitzplaetze() == null ? 0 : command.getSitzplaetze().size()));
            java.util.List<SitzplatzInfo> conflicts = new java.util.ArrayList<>();
            for (SitzplatzInfo info : command.getSitzplaetze()) {
                Sitzplatz sitzplatz = sitzplatzRepository.findById(info.getSitzplatzId())
                        .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden: " + info.getSitzplatzId()));

                // Prüfen, ob Sitzplatz bereits für DIESELBE Aufführung reserviert/gebucht ist
                boolean belegtGleicheAuff = false;
                if (sitzplatz.getReservierung() != null && sitzplatz.getReservierung().getAuffuehrung() != null) {
                    Long reservAuffId = sitzplatz.getReservierung().getAuffuehrung().getId();
                    if (reservAuffId != null && reservAuffId.equals(command.getAuffuehrungId())) {
                        belegtGleicheAuff = true;
                    }
                }
                if (!belegtGleicheAuff && sitzplatz.getBuchung() != null && sitzplatz.getBuchung().getAuffuehrung() != null) {
                    Long buchAuffId = sitzplatz.getBuchung().getAuffuehrung().getId();
                    if (buchAuffId != null && buchAuffId.equals(command.getAuffuehrungId())) {
                        belegtGleicheAuff = true;
                    }
                }
                if (belegtGleicheAuff) {
                    System.out.println(">>> [ReservationConsumer] Sitzplatz belegt für dieselbe Aufführung (Konflikt): ID=" + sitzplatz.getId());
                    conflicts.add(info);
                    continue; // nicht reservieren
                }

                // ReservierungSitzplatz Join-Entity erstellen
                ReservierungSitzplatz rs = new ReservierungSitzplatz();
                rs.setReservierung(reservierung);
                rs.setSitzplatz(sitzplatz);
                rs.setPreis(info.getPreis());
                rs = reservierungSitzplatzRepository.save(rs);
                // Bidirektional: der Reservierung ihre Liste direkt erweitern
                reservierung.getReservierungSitzplaetze().add(rs);
                
                // Sitzplatz als reserviert markieren
                sitzplatz.setReservierung(reservierung);
                sitzplatz.setFrei(false); // explizit als belegt kennzeichnen
                sitzplatzRepository.save(sitzplatz);
                System.out.println(">>> [ReservationConsumer] Sitzplatz reserviert: ID=" + sitzplatz.getId() + ", Reihe=" + info.getReiheNummer() + ", Platz=" + info.getPlatzNummer());
                // Zwischenstand speichern (reduziert Risiko leerer Liste bei Lazy/Cache)
                reservierung = reservierungRepository.save(reservierung);
            }

            // Finale Sicherung
            if (reservierung.getReservierungSitzplaetze().isEmpty()) {
                Long tmpId = reservierung.getId();
                reservierungRepository.delete(reservierung);
                System.out.println(">>> [ReservationConsumer] Reservierung verworfen (keine Sitzplätze): id=" + tmpId);
                // Sende Failure-Event mit Konflikt-Information
                ReservationEvent failed = new ReservationEvent(
                        null,
                        reservierung.getReservierungsnummer(),
                        auffuehrung.getId(),
                        kunde.getId(),
                        "FAILED_SEATS_OCCUPIED"
                );
                failed.setCorrelationId(command.getCorrelationId());
                eventProducer.sendReservationEvent(failed);
                return;
            }
            reservierung = reservierungRepository.save(reservierung);
            System.out.println(">>> [ReservationConsumer] Gesamt reservierte Sitzplätze für Reservierung " + reservierung.getId() + ": " + reservierung.getReservierungSitzplaetze().size());

            System.out.println(">>> Reservierung erfolgreich gespeichert: " + reservierung.getId());

            // 5. Event verschicken
                ReservationEvent event = new ReservationEvent(
                    reservierung.getId(),
                    reservierung.getReservierungsnummer(),
                    auffuehrung.getId(),
                    kunde.getId(),
                    conflicts.isEmpty() ? "CREATED" : "CREATED_PARTIAL"
                );
                event.setCorrelationId(command.getCorrelationId());
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

    /**
     * Behandelt das Löschen einer Reservierung
     */
    private void handleDeleteReservation(ReservationCommand command) {
        System.out.println(">>> [ReservationConsumer] Lösch-Command erhalten: " + command);

        try {
            Long reservierungId = command.getReservierungId();

            // 1. Reservierung laden
            Reservierung reservierung = reservierungRepository.findById(reservierungId)
                    .orElseThrow(() -> new RuntimeException("Reservierung nicht gefunden: " + reservierungId));

            // 2. Alle ReservierungSitzplatz-Verknüpfungen holen
            List<ReservierungSitzplatz> sitzplaetze = reservierungSitzplatzRepository.findByReservierung(reservierung);

            // 3. Sitzplatz-Referenzen auf null setzen (wichtig für Hibernate!)
            for (ReservierungSitzplatz rs : sitzplaetze) {
                Sitzplatz sitz = rs.getSitzplatz();
                if (sitz != null) {
                    sitz.setReservierung(null);
                    sitz.setFrei(true); // wieder freigeben
                    sitzplatzRepository.save(sitz);
                }
            }

            // 4. Join-Entities löschen
            reservierungSitzplatzRepository.deleteAll(sitzplaetze);

            // 5. Reservierung selbst löschen
            int reservierungsnummer = reservierung.getReservierungsnummer();
            Long auffuehrungId = reservierung.getAuffuehrung() != null ? reservierung.getAuffuehrung().getId() : null;
            Long kundeId = reservierung.getKunde() != null ? reservierung.getKunde().getId() : null;

            reservierungRepository.deleteById(reservierungId);

            System.out.println(">>> Reservierung erfolgreich gelöscht: " + reservierungId);

            // 6. Event verschicken
            ReservationEvent event = new ReservationEvent(
                    reservierungId,
                    reservierungsnummer,
                    auffuehrungId,
                    kundeId,
                    "DELETED"
            );
            eventProducer.sendReservationEvent(event);

        } catch (Exception e) {
            System.err.println(">>> [ReservationConsumer] Fehler beim Löschen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleQueryReservations(ReservationCommand command) {
        ReservationEvent event = new ReservationEvent();
        event.setCorrelationId(command.getCorrelationId());
        event.setStatus("OK");

        try {
            if (command.getKundeId() != null) {
                // Neue Fetch-Query für vollständige Sitzplatz-Daten
                java.util.List<kino.application.data.Reservierung> reservierungen = 
                        reservierungRepository.findWithSeatsByKundeId(command.getKundeId());
                System.out.println(">>> [ReservationConsumer-QUERY] Gefundene Reservierungen: " + reservierungen.size());
                for (kino.application.data.Reservierung r : reservierungen) {
                    int seats = r.getReservierungSitzplaetze() == null ? 0 : r.getReservierungSitzplaetze().size();
                    System.out.println(">>> [ReservationConsumer-QUERY] Reservierung=" + r.getId() + " Sitzplätze=" + seats);
                }
                // Filter: Reservierungen ohne Sitzplätze ausblenden (Altbestand / Fehlerfälle)
                java.util.List<kino.application.data.Reservierung> mitSitzen = reservierungen.stream()
                        .filter(r -> r.getReservierungSitzplaetze() != null && !r.getReservierungSitzplaetze().isEmpty())
                        .collect(java.util.stream.Collectors.toList());
                int entfernte = reservierungen.size() - mitSitzen.size();
                if (entfernte > 0) {
                    System.out.println(">>> [ReservationConsumer-QUERY] Filter: " + entfernte + " Reservierungen ohne Sitzplätze ausgeblendet");
                }
                // Mapping nur für Reservierungen mit Sitzplätzen
                java.util.List<kino.application.kafka.dto.ReservierungDTO> dtos = mitSitzen.stream()
                        .map(kino.application.kafka.dto.ReservierungDTO::new)
                        .collect(java.util.stream.Collectors.toList());
                event.setReservierungen(dtos);
            } else {
                event.setStatus("NOT_FOUND");
            }
        } catch (Exception e) {
            System.err.println(">>> Fehler bei Reservierungsabfrage: " + e.getMessage());
            e.printStackTrace();
            event.setStatus("ERROR");
        }

        eventProducer.sendReservationEvent(event);
        kino.application.reservation.ReservationUIEventBus.broadcast(event);
    }
}
