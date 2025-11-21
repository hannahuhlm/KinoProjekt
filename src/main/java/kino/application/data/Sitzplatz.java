package kino.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * Entity-Klasse für einen Sitzplatz im Kinosaal.
 *
 * Abgeleitet aus dem EMF-Modell:
 * - Attribute:
 *      platznummer : int
 *      isFrei      : boolean (Default true)
 * - Referenzen:
 *      reihe        : Sitzreihe      (required)
 *      reservierung : Reservierung   (optional)
 *      buchung      : Buchung        (optional)
 *
 * Persistenzsicht:
 * - Entspricht einer Tabelle SITZPLATZ in der Datenbank.
 * - Die Fremdschlüssel auf Reihe, Reservierung und Buchung werden
 *   als Spalten REIHE_ID, RESERVIERUNG_ID und BUCHUNG_ID abgebildet.
 */
@Entity
public class Sitzplatz {

    /**
     * Technischer Primärschlüssel des Sitzplatzes.
     *
     * In der Datenbank z.B. BIGINT AUTO_INCREMENT.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Laufende Nummer innerhalb der Sitzreihe.
     *
     * Entspricht dem EMF-Attribut "platznummer : int".
     * Beispiel: 1, 2, 3, ...
     */
    private int platznummer;

    /**
     * Flag, ob der Sitzplatz aktuell frei ist.
     *
     * Entspricht "isFrei : boolean" mit Default-Wert true im EMF-Modell.
     * Fachlich:
     * - true  = Platz ist verfügbar
     * - false = Platz ist belegt (reserviert oder gebucht)
     */
    private boolean isFrei = true;

    /**
     * Die Sitzreihe, zu der dieser Platz gehört.
     *
     * Beziehung:
     * - Viele Sitzplätze gehören zu genau einer Sitzreihe (n:1).
     * - In der Entity Sitzreihe gibt es dazu eine Liste "plaetze"
     *   oder "sitzplaetze" mit @OneToMany(mappedBy = "reihe").
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "reihe_id")
    private Sitzreihe reihe;

    /**
     * Die Reservierung, zu der dieser Platz aktuell gehört (wenn überhaupt).
     *
     * Beziehung:
     * - Eine Reservierung kann mehrere Sitzplätze enthalten (1:n).
     * - Ein Sitzplatz kann laut EMF-Modell maximal einer Reservierung
     *   zugeordnet sein (0..1).
     *
     * In der Entity Reservierung kannst du optional eine Liste
     * von Sitzplätzen definieren, z.B.:
     *
     *   @OneToMany(mappedBy = "reservierung")
     *   private List<Sitzplatz> plaetze;
     *
     * Dann ist die Beziehung vollständig bidirektional.
     */
    @ManyToOne
    @JoinColumn(name = "reservierung_id")
    private Reservierung reservierung;

    /**
     * Die Buchung, zu der dieser Platz aktuell gehört (wenn überhaupt).
     *
     * Beziehung:
     * - Eine Buchung kann mehrere Sitzplätze enthalten (1:n).
     * - Ein Sitzplatz kann laut EMF-Modell maximal einer Buchung
     *   zugeordnet sein (0..1).
     *
     * In der Entity Buchung könntest du analog eine Liste von
     * Sitzplätzen definieren:
     *
     *   @OneToMany(mappedBy = "buchung")
     *   private List<Sitzplatz> plaetze;
     *
     * Wenn du stattdessen die Join-Entity BuchungSitzplatz verwendest,
     * wäre dieses Feld hier nicht nötig – siehe Kommentar unten.
     */
    @ManyToOne
    @JoinColumn(name = "buchung_id")
    private Buchung buchung;

    /**
     * Leerer Konstruktor, den JPA/Hibernate benötigt, um Instanzen
     * aus der Datenbank zu laden.
     */
    public Sitzplatz() {
    }

    // === Getter und Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPlatznummer() {
        return platznummer;
    }

    public void setPlatznummer(int platznummer) {
        this.platznummer = platznummer;
    }

    public boolean isFrei() {
        return isFrei;
    }

    public void setFrei(boolean frei) {
        isFrei = frei;
    }

    public Sitzreihe getReihe() {
        return reihe;
    }

    public void setReihe(Sitzreihe reihe) {
        this.reihe = reihe;
    }

    public Reservierung getReservierung() {
        return reservierung;
    }

    public void setReservierung(Reservierung reservierung) {
        this.reservierung = reservierung;
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;
    }
}
