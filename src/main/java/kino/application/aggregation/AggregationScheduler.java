package kino.application.aggregation;

import kino.application.kafka.events.AggregationCommand;
import kino.application.kafka.producer.AggregationCommandProducer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@EnableScheduling
@Component
public class AggregationScheduler {
    private final AggregationCommandProducer producer;

    public AggregationScheduler(AggregationCommandProducer producer) {
        this.producer = producer;
    }

    // Run daily at 02:00
    @Scheduled(cron = "0 0 2 * * *")
    public void scheduleDailyAggregation() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        producer.send(new AggregationCommand(yesterday));
    }
}
