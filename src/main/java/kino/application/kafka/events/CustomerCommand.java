package kino.application.kafka.events;

import java.time.Instant;

public class CustomerCommand {
    public enum Action { CREATE, QUERY }

    private Action action;
    private Instant issuedAt = Instant.now();

    private String name;
    private String email;
    private String correlationId;

    public CustomerCommand() {}

    public CustomerCommand(Action action, String name, String email) {
        this.action = action;
        this.name = name;
        this.email = email;
    }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }

    public Instant getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Instant issuedAt) { this.issuedAt = issuedAt; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }

    @Override
    public String toString() {
        return "CustomerCommand{" +
            "action=" + action +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", correlationId='" + correlationId + '\'' +
            '}';
    }
}
