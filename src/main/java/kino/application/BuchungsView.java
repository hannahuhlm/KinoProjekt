package kino.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
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
import kino.application.data.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Route(value = "buchung", layout = MainViewLayout.class)
@PageTitle("Buchung")
@PermitAll
public class BuchungsView extends VerticalLayout implements BeforeEnterObserver {

    private final AuffuehrungRepository auffuehrungRepository;
    private final KundeRepository kundeRepository;
    private final SitzplatzRepository sitzplatzRepository;

    private final BuchungRepository buchungRepository;
    private final BuchungSitzplatzRepository buchungSitzplatzRepository;

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
            BuchungRepository buchungRepository,
            BuchungSitzplatzRepository buchungSitzplatzRepository
    ) {
        this.auffuehrungRepository = auffuehrungRepository;
        this.kundeRepository = kundeRepository;
        this.sitzplatzRepository = sitzplatzRepository;
        this.buchungRepository = buchungRepository;
        this.buchungSitzplatzRepository = buchungSitzplatzRepository;

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

        add(new Hr());

        Film film = auffuehrung.getFilm();

        // ===== Block wie Filmdetail (Poster + Kacheln, ohne Beschreibung) =====
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
        poster.getStyle()
                .set("box-shadow", "0 4px 12px rgba(0,0,0,0.4)")
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

        Div fskBox = createInfoBox("FSK 6");
        Div genreBox = createInfoBox("Abenteuer | Animation");
        Div dauerBox = createInfoBox(film.getDauer() + " Minuten");

        String filmstartText = film.getFilmstart() != null
                ? film.getFilmstart().format(filmStartFormatter)
                : "-";
        Div startBox = createInfoBox("Start: " + filmstartText);

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

    private String generateBuchungsnummer() {
        // z.B. einfache 8-stellige Nummer
        int num = (int) (Math.random() * 1_0000_0000);
        return String.format("%08d", num);
    }

    private void finalizeBooking() {
        //Preis berechnen
        double gesamtPreis = berechnePreis(sitzplaetze);

        // Buchung anlegen
        Buchung buchung = new Buchung();
        buchung.setKunde(kunde);
        buchung.setAuffuehrung(auffuehrung);
        buchung.setGesamtpreis(gesamtPreis);
        buchung.setBezahlt(false);
        buchung.setBuchungsZeitstempel(new Date());
        buchung.setBuchungsnummer(generateBuchungsnummer());

        buchungRepository.save(buchung);
        
        //Einnahmen der Aufführung hochzählen
        double neueEinnahmen = auffuehrung.getAktuelleEinnahmen() + gesamtPreis;
        auffuehrung.setAktuelleEinnahmen(neueEinnahmen);
        auffuehrungRepository.save(auffuehrung);

        // Sitzplätze auf belegt setzen + BuchungSitzplatz anlegen
        for (Sitzplatz s : sitzplaetze) {
            if (s.isFrei()) {
                s.setFrei(false);
                sitzplatzRepository.save(s);
            }

            BuchungSitzplatz bs = new BuchungSitzplatz();
            bs.setBuchung(buchung);
            bs.setSitzplatz(s);
            buchungSitzplatzRepository.save(bs);
        }

        // 4) BuchungContext leeren
        VaadinSession.getCurrent().setAttribute(BuchungContext.class, null);

        // 5) Danke-Dialog anzeigen
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        H2 title = new H2("Vielen Dank für Ihre Buchung!");
        Paragraph info = new Paragraph("Ihre Buchungsnummer: " + buchung.getBuchungsnummer());
        Paragraph hint = new Paragraph("Bitte notieren Sie sich diese Nummer für Rückfragen.");

        Button close = new Button("Schließen", e -> {
            dialog.close();
            // z.B. zurück auf Startseite oder Reservierungsübersicht
            UI.getCurrent().navigate("");
        });

        VerticalLayout layout = new VerticalLayout(title, info, hint, close);
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setAlignItems(Alignment.START);

        dialog.add(layout);
        dialog.open();
    }
}
