package kino.application.kafka.dto;

import kino.application.data.Auffuehrung;
import java.util.Date;

public class AuffuehrungDTO {
    private Long id;
    private Long filmId;
    private Long saalId;
    private String saalName;
    private Date startzeitpunkt;

    public AuffuehrungDTO() {}

    public AuffuehrungDTO(Auffuehrung a) {
        this.id = a.getId();
        this.filmId = a.getFilm() != null ? a.getFilm().getId() : null;
        this.saalId = a.getSaal() != null ? a.getSaal().getId() : null;
        this.saalName = a.getSaal() != null ? a.getSaal().getName() : null;
        this.startzeitpunkt = a.getStartzeitpunkt();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFilmId() { return filmId; }
    public void setFilmId(Long filmId) { this.filmId = filmId; }

    public Long getSaalId() { return saalId; }
    public void setSaalId(Long saalId) { this.saalId = saalId; }

    public String getSaalName() { return saalName; }
    public void setSaalName(String saalName) { this.saalName = saalName; }

    public Date getStartzeitpunkt() { return startzeitpunkt; }
    public void setStartzeitpunkt(Date startzeitpunkt) { this.startzeitpunkt = startzeitpunkt; }
}
