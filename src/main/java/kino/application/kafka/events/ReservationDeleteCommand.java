package kino.application.kafka.events;

/**
 * Kommando zum Löschen einer Reservierung über Kafka.
 */
public class ReservationDeleteCommand {

    private Long reservierungId;

    public ReservationDeleteCommand() {
        // für JSON-Deserialization
    }

    public ReservationDeleteCommand(Long reservierungId) {
        this.reservierungId = reservierungId;
    }

    public Long getReservierungId() {
        return reservierungId;
    }

    public void setReservierungId(Long reservierungId) {
        this.reservierungId = reservierungId;
    }

    @Override
    public String toString() {
        return "ReservationDeleteCommand{" +
                "reservierungId=" + reservierungId +
                '}';
    }
}
