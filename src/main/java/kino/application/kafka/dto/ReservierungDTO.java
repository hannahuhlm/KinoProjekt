package kino.application.kafka.dto;

import kino.application.data.Reservierung;
import java.util.Date;

public class ReservierungDTO {
    private Long id;
    private int reservierungsnummer;
    private Long auffuehrungId;
    private String filmTitel;
    private String saalName;
    private Date startzeitpunkt;
    private Long kundeId;

    public ReservierungDTO() {}

    public ReservierungDTO(Reservierung r) {
        this.id = r.getId();
        this.reservierungsnummer = r.getReservierungsnummer();
        if (r.getAuffuehrung() != null) {
            this.auffuehrungId = r.getAuffuehrung().getId();
            this.startzeitpunkt = r.getAuffuehrung().getStartzeitpunkt();
            if (r.getAuffuehrung().getFilm() != null) {
                this.filmTitel = r.getAuffuehrung().getFilm().getTitel();
            }
            if (r.getAuffuehrung().getSaal() != null) {
                this.saalName = r.getAuffuehrung().getSaal().getName();
            }
        }
        if (r.getKunde() != null) {
            this.kundeId = r.getKunde().getId();
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

    public String getSaalName() { return saalName; }
    public void setSaalName(String saalName) { this.saalName = saalName; }

    public Date getStartzeitpunkt() { return startzeitpunkt; }
    public void setStartzeitpunkt(Date startzeitpunkt) { this.startzeitpunkt = startzeitpunkt; }

    public Long getKundeId() { return kundeId; }
    public void setKundeId(Long kundeId) { this.kundeId = kundeId; }
}
