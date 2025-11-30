package kino.application;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
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
import jakarta.transaction.Transactional;
import kino.application.buchung.BuchungContext;
import kino.application.data.Auffuehrung;
import kino.application.data.Film;
import kino.application.data.Kunde;
import kino.application.data.Reservierung;
import kino.application.data.KundeRepository;
import kino.application.data.ReservierungRepository;
import kino.application.data.ReservierungSitzplatz;
import kino.application.data.Sitzplatz;
import kino.application.data.SitzreihenKategorie;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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

    private final KundeRepository kundeRepository;
    private final ReservierungRepository reservierungRepository;

    private TextField nameField;
    private EmailField emailField;
    private Button searchButton;

    private H2 reservierungenTitel;
    private VerticalLayout reservierungenContainer;

    private Kunde aktuellerKunde;

    @Autowired
    public ReservierungenView(KundeRepository kundeRepository,
            ReservierungRepository reservierungRepository) {
		this.kundeRepository = kundeRepository;
		this.reservierungRepository = reservierungRepository;
		
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
	        emailField.setValue(emailFromSession);
	        
	        // Kurze Verzögerung, damit neue Reservierung sicher in DB ist
	        UI ui = UI.getCurrent();
	        new Thread(() -> {
	            try {
	                Thread.sleep(200); // 0.2 Sekunden zusätzliche Sicherheit
	                ui.access(() -> {
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

        if (email == null || email.isBlank()) {
            Notification.show("Bitte E-Mail-Adresse eingeben.");
            return;
        }

        Kunde kunde = kundeRepository.findByEmail(email);
        if (kunde == null) {
            aktuellerKunde = null;
            reservierungenTitel.setVisible(false);
            reservierungenContainer.removeAll();
            Notification.show("Kein Kunde mit dieser E-Mail gefunden.");
            return;
        }

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

        // Debug: Alle Reservierungen ausgeben
        System.out.println(">>> Alle Reservierungen für " + aktuellerKunde.getName() + ": " + aktuellerKunde.getReservierungen().size());
        aktuellerKunde.getReservierungen().forEach(r -> {
            System.out.println("  - Reservierung #" + r.getReservierungsnummer() 
                + ", Aufführung: " + (r.getAuffuehrung() != null ? r.getAuffuehrung().getId() : "null")
                + ", Startzeit: " + (r.getAuffuehrung() != null && r.getAuffuehrung().getStartzeitpunkt() != null 
                    ? r.getAuffuehrung().getStartzeitpunkt() : "null"));
        });

        List<Reservierung> zukunftsReservierungen = aktuellerKunde.getReservierungen().stream()
                .filter(r -> r.getAuffuehrung() != null
                        && r.getAuffuehrung().getStartzeitpunkt() != null
                        && r.getAuffuehrung().getStartzeitpunkt().after(jetzt))
                .sorted(Comparator.comparing(
                        r -> r.getAuffuehrung().getStartzeitpunkt()))
                .collect(Collectors.toList());

        System.out.println(">>> Zukünftige Reservierungen: " + zukunftsReservierungen.size());

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
        Span preisSpan = new Span("Preis: " + formatierePreis(berechnePreis(reservierung)));

        // Buttons
        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setSpacing(true);

        Button buchenButton = new Button("Buchen");
        buchenButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buchenButton.getStyle().set("color", "#c76b28");
        buchenButton.addClickListener(e -> starteBuchungAusReservierung(reservierung));

        //löscvh button 
        Button loeschenButton = new Button(new Icon(VaadinIcon.TRASH));
        loeschenButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        loeschenButton.getElement().setProperty("title", "Reservierung löschen");
        loeschenButton.addClickListener(e -> loescheReservierung(reservierung));

        buttonRow.add(buchenButton, loeschenButton);

        rechts.add(plaetzeSpan, preisSpan, buttonRow);

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

    /**
     * Preisberechnung – vorerst Dummy (z.B. 9.50 € pro Platz),
     * damit die Oberfläche funktioniert. Kannst du später
     * an dein Preissystem anpassen.
     */
    private double berechnePreis(Reservierung reservierung) {
        int anzahlPlaetze = reservierung.getReservierungSitzplaetze() == null
                ? 0
                : reservierung.getReservierungSitzplaetze().size();
        double preisProPlatz = 9.50;
        return anzahlPlaetze * preisProPlatz;
    }

    private String formatierePreis(double wert) {
        return String.format("%.2f €", wert);
    }

    /**
     * Löscht die Reservierung:
     * - aus der Datenbank,
     * - aktualisiert danach den Kunden aus der DB,
     * - aktualisiert die Oberfläche.
     */

    @Transactional
    private void loescheReservierung(Reservierung reservierung) {
        if (reservierung == null || reservierung.getId() == null) {
            return;
        }

        // Direkt aus der DB löschen
        reservierungRepository.deleteById(reservierung.getId());

        // Kunde nach dem Löschen frisch einlesen,
        // damit die Reservierungsliste aktuell ist
        if (aktuellerKunde != null) {
            aktuellerKunde = kundeRepository
                    .findById(aktuellerKunde.getId())
                    .orElse(null);
        }

        // UI aktualisieren
        aktualisiereReservierungsAnzeige();
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

        // In Session legen und zur Buchungsseite navigieren
        VaadinSession.getCurrent().setAttribute(BuchungContext.class, ctx);
        UI.getCurrent().navigate(BuchungsView.class);
    }

}
