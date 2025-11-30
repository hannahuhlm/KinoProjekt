package kino.application.kafka.events;

import java.time.Instant;
import java.util.List;

/**
 * Generic admin command to manage films, halls and showings via Kafka.
 */
public class AdminCommand {
    public enum Entity { FILM, SAAL, AUFFUEHRUNG }
    public enum Action { SAVE, DELETE, CREATE, QUERY }

    private Entity entity;
    private Action action;
    private Instant issuedAt = Instant.now();

    // Payloads (only one is populated depending on entity/action)
    private FilmPayload film;
    private SaalPayload saal;
    private AuffuehrungPayload auffuehrung;
    private QueryPayload query;

    public AdminCommand() {}

    public AdminCommand(Entity entity, Action action) {
        this.entity = entity;
        this.action = action;
    }

    public Entity getEntity() { return entity; }
    public void setEntity(Entity entity) { this.entity = entity; }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }

    public Instant getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Instant issuedAt) { this.issuedAt = issuedAt; }

    public FilmPayload getFilm() { return film; }
    public void setFilm(FilmPayload film) { this.film = film; }

    public SaalPayload getSaal() { return saal; }
    public void setSaal(SaalPayload saal) { this.saal = saal; }

    public AuffuehrungPayload getAuffuehrung() { return auffuehrung; }
    public void setAuffuehrung(AuffuehrungPayload auffuehrung) { this.auffuehrung = auffuehrung; }

    public QueryPayload getQuery() { return query; }
    public void setQuery(QueryPayload query) { this.query = query; }

    @Override
    public String toString() {
        return "AdminCommand{" +
                "entity=" + entity +
                ", action=" + action +
                ", film=" + film +
                ", saal=" + saal +
                ", auffuehrung=" + auffuehrung +
                '}';
    }

    // --- Payloads ---
    public static class FilmPayload {
        private Long id;
        private String titel;
        private Integer dauer;
        private String filmstart; // ISO-8601 Date, optional
        private String posterUrl;
        private String beschreibung;
        public FilmPayload() {}
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitel() { return titel; }
        public void setTitel(String titel) { this.titel = titel; }
        public Integer getDauer() { return dauer; }
        public void setDauer(Integer dauer) { this.dauer = dauer; }
        public String getFilmstart() { return filmstart; }
        public void setFilmstart(String filmstart) { this.filmstart = filmstart; }
        public String getPosterUrl() { return posterUrl; }
        public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
        public String getBeschreibung() { return beschreibung; }
        public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
        @Override public String toString(){return "FilmPayload{"+"id="+id+"}";}
    }

    public static class SaalPayload {
        private Long id;
        private String name;
        private boolean freigegeben;
        private List<SitzreihePayload> reihen;
        public SaalPayload() {}
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public boolean isFreigegeben() { return freigegeben; }
        public void setFreigegeben(boolean freigegeben) { this.freigegeben = freigegeben; }
        public List<SitzreihePayload> getReihen() { return reihen; }
        public void setReihen(List<SitzreihePayload> reihen) { this.reihen = reihen; }
        @Override public String toString(){return "SaalPayload{"+"id="+id+"}";}
    }

    public static class SitzreihePayload {
        private Integer reihennummer;
        private String kategorie; // SitzreihenKategorie name
        private Integer anzahlSitze;
        public SitzreihePayload() {}
        public Integer getReihennummer() { return reihennummer; }
        public void setReihennummer(Integer reihennummer) { this.reihennummer = reihennummer; }
        public String getKategorie() { return kategorie; }
        public void setKategorie(String kategorie) { this.kategorie = kategorie; }
        public Integer getAnzahlSitze() { return anzahlSitze; }
        public void setAnzahlSitze(Integer anzahlSitze) { this.anzahlSitze = anzahlSitze; }
    }

    public static class AuffuehrungPayload {
        private Long id; // for delete
        private Long filmId;
        private Long saalId;
        private String startzeit; // "yyyy-MM-dd HH:mm"
        public AuffuehrungPayload() {}
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getFilmId() { return filmId; }
        public void setFilmId(Long filmId) { this.filmId = filmId; }
        public Long getSaalId() { return saalId; }
        public void setSaalId(Long saalId) { this.saalId = saalId; }
        public String getStartzeit() { return startzeit; }
        public void setStartzeit(String startzeit) { this.startzeit = startzeit; }
        @Override public String toString(){return "AuffuehrungPayload{"+"filmId="+filmId+", saalId="+saalId+"}";}
    }

    public static class QueryPayload {
        public enum Type { LIST_ALL, GET_BY_ID, LIST_BY_FILM }
        private Type type;
        private Long id; // for GET_BY_ID
        private Long filmId; // for LIST_BY_FILM (auffuehrungen)
        private String correlationId;
        public QueryPayload() {}
        public Type getType() { return type; }
        public void setType(Type type) { this.type = type; }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getFilmId() { return filmId; }
        public void setFilmId(Long filmId) { this.filmId = filmId; }
        public String getCorrelationId() { return correlationId; }
        public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
        @Override public String toString(){return "QueryPayload{"+"type="+type+", id="+id+"}";}
    }
}
