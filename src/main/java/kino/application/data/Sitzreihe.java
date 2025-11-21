package kino.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import java.util.List;

/**
 * Entity-Klasse für eine Sitzreihe in einem Kinosaal.
 */
@Entity
public class Sitzreihe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nummer der Reihe innerhalb des Saals (z.B. 1, 2, 3). */
    private int reihennummer;

    /**
     * Kategorie der Sitzreihe (PARKETT, LOGE, LOGE_MIT_SERVICE).
     *
     * @Enumerated(EnumType.STRING) sorgt dafür, dass der Enum-Name
     * als String in der Datenbank gespeichert wird.
     */
    @Enumerated(EnumType.STRING)
    private SitzreihenKategorie kategorie;

    /** Anzahl der Sitzplätze in dieser Reihe. */
    private int anzahlSitze;

    /**
     * Alle Sitzplätze in dieser Reihe.
     * (1 Sitzreihe : viele Sitzplätze)
     */
    @OneToMany(mappedBy = "reihe")
    private List<Sitzplatz> plaetze;

    /**
     * Kinosaal, zu dem diese Sitzreihe gehört.
     * (viele Reihen : ein Saal)
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "saal_id")
    private Kinosaal saal;

    public Sitzreihe() {
    }

    // Getter / Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReihennummer() {
        return reihennummer;
    }

    public void setReihennummer(int reihennummer) {
        this.reihennummer = reihennummer;
    }

    public SitzreihenKategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(SitzreihenKategorie kategorie) {
        this.kategorie = kategorie;
    }

    public int getAnzahlSitze() {
        return anzahlSitze;
    }

    public void setAnzahlSitze(int anzahlSitze) {
        this.anzahlSitze = anzahlSitze;
    }

    public List<Sitzplatz> getPlaetze() {
        return plaetze;
    }

    public void setPlaetze(List<Sitzplatz> plaetze) {
        this.plaetze = plaetze;
    }

    public Kinosaal getSaal() {
        return saal;
    }

    public void setSaal(Kinosaal saal) {
        this.saal = saal;
    }
}
