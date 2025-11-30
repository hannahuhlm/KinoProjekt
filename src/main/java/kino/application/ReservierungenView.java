package kino.application;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.PermitAll;
import kino.application.buchung.BuchungContext;
import kino.application.data.Auffuehrung;
import kino.application.data.Film;
import kino.application.data.Kunde;
import kino.application.data.Reservierung;
import kino.application.data.KundeRepository;
// Entfernt: nicht mehr benötigt in dieser View
import kino.application.data.ReservierungSitzplatz;
import kino.application.data.Sitzplatz;
import kino.application.data.SitzreihenKategorie;
import kino.application.service.BuchungsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Seite "Reservierungen" – zeigt die Reservierungen eines Kunden an
 * und erlaubt das Löschen einer Reservierung und die Buchung.
 */
@PageTitle("Reservierungen")
@Route(value = "reservierungen", layout = MainViewLayout.class)
@PermitAll
public class ReservierungenView extends VerticalLayout {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservierungenView.class);

    private final KundeRepository kundeRepository;
    // Entfernt ungenutzte Repositories (Delete via Kafka, kein Direktzugriff nötig)
    private final kino.application.service.ReservierungsService reservierungsService;
    private final BuchungsService buchungsService;

    private TextField nameField;
    private EmailField emailField;
    private Button searchButton;

    private H2 reservierungenTitel;
    private VerticalLayout reservierungenContainer;

    private Kunde aktuellerKunde;

    @Autowired
        public ReservierungenView(KundeRepository kundeRepository,
            kino.application.service.ReservierungsService reservierungsService,
            BuchungsService buchungsService) {
		this.kundeRepository = kundeRepository;
		this.reservierungsService = reservierungsService;
        this.buchungsService = buchungsService;
		
		setWidthFull();
		setMinHeight("100vh");           
		getStyle().set("background-color", "#241f20");
		
		setPadding(false);
		setSpacing(false);
		
		createSearchBar();
		createContentArea();
		
        //email bei weiterleitung aus sitzplatzwahlview übernehmen
		String emailFromSession = (String) VaadinSession.getCurrent().getAttribute("kundenEmail");

	    if (emailFromSession != null && !emailFromSession.isBlank()) {
            LOGGER.info("ReservierungenView init: emailFromSession='{}'", emailFromSession);
	        emailField.setValue(emailFromSession);
	        
	        // Kurze Verzögerung, damit neue Reservierung sicher in DB ist
	        UI ui = UI.getCurrent();
	        new Thread(() -> {
	            try {
                    Thread.sleep(800); // 0.8 Sekunden zusätzliche Sicherheit für Kafka-Verarbeitung
	                ui.access(() -> {
                        LOGGER.debug("Auto-loading reservations after delay for email='{}'", emailFromSession);
	                    ladeKundeUndReservierungen();
	                });
	            } catch (InterruptedException ex) {
	                ex.printStackTrace();
	            }
	        }).start();

	        // optional: wieder löschen, damit es nur einmal automatisch passiert
	        VaadinSession.getCurrent().setAttribute("kundenEmail", null);
	    }
	}


    private void createSearchBar() {
        // Obere beige Zeile
        HorizontalLayout searchBar = new HorizontalLayout();
        searchBar.setWidthFull();
        searchBar.setPadding(true);
        searchBar.setSpacing(true);
        searchBar.setAlignItems(FlexComponent.Alignment.CENTER);
        searchBar.getStyle().set("background-color", "#f3e0b5");

        nameField = new TextField("Name");
        nameField.setPlaceholder("Name");
        nameField.setWidth("300px");

        emailField = new EmailField("E-Mail");
        emailField.setPlaceholder("E-Mail");
        emailField.setWidth("400px");

        searchButton = new Button("OK");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.getStyle().set("background-color", "#c76b28");

        searchButton.addClickListener(e -> ladeKundeUndReservierungen());

        searchBar.add(nameField, emailField, searchButton);
        add(searchBar);
    }

    private void createContentArea() {
        // Überschrift
        reservierungenTitel = new H2();
        reservierungenTitel.getStyle()
        	.set("color", "#e0c184")
        	.set("margin-left", "300px")
        	.set("margin-top", "20px");
        reservierungenTitel.setVisible(false);

        // Container für die Kacheln
        reservierungenContainer = new VerticalLayout();
        reservierungenContainer.setWidthFull();
        reservierungenContainer.setPadding(false);
        reservierungenContainer.setSpacing(true);
        reservierungenContainer.getStyle().set("max-width", "1200px");
        reservierungenContainer.getStyle().set("margin", "0 auto 0 auto");

        add(reservierungenTitel, reservierungenContainer);
    }



    private void ladeKundeUndReservierungen() {
        String email = emailField.getValue();
        LOGGER.info("ladeKundeUndReservierungen called with email='{}'", email);

        if (email == null || email.isBlank()) {
            Notification.show("Bitte E-Mail-Adresse eingeben.");
            return;
        }

        Kunde kunde = kundeRepository.findByEmail(email);
        if (kunde == null) {
            LOGGER.warn("Kein Kunde mit email='{}' gefunden", email);
            aktuellerKunde = null;
            reservierungenTitel.setVisible(false);
            reservierungenContainer.removeAll();
            Notification.show("Kein Kunde mit dieser E-Mail gefunden.");
            return;
        }

        LOGGER.debug("Kunde gefunden: id={}, name={} -> Reservierungen werden geladen", kunde.getId(), kunde.getName());
        aktuellerKunde = kunde;
        aktualisiereReservierungsAnzeige();
    }

    private void aktualisiereReservierungsAnzeige() {
        reservierungenContainer.removeAll();

        if (aktuellerKunde == null) {
            reservierungenTitel.setVisible(false);
            return;
        }

        reservierungenTitel.setText("Reservierungen von " + aktuellerKunde.getName());
        reservierungenTitel.setVisible(true);

        Date jetzt = new Date();

        List<Reservierung> zukunftsReservierungen = aktuellerKunde.getReservierungen().stream()
                .filter(r -> r.getAuffuehrung() != null
                        && r.getAuffuehrung().getStartzeitpunkt() != null
                        && r.getAuffuehrung().getStartzeitpunkt().after(jetzt))
                .sorted(Comparator.comparing(
                        r -> r.getAuffuehrung().getStartzeitpunkt()))
                .collect(Collectors.toList());

        LOGGER.info("Anzahl zukünftiger Reservierungen für kundeId={}: {}", aktuellerKunde.getId(), zukunftsReservierungen.size());

        if (zukunftsReservierungen.isEmpty()) {
            reservierungenContainer.add(erzeugeKeineReservierungenHinweis());
            return;
        }

        zukunftsReservierungen.forEach(r ->
                reservierungenContainer.add(erzeugeReservierungsKachel(r)));
    }

    private Div erzeugeKeineReservierungenHinweis() {
        Div div = new Div(new Text("Es liegen keine zukünftigen Reservierungen vor."));
        div.getStyle().set("color", "#ffffff");
        div.getStyle().set("margin-left", "300px");
        return div;
    }

    private Div erzeugeReservierungsKachel(Reservierung reservierung) {
        Auffuehrung auffuehrung = reservierung.getAuffuehrung();
        Film film = auffuehrung.getFilm();

        Div card = new Div();
        card.addClassName("reservierungs-card");
        card.getStyle().set("background-color", "#f6e6bf");
        card.getStyle().set("color", "black");
        card.getStyle().set("border-radius", "8px");
        card.getStyle().set("padding", "20px");
        card.getStyle().set("display", "flex");
        card.getStyle().set("gap", "20px");
        card.getStyle().set("align-items", "stretch");
        card.getStyle().set("max-width", "900px");
        card.getStyle().set("margin-left", "300px");

        // Poster links
        Image poster = new Image();
        poster.setAlt(film.getTitel());
        poster.setWidth("120px");
        poster.setHeight("160px");
        poster.getStyle().set("object-fit", "cover");

        if (film.getPosterUrl() != null && !film.getPosterUrl().isBlank()) {
            poster.setSrc(film.getPosterUrl());
        } else {
            // Fallback-Bild (muss in /frontend/images liegen)
            poster.setSrc("images/placeholder-poster.png");
        }

        // mittlere Spalte: Filminfo
        VerticalLayout mitte = new VerticalLayout();
        mitte.setPadding(false);
        mitte.setSpacing(false);

        Span titelSpan = new Span(film.getTitel());
        titelSpan.getStyle().set("font-weight", "600");
        titelSpan.getStyle().set("font-size", "18px");
        titelSpan.getStyle().set("margin-bottom", "10px");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datumText = auffuehrung.getStartzeitpunkt() != null
                ? sdf.format(auffuehrung.getStartzeitpunkt())
                : "";

        Span datumSpan = new Span("Datum: " + datumText);
        Span reservierungsNrSpan =
                new Span("Reservierung #" + reservierung.getReservierungsnummer());

        mitte.add(titelSpan, datumSpan, reservierungsNrSpan);

        // rechte Spalte: Plätze, Preis, Buttons
        VerticalLayout rechts = new VerticalLayout();
        rechts.setPadding(false);
        rechts.setSpacing(false);
        rechts.setAlignItems(FlexComponent.Alignment.START);

        Span plaetzeSpan = new Span("Plätze: " + bauePlaetzeText(reservierung));
        // Preis konsistent über BuchungsService berechnen
        String preisText;
        String preisDetails = "";
        try {
            List<Long> sitzplatzIds = reservierung.getReservierungSitzplaetze().stream()
                    .map(rs -> rs.getSitzplatz())
                    .filter(Objects::nonNull)
                    .map(Sitzplatz::getId)
                    .filter(Objects::nonNull)
                    .toList();
            double total = buchungsService.berechneGesamtpreis(sitzplatzIds);
            preisText = formatierePreis(total);
            preisDetails = bauePreisAufschluesselung(reservierung);
        } catch (Exception ex) {
            preisText = "-"; // Fallback
        }
        Span preisSpan = new Span("Preis: " + preisText);
        if (!preisDetails.isBlank()) {
            preisSpan.getElement().setProperty("title", preisDetails);
        }

        // Buttons
        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setSpacing(true);

        Button buchenButton = new Button("Buchen");
        buchenButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buchenButton.getStyle().set("color", "#c76b28");
        buchenButton.addClickListener(e -> bestaetigeUndStarteBuchung(reservierung));

        //lösch button 
        Button loeschenButton = new Button(new Icon(VaadinIcon.TRASH));
        loeschenButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        loeschenButton.getElement().setProperty("title", "Reservierung löschen");
        loeschenButton.addClickListener(e -> loescheReservierung(reservierung));

        buttonRow.add(buchenButton, loeschenButton);

        // Subline mit Preisaufschlüsselung
        if (!preisDetails.isBlank()) {
            Span preisDetailsSpan = new Span(preisDetails);
            preisDetailsSpan.getStyle()
                    .set("color", "#555")
                    .set("font-size", "12px")
                    .set("margin-top", "4px");
            rechts.add(plaetzeSpan, preisSpan, preisDetailsSpan, buttonRow);
        } else {
            rechts.add(plaetzeSpan, preisSpan, buttonRow);
        }

        card.add(poster, mitte, rechts);
        return card;
    }

    //Baut einen kurzen Text zu den reservierten Plätzen
	private String bauePlaetzeText(Reservierung reservierung) {
	    if (reservierung.getReservierungSitzplaetze() == null
	            || reservierung.getReservierungSitzplaetze().isEmpty()) {
	        return "-";
	    }
	
	    // Kategorie je Sitz sammeln
	    Map<SitzreihenKategorie, Long> anzahlProKategorie =
	            reservierung.getReservierungSitzplaetze().stream()
	                    .map(rs -> rs.getSitzplatz())      
	                    .filter(Objects::nonNull)
	                    .map(sp -> sp.getReihe())   
	                    .filter(Objects::nonNull)
	                    .map(r -> r.getKategorie())
	                    .filter(Objects::nonNull)
	                    .collect(Collectors.groupingBy(
	                            Function.identity(),
	                            LinkedHashMap::new,
	                            Collectors.counting()
	                    ));
	
	    // Aus Map kategorie ziehen 
	    return anzahlProKategorie.entrySet().stream()
	            .map(e -> e.getValue() + "x " + e.getKey())
	            .collect(Collectors.joining(", "));
	}

    // Preisberechnung erfolgt zentral im BuchungsService

    private String formatierePreis(double wert) {
        return String.format("%.2f €", wert);
    }

    // Baut eine Preisaufschlüsselung je Kategorie, z. B. "2 x PARKETT à 12,00 € = 24,00 € | 1 x LOGE à 18,00 € = 18,00 €"
    private String bauePreisAufschluesselung(Reservierung reservierung) {
        if (reservierung == null || reservierung.getReservierungSitzplaetze() == null
                || reservierung.getReservierungSitzplaetze().isEmpty()) {
            return "";
        }

        Map<SitzreihenKategorie, Long> anzahlProKategorie = reservierung.getReservierungSitzplaetze().stream()
            .map(ReservierungSitzplatz::getSitzplatz)
            .filter(Objects::nonNull)
            .map(sp -> sp.getReihe())
            .filter(Objects::nonNull)
            .map(r -> r.getKategorie())
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (anzahlProKategorie.isEmpty()) {
            return "";
        }

        List<SitzreihenKategorie> reihenfolge = List.of(
                SitzreihenKategorie.PARKETT,
                SitzreihenKategorie.LOGE,
                SitzreihenKategorie.LOGE_MIT_SERVICE
        );

        List<String> teile = new java.util.ArrayList<>();
        for (SitzreihenKategorie kat : reihenfolge) {
            Long count = anzahlProKategorie.get(kat);
            if (count == null || count == 0) continue;
            double einzel = preisFuerKategorie(kat);
            double summe = einzel * count;
            String katName = kat.name().replace('_', ' ');
            teile.add(count + " x " + katName + " à " + formatierePreis(einzel) + " = " + formatierePreis(summe));
        }
        return String.join(" | ", teile);
    }

    // Muss konsistent sein mit BuchungsService-Preisen
    private double preisFuerKategorie(SitzreihenKategorie kat) {
        if (kat == null) return 0.0;
        switch (kat) {
            case LOGE_MIT_SERVICE:
                return 25.0;
            case LOGE:
                return 18.0;
            case PARKETT:
                return 12.0;
            default:
                return 0.0;
        }
    }

    /**
     * Löscht die Reservierung über Kafka.
     * Sendet Lösch-Command und aktualisiert nach kurzer Verzögerung die UI.
     */
    private void loescheReservierung(Reservierung reservierung) {
        if (reservierung == null || reservierung.getId() == null) {
            return;
        }

        try {
            // Lösch-Command über Kafka senden
            LOGGER.info("Sende Delete-Command für reservierungId={}", reservierung.getId());
            reservierungsService.loescheReservierung(reservierung.getId());
            
            Notification.show("Reservierung wird gelöscht...");
            
            // Kurze Verzögerung für Kafka-Verarbeitung, dann UI aktualisieren
            UI ui = UI.getCurrent();
            new Thread(() -> {
                try {
                    Thread.sleep(500); // 0.5 Sekunden für Lösch-Command
                    ui.access(() -> {
                        LOGGER.debug("Aktualisiere UI nach Lösch-Command für reservierungId={}", reservierung.getId());
                        // Kunde nach dem Löschen frisch einlesen
                        if (aktuellerKunde != null) {
                            aktuellerKunde = kundeRepository
                                    .findById(aktuellerKunde.getId())
                                    .orElse(null);
                        }
                        
                        // UI aktualisieren
                        aktualisiereReservierungsAnzeige();
                        Notification.show("Reservierung gelöscht.");
                    });
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
            
        } catch (Exception e) {
            LOGGER.error("Fehler beim Senden des Delete-Commands für reservierungId={}: {}", reservierung.getId(), e.getMessage(), e);
            Notification.show("Fehler beim Löschen der Reservierung: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void starteBuchungAusReservierung(Reservierung reservierung) {
        if (reservierung == null) {
            Notification.show("Reservierung konnte nicht geladen werden.");
            return;
        }
        if (reservierung.getAuffuehrung() == null || reservierung.getKunde() == null) {
            Notification.show("Reservierung ist unvollständig (kein Kunde oder keine Aufführung).");
            return;
        }
        if (reservierung.getReservierungSitzplaetze() == null
                || reservierung.getReservierungSitzplaetze().isEmpty()) {
            Notification.show("Diese Reservierung enthält keine Sitzplätze.");
            return;
        }

        // Sitzplatz-IDs aus der Reservierung sammeln
        List<Long> sitzplatzIds = reservierung.getReservierungSitzplaetze().stream()
                .map(ReservierungSitzplatz::getSitzplatz)
                .filter(sp -> sp != null && sp.getId() != null)
                .map(Sitzplatz::getId)
                .toList();

        if (sitzplatzIds.isEmpty()) {
            LOGGER.warn("Reservierung {} hat keine gültigen Sitzplatz-IDs", reservierung.getId());
            Notification.show("Sitzplätze konnten nicht ermittelt werden.");
            return;
        }

        // BuchungContext wie in SitzplatzWahlView aufbauen
        BuchungContext ctx = new BuchungContext();
        ctx.setAuffuehrungId(reservierung.getAuffuehrung().getId());
        ctx.setKundeId(reservierung.getKunde().getId());
        ctx.setSitzplatzIds(sitzplatzIds);
        //reservierungs id setzen um reservieung löschen zu können
        ctx.setReservierungsId(reservierung.getId());

        // In Session legen und explizit zur Alias-Route navigieren
        VaadinSession.getCurrent().setAttribute(BuchungContext.class, ctx);
        LOGGER.info("Starte Buchung aus Reservierung: resId={}, auffId={}, kundeId={}, sitze={}"
            , reservierung.getId(), ctx.getAuffuehrungId(), ctx.getKundeId(), sitzplatzIds.size());
        Notification.show("Buchung wird vorbereitet...");
        UI.getCurrent().navigate("buchung");
    }

    private void bestaetigeUndStarteBuchung(Reservierung reservierung) {
        if (reservierung == null) {
            Notification.show("Reservierung konnte nicht geladen werden.");
            return;
        }
        if (reservierung.getAuffuehrung() == null || reservierung.getKunde() == null) {
            Notification.show("Reservierung ist unvollständig (kein Kunde oder keine Aufführung).");
            return;
        }
        if (reservierung.getReservierungSitzplaetze() == null
                || reservierung.getReservierungSitzplaetze().isEmpty()) {
            Notification.show("Diese Reservierung enthält keine Sitzplätze.");
            return;
        }

        // Sitzplatz-IDs vorbereiten
        List<Long> sitzplatzIds = reservierung.getReservierungSitzplaetze().stream()
                .map(rs -> rs.getSitzplatz())
                .filter(sp -> sp != null && sp.getId() != null)
                .map(Sitzplatz::getId)
                .toList();
        if (sitzplatzIds.isEmpty()) {
            Notification.show("Sitzplätze konnten nicht ermittelt werden.");
            return;
        }

        double total;
        try {
            total = buchungsService.berechneGesamtpreis(sitzplatzIds);
        } catch (Exception ex) {
            Notification.show("Preisberechnung fehlgeschlagen: " + ex.getMessage());
            return;
        }

        String plaetzeText = reservierung.getReservierungSitzplaetze().stream()
                .map(rs -> rs.getSitzplatz())
                .filter(Objects::nonNull)
                .map(sp -> "Reihe " + sp.getReihe().getReihennummer() + ", Platz " + sp.getPlatznummer())
                .reduce((a, b) -> a + " | " + b)
                .orElse("-");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Kauf bestätigen");
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);

        Paragraph p1 = new Paragraph("Ausgewählte Plätze: " + plaetzeText);
        Paragraph p2 = new Paragraph("Gesamtpreis: " + String.format("%.2f", total) + " €");

        HorizontalLayout actions = new HorizontalLayout();
        Button abbrechen = new Button("Abbrechen", e -> dialog.close());
        Button bestaetigen = new Button("Jetzt kaufen", e -> {
            dialog.close();
            // Bestehenden Flow ausführen
            starteBuchungAusReservierung(reservierung);
        });
        actions.add(abbrechen, bestaetigen);

        layout.add(p1, p2, actions);
        dialog.add(layout);
        dialog.open();
    }

}
