package kino.application.kafka.producer;

import kino.application.kafka.events.AggregationCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AggregationCommandProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kino.kafka.topic.aggregation}")
    private String topic;

    public AggregationCommandProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(AggregationCommand cmd) {
        kafkaTemplate.send(topic, cmd);
    }
}
