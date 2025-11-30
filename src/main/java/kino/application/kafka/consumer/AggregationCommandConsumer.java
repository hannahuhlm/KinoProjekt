package kino.application.kafka.consumer;

import kino.application.aggregation.AggregationService;
import kino.application.kafka.events.AggregationCommand;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AggregationCommandConsumer {
    private final AggregationService aggregationService;

    public AggregationCommandConsumer(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @KafkaListener(topics = "${kino.kafka.topic.aggregation}", groupId = "kino-aggregation-worker")
    public void onAggregation(AggregationCommand cmd) {
        aggregationService.aggregateDay(cmd.getDay());
    }
}
