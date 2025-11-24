package kino.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import kino.application.buchung.BuchungContext;
import kino.application.data.Auffuehrung;
import kino.application.data.AuffuehrungRepository;
import kino.application.data.Film;
import kino.application.data.Kunde;
import kino.application.data.KundeRepository;
import kino.application.data.Reservierung;
import kino.application.data.ReservierungRepository;
import kino.application.data.ReservierungSitzplatz;
import kino.application.data.ReservierungSitzplatzRepository;
import kino.application.data.Sitzplatz;
import kino.application.data.SitzplatzRepository;
import kino.application.data.SitzreihenKategorie;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "buchung", layout = MainViewLayout.class)
@PageTitle("Buchung")
@PermitAll
public class BuchungsView extends VerticalLayout implements BeforeEnterObserver {

    private final AuffuehrungRepository auffuehrungRepository;
    private final KundeRepository kundeRepository;
    private final SitzplatzRepository sitzplatzRepository;
    private final ReservierungRepository reservierungRepository;
    private final ReservierungSitzplatzRepository reservierungSitzplatzRepository;

    private BuchungContext ctx;
    private Auffuehrung auffuehrung;
    private Kunde kunde;
    private List<Sitzplatz> sitzplaetze;

    private final DateTimeFormatter filmStartFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter auffuehrungsFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Autowired
    public BuchungsView(
            AuffuehrungRepository auffuehrungRepository,
            KundeRepository kundeRepository,
            SitzplatzRepository sitzplatzRepository,
            ReservierungRepository reservierungRepository,
            ReservierungSitzplatzRepository reservierungSitzplatzRepository
    ) {
        this.auffuehrungRepository = auffuehrungRepository;
        this.kundeRepository = kundeRepository;
        this.sitzplatzRepository = sitzplatzRepository;
        this.reservierungRepository = reservierungRepository;
        this.reservierungSitzplatzRepository = reservierungSitzplatzRepository;

        setWidthFull();
        setPadding(true);
        setSpacing(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        ctx = VaadinSession.getCurrent().getAttribute(BuchungContext.class);

        if (ctx == null) {
            Notification.show("Keine Buchungsdaten gefunden.");
            UI.getCurrent().navigate("");
            return;
        }

        auffuehrung = auffuehrungRepository.findById(ctx.getAuffuehrungId())
                .orElseThrow(() -> new IllegalStateException("Aufführung nicht gefunden"));

        kunde = kundeRepository.findById(ctx.getKundeId())
                .orElseThrow(() -> new IllegalStateException("Kunde nicht gefunden"));

        sitzplaetze = sitzplatzRepository.findAllById(ctx.getSitzplatzIds());

        buildUI();
    }

    private void buildUI() {
        removeAll();

        // ===== Überschrift =====
        H2 heading = new H2("Meine Buchung");
        heading.getStyle()
                .set("color", "#f5f1e6")
                .set("text-shadow", "0 2px 6px rgba(0,0,0,0.7)")
                .set("margin-top", "0");
        add(heading);

        // Optional eine Trennlinie
        add(new Hr());

        Film film = auffuehrung.getFilm();

        // ===== Details mit Poster (wie FilmDetailView, aber ohne Beschreibung) =====
        HorizontalLayout details = new HorizontalLayout();
        details.setWidthFull();
        details.setPadding(true);
        details.setSpacing(true);
        details.setAlignItems(Alignment.START);
        details.getStyle()
                .set("background-color", "#2c2723")
                .set("border-radius", "10px");

        Image poster = new Image(film.getPosterUrl(), "Poster");
        poster.setWidth("160px");
        poster.setHeight("240px");
        poster.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.4)")
                .set("border-radius", "8px");

        VerticalLayout info = new VerticalLayout();
        info.setPadding(false);
        info.setSpacing(true);

        H2 title = new H2(film.getTitel());
        title.getStyle()
                .set("color", "#f5f1e6")
                .set("margin-top", "0")
                .set("text-shadow", "0 2px 6px rgba(0,0,0,0.7)");

        HorizontalLayout metaRow = new HorizontalLayout();
        metaRow.setSpacing(true);

        // FSK & Genre sind bei dir im Beispiel hart kodiert – hier genauso:
        Div fskBox = createInfoBox("FSK 6");
        Div genreBox = createInfoBox("Abenteuer | Animation");

        Div dauerBox = createInfoBox(film.getDauer() + " Minuten");
        String filmstartText = film.getFilmstart() != null
                ? film.getFilmstart().format(filmStartFormatter)
                : "-";
        Div startBox = createInfoBox("Start: " + filmstartText);

        // Aufführungsdatum/-zeit extra Box
        String auffText = auffuehrung.getStartzeitpunkt()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(auffuehrungsFormatter);
        Div auffBox = createInfoBox("Vorstellung: " + auffText);

        metaRow.add(fskBox, genreBox, dauerBox, startBox, auffBox);

        info.add(title, metaRow);
        details.add(poster, info);
        details.expand(info);

        add(details);

        // ===== Buchungszusammenfassung =====
        add(new Hr());

        Paragraph kundeInfo = new Paragraph(
                "Kunde: " + kunde.getName() + " (" + kunde.getEmail() + ")"
        );
        kundeInfo.getStyle().set("font-weight", "bold");

        String plaetze = sitzplaetze.stream()
                .map(p -> "Reihe " + p.getReihe().getReihennummer()
                        + ", Platz " + p.getPlatznummer())
                .reduce((a, b) -> a + " | " + b)
                .orElse("-");

        Paragraph platzInfo = new Paragraph("Ausgewählte Plätze: " + plaetze);

        double gesamtPreis = berechnePreis(sitzplaetze);
        Paragraph preisInfo = new Paragraph(
                "Gesamtpreis: " + String.format("%.2f", gesamtPreis) + " €"
        );

        add(kundeInfo, platzInfo, preisInfo);

        // ===== Button: Buchung abschließen =====
        Button buchen = new Button("Buchung abschließen", e -> finalizeBooking());
        buchen.getStyle()
                .set("background-color", "#f5e1a4")
                .set("color", "black")
                .set("font-weight", "bold");

        add(buchen);
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

    private double berechnePreis(List<Sitzplatz> plaetze) {
        double preis = 0.0;
        for (Sitzplatz s : plaetze) {
            SitzreihenKategorie kat = s.getReihe().getKategorie();
            if (SitzreihenKategorie.LOGE.equals(kat)) {
                preis += 10.50;
            } else if (SitzreihenKategorie.PARKETT.equals(kat)) {
                preis += 9.50;
            } else if (SitzreihenKategorie.LOGE_MIT_SERVICE.equals(kat)) {
                preis += 12.00;
            }
        }
        return preis;
    }

    private void finalizeBooking() {
        // Reservierung anlegen
        Reservierung reservierung = new Reservierung();
        reservierung.setAuffuehrung(auffuehrung);
        reservierung.setKunde(kunde);
        reservierung.setStartZeitstempel(new java.util.Date());
        // falls du eine Nummer brauchst, kannst du hier ähnlich wie in der Sitzplatzwahl generieren
        reservierung.setReservierungsnummer((int) (Math.random() * 10000));

        reservierungRepository.save(reservierung);

        // Sitzplätze belegen & ReservierungSitzplatz anlegen
        for (Sitzplatz s : sitzplaetze) {
            s.setFrei(false);
            sitzplatzRepository.save(s);

            ReservierungSitzplatz rsp = new ReservierungSitzplatz();
            rsp.setReservierung(reservierung);
            rsp.setSitzplatz(s);
            reservierungSitzplatzRepository.save(rsp);
        }

        // Kontext leeren
        VaadinSession.getCurrent().setAttribute(BuchungContext.class, null);

        Notification.show("Buchung erfolgreich!", 3000, Notification.Position.MIDDLE);
        UI.getCurrent().navigate(ReservierungenView.class);
    }
}
