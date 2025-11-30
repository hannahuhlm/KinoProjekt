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
            // Handle queries separately (no transaction needed)
            if (cmd.getAction() == AdminCommand.Action.QUERY) {
                handleQuery(cmd);
                return;
            }
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

    private void handleQuery(AdminCommand cmd) {
        var q = cmd.getQuery();
        System.out.println(">>> handleQuery called - Entity: " + cmd.getEntity() + ", Query: " + q);
        if (q == null) {
            System.out.println(">>> Query ignored - q is null or not FILM entity");
            return;
        }
        
        AdminEvent ev = new AdminEvent(AdminEvent.Entity.valueOf(cmd.getEntity().name()), AdminEvent.Action.QUERY, AdminEvent.Status.OK);
        ev.setCorrelationId(q.getCorrelationId());
        System.out.println(">>> Query type: " + q.getType() + ", CorrelationId: " + q.getCorrelationId());
        
        try {
            if (cmd.getEntity() == AdminCommand.Entity.FILM) {
                switch (q.getType()) {
                    case LIST_ALL -> {
                        List<Film> films = filmRepository.findAll();
                        System.out.println(">>> Found " + films.size() + " films (no pagination)");
                        // Convert to DTOs to avoid serialization issues
                        List<kino.application.kafka.dto.FilmDTO> filmDTOs = films.stream()
                                .map(kino.application.kafka.dto.FilmDTO::new)
                                .toList();
                        ev.setFilms(filmDTOs);
                    }
                    case GET_BY_ID -> {
                        if (q.getId() != null) {
                            Film f = filmRepository.findById(q.getId()).orElse(null);
                            if (f == null) {
                                ev.setStatus(AdminEvent.Status.NOT_FOUND);
                                System.out.println(">>> Film not found for ID: " + q.getId());
                            } else {
                                // Convert to DTO to avoid serialization issues
                                ev.setFilm(new kino.application.kafka.dto.FilmDTO(f));
                                System.out.println(">>> Found film: " + f.getTitel());
                            }
                        } else {
                            ev.setStatus(AdminEvent.Status.NOT_FOUND);
                        }
                    }
                    case LIST_BY_FILM -> {
                        // Not applicable for FILM entity; mark as NOT_FOUND
                        ev.setStatus(AdminEvent.Status.NOT_FOUND);
                    }
                }
            } else if (cmd.getEntity() == AdminCommand.Entity.SAAL) {
                switch (q.getType()) {
                    case LIST_ALL -> {
                        List<Kinosaal> saele = kinosaalRepository.findAll();
                        System.out.println(">>> Found " + saele.size() + " saals");
                        List<kino.application.kafka.dto.SaalDTO> saalDTOs = saele.stream()
                                .map(kino.application.kafka.dto.SaalDTO::new)
                                .toList();
                        ev.setSaals(saalDTOs);
                    }
                    case GET_BY_ID -> {
                        if (q.getId() != null) {
                            Kinosaal s = kinosaalRepository.findById(q.getId()).orElse(null);
                            if (s == null) {
                                ev.setStatus(AdminEvent.Status.NOT_FOUND);
                            } else {
                                ev.setSaals(java.util.List.of(new kino.application.kafka.dto.SaalDTO(s)));
                            }
                        } else {
                            ev.setStatus(AdminEvent.Status.NOT_FOUND);
                        }
                    }
                    case LIST_BY_FILM -> {
                        // Not applicable for SAAL entity; mark as NOT_FOUND
                        ev.setStatus(AdminEvent.Status.NOT_FOUND);
                    }
                }
            } else if (cmd.getEntity() == AdminCommand.Entity.AUFFUEHRUNG) {
                switch (q.getType()) {
                    case LIST_BY_FILM -> {
                        if (q.getFilmId() != null) {
                            List<Auffuehrung> list = auffuehrungRepository.findByFilmOrderByStartzeitpunktAsc(
                                    filmRepository.findById(q.getFilmId()).orElseThrow()
                            );
                            List<kino.application.kafka.dto.AuffuehrungDTO> dto = list.stream()
                                    .map(kino.application.kafka.dto.AuffuehrungDTO::new)
                                    .toList();
                            ev.setAuffuehrungen(dto);
                        } else {
                            ev.setStatus(AdminEvent.Status.NOT_FOUND);
                        }
                    }
                    case LIST_ALL -> {
                        List<Auffuehrung> list = auffuehrungRepository.findAll();
                        List<kino.application.kafka.dto.AuffuehrungDTO> dto = list.stream()
                                .map(kino.application.kafka.dto.AuffuehrungDTO::new)
                                .toList();
                        ev.setAuffuehrungen(dto);
                    }
                    case GET_BY_ID -> {
                        if (q.getId() != null) {
                            Auffuehrung a = auffuehrungRepository.findById(q.getId()).orElse(null);
                            if (a == null) {
                                ev.setStatus(AdminEvent.Status.NOT_FOUND);
                            } else {
                                ev.setAuffuehrungen(java.util.List.of(new kino.application.kafka.dto.AuffuehrungDTO(a)));
                            }
                        } else {
                            ev.setStatus(AdminEvent.Status.NOT_FOUND);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(">>> Query error: " + e.getMessage());
            e.printStackTrace();
            ev.setStatus(AdminEvent.Status.ERROR);
            ev.setMessage(e.getMessage());
        }
        System.out.println(">>> Sending AdminEvent - Status: " + ev.getStatus() + 
                ", Films: " + (ev.getFilms() != null ? ev.getFilms().size() : "null") +
                ", Saals: " + (ev.getSaals() != null ? ev.getSaals().size() : "null") +
                ", Auffuehrungen: " + (ev.getAuffuehrungen() != null ? ev.getAuffuehrungen().size() : "null"));
        adminEventProducer.send(ev);
        System.out.println(">>> Broadcasting to AdminUIEventBus with correlationId=" + ev.getCorrelationId());
        AdminUIEventBus.broadcast(ev);
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
        if (p == null) {
            return;
        }
        if (cmd.getAction() == AdminCommand.Action.DELETE) {
            if (p.getId() != null) {
                try {
                    Optional<Auffuehrung> optAuff = auffuehrungRepository.findById(p.getId());
                    if (optAuff.isPresent()) {
                        Auffuehrung auff = optAuff.get();
                        boolean hasBuchungen = auff.getBuchungen() != null && !auff.getBuchungen().isEmpty();
                        boolean hasReservierungen = auff.getReservierungen() != null && !auff.getReservierungen().isEmpty();
                        
                        if (hasBuchungen || hasReservierungen) {
                            AdminEvent ev = new AdminEvent(AdminEvent.Entity.AUFFUEHRUNG, AdminEvent.Action.DELETE, AdminEvent.Status.FAILURE);
                            ev.setMessage("Aufführung kann nicht gelöscht werden: Es existieren bereits Buchungen oder Reservierungen.");
                            ev.setAuffuehrungId(p.getId());
                            ev.setCorrelationId(cmd.getCorrelationId());
                            adminEventProducer.send(ev);
                            AdminUIEventBus.broadcast(ev);
                            return;
                        }
                        
                        auffuehrungRepository.deleteAuffuehrungById(p.getId());
                        
                        AdminEvent ev = new AdminEvent(AdminEvent.Entity.AUFFUEHRUNG, AdminEvent.Action.DELETE, AdminEvent.Status.SUCCESS);
                        ev.setAuffuehrungId(p.getId());
                        ev.setCorrelationId(cmd.getCorrelationId());
                        adminEventProducer.send(ev);
                        AdminUIEventBus.broadcast(ev);
                    } else {
                        AdminEvent ev = new AdminEvent(AdminEvent.Entity.AUFFUEHRUNG, AdminEvent.Action.DELETE, AdminEvent.Status.NOT_FOUND);
                        ev.setAuffuehrungId(p.getId());
                        ev.setCorrelationId(cmd.getCorrelationId());
                        adminEventProducer.send(ev);
                        AdminUIEventBus.broadcast(ev);
                    }
                } catch (Exception e) {
                    AdminEvent ev = new AdminEvent(AdminEvent.Entity.AUFFUEHRUNG, AdminEvent.Action.DELETE, AdminEvent.Status.FAILURE);
                    ev.setMessage("Fehler beim Löschen: " + e.getMessage());
                    ev.setAuffuehrungId(p.getId());
                    ev.setCorrelationId(cmd.getCorrelationId());
                    adminEventProducer.send(ev);
                    AdminUIEventBus.broadcast(ev);
                    return;
                }
            }
            return;
        }
        if (cmd.getAction() == AdminCommand.Action.CREATE) {
            try {
                Film film = filmRepository.findById(p.getFilmId()).orElseThrow(() -> new IllegalArgumentException("Film nicht gefunden"));
                Kinosaal saal = kinosaalRepository.findById(p.getSaalId()).orElseThrow(() -> new IllegalArgumentException("Kinosaal nicht gefunden"));
                Date start;
                try {
                    start = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(p.getStartzeit());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Ungültige Startzeit: " + p.getStartzeit());
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
                ev.setCorrelationId(cmd.getCorrelationId());
                adminEventProducer.send(ev);
                AdminUIEventBus.broadcast(ev);
            } catch (Exception e) {
                AdminEvent ev = new AdminEvent(AdminEvent.Entity.AUFFUEHRUNG, AdminEvent.Action.CREATE, AdminEvent.Status.FAILURE);
                ev.setMessage("Fehler beim Anlegen der Aufführung: " + e.getMessage());
                ev.setCorrelationId(cmd.getCorrelationId());
                adminEventProducer.send(ev);
                AdminUIEventBus.broadcast(ev);
            }
        }
    }
}
