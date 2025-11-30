package kino.application.kafka.producer;

import kino.application.kafka.events.ReservationCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Producer für Reservierungs-Kommandos.
 * Verschickt Reservierungsanfragen an Kafka.
 */
@Service
public class ReservationCommandProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public ReservationCommandProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kino.kafka.topic.reservations}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendReservation(ReservationCommand command) {
        String key = command.getAction().equals("DELETE") && command.getReservierungId() != null
                ? command.getReservierungId().toString()
                : (command.getAuffuehrungId() != null ? command.getAuffuehrungId().toString() : "unknown");

        System.out.println(">>> [ReservationProducer] Sende Command an Kafka: " + command);
        kafkaTemplate.send(topic, key, command);
    }
}
