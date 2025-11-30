package kino.application.kafka.events;

import java.time.LocalDate;

public class AggregationCommand {
    private LocalDate day; // day to aggregate

    public AggregationCommand() {}
    public AggregationCommand(LocalDate day) { this.day = day; }

    public LocalDate getDay() { return day; }
    public void setDay(LocalDate day) { this.day = day; }
}
