package kino.application.kafka.events;

import java.time.LocalDate;

public class AggregationCommand {
    private LocalDate day; // day to aggregate
    private String correlationId;

    public AggregationCommand() {}
    public AggregationCommand(LocalDate day) { this.day = day; }
    public AggregationCommand(LocalDate day, String correlationId) { this.day = day; this.correlationId = correlationId; }

    public LocalDate getDay() { return day; }
    public void setDay(LocalDate day) { this.day = day; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}
