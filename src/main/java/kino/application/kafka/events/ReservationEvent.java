package kino.application.kafka.events;

import java.util.Date;

/**
 * Event für erfolgreiche Reservierungen.
 * 
 * Wird vom Listener-Service zurück an Kafka geschickt,
 * um andere Services über die erfolgreiche Reservierung zu informieren.
 */
public class ReservationEvent {
    
    private Long reservierungId;
    private int reservierungsnummer;
    private Long auffuehrungId;
    private Long kundeId;
    private Date timestamp;
    private String status; // z.B. "CREATED", "CANCELLED"
    
    public ReservationEvent() {
        // Für JSON-Deserialisierung
    }
    
    public ReservationEvent(Long reservierungId, int reservierungsnummer, 
                           Long auffuehrungId, Long kundeId, String status) {
        this.reservierungId = reservierungId;
        this.reservierungsnummer = reservierungsnummer;
        this.auffuehrungId = auffuehrungId;
        this.kundeId = kundeId;
        this.timestamp = new Date();
        this.status = status;
    }
    
    // Getter und Setter
    
    public Long getReservierungId() {
        return reservierungId;
    }
    
    public void setReservierungId(Long reservierungId) {
        this.reservierungId = reservierungId;
    }
    
    public int getReservierungsnummer() {
        return reservierungsnummer;
    }
    
    public void setReservierungsnummer(int reservierungsnummer) {
        this.reservierungsnummer = reservierungsnummer;
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
        return "ReservationEvent{" +
                "reservierungId=" + reservierungId +
                ", reservierungsnummer=" + reservierungsnummer +
                ", auffuehrungId=" + auffuehrungId +
                ", kundeId=" + kundeId +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
