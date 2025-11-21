package kino.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import java.util.Date;
import java.util.List;

/**
 * Entity-Klasse für eine Aufführung (Vorstellung) eines Films.
 *
 * Fachlich:
 * - Eine Aufführung ist die Projektion eines bestimmten Films
 *   zu einem bestimmten Zeitpunkt in einem bestimmten Kinosaal.
 * - Zu einer Aufführung gibt es mehrere Reservierungen und Buchungen.
 *
 * Abgeleitet aus dem EMF-Modell:
 * - Attribute:
 *      startzeitpunkt      : Date
 *      aktuelleEinnahmen   : double
 * - Referenzen:
 *      saal                : Kinosaal       (required)
 *      film                : Film           (required)
 *      reservierungen      : EList<Reservierung>
 *      buchungen           : EList<Buchung>
 */
@Entity
public class Auffuehrung {

    /**
     * Technischer Primärschlüssel der Aufführung.
     *
     * Wird von der Datenbank automatisch generiert
     * (z.B. AUTO_INCREMENT / IDENTITY).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Zeitpunkt, zu dem die Aufführung startet.
     *
     * Entspricht dem EMF-Attribut "startzeitpunkt : Date".
     * @Temporal(TIMESTAMP) sagt JPA, dass Datum + Uhrzeit gespeichert
     * werden sollen.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date startzeitpunkt;

    /**
     * Aktuelle Einnahmen dieser Aufführung.
     *
     * Entspricht dem EMF-Attribut "aktuelleEinnahmen : double".
     * Dieser Wert kann z.B. aus den Buchungen berechnet und bei
     * Änderungen aktualisiert werden.
     */
    private double aktuelleEinnahmen;

    /**
     * Kinosaal, in dem die Aufführung stattfindet.
     *
     * Beziehung:
     * - Viele Aufführungen können im selben Saal stattfinden (n:1).
     * - In der Entity Kinosaal gibt es dazu eine Liste
     *   "auffuehrungen" mit @OneToMany(mappedBy = "saal").
     *
     * optional = false bedeutet:
     * - In der Datenbank ist die Spalte SAAL_ID NOT NULL.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "saal_id")
    private Kinosaal saal;

    /**
     * Film, der in dieser Aufführung gezeigt wird.
     *
     * Beziehung:
     * - Ein Film kann viele Aufführungen haben (1:n).
     * - In der Entity Film gibt es dazu eine Liste
     *   "auffuehrungen" mit @OneToMany(mappedBy = "film").
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id")
    private Film film;

    /**
     * Alle Reservierungen zu dieser Aufführung.
     *
     * Beziehung:
     * - Eine Aufführung kann viele Reservierungen haben (1:n).
     * - In der Entity Reservierung gibt es ein Feld "auffuehrung"
     *   mit @ManyToOne und @JoinColumn(name = "auffuehrung_id").
     *
     * mappedBy = "auffuehrung" muss exakt dem Feldnamen in Reservierung
     * entsprechen – dann weiß JPA, dass der Fremdschlüssel dort liegt.
     */
    @OneToMany(mappedBy = "auffuehrung")
    private List<Reservierung> reservierungen;

    /**
     * Alle Buchungen zu dieser Aufführung.
     *
     * Beziehung:
     * - Eine Aufführung kann viele Buchungen haben (1:n).
     * - In der Entity Buchung gibt es ein Feld "auffuehrung"
     *   mit @ManyToOne und @JoinColumn(name = "auffuehrung_id").
     */
    @OneToMany(mappedBy = "auffuehrung")
    private List<Buchung> buchungen;

    /**
     * Leerer Standardkonstruktor.
     * Wird von JPA/Hibernate benötigt, um Objekte aus der
     * Datenbank zu instanziieren.
     */
    public Auffuehrung() {
    }

    // === Getter und Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartzeitpunkt() {
        return startzeitpunkt;
    }

    public void setStartzeitpunkt(Date startzeitpunkt) {
        this.startzeitpunkt = startzeitpunkt;
    }

    public double getAktuelleEinnahmen() {
        return aktuelleEinnahmen;
    }

    public void setAktuelleEinnahmen(double aktuelleEinnahmen) {
        this.aktuelleEinnahmen = aktuelleEinnahmen;
    }

    public Kinosaal getSaal() {
        return saal;
    }

    public void setSaal(Kinosaal saal) {
        this.saal = saal;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<Reservierung> getReservierungen() {
        return reservierungen;
    }

    public void setReservierungen(List<Reservierung> reservierungen) {
        this.reservierungen = reservierungen;
    }

    public List<Buchung> getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(List<Buchung> buchungen) {
        this.buchungen = buchungen;
    }
}
