package kino.application.kafka;

/** Max S. Jonas C. 21.11.2025 00:20
 * Kommandodaten, die der Server an Kafka sendet,
 * wenn eine Reservierung ausgelöst wird.
 *
 * Später kann dieses Objekt in einem Listener in
 * eine Datenbank oder in das EMF-Modell übersetzt werden.
 */
public class ReservationCommand {

    private Long auffuehrungId;
    private String kundeName;
    private int sitzreihe;
    private int sitzplatz;

    public ReservationCommand() {
        // für JSON-Deserialization durch Kafka
    }

    public ReservationCommand(Long auffuehrungId, String kundeName, int sitzreihe, int sitzplatz) {
        this.auffuehrungId = auffuehrungId;
        this.kundeName = kundeName;
        this.sitzreihe = sitzreihe;
        this.sitzplatz = sitzplatz;
    }

    public Long getAuffuehrungId() {
        return auffuehrungId;
    }

    public void setAuffuehrungId(Long auffuehrungId) {
        this.auffuehrungId = auffuehrungId;
    }

    public String getKundeName() {
        return kundeName;
    }

    public void setKundeName(String kundeName) {
        this.kundeName = kundeName;
    }

    public int getSitzreihe() {
        return sitzreihe;
    }

    public void setSitzreihe(int sitzreihe) {
        this.sitzreihe = sitzreihe;
    }

    public int getSitzplatz() {
        return sitzplatz;
    }

    public void setSitzplatz(int sitzplatz) {
        this.sitzplatz = sitzplatz;
    }

    @Override
    public String toString() {
        return "ReservationCommand{" +
                "auffuehrungId=" + auffuehrungId +
                ", kundeName='" + kundeName + '\'' +
                ", sitzreihe=" + sitzreihe +
                ", sitzplatz=" + sitzplatz +
                '}';
    }
}