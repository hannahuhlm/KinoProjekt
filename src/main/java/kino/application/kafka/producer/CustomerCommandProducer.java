package kino.application.kafka.producer;

import kino.application.kafka.events.CustomerCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kino.kafka.topic.customer}")
    private String topic;

    public CustomerCommandProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(CustomerCommand cmd) {
        kafkaTemplate.send(topic, cmd);
    }
}
