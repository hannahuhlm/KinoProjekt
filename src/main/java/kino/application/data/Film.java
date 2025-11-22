package kino.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

/**
 * Entity-Klasse für einen Film im Kino-System.
 *
 * Abgeleitet aus dem EMF-Modell:
 * - Attribute: titel, dauer, beschreibung
 * - Referenzen: Liste von Aufführungen, in denen der Film gezeigt wird.
 *
 * Persistenzsicht:
 * - Entspricht einer Tabelle FILM in der Datenbank.
 * - Jede Instanz dieser Klasse entspricht einer Zeile in dieser Tabelle.
 */
@Entity
public class Film {

    /**
     * Primärschlüssel des Films.
     *
     * Wird von der Datenbank automatisch generiert
     * (z.B. AUTO_INCREMENT / IDENTITY).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titel des Films.
     * Beispiel: "Der Herr der Ringe: Die Gefährten".
     */
    private String titel;

    /**
     * Dauer des Films in Minuten.
     * Entspricht dem EMF-Attribut "dauer : int".
     */
    private int dauer;

    /**
     * Freitext-Beschreibung oder Zusammenfassung des Films.
     * Entspricht dem EMF-Attribut "beschreibung : String".
     */
    private String beschreibung;

    /**
     * Alle Aufführungen, in denen dieser Film gezeigt wird.
     *
     * Beziehung:
     * - Ein Film kann in vielen Aufführungen laufen (1:n).
     * - In der Entity Auffuehrung gibt es dazu ein Feld "film"
     *   mit @ManyToOne und @JoinColumn(name = "film_id").
     *
     * mappedBy = "film" bedeutet:
     * - Die Spalte FILM_ID liegt in der Tabelle AUFFUEHRUNG.
     * - Diese Seite der Beziehung ist rein "lesend" organisiert
     *   und erzeugt keine zusätzliche Join-Tabelle.
     */
    @OneToMany(mappedBy = "film")
    private List<Auffuehrung> auffuehrungen;

    /**
     * Leerer Konstruktor, den JPA/Hibernate für das Anlegen
     * neuer Instanzen aus der Datenbank benötigt.
     */
    public Film() {
    }

    // === Getter und Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public List<Auffuehrung> getAuffuehrungen() {
        return auffuehrungen;
    }

    public void setAuffuehrungen(List<Auffuehrung> auffuehrungen) {
        this.auffuehrungen = auffuehrungen;
    }

    // Nicht aus dem EMF entnommende Infos: Poster zum anzeigen und Filmstart

    private String posterUrl;      // z.B. "images/monster.jpg"
    private java.time.LocalDate filmstart;

    // Getter/Setter
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public LocalDate getFilmstart() { return filmstart; }
    public void setFilmstart(LocalDate filmstart) { this.filmstart = filmstart; }
}
