package kino.application.service;

import kino.application.data.*;
import kino.application.kafka.events.ReservationCommand;
import kino.application.kafka.events.SitzplatzInfo;
import kino.application.kafka.producer.ReservationCommandProducer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Fachservice für Reservierungen.
 * Erzeugt Commands und schickt sie über Kafka weg.
 */
@Service
public class ReservierungsService {

    private final ReservationCommandProducer producer;
    private final SitzplatzRepository sitzplatzRepository;
    private final SitzreiheRepository sitzreiheRepository;

    public ReservierungsService(
            ReservationCommandProducer producer,
            SitzplatzRepository sitzplatzRepository,
            SitzreiheRepository sitzreiheRepository) {
        this.producer = producer;
        this.sitzplatzRepository = sitzplatzRepository;
        this.sitzreiheRepository = sitzreiheRepository;
    }

    /**
     * Reserviert Plätze für einen Kunden.
     * 
     * @param auffuehrungId ID der Aufführung
     * @param kundeId ID des Kunden (optional, kann null sein für neue Kunden)
     * @param kundeName Name des Kunden
     * @param sitzplatzIds Liste der zu reservierenden Sitzplatz-IDs
     */
    public void reservierePlaetze(Long auffuehrungId, Long kundeId, String kundeName, List<Long> sitzplatzIds) {
        // Sitzplätze laden und SitzplatzInfo-Objekte erstellen
        List<SitzplatzInfo> sitzplatzInfos = new ArrayList<>();
        
        for (Long sitzplatzId : sitzplatzIds) {
            Sitzplatz sitzplatz = sitzplatzRepository.findById(sitzplatzId)
                    .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden: " + sitzplatzId));
            
            Sitzreihe reihe = sitzplatz.getReihe();
            double preis = calculatePreis(reihe.getKategorie());
            
            SitzplatzInfo info = new SitzplatzInfo(
                    sitzplatz.getId(),
                    reihe.getReihennummer(),
                    sitzplatz.getPlatznummer(),
                    preis
            );
            sitzplatzInfos.add(info);
        }

        // Command erstellen und senden
        ReservationCommand command = new ReservationCommand(
                auffuehrungId,
                kundeId,
                kundeName,
                sitzplatzInfos
        );
        
        producer.sendReservation(command);
        System.out.println(">>> [ReservierungsService] Reservierung an Kafka gesendet für " + kundeName);
    }

    /**
     * Berechnet den Preis basierend auf der Kategorie.
     */
    private double calculatePreis(SitzreihenKategorie kategorie) {
        switch (kategorie) {
            case LOGE_MIT_SERVICE:
                return 25.0;
            case LOGE:
                return 18.0;
            case PARKETT:
            default:
                return 12.0;
        }
    }

    /**
     * Löscht eine Reservierung über Kafka.
     * 
     * @param reservierungId ID der zu löschenden Reservierung
     */
    public void loescheReservierung(Long reservierungId) {
        ReservationCommand command = new ReservationCommand("DELETE", reservierungId);
        
        producer.sendReservation(command);
        System.out.println(">>> [ReservierungsService] Lösch-Command an Kafka gesendet für Reservierung: " + reservierungId);
    }

    /**
     * Kleine Hilfsmethode zum Testen – schickt Dummy-Daten.
     */
    public void sendeTestReservierung() {
        List<Long> testSitzplaetze = List.of(1L, 2L);
        reservierePlaetze(1L, null, "Testkunde Kafka", testSitzplaetze);
    }
}

