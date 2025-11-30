package kino.application.kafka.events;

public class CustomerEvent {
    public enum Action { CREATE, QUERY }
    public enum Status { SUCCESS, FAILURE, NOT_FOUND }

    private Action action;
    private Status status;
    private Long kundeId;
    private String email;
    private String message;
    private String correlationId;

    public CustomerEvent() {}

    public CustomerEvent(Action action, Status status) {
        this.action = action;
        this.status = status;
    }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Long getKundeId() { return kundeId; }
    public void setKundeId(Long kundeId) { this.kundeId = kundeId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}
