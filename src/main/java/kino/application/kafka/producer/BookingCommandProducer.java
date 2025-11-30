package kino.application.kafka.producer;

import kino.application.kafka.events.BookingCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Producer für Buchungs-Kommandos.
 * Verschickt Buchungsanfragen an Kafka.
 */
@Service
public class BookingCommandProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public BookingCommandProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kino.kafka.topic.bookings}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendBooking(BookingCommand command) {
        String key = command.getAuffuehrungId() != null
                ? command.getAuffuehrungId().toString()
                : "unknown";

        System.out.println(">>> [BookingProducer] Sende Buchung an Kafka: " + command);
        kafkaTemplate.send(topic, key, command);
    }
}
