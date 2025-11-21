package kino.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * Join-Entity zwischen Buchung und Sitzplatz.
 *
 * Jede Instanz entspricht einem Eintrag in der Zuordnungstabelle:
 * "Buchung X enthält Sitzplatz Y".
 */
@Entity
public class BuchungSitzplatz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Die zugehörige Buchung.
     *
     * In der Datenbank: Spalte BUCHUNG_ID (Fremdschlüssel auf BUCHUNG.ID)
     */
    @ManyToOne
    @JoinColumn(name = "buchung_id", nullable = false)
    private Buchung buchung;

    /**
     * Der zugehörige Sitzplatz.
     *
     * In der Datenbank: Spalte SITZPLATZ_ID (Fremdschlüssel auf SITZPLATZ.ID)
     */
    @ManyToOne
    @JoinColumn(name = "sitzplatz_id", nullable = false)
    private Sitzplatz sitzplatz;

    public BuchungSitzplatz() {
    }

    // === Getter/Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;
    }

    public Sitzplatz getSitzplatz() {
        return sitzplatz;
    }

    public void setSitzplatz(Sitzplatz sitzplatz) {
        this.sitzplatz = sitzplatz;
    }
}
