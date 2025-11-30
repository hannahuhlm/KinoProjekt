package kino.application.kafka.events;

import java.util.List;

/**
 * Kommandodaten, die der Server an Kafka sendet,
 * wenn eine Reservierung ausgelöst wird.
 * Action kann sein: CREATE oder DELETE
 */
public class ReservationCommand {

    private String action; // "CREATE", "DELETE", oder "QUERY"
    private Long reservierungId; // Für DELETE
    private Long auffuehrungId;
    private Long kundeId;
    private String kundeName;
    private String kundeEmail;
    private List<SitzplatzInfo> sitzplaetze;
    private String correlationId; // Für QUERY

    public ReservationCommand() {
        // für JSON-Deserialization durch Kafka
    }

    // Konstruktor für CREATE
    public ReservationCommand(Long auffuehrungId, Long kundeId, String kundeName,
                              String kundeEmail,
                              List<SitzplatzInfo> sitzplaetze) {
        this.action = "CREATE";
        this.auffuehrungId = auffuehrungId;
        this.kundeId = kundeId;
        this.kundeName = kundeName;
        this.kundeEmail = kundeEmail;
        this.sitzplaetze = sitzplaetze;
    }

    // Konstruktor für DELETE
    public ReservationCommand(String action, Long reservierungId) {
        this.action = action;
        this.reservierungId = reservierungId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getReservierungId() {
        return reservierungId;
    }

    public void setReservierungId(Long reservierungId) {
        this.reservierungId = reservierungId;
    }

    public Long getAuffuehrungId() {
        return auffuehrungId;
    }

    public void setAuffuehrungId(Long auffuehrungId) {
        this.auffuehrungId = auffuehrungId;
    }

    public Long getKundeId() {
        return kundeId;
    }

    public void setKundeId(Long kundeId) {
        this.kundeId = kundeId;
    }

    public String getKundeName() {
        return kundeName;
    }

    public void setKundeName(String kundeName) {
        this.kundeName = kundeName;
    }

    public String getKundeEmail() {
        return kundeEmail;
    }

    public void setKundeEmail(String kundeEmail) {
        this.kundeEmail = kundeEmail;
    }

    public List<SitzplatzInfo> getSitzplaetze() {
        return sitzplaetze;
    }

    public void setSitzplaetze(List<SitzplatzInfo> sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String toString() {
        return "ReservationCommand{" +
                "action='" + action + '\'' +
                ", reservierungId=" + reservierungId +
                ", auffuehrungId=" + auffuehrungId +
                ", kundeId=" + kundeId +
                ", kundeName='" + kundeName + '\'' +
            ", kundeEmail='" + kundeEmail + '\'' +
                ", sitzplaetze=" + sitzplaetze +
                '}';
    }
}
