package kino.application.kafka.events;

import java.util.List;

/**
 * Kommandodaten, die der Server an Kafka sendet,
 * wenn eine Reservierung ausgelöst wird.
 */
public class ReservationCommand {

    private Long auffuehrungId;
    private Long kundeId;
    private String kundeName;
    private List<SitzplatzInfo> sitzplaetze;

    public ReservationCommand() {
        // für JSON-Deserialization durch Kafka
    }

    public ReservationCommand(Long auffuehrungId, Long kundeId, String kundeName, 
                             List<SitzplatzInfo> sitzplaetze) {
        this.auffuehrungId = auffuehrungId;
        this.kundeId = kundeId;
        this.kundeName = kundeName;
        this.sitzplaetze = sitzplaetze;
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

    public List<SitzplatzInfo> getSitzplaetze() {
        return sitzplaetze;
    }

    public void setSitzplaetze(List<SitzplatzInfo> sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    @Override
    public String toString() {
        return "ReservationCommand{" +
                "auffuehrungId=" + auffuehrungId +
                ", kundeId=" + kundeId +
                ", kundeName='" + kundeName + '\'' +
                ", sitzplaetze=" + sitzplaetze +
                '}';
    }
}
