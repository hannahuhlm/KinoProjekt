package kino.application.kafka.consumer;

import kino.application.aggregation.AggregationService;
import kino.application.kafka.events.AggregationCommand;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AggregationCommandConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationCommandConsumer.class);
    
    private final AggregationService aggregationService;

    public AggregationCommandConsumer(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @KafkaListener(topics = "${kino.kafka.topic.aggregation}", groupId = "kino-aggregation-worker")
    public void onAggregation(AggregationCommand cmd) {
        LOGGER.info("📨 Kafka: AggregationCommand empfangen für Tag: {}, corr={} ", cmd.getDay(), cmd.getCorrelationId());
        try {
            aggregationService.aggregateDay(cmd.getDay(), cmd.getCorrelationId());
            LOGGER.info("✅ Aggregation erfolgreich verarbeitet für Tag: {}, corr={}", cmd.getDay(), cmd.getCorrelationId());
        } catch (Exception ex) {
            LOGGER.error("❌ Fehler bei Aggregation für Tag {}, corr={}: {}", cmd.getDay(), cmd.getCorrelationId(), ex.getMessage(), ex);
            throw ex;
        }
    }
}
