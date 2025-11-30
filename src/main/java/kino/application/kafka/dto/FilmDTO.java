package kino.application.kafka.dto;

import kino.application.data.Film;
import java.time.LocalDate;

/**
 * Data Transfer Object for Film without relationships to avoid serialization issues.
 */
public class FilmDTO {
    private Long id;
    private String titel;
    private int dauer;
    private String beschreibung;
    private String posterUrl;
    private LocalDate filmstart;

    public FilmDTO() {}

    public FilmDTO(Film film) {
        this.id = film.getId();
        this.titel = film.getTitel();
        this.dauer = film.getDauer();
        this.beschreibung = film.getBeschreibung();
        this.posterUrl = film.getPosterUrl();
        this.filmstart = film.getFilmstart();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitel() { return titel; }
    public void setTitel(String titel) { this.titel = titel; }

    public int getDauer() { return dauer; }
    public void setDauer(int dauer) { this.dauer = dauer; }

    public String getBeschreibung() { return beschreibung; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public LocalDate getFilmstart() { return filmstart; }
    public void setFilmstart(LocalDate filmstart) { this.filmstart = filmstart; }
}
