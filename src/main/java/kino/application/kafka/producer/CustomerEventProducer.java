package kino.application.kafka.producer;

import kino.application.kafka.events.CustomerEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public CustomerEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                                 @Value("${kino.kafka.topic.customer-events}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(CustomerEvent event) {
        String key = event.getEmail() != null ? event.getEmail() : "unknown";
        kafkaTemplate.send(topic, key, event);
    }
}
