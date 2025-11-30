package kino.application.service;

import kino.application.data.*;
import kino.application.kafka.events.BookingCommand;
import kino.application.kafka.events.SitzplatzInfo;
import kino.application.kafka.producer.BookingCommandProducer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Fachservice für Buchungen.
 * Erzeugt Commands und schickt sie über Kafka weg.
 */
@Service
public class BuchungsService {

    private final BookingCommandProducer producer;
    private final SitzplatzRepository sitzplatzRepository;
    private final SitzreiheRepository sitzreiheRepository;
    private final KundeRepository kundeRepository;

    public BuchungsService(
            BookingCommandProducer producer,
            SitzplatzRepository sitzplatzRepository,
            SitzreiheRepository sitzreiheRepository,
            KundeRepository kundeRepository) {
        this.producer = producer;
        this.sitzplatzRepository = sitzplatzRepository;
        this.sitzreiheRepository = sitzreiheRepository;
        this.kundeRepository = kundeRepository;
    }

    /**
     * Bucht Plätze für einen Kunden.
     * 
     * @param auffuehrungId ID der Aufführung
     * @param kundeId ID des Kunden
     * @param sitzplatzIds Liste der zu buchenden Sitzplatz-IDs
     */
    public void buchePlaetze(Long auffuehrungId, Long kundeId, List<Long> sitzplatzIds) {
        // Kunde laden
        Kunde kunde = kundeRepository.findById(kundeId)
                .orElseThrow(() -> new RuntimeException("Kunde nicht gefunden: " + kundeId));

        // Sitzplätze laden und SitzplatzInfo-Objekte erstellen
        List<SitzplatzInfo> sitzplatzInfos = new ArrayList<>();
        double gesamtpreis = 0.0;
        
        for (Long sitzplatzId : sitzplatzIds) {
            Sitzplatz sitzplatz = sitzplatzRepository.findById(sitzplatzId)
                    .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden: " + sitzplatzId));
            
            Sitzreihe reihe = sitzplatz.getReihe();
            double preis = calculatePreis(reihe.getKategorie());
            gesamtpreis += preis;
            
            SitzplatzInfo info = new SitzplatzInfo(
                    sitzplatz.getId(),
                    reihe.getReihennummer(),
                    sitzplatz.getPlatznummer(),
                    preis
            );
            sitzplatzInfos.add(info);
        }

        // Command erstellen und senden
        BookingCommand command = new BookingCommand(
                auffuehrungId,
                kundeId,
                kunde.getName(),
                sitzplatzInfos,
                gesamtpreis
        );
        
        producer.sendBooking(command);
        System.out.println(">>> [BuchungsService] Buchung an Kafka gesendet für " + kunde.getName() + 
                         " (Gesamtpreis: " + gesamtpreis + " €)");
    }

    /**
     * Berechnet den Gesamtpreis für die gegebenen Sitzplatz-IDs,
     * ohne eine Buchung auszulösen.
     */
    public double berechneGesamtpreis(List<Long> sitzplatzIds) {
        double gesamtpreis = 0.0;
        for (Long sitzplatzId : sitzplatzIds) {
            Sitzplatz sitzplatz = sitzplatzRepository.findById(sitzplatzId)
                    .orElseThrow(() -> new RuntimeException("Sitzplatz nicht gefunden: " + sitzplatzId));
            Sitzreihe reihe = sitzplatz.getReihe();
            gesamtpreis += calculatePreis(reihe.getKategorie());
        }
        return gesamtpreis;
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
     * Test-Methode zum Senden einer Dummy-Buchung.
     */
    public void sendeTestBuchung() {
        List<Long> testSitzplaetze = List.of(3L, 4L);
        buchePlaetze(1L, 1L, testSitzplaetze);
    }
}
