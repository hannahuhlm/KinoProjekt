package kino.application.service;

import kino.application.kafka.ReservationCommand;

import kino.application.kafka.ReservationCommandProducer;

import org.springframework.stereotype.Service;

/**

 * Fachservice für Reservierungen.

 * Erzeugt Commands und schickt sie über Kafka weg.

 */

@Service

public class ReservierungsService {

    private final ReservationCommandProducer producer;

    public ReservierungsService(ReservationCommandProducer producer) {

        this.producer = producer;

    }

    /**

     * Noch sehr einfache Variante:

     * Schickt eine Reservierung als Command an Kafka.

     */

    public void reservierePlatz(Long auffuehrungId,

                                String kundeName,

                                int sitzreihe,

                                int sitzplatz) {

        ReservationCommand command =

                new ReservationCommand(auffuehrungId, kundeName, sitzreihe, sitzplatz);

        producer.sendReservation(command);

    }

    /**

     * Kleine Hilfsmethode zum Testen – schickt Dummy-Daten.

     */

    public void sendeTestReservierung() {

        reservierePlatz(1L, "Testkunde Kafka", 5, 12);

    }

}

