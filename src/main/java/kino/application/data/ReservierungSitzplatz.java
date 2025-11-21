package kino.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * Join-Entity zwischen Reservierung und Sitzplatz.
 *
 * Jede Instanz repräsentiert die Zuordnung "Reservierung X enthält Sitzplatz Y".
 * Dadurch können wir die n:m-Beziehung aus dem EMF-Modell
 * (EList<Sitzplatz> plaetze in Reservierung) relational abbilden.
 */
@Entity
public class ReservierungSitzplatz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Die zugehörige Reservierung.
     *
     * In der Datenbank entsteht daraus eine Fremdschlüssel-Spalte
     * RESERVIERUNG_ID in der Tabelle RESERVIERUNG_SITZPLATZ.
     */
    @ManyToOne
    @JoinColumn(name = "reservierung_id", nullable = false)
    private Reservierung reservierung;

    /**
     * Der zugehörige Sitzplatz.
     *
     * In der Datenbank wird das als Fremdschlüssel-Spalte
     * SITZPLATZ_ID in derselben Tabelle modelliert.
     */
    @ManyToOne
    @JoinColumn(name = "sitzplatz_id", nullable = false)
    private Sitzplatz sitzplatz;

    public ReservierungSitzplatz() {
    }

    // === Getter und Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservierung getReservierung() {
        return reservierung;
    }

    public void setReservierung(Reservierung reservierung) {
        this.reservierung = reservierung;
    }

    public Sitzplatz getSitzplatz() {
        return sitzplatz;
    }

    public void setSitzplatz(Sitzplatz sitzplatz) {
        this.sitzplatz = sitzplatz;
    }
}
