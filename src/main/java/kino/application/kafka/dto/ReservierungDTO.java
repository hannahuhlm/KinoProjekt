package kino.application.kafka.dto;

import kino.application.data.Reservierung;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

public class ReservierungDTO {
    private Long id;
    private int reservierungsnummer;
    private Long auffuehrungId;
    private String filmTitel;
    private String filmPosterUrl;
    private String saalName;
    private Date startzeitpunkt;
    private Long kundeId;
    private List<SitzplatzDTO> sitzplaetze;
    private double gesamtpreis;

    public ReservierungDTO() {}

    public ReservierungDTO(Reservierung r) {
        this.id = r.getId();
        this.reservierungsnummer = r.getReservierungsnummer();
        if (r.getAuffuehrung() != null) {
            this.auffuehrungId = r.getAuffuehrung().getId();
            this.startzeitpunkt = r.getAuffuehrung().getStartzeitpunkt();
            if (r.getAuffuehrung().getFilm() != null) {
                this.filmTitel = r.getAuffuehrung().getFilm().getTitel();
                this.filmPosterUrl = r.getAuffuehrung().getFilm().getPosterUrl();
            }
            if (r.getAuffuehrung().getSaal() != null) {
                this.saalName = r.getAuffuehrung().getSaal().getName();
            }
        }
        if (r.getKunde() != null) {
            this.kundeId = r.getKunde().getId();
        }
        if (r.getReservierungSitzplaetze() != null) {
            this.sitzplaetze = r.getReservierungSitzplaetze().stream()
                    .map(rs -> new SitzplatzDTO(
                            rs.getSitzplatz() != null ? rs.getSitzplatz().getId() : null,
                            rs.getSitzplatz() != null && rs.getSitzplatz().getReihe() != null ? rs.getSitzplatz().getReihe().getReihennummer() : -1,
                            rs.getSitzplatz() != null ? rs.getSitzplatz().getPlatznummer() : -1,
                            rs.getPreis()
                    ))
                    .collect(Collectors.toList());
            this.gesamtpreis = this.sitzplaetze.stream().mapToDouble(SitzplatzDTO::getPreis).sum();
        } else {
            this.sitzplaetze = java.util.Collections.emptyList();
            this.gesamtpreis = 0.0;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getReservierungsnummer() { return reservierungsnummer; }
    public void setReservierungsnummer(int reservierungsnummer) { this.reservierungsnummer = reservierungsnummer; }

    public Long getAuffuehrungId() { return auffuehrungId; }
    public void setAuffuehrungId(Long auffuehrungId) { this.auffuehrungId = auffuehrungId; }

    public String getFilmTitel() { return filmTitel; }
    public void setFilmTitel(String filmTitel) { this.filmTitel = filmTitel; }
    public String getFilmPosterUrl() { return filmPosterUrl; }
    public void setFilmPosterUrl(String filmPosterUrl) { this.filmPosterUrl = filmPosterUrl; }

    public String getSaalName() { return saalName; }
    public void setSaalName(String saalName) { this.saalName = saalName; }

    public Date getStartzeitpunkt() { return startzeitpunkt; }
    public void setStartzeitpunkt(Date startzeitpunkt) { this.startzeitpunkt = startzeitpunkt; }

    public Long getKundeId() { return kundeId; }
    public void setKundeId(Long kundeId) { this.kundeId = kundeId; }

    public List<SitzplatzDTO> getSitzplaetze() { return sitzplaetze; }
    public void setSitzplaetze(List<SitzplatzDTO> sitzplaetze) { this.sitzplaetze = sitzplaetze; }
    public double getGesamtpreis() { return gesamtpreis; }
    public void setGesamtpreis(double gesamtpreis) { this.gesamtpreis = gesamtpreis; }

    public static class SitzplatzDTO {
        private Long id;
        private int reihe;
        private int platz;
        private double preis;

        public SitzplatzDTO() {}
        public SitzplatzDTO(Long id, int reihe, int platz, double preis) {
            this.id = id; this.reihe = reihe; this.platz = platz; this.preis = preis;
        }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public int getReihe() { return reihe; }
        public void setReihe(int reihe) { this.reihe = reihe; }
        public int getPlatz() { return platz; }
        public void setPlatz(int platz) { this.platz = platz; }
        public double getPreis() { return preis; }
        public void setPreis(double preis) { this.preis = preis; }
    }
}
