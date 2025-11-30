package kino.application.kafka.producer;

import kino.application.kafka.events.AdminEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public AdminEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                              @Value("${kino.kafka.topic.admin-events}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(AdminEvent event) {
        String key = event.getEntity() + ":" + event.getAction();
        kafkaTemplate.send(topic, key, event);
    }
}
