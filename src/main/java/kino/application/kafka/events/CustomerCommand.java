package kino.application.kafka.events;

import java.time.Instant;

public class CustomerCommand {
    public enum Action { CREATE }

    private Action action;
    private Instant issuedAt = Instant.now();

    private String name;
    private String email;

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

    @Override
    public String toString() {
        return "CustomerCommand{" +
                "action=" + action +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
