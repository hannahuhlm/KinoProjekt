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
 * Entity für eine Buchung im Kino-System.
 *
 * Fachlich:
 * - Gehört zu genau einem Kunden und genau einer Aufführung.
 * - Kann mehrere Sitzplätze enthalten (über die Join-Entity BuchungSitzplatz).
 */
@Entity
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Eindeutige Buchungsnummer, die du z.B. auf Tickets anzeigen kannst. */
    private String buchungsnummer;

    /** Zeitpunkt, zu dem die Buchung erstellt wurde. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date buchungsZeitstempel;

    /** Ob die Buchung bereits bezahlt wurde (z.B. an der Kasse oder online). */
    private boolean bezahlt = false;

    /** Gesamtpreis dieser Buchung (Summe der einzelnen Plätze). */
    private double gesamtpreis;

    /** Kunde, der diese Buchung vorgenommen hat (Pflichtfeld). */
    @ManyToOne(optional = false)
    @JoinColumn(name = "kunde_id")
    private Kunde kunde;

    /** Aufführung, für die diese Buchung gilt (Pflichtfeld). */
    @ManyToOne(optional = false)
    @JoinColumn(name = "auffuehrung_id")
    private Auffuehrung auffuehrung;

    /**
     * Alle Sitzplätze, die zu dieser Buchung gehören, über die Join-Entity BuchungSitzplatz.
     *
     * mappedBy = "buchung" heißt:
     * - In der Klasse BuchungSitzplatz gibt es ein Feld "buchung".
     * - Dieses Feld ist die "besitzende" Seite der Beziehung (ManyToOne).
     * - Hier in Buchung wird die umgekehrte Richtung abgebildet (OneToMany).
     */
    @OneToMany(mappedBy = "buchung")
    private List<BuchungSitzplatz> buchungSitzplaetze;

    public Buchung() {
    }

    // === Getter/Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuchungsnummer() {
        return buchungsnummer;
    }

    public void setBuchungsnummer(String buchungsnummer) {
        this.buchungsnummer = buchungsnummer;
    }

    public Date getBuchungsZeitstempel() {
        return buchungsZeitstempel;
    }

    public void setBuchungsZeitstempel(Date buchungsZeitstempel) {
        this.buchungsZeitstempel = buchungsZeitstempel;
    }

    public boolean isBezahlt() {
        return bezahlt;
    }

    public void setBezahlt(boolean bezahlt) {
        this.bezahlt = bezahlt;
    }

    public double getGesamtpreis() {
        return gesamtpreis;
    }

    public void setGesamtpreis(double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
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

    public List<BuchungSitzplatz> getBuchungSitzplaetze() {
        return buchungSitzplaetze;
    }

    public void setBuchungSitzplaetze(List<BuchungSitzplatz> buchungSitzplaetze) {
        this.buchungSitzplaetze = buchungSitzplaetze;
    }
}
