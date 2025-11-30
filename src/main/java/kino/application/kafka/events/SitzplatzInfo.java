package kino.application.kafka.events;

/**
 * Hilfsobjekt für Sitzplatz-Informationen in Kafka-Events.
 */
public class SitzplatzInfo {
    
    private Long sitzplatzId;
    private int reiheNummer;
    private int platzNummer;
    private double preis;
    
    public SitzplatzInfo() {
        // Für JSON-Deserialisierung
    }
    
    public SitzplatzInfo(Long sitzplatzId, int reiheNummer, int platzNummer, double preis) {
        this.sitzplatzId = sitzplatzId;
        this.reiheNummer = reiheNummer;
        this.platzNummer = platzNummer;
        this.preis = preis;
    }
    
    // Getter und Setter
    
    public Long getSitzplatzId() {
        return sitzplatzId;
    }
    
    public void setSitzplatzId(Long sitzplatzId) {
        this.sitzplatzId = sitzplatzId;
    }
    
    public int getReiheNummer() {
        return reiheNummer;
    }
    
    public void setReiheNummer(int reiheNummer) {
        this.reiheNummer = reiheNummer;
    }
    
    public int getPlatzNummer() {
        return platzNummer;
    }
    
    public void setPlatzNummer(int platzNummer) {
        this.platzNummer = platzNummer;
    }
    
    public double getPreis() {
        return preis;
    }
    
    public void setPreis(double preis) {
        this.preis = preis;
    }
    
    @Override
    public String toString() {
        return "SitzplatzInfo{" +
                "sitzplatzId=" + sitzplatzId +
                ", reiheNummer=" + reiheNummer +
                ", platzNummer=" + platzNummer +
                ", preis=" + preis +
                '}';
    }
}
