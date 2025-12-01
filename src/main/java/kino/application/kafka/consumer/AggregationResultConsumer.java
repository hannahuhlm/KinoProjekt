package kino.application.kafka.consumer;

import kino.application.aggregation.AggregationUIEventBus;
import kino.application.kafka.events.AggregationResultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AggregationResultConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationResultConsumer.class);

    @KafkaListener(topics = "${kino.kafka.topic.aggregation-events}", groupId = "kino-aggregation-ui")
    public void onAggregationResult(AggregationResultEvent ev) {
        LOGGER.info("📨 AggregationResultEvent empfangen: {}", ev);
        AggregationUIEventBus.broadcast(ev);
    }
}
