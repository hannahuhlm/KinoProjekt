package kino.application.kafka.events;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AggregationResultEvent {
    public enum Operation { INSERT, DELETE }
    public enum Status { SUCCESS, FAILURE }

    private LocalDate day;
    private String correlationId;
    private Operation operation;
    private Status status;
    private int count;
    private String message;
    private LocalDateTime timestamp;

    public AggregationResultEvent() {}

    public AggregationResultEvent(LocalDate day, Operation operation, Status status, int count, String message, LocalDateTime timestamp) {
        this.day = day;
        this.operation = operation;
        this.status = status;
        this.count = count;
        this.message = message;
        this.timestamp = timestamp;
    }

    public AggregationResultEvent(LocalDate day, String correlationId, Operation operation, Status status, int count, String message, LocalDateTime timestamp) {
        this(day, operation, status, count, message, timestamp);
        this.correlationId = correlationId;
    }

    public LocalDate getDay() { return day; }
    public void setDay(LocalDate day) { this.day = day; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }

    public Operation getOperation() { return operation; }
    public void setOperation(Operation operation) { this.operation = operation; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "AggregationResultEvent{" +
                "day=" + day +
            ", correlationId='" + correlationId + '\'' +
                ", operation=" + operation +
                ", status=" + status +
                ", count=" + count +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
