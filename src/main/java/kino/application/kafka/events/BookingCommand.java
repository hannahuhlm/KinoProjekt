package kino.application.kafka.events;

import java.util.List;

/**
 * Event für Buchungs-Kommandos über Kafka.
 * 
 * Wird vom Backend an Kafka geschickt, wenn eine Buchung durchgeführt wird.
 */
public class BookingCommand {
    
    private Long auffuehrungId;
    private Long kundeId;
    private String kundeName;
    private List<SitzplatzInfo> sitzplaetze;
    private double gesamtpreis;
    
    public BookingCommand() {
        // Für JSON-Deserialisierung
    }
    
    public BookingCommand(Long auffuehrungId, Long kundeId, String kundeName, 
                         List<SitzplatzInfo> sitzplaetze, double gesamtpreis) {
        this.auffuehrungId = auffuehrungId;
        this.kundeId = kundeId;
        this.kundeName = kundeName;
        this.sitzplaetze = sitzplaetze;
        this.gesamtpreis = gesamtpreis;
    }
    
    // Getter und Setter
    
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
    
    public double getGesamtpreis() {
        return gesamtpreis;
    }
    
    public void setGesamtpreis(double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }
    
    @Override
    public String toString() {
        return "BookingCommand{" +
                "auffuehrungId=" + auffuehrungId +
                ", kundeId=" + kundeId +
                ", kundeName='" + kundeName + '\'' +
                ", sitzplaetze=" + sitzplaetze +
                ", gesamtpreis=" + gesamtpreis +
                '}';
    }
}
