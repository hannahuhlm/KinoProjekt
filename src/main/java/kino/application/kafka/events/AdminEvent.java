package kino.application.kafka.events;

/**
 * Event emitted after admin operations (success/failure).
 */
public class AdminEvent {
    public enum Entity { FILM, SAAL, AUFFUEHRUNG }
    public enum Action { SAVE, DELETE, CREATE }
    public enum Status { SUCCESS, FAILURE }

    private Entity entity;
    private Action action;
    private Status status;
    private String message;

    private Long filmId;
    private Long saalId;
    private Long auffuehrungId;

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
}
