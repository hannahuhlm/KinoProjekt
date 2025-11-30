package kino.application.service;

import kino.application.kafka.events.AdminCommand;
import kino.application.kafka.events.AdminCommand.*;
import kino.application.kafka.producer.AdminCommandProducer;
import org.springframework.stereotype.Service;

import kino.application.data.Film;
import kino.application.data.Kinosaal;
import kino.application.data.Sitzreihe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminService {

    private final AdminCommandProducer producer;

    public AdminService(AdminCommandProducer producer) {
        this.producer = producer;
    }

    // --- Film ---
    public void saveFilm(Film film) {
        AdminCommand cmd = new AdminCommand(AdminCommand.Entity.FILM, AdminCommand.Action.CREATE);
        FilmPayload payload = new FilmPayload();
        payload.setId(film.getId());
        payload.setTitel(film.getTitel());
        payload.setDauer(film.getDauer());
        if (film.getFilmstart() != null) {
            payload.setFilmstart(film.getFilmstart().toString());
        }
        payload.setPosterUrl(film.getPosterUrl());
        payload.setBeschreibung(film.getBeschreibung());
        cmd.setFilm(payload);
        producer.send(cmd);
    }

    public void deleteFilm(Long filmId) {
        AdminCommand cmd = new AdminCommand(AdminCommand.Entity.FILM, AdminCommand.Action.DELETE);
        FilmPayload payload = new FilmPayload();
        payload.setId(filmId);
        cmd.setFilm(payload);
        producer.send(cmd);
    }

    // --- Aufführung ---
    public void createAuffuehrung(Long filmId, Long saalId, Date start) {
        createAuffuehrung(filmId, saalId, start, null);
    }

    public void createAuffuehrung(Long filmId, Long saalId, Date start, String correlationId) {
        AdminCommand cmd = new AdminCommand(AdminCommand.Entity.AUFFUEHRUNG, AdminCommand.Action.CREATE);
        AuffuehrungPayload p = new AuffuehrungPayload();
        p.setFilmId(filmId);
        p.setSaalId(saalId);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        p.setStartzeit(fmt.format(start));
        cmd.setAuffuehrung(p);
        cmd.setCorrelationId(correlationId);
        producer.send(cmd);
    }

    public void deleteAuffuehrung(Long auffuehrungId) {
        AdminCommand cmd = new AdminCommand(AdminCommand.Entity.AUFFUEHRUNG, AdminCommand.Action.DELETE);
        AuffuehrungPayload p = new AuffuehrungPayload();
        p.setId(auffuehrungId);
        cmd.setAuffuehrung(p);
        producer.send(cmd);
    }

    // --- Saal ---
    public void saveSaal(Kinosaal saal) {
        AdminCommand cmd = new AdminCommand(AdminCommand.Entity.SAAL, AdminCommand.Action.CREATE);
        SaalPayload sp = new SaalPayload();
        sp.setId(saal.getId());
        sp.setName(saal.getName());
        sp.setFreigegeben(saal.isFreigegeben());

        List<SitzreihePayload> reihen = new ArrayList<>();
        if (saal.getReihen() != null) {
            for (Sitzreihe r : saal.getReihen()) {
                SitzreihePayload rp = new SitzreihePayload();
                rp.setReihennummer(r.getReihennummer());
                rp.setKategorie(r.getKategorie() != null ? r.getKategorie().name() : null);
                rp.setAnzahlSitze(r.getAnzahlSitze());
                reihen.add(rp);
            }
        }
        sp.setReihen(reihen);
        cmd.setSaal(sp);
        producer.send(cmd);
    }
}
