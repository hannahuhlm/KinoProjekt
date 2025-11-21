package kino.application.kafka;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

/** Max S. Jonas C. 21.11.2025 01:29

 * Simulierte Datenbank-Komponente:

 * Empfängt Reservierungs-Kommandos von Kafka.

 *

 * Hier würden später DB-Zugriffe oder EMF-Operationen ausgeführt.

 */

@Service

public class ReservationCommandListener {

    @KafkaListener(

            topics = "${kino.kafka.topic.reservations}",

            groupId = "kino-reservation-worker"

    )

    public void handleReservationCommand(ReservationCommand command) {

        // Aktuell nur Logging, um zu sehen, dass alles klappt

        System.out.println(">>> [Listener] Reservierung erhalten: " + command);

        // TODO: Später hier:

        //  - EMF-Modell aktualisieren (Aufführung finden, reservieren, etc.)

        //  - oder JPA-Entity in PostgreSQL schreiben

    }

}

