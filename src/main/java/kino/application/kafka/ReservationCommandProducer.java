package kino.application.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/** Max S. Jonas C. 21.11.2025 01:20
 * Verschickt Reservierungs-Kommandos an Kafka.
 */
@Service
public class ReservationCommandProducer {

    private final KafkaTemplate<String, ReservationCommand> kafkaTemplate;
    private final String topic;

    public ReservationCommandProducer(
            KafkaTemplate<String, ReservationCommand> kafkaTemplate,
            @Value("${kino.kafka.topic.reservations}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendReservation(ReservationCommand command) {
        String key = command.getAuffuehrungId() != null
                ? command.getAuffuehrungId().toString()
                : "unknown";

        kafkaTemplate.send(topic, key, command);
    }
}