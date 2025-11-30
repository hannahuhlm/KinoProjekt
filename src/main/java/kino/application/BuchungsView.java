package kino.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.VaadinSession;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kino.application.buchung.BuchungContext;
import kino.application.data.Auffuehrung;
import kino.application.data.AuffuehrungRepository;
import kino.application.data.Buchung;
import kino.application.data.BuchungRepository;
import kino.application.data.Film;
import kino.application.data.Kunde;
import kino.application.data.KundeRepository;
import kino.application.data.Sitzplatz;
import kino.application.data.SitzplatzRepository;
import kino.application.service.ReservierungsService;
import kino.application.service.BuchungsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route(value = "buchung/:buchungId", layout = MainViewLayout.class)
@RouteAlias(value = "buchung", layout = MainViewLayout.class)
@PageTitle("Buchungsbestätigung")
@AnonymousAllowed
public class BuchungsView extends VerticalLayout implements BeforeEnterObserver {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuchungsView.class);

    private final BuchungRepository buchungRepository;
    // Repositories kept for read/poll and future fallback
    private final AuffuehrungRepository auffuehrungRepository;
    private final KundeRepository kundeRepository;
    private final SitzplatzRepository sitzplatzRepository;
    private final ReservierungsService reservierungsService;
    private final BuchungsService buchungsService;

    private Buchung buchung;
    private BuchungContext ctx;
    private Auffuehrung auffuehrung;
    private Kunde kunde;
    private List<Sitzplatz> sitzplaetze;
    
    private final DateTimeFormatter auffuehrungsFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Autowired
    public BuchungsView(
            AuffuehrungRepository auffuehrungRepository,
            KundeRepository kundeRepository,
            SitzplatzRepository sitzplatzRepository,
            BuchungRepository buchungRepository,
            
                ReservierungsService reservierungsService,
                BuchungsService buchungsService
    ) {
        this.auffuehrungRepository = auffuehrungRepository;
        this.kundeRepository = kundeRepository;
        this.sitzplatzRepository = sitzplatzRepository;
        this.buchungRepository = buchungRepository;
        this.reservierungsService = reservierungsService;
        this.buchungsService = buchungsService;

        setWidthFull();
        setPadding(true);
        setSpacing(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idStr = event.getRouteParameters().get("buchungId").orElse(null);
        LOGGER.debug("BuchungsView.beforeEnter: routeParam.buchungId={}", idStr);
        if (idStr == null) {
            // Kein Parameter: Versuche Buchung über BuchungContext zu erzeugen
            this.ctx = VaadinSession.getCurrent().getAttribute(BuchungContext.class);
            if (this.ctx == null) {
                LOGGER.warn("Kein BuchungContext in Session gefunden; gehe zurück zu 'reservierungen'.");
                // Kein Kontext vorhanden, zurück zur Reservierungsübersicht
                UI.getCurrent().navigate("reservierungen");
                return;
            }
            try {
                // Entitäten laden
                LOGGER.info("Erzeuge Buchung aus Context: auffId={}, kundeId={}, sitze={}, resId={}",
                        ctx.getAuffuehrungId(), ctx.getKundeId(),
                        ctx.getSitzplatzIds() != null ? ctx.getSitzplatzIds().size() : 0,
                        ctx.getReservierungsId());
                this.auffuehrung = auffuehrungRepository.findById(ctx.getAuffuehrungId()).orElse(null);
                this.kunde = kundeRepository.findById(ctx.getKundeId()).orElse(null);
                this.sitzplaetze = ctx.getSitzplatzIds().stream()
                        .map(id -> sitzplatzRepository.findById(id).orElse(null))
                        .filter(sp -> sp != null)
                        .toList();

                if (auffuehrung == null || kunde == null || sitzplaetze.isEmpty()) {
                    LOGGER.error("Buchung aus Context fehlgeschlagen: auff={} kunde={} sitze={}",
                            auffuehrung != null, kunde != null, sitzplaetze != null ? sitzplaetze.size() : 0);
                    showNotFound();
                    return;
                }

                // Buchung erzeugen und anzeigen
                LOGGER.debug("Starte finalizeBooking für kundeId={}, auffId={}, plaetze={}",
                        kunde.getId(), auffuehrung.getId(), sitzplaetze.size());
                finalizeBooking();
            } catch (Exception ex) {
                ex.printStackTrace();
                LOGGER.error("Exception in beforeEnter (Context-Buchung): {}", ex.getMessage(), ex);
                showNotFound();
            }
            return;
        }
        try {
            Long id = Long.valueOf(idStr);
            this.buchung = buchungRepository.findById(id).orElse(null);
            LOGGER.info("Lade Buchung per ID: {} -> vorhanden={}", id, this.buchung != null);
        } catch (NumberFormatException ex) {
            LOGGER.error("Ungültige Buchungs-ID im Pfad: {}", idStr);
            this.buchung = null;
        }
        if (this.buchung == null) {
            showNotFound();
        } else {
            buildUI();
        }
    }

    private void showNotFound() {
        removeAll();
        add(new H2("Buchung nicht gefunden"));
        add(new Paragraph("Die angegebene Buchung konnte nicht geladen werden."));
        add(new Button("Zur Startseite", e -> UI.getCurrent().navigate("")));
    }

    private void buildUI() {
        removeAll();

        H2 heading = new H2("Vielen Dank für Ihre Buchung!");
        heading.getStyle().set("color", "#f5f1e6").set("margin-top", "0");
        add(heading);

        Film film = buchung.getAuffuehrung() != null ? buchung.getAuffuehrung().getFilm() : null;

        HorizontalLayout details = new HorizontalLayout();
        details.setWidthFull();
        details.setPadding(true);
        details.setSpacing(true);
        details.setAlignItems(FlexComponent.Alignment.START);
        details.getStyle().set("background-color", "#2c2723").set("border-radius", "10px");

        Image poster = new Image(film != null ? film.getPosterUrl() : "", "Poster");
        poster.setWidth("160px");
        poster.setHeight("240px");
        poster.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.4)").set("border-radius", "8px");

        VerticalLayout info = new VerticalLayout();
        info.setPadding(false);
        info.setSpacing(true);

        H2 title = new H2(film != null ? film.getTitel() : "Buchung");
        title.getStyle().set("color", "#f5f1e6").set("margin-top", "0").set("text-shadow", "0 2px 6px rgba(0,0,0,0.7)");

        HorizontalLayout metaRow = new HorizontalLayout();
        metaRow.setSpacing(true);
        Div dauerBox = createInfoBox(film != null ? (film.getDauer() + " Minuten") : "-");
        String auffText = buchung.getAuffuehrung().getStartzeitpunkt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(auffuehrungsFormatter);
        Div auffBox = createInfoBox("Vorstellung: " + auffText);
        metaRow.add(dauerBox, auffBox);
        info.add(title, metaRow);

        details.add(poster, info);
        details.expand(info);
        add(details);

        add(new Hr());

        Paragraph nrInfo = new Paragraph("Ihre Buchungsnummer: " + (buchung.getBuchungsnummer() != null ? buchung.getBuchungsnummer() : "-"));
        Paragraph kundeInfo = new Paragraph("Kunde: " + (buchung.getKunde() != null ? buchung.getKunde().getName() : "-") + " (" + (buchung.getKunde() != null ? buchung.getKunde().getEmail() : "-") + ")");

        String plaetze = buchung.getBuchungSitzplaetze() != null ?
                buchung.getBuchungSitzplaetze().stream()
                        .map(bs -> bs.getSitzplatz() != null ? ("Reihe " + bs.getSitzplatz().getReihe().getReihennummer() + ", Platz " + bs.getSitzplatz().getPlatznummer()) : "?")
                        .reduce((a, b) -> a + " | " + b)
                        .orElse("-") : "-";
        Paragraph platzInfo = new Paragraph("Ausgewählte Plätze: " + plaetze);
        Paragraph preisInfo = new Paragraph("Gesamtpreis: " + String.format("%.2f", buchung.getGesamtpreis()) + " €");

        Button close = new Button("Zur Startseite", e -> UI.getCurrent().navigate(""));
        close.getStyle().set("background-color", "#f5e1a4").set("color", "black").set("font-weight", "bold");

        add(nrInfo, kundeInfo, platzInfo, preisInfo, close);
    }

    private Div createInfoBox(String text) {
        Div box = new Div();
        box.setText(text);
        box.getStyle()
                .set("background-color", "#3a332f")
                .set("color", "#f5f1e6")
                .set("padding", "6px 12px")
                .set("border-radius", "6px")
                .set("font-size", "13px");
        return box;
    }

    // Preis- und Nummern-Generierung wird im Service/Consumer gehandhabt

    private void finalizeBooking() {
        // 1) Command über Service senden
        List<Long> sitzplatzIds = sitzplaetze.stream().map(Sitzplatz::getId).toList();
        LOGGER.info("Sende BookingCommand via BuchungsService: auffId={}, kundeId={}, sitze={}",
                auffuehrung.getId(), kunde.getId(), sitzplatzIds.size());
        try {
            buchungsService.buchePlaetze(auffuehrung.getId(), kunde.getId(), sitzplatzIds);
        } catch (Exception ex) {
            LOGGER.error("Fehler beim Senden des BookingCommand: {}", ex.getMessage(), ex);
            showNotFound();
            return;
        }

        // 2) Asynchron warten, bis der Consumer die Buchung persistiert hat, dann UI anzeigen
        UI ui = UI.getCurrent();
        new Thread(() -> {
            long deadline = System.currentTimeMillis() + 3000; // bis zu 3s warten
            Buchung found = null;
            while (System.currentTimeMillis() < deadline) {
                try {
                    List<Buchung> all = buchungRepository.findAll();
                    found = all.stream()
                            .filter(b -> b.getKunde() != null && b.getAuffuehrung() != null)
                            .filter(b -> b.getKunde().getId().equals(kunde.getId())
                                    && b.getAuffuehrung().getId().equals(auffuehrung.getId()))
                            .sorted((a, b) -> Long.compare(
                                    b.getId() != null ? b.getId() : 0L,
                                    a.getId() != null ? a.getId() : 0L))
                            .findFirst()
                            .orElse(null);
                    // Warten bis Consumer Plätze UND Gesamtpreis gesetzt hat
                    if (found != null
                            && found.getBuchungSitzplaetze() != null
                            && !found.getBuchungSitzplaetze().isEmpty()
                            && found.getGesamtpreis() > 0.0) {
                        break;
                    }
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    LOGGER.warn("Polling-Fehler beim Lesen der Buchung: {}", e.getMessage());
                }
            }

            final Buchung result = found;
            ui.access(() -> {
                if (result == null) {
                    LOGGER.error("Buchung wurde nicht rechtzeitig gefunden.");
                    showNotFound();
                    return;
                }

                this.buchung = result;

                // 3) Falls aus Reservierung: Delete-Kommando senden
                if (ctx != null && ctx.getReservierungsId() != null) {
                    try {
                        LOGGER.info("Sende Kafka-Delete für reservierungId={}", ctx.getReservierungsId());
                        reservierungsService.loescheReservierung(ctx.getReservierungsId());
                    } catch (Exception ex) {
                        LOGGER.error("Kafka-Delete fehlgeschlagen (wird ignoriert in UI): {}", ex.getMessage());
                    }
                }

                // 4) BuchungContext leeren und Bestätigung anzeigen
                VaadinSession.getCurrent().setAttribute(BuchungContext.class, null);
                LOGGER.debug("BuchungContext aus Session entfernt");

                removeAll();
                buildUI();
            });
        }).start();
    }
}
