package kino.application.kafka.events;

import java.util.Date;

/**
 * Event für erfolgreiche Buchungen.
 * 
 * Wird vom Listener-Service zurück an Kafka geschickt,
 * um andere Services über die erfolgreiche Buchung zu informieren.
 */
public class BookingEvent {
    
    private Long buchungId;
    private String buchungsnummer;
    private Long auffuehrungId;
    private Long kundeId;
    private double gesamtpreis;
    private Date timestamp;
    private String status; // z.B. "COMPLETED", "FAILED"
    
    public BookingEvent() {
        // Für JSON-Deserialisierung
    }
    
    public BookingEvent(Long buchungId, String buchungsnummer, Long auffuehrungId, 
                       Long kundeId, double gesamtpreis, String status) {
        this.buchungId = buchungId;
        this.buchungsnummer = buchungsnummer;
        this.auffuehrungId = auffuehrungId;
        this.kundeId = kundeId;
        this.gesamtpreis = gesamtpreis;
        this.timestamp = new Date();
        this.status = status;
    }
    
    // Getter und Setter
    
    public Long getBuchungId() {
        return buchungId;
    }
    
    public void setBuchungId(Long buchungId) {
        this.buchungId = buchungId;
    }
    
    public String getBuchungsnummer() {
        return buchungsnummer;
    }
    
    public void setBuchungsnummer(String buchungsnummer) {
        this.buchungsnummer = buchungsnummer;
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
    
    public double getGesamtpreis() {
        return gesamtpreis;
    }
    
    public void setGesamtpreis(double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "BookingEvent{" +
                "buchungId=" + buchungId +
                ", buchungsnummer='" + buchungsnummer + '\'' +
                ", auffuehrungId=" + auffuehrungId +
                ", kundeId=" + kundeId +
                ", gesamtpreis=" + gesamtpreis +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
