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
 * Entity-Klasse für eine Reservierung im Kino-System.
 *
 * Abgeleitet aus dem EMF-Modell:
 * - Attribute:
 *      reservierungsnummer : int
 *      startZeitstempel    : Date
 * - Referenzen:
 *      kunde        : Kunde         (required)
 *      auffuehrung  : Auffuehrung   (required)
 *      plaetze      : EList<Sitzplatz> (viele Plätze)
 *
 * Persistenzsicht:
 * - Entspricht einer Tabelle RESERVIERUNG in der Datenbank.
 * - Die einzelnen reservierten Sitzplätze werden über die Join-Entity
 *   ReservierungSitzplatz abgebildet.
 */
@Entity
public class Reservierung {

    /**
     * Technischer Primärschlüssel der Reservierung.
     *
     * Zusätzlich dazu gibt es die fachliche Reservierungsnummer
     * (reservierungsnummer), die du z.B. dem Kunden anzeigst.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fachliche Reservierungsnummer.
     *
     * Entspricht dem EMF-Attribut "reservierungsnummer : int".
     * Du kannst diese Nummer z.B. fortlaufend vergeben oder aus
     * einer eigenen Logik generieren, sie ist unabhängig von der DB-ID.
     */
    private int reservierungsnummer;

    /**
     * Zeitpunkt, zu dem die Reservierung erstellt wurde.
     *
     * Entspricht "startZeitstempel : Date" im EMF-Modell.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date startZeitstempel;

    /**
     * Kunde, auf den diese Reservierung läuft.
     *
     * Beziehung:
     * - Viele Reservierungen können zu einem Kunden gehören (n:1).
     * - In der Kunde-Entity gibt es dazu eine Liste "reservierungen"
     *   mit @OneToMany(mappedBy = "kunde").
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "kunde_id")
    private Kunde kunde;

    /**
     * Aufführung, für die diese Reservierung gilt.
     *
     * Beziehung:
     * - Viele Reservierungen können zu einer Aufführung gehören (n:1).
     * - In der Auffuehrung-Entity kannst du eine Liste "reservierungen"
     *   mit @OneToMany(mappedBy = "auffuehrung") definieren.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auffuehrung_id")
    private Auffuehrung auffuehrung;

    /**
     * Alle reservierten Sitzplätze für diese Reservierung.
     *
     * Im EMF-Modell ist das "plaetze : EList<Sitzplatz>".
     *
     * In der relationalen Welt modellieren wir das über eine Join-Entity
     * ReservierungSitzplatz:
     * - Jede Reservierung kann mehrere Einträge in ReservierungSitzplatz haben.
     * - Jeder dieser Einträge verweist auf genau einen Sitzplatz.
     */
    @OneToMany(mappedBy = "reservierung")
    private List<ReservierungSitzplatz> reservierungSitzplaetze;

    /**
     * Leerer Konstruktor, den JPA/Hibernate für das Anlegen
     * neuer Instanzen aus der Datenbank benötigt.
     */
    public Reservierung() {
    }

    // === Getter und Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReservierungsnummer() {
        return reservierungsnummer;
    }

    public void setReservierungsnummer(int reservierungsnummer) {
        this.reservierungsnummer = reservierungsnummer;
    }

    public Date getStartZeitstempel() {
        return startZeitstempel;
    }

    public void setStartZeitstempel(Date startZeitstempel) {
        this.startZeitstempel = startZeitstempel;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Auffuehrung getAuffuehrung() {
        return auffuehrung;
    }

    public void setAuffuehrung(Auffuehrung auffuehrung) {
        this.auffuehrung = auffuehrung;
    }

    public List<ReservierungSitzplatz> getReservierungSitzplaetze() {
        return reservierungSitzplaetze;
    }

    public void setReservierungSitzplaetze(List<ReservierungSitzplatz> reservierungSitzplaetze) {
        this.reservierungSitzplaetze = reservierungSitzplaetze;
    }
}
