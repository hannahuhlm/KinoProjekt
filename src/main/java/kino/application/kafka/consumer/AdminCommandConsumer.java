package kino.application.kafka.consumer;

import kino.application.data.*;
import kino.application.kafka.events.AdminCommand;
import kino.application.admin.AdminUIEventBus;
import kino.application.kafka.events.AdminEvent;
import kino.application.kafka.producer.AdminEventProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminCommandConsumer {

    private final FilmRepository filmRepository;
    private final KinosaalRepository kinosaalRepository;
    private final AuffuehrungRepository auffuehrungRepository;

    private final AdminEventProducer adminEventProducer;

    public AdminCommandConsumer(FilmRepository filmRepository,
                                KinosaalRepository kinosaalRepository,
                                AuffuehrungRepository auffuehrungRepository,
                                AdminEventProducer adminEventProducer) {
        this.filmRepository = filmRepository;
        this.kinosaalRepository = kinosaalRepository;
        this.auffuehrungRepository = auffuehrungRepository;
        this.adminEventProducer = adminEventProducer;
    }

    @KafkaListener(topics = "${kino.kafka.topic.admin}", groupId = "kino-admin-worker")
    @Transactional
    public void onMessage(AdminCommand cmd) {
        try {
            switch (cmd.getEntity()) {
                case FILM -> handleFilm(cmd);
                case SAAL -> handleSaal(cmd);
                case AUFFUEHRUNG -> handleAuffuehrung(cmd);
            }
        } catch (Exception e) {
            // generic failure safety net
            AdminEvent ev = new AdminEvent(
                    AdminEvent.Entity.valueOf(cmd.getEntity().name()),
                    AdminEvent.Action.valueOf(cmd.getAction().name()),
                    AdminEvent.Status.FAILURE);
            ev.setMessage(e.getMessage());
            adminEventProducer.send(ev);
            AdminUIEventBus.broadcast(ev);
            throw e;
        }
    }

    private void handleFilm(AdminCommand cmd) {
        var p = cmd.getFilm();
        if (p == null) return;
        if (cmd.getAction() == AdminCommand.Action.DELETE) {
            if (p.getId() != null) {
                filmRepository.deleteById(p.getId());
            }
            AdminEvent ev = new AdminEvent(AdminEvent.Entity.FILM, AdminEvent.Action.DELETE, AdminEvent.Status.SUCCESS);
            ev.setFilmId(p.getId());
            adminEventProducer.send(ev);
            AdminUIEventBus.broadcast(ev);
            return;
        }
        // SAVE
        Film film = (p.getId() != null) ? filmRepository.findById(p.getId()).orElse(new Film()) : new Film();
        film.setTitel(p.getTitel());
        film.setDauer(p.getDauer() != null ? p.getDauer() : 0);
        film.setPosterUrl(p.getPosterUrl());
        film.setBeschreibung(p.getBeschreibung());
        if (p.getFilmstart() != null && !p.getFilmstart().isBlank()) {
            try { film.setFilmstart(java.time.LocalDate.parse(p.getFilmstart())); } catch (Exception ignored) {}
        }
        film = filmRepository.save(film);
        AdminEvent ev = new AdminEvent(AdminEvent.Entity.FILM, AdminEvent.Action.CREATE, AdminEvent.Status.SUCCESS);
        ev.setFilmId(film.getId());
        adminEventProducer.send(ev);
        AdminUIEventBus.broadcast(ev);
    }

    private void handleSaal(AdminCommand cmd) {
        var p = cmd.getSaal();
        if (p == null) return;
        // Only SAVE supported for now
        Kinosaal saal = (p.getId() != null) ? kinosaalRepository.findById(p.getId()).orElse(new Kinosaal()) : new Kinosaal();
        saal.setName(p.getName());
        saal.setFreigegeben(p.isFreigegeben());

        // Synchronize rows based on payload
        List<Sitzreihe> reihen = saal.getReihen() != null ? saal.getReihen() : new ArrayList<>();
        saal.setReihen(reihen);

        // Ensure enough rows
        int payloadRows = p.getReihen() != null ? p.getReihen().size() : 0;
        while (reihen.size() < payloadRows) {
            Sitzreihe r = new Sitzreihe();
            r.setSaal(saal);
            reihen.add(r);
        }
        while (reihen.size() > payloadRows) {
            reihen.remove(reihen.size() - 1);
        }

        // Apply each row
        for (int i = 0; i < payloadRows; i++) {
            var rp = p.getReihen().get(i);
            Sitzreihe r = reihen.get(i);
            r.setSaal(saal);
            r.setReihennummer(rp.getReihennummer() != null ? rp.getReihennummer() : (i + 1));
            try { r.setKategorie(SitzreihenKategorie.valueOf(rp.getKategorie())); } catch (Exception e) { r.setKategorie(SitzreihenKategorie.PARKETT); }
            r.setAnzahlSitze(rp.getAnzahlSitze() != null ? rp.getAnzahlSitze() : 10);

            // Sync seats
            List<Sitzplatz> plaetze = r.getPlaetze() != null ? r.getPlaetze() : new ArrayList<>();
            r.setPlaetze(plaetze);
            while (plaetze.size() < r.getAnzahlSitze()) {
                Sitzplatz platz = new Sitzplatz();
                platz.setReihe(r);
                platz.setPlatznummer(plaetze.size() + 1);
                plaetze.add(platz);
            }
            while (plaetze.size() > r.getAnzahlSitze()) {
                plaetze.remove(plaetze.size() - 1);
            }
            for (int j = 0; j < plaetze.size(); j++) {
                plaetze.get(j).setPlatznummer(j + 1);
            }
        }

        saal = kinosaalRepository.save(saal);
        AdminEvent ev = new AdminEvent(AdminEvent.Entity.SAAL, AdminEvent.Action.CREATE, AdminEvent.Status.SUCCESS);
        ev.setSaalId(saal.getId());
        adminEventProducer.send(ev);
        AdminUIEventBus.broadcast(ev);
    }

    private void handleAuffuehrung(AdminCommand cmd) {
        var p = cmd.getAuffuehrung();
        if (p == null) return;
        if (cmd.getAction() == AdminCommand.Action.DELETE) {
            if (p.getId() != null) {
                auffuehrungRepository.deleteById(p.getId());
            }
            AdminEvent ev = new AdminEvent(AdminEvent.Entity.AUFFUEHRUNG, AdminEvent.Action.DELETE, AdminEvent.Status.SUCCESS);
            ev.setAuffuehrungId(p.getId());
            adminEventProducer.send(ev);
            AdminUIEventBus.broadcast(ev);
            return;
        }
        if (cmd.getAction() == AdminCommand.Action.CREATE) {
            Film film = filmRepository.findById(p.getFilmId()).orElseThrow();
            Kinosaal saal = kinosaalRepository.findById(p.getSaalId()).orElseThrow();
            Date start;
            try {
                start = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(p.getStartzeit());
            } catch (Exception e) {
                throw new RuntimeException("Ungültige Startzeit: " + p.getStartzeit());
            }
            Auffuehrung a = new Auffuehrung();
            a.setFilm(film);
            a.setSaal(saal);
            a.setStartzeitpunkt(start);
            a = auffuehrungRepository.save(a);
            AdminEvent ev = new AdminEvent(AdminEvent.Entity.AUFFUEHRUNG, AdminEvent.Action.CREATE, AdminEvent.Status.SUCCESS);
            ev.setAuffuehrungId(a.getId());
            ev.setFilmId(film.getId());
            ev.setSaalId(saal.getId());
            adminEventProducer.send(ev);
            AdminUIEventBus.broadcast(ev);
        }
    }
}
