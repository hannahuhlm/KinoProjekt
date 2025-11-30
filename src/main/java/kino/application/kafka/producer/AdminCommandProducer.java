package kino.application.kafka.producer;

import kino.application.kafka.events.AdminCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminCommandProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public AdminCommandProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kino.kafka.topic.admin}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(AdminCommand cmd) {
        String key = cmd.getEntity() + ":" + (cmd.getAction() != null ? cmd.getAction().name() : "");
        kafkaTemplate.send(topic, key, cmd);
    }
}
