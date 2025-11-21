package kino.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

/**
 * Entity-Klasse für einen Kinosaal.
 *
 * Abgeleitet aus dem EMF-Modell:
 * - Attribute: name, freigegeben
 * - Referenzen: Listen von Sitzreihen und Aufführungen.
 *
 * Persistenzsicht:
 * - Entspricht einer Tabelle KINOSAAL in der Datenbank.
 * - Jede Instanz stellt einen realen Saal im Kino dar (z.B. "Saal 1").
 */
@Entity
public class Kinosaal {

    /**
     * Primärschlüssel des Saals.
     *
     * Wird von der Datenbank automatisch generiert
     * (IDENTITY-Strategie = Auto-Inkrement in vielen DBs).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Anzeigename des Saals.
     * Beispiele: "Saal 1", "IMAX", "Studio".
     */
    private String name;

    /**
     * Flag, ob der Saal aktuell freigegeben/verwendbar ist.
     *
     * Entspricht dem EMF-Attribut "freigegeben : boolean" mit
     * Defaultwert false.
     *
     * Fachlich könntest du hier z.B. sperren, wenn der Saal wegen
     * Renovierung oder Defekt nicht genutzt werden darf.
     */
    private boolean freigegeben = false;

    /**
     * Alle Sitzreihen, die zu diesem Saal gehören.
     *
     * Beziehung:
     * - Ein Kinosaal besitzt mehrere Sitzreihen (1:n).
     * - In der Entity Sitzreihe gibt es dazu ein Feld "kinosaal"
     *   mit @ManyToOne.
     *
     * mappedBy = "kinosaal" bedeutet:
     * - Das Feld "kinosaal" in Sitzreihe ist die besitzende Seite.
     * - Die Fremdschlüssel-Spalte (KINOSAAL_ID) liegt in der
     *   SITZREIHE-Tabelle.
     * - Hier in Kinosaal wird nur die umgekehrte Sicht gepflegt;
     *   JPA erzeugt dafür keine zusätzliche Join-Tabelle.
     */
    @OneToMany(mappedBy = "saal")
    private List<Sitzreihe> reihen;

    /**
     * Alle Aufführungen, die in diesem Saal stattfinden.
     *
     * Beziehung:
     * - Ein Kinosaal kann viele Aufführungen haben (1:n).
     * - In der Entity Auffuehrung gibt es ein Feld "saal"
     *   mit @ManyToOne und @JoinColumn(name = "saal_id").
     *
     * mappedBy = "saal" muss genau dem Feldnamen in Auffuehrung
     * entsprechen, damit JPA die Beziehung korrekt verknüpfen kann.
     */
    @OneToMany(mappedBy = "saal")
    private List<Auffuehrung> auffuehrungen;

    /**
     * Leerer Konstruktor, den JPA/Hibernate für das Anlegen neuer
     * Instanzen aus der Datenbank benötigt.
     */
    public Kinosaal() {
    }

    // === Getter und Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFreigegeben() {
        return freigegeben;
    }

    public void setFreigegeben(boolean freigegeben) {
        this.freigegeben = freigegeben;
    }

    public List<Sitzreihe> getReihen() {
        return reihen;
    }

    public void setReihen(List<Sitzreihe> reihen) {
        this.reihen = reihen;
    }

    public List<Auffuehrung> getAuffuehrungen() {
        return auffuehrungen;
    }

    public void setAuffuehrungen(List<Auffuehrung> auffuehrungen) {
        this.auffuehrungen = auffuehrungen;
    }

    /*
     * Hinweis zu den EMF-Operationen freigeben()/istFreigegeben():
     * -----------------------------------------------------------
     * Die Logik aus dem EMF-Modell kannst du einfach als normale
     * Java-Methoden hier implementieren, z.B.:
     *
     * public void freigeben() {
     *     this.freigegeben = true;
     * }
     *
     * public boolean istFreigegeben() {
     *     return this.freigegeben;
     * }
     *
     * Diese Methoden beeinflussen nur den Wert des Feldes
     * "freigegeben"; in der Datenbank gespeichert wird weiterhin
     * nur das boolean-Feld selbst.
     */
}
