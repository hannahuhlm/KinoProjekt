package kino.application.kafka.events;

import kino.application.kafka.dto.FilmDTO;
import kino.application.kafka.dto.SaalDTO;
import java.util.List;

/**
 * Event emitted after admin operations (success/failure).
 */
public class AdminEvent {
    public enum Entity { FILM, SAAL, AUFFUEHRUNG }
    public enum Action { SAVE, DELETE, CREATE, QUERY }
    public enum Status { SUCCESS, FAILURE, OK, NOT_FOUND, ERROR }

    private Entity entity;
    private Action action;
    private Status status;
    private String message;

    private Long filmId;
    private Long saalId;
    private Long auffuehrungId;

    // Query result fields
    private String correlationId;
    private FilmDTO film;
    private List<FilmDTO> films;
    private List<SaalDTO> saals;
    private java.util.List<kino.application.kafka.dto.AuffuehrungDTO> auffuehrungen;

    public AdminEvent() {}

    public AdminEvent(Entity entity, Action action, Status status) {
        this.entity = entity;
        this.action = action;
        this.status = status;
    }

    public Entity getEntity() { return entity; }
    public void setEntity(Entity entity) { this.entity = entity; }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getFilmId() { return filmId; }
    public void setFilmId(Long filmId) { this.filmId = filmId; }

    public Long getSaalId() { return saalId; }
    public void setSaalId(Long saalId) { this.saalId = saalId; }

    public Long getAuffuehrungId() { return auffuehrungId; }
    public void setAuffuehrungId(Long auffuehrungId) { this.auffuehrungId = auffuehrungId; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }

    public FilmDTO getFilm() { return film; }
    public void setFilm(FilmDTO film) { this.film = film; }

    public List<FilmDTO> getFilms() { return films; }
    public void setFilms(List<FilmDTO> films) { this.films = films; }

    public List<SaalDTO> getSaals() { return saals; }
    public void setSaals(List<SaalDTO> saals) { this.saals = saals; }
    public java.util.List<kino.application.kafka.dto.AuffuehrungDTO> getAuffuehrungen() { return auffuehrungen; }
    public void setAuffuehrungen(java.util.List<kino.application.kafka.dto.AuffuehrungDTO> auffuehrungen) { this.auffuehrungen = auffuehrungen; }
}
