package kino.application.kafka.producer;

import kino.application.kafka.events.BookingEvent;
import kino.application.kafka.events.ReservationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Producer für Events (Benachrichtigungen über abgeschlossene Vorgänge).
 * Verschickt Events an Kafka, die andere Services konsumieren können.
 */
@Service
public class EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private final String reservationEventTopic;
    private final String bookingEventTopic;

    public EventProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kino.kafka.topic.reservation-events}") String reservationEventTopic,
            @Value("${kino.kafka.topic.booking-events}") String bookingEventTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.reservationEventTopic = reservationEventTopic;
        this.bookingEventTopic = bookingEventTopic;
    }

    /**
     * Verschickt ein Reservierungs-Event.
     */
    public void sendReservationEvent(ReservationEvent event) {
        String key = event.getReservierungId() != null
                ? event.getReservierungId().toString()
                : "unknown";

        System.out.println(">>> [EventProducer] Sende Reservierungs-Event: " + event);
        kafkaTemplate.send(reservationEventTopic, key, event);
    }

    /**
     * Verschickt ein Buchungs-Event.
     */
    public void sendBookingEvent(BookingEvent event) {
        String key = event.getBuchungId() != null
                ? event.getBuchungId().toString()
                : "unknown";

        System.out.println(">>> [EventProducer] Sende Buchungs-Event: " + event);
        kafkaTemplate.send(bookingEventTopic, key, event);
    }
}
