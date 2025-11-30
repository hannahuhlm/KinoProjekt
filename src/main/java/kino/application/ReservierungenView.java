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
	        ladeKundeUndReservierungen();

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

        if (kunde != null) {
            // Kunde gefunden, Reservierungen anzeigen
            showReservierungen(kunde);
            centralText.setVisible(false); // Text ausblenden nach Anmeldung
        } else {
            // Kein Kunde gefunden, Pop-Up für Kundenanmeldung
            showCustomerNotFoundPopup();
        }
    }

    private void showReservierungen(Kunde kunde) {
        reservierungenLayout.removeAll();

        //Abgelaufene Reservierungen aufräumen und nur aktive zurückbekommen
        List<Reservierung> reservierungen = filterUndBereinigeReservierungen(kunde);

        // Überschrift für die Reservierungen
        H3 reservierungenHeader = new H3("Reservierungen von " + kunde.getName());
        reservierungenHeader.getStyle().set("color", "#d1b58d");  // Beige Farbe für Überschrift
        reservierungenLayout.add(reservierungenHeader);

        // Nur noch aktive Reservierungen in Kacheln anzeigen
        for (Reservierung reservierung : reservierungen) {
        	// Reservierungskarte erstellen
        	Div reservierungCard = new Div();
        	reservierungCard.addClassName("reservation-card");
        	reservierungCard.getStyle()
        	    .set("padding", "20px")
        	    .set("background-color", "#e0e0e0") // Helles Grau
        	    .set("border-radius", "8px")
        	    .set("margin-bottom", "15px")
        	    .set("display", "flex")
        	    .set("align-items", "center")
        	    .set("position", "relative"); // für den Delete-Button oben rechts

        	// Cover-Bild der Aufführung
        	Image coverImage = new Image(
        	        reservierung.getAuffuehrung().getFilm().getPosterUrl(), "Film Cover");
        	coverImage.setWidth("100px");
        	coverImage.setHeight("150px");

        	// Text-Bereich für Titel, Datum, Startzeit und Reservierungsnummer
        	VerticalLayout textLayout = new VerticalLayout();
        	textLayout.setSpacing(false);
        	textLayout.getStyle().set("margin-left", "15px");

        	// Film Titel
        	Paragraph filmTitle = new Paragraph(reservierung.getAuffuehrung().getFilm().getTitel());
        	filmTitle.getStyle()
        	        .set("font-weight", "bold")
        	        .set("font-size", "1.1em")
        	        .set("color", "black");

        	// Datum / Startzeit der Aufführung (aktuell einfach das Date-Objekt)
        	Paragraph reservierungsDatum =
        	        new Paragraph("Datum: " + reservierung.getAuffuehrung().getStartzeitpunkt());
        	reservierungsDatum.getStyle().set("color", "black");

        	// Reservierungsnummer
        	Paragraph startzeitReservierung =
        	        new Paragraph("Reservierung #" + reservierung.getReservierungsnummer());
        	startzeitReservierung.getStyle().set("color", "black");

        	textLayout.add(filmTitle, reservierungsDatum, startzeitReservierung);

        	// Vertikale Trennlinie
        	Div verticalLine = new Div();
        	verticalLine.getStyle()
        	        .set("width", "1px")
        	        .set("background-color", "#b2b2b2")
        	        .set("height", "100px");

        	// Platzinformationen und Preis
        	VerticalLayout platzLayout = new VerticalLayout();
        	platzLayout.getStyle().set("margin-left", "15px");

        	// Plätze formatieren
        	String platzInfo = formatPlatzInformationen(reservierung);
        	Paragraph platzParagraph = new Paragraph("Plätze: " + platzInfo);
        	platzParagraph.getStyle().set("color", "black");

        	// Preis berechnen
        	double preis = berechnePreis(reservierung);
        	Paragraph preisParagraph =
        	        new Paragraph("Preis: " + String.format("%.2f", preis) + " €");
        	preisParagraph.getStyle().set("color", "black");

        	platzLayout.add(platzParagraph, preisParagraph);

            // Buchen-Button in jeder Reservierung
            Button buchenButton = new Button("Jetzt buchen");
            buchenButton.getStyle()
                    .set("margin-top", "10px")
                    .set("border-radius", "20px")
                    .set("background", "#ff9800")
                    .set("color", "white");
            buchenButton.addClickListener(click -> {
                buchenButton.setEnabled(false); // Doppel-Klick vermeiden
                try {
                    Kunde resKunde = reservierung.getKunde();
                    if (resKunde == null) {
                        Notification.show("Kein Kunde zugeordnet – Buchung nicht möglich.");
                        return;
                    }
                    Long auffId = reservierung.getAuffuehrung() != null ? reservierung.getAuffuehrung().getId() : null;
                    if (auffId == null) {
                        Notification.show("Aufführung fehlt – Buchung abgebrochen.");
                        return;
                    }
                    // Sitzplatz-IDs ermitteln
                    java.util.List<Long> sitzplatzIds = reservierung.getReservierungSitzplaetze().stream()
                            .map(rsp -> rsp.getSitzplatz())
                            .filter(sp -> sp != null && sp.getId() != null)
                            .map(Sitzplatz::getId)
                            .toList();
                    if (sitzplatzIds.isEmpty()) {
                        Notification.show("Keine Sitzplätze gefunden.");
                        return;
                    }
                    // Buchung via Kafka-Service senden
                    buchungsService.buchePlaetze(auffId, resKunde.getId(), sitzplatzIds);
                    Notification.show("Buchung gesendet – wird verarbeitet.");
                    // Optional: Reservierung aus UI entfernen (optimistisch)
                    reservierungenLayout.remove(reservierungCard);
                } catch (Exception ex) {
                    Notification.show("Fehler beim Buchen: " + ex.getMessage());
                } finally {
                    buchenButton.setEnabled(true);
                }
            });
            platzLayout.add(buchenButton);

        	// Löschen-Button hinzufügen (oben rechts)
        	Button deleteButton = new Button();
        	deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
        	deleteButton.getStyle()
        	    .set("position", "absolute")
        	    .set("top", "10px")
        	    .set("right", "10px");

        	deleteButton.addClickListener(e -> deleteReservierung(reservierung)); // Klick-Listener für Löschen

        	// Kombiniere Cover, Text und Platzinformationen
        	HorizontalLayout reservierungContent =
        	        new HorizontalLayout(coverImage, textLayout, verticalLine, platzLayout);
        	reservierungCard.add(reservierungContent, deleteButton);

        	reservierungenLayout.add(reservierungCard);
        }
    }

    /**
     * Filtert Reservierungen eines Kunden:
     * - Reservierungen, deren Aufführung schon begonnen hat, werden:
     *   - NICHT zurückgegeben
     *   - aus dem Kunden-Objekt entfernt (Beziehung wird gelöst)
     */
    private List<Reservierung> filterUndBereinigeReservierungen(Kunde kunde) {
        List<Reservierung> aktiveReservierungen = new ArrayList<>();
        Date jetzt = new Date();

        List<Reservierung> alle = kunde.getReservierungen();
        if (alle == null) {
            return aktiveReservierungen;
        }

        // Über eine Kopie iterieren, um ConcurrentModification zu vermeiden
        for (Reservierung reservierung : new ArrayList<>(alle)) {
            Date start = reservierung.getAuffuehrung() != null
                    ? reservierung.getAuffuehrung().getStartzeitpunkt()
                    : null;

            if (start != null && start.before(jetzt)) {
                // Aufführung hat schon begonnen -> Reservierung aus dem Kunden lösen
                entferneReservierungAusKunde(kunde, reservierung);
            } else {
                aktiveReservierungen.add(reservierung);
            }
        }
        return aktiveReservierungen;
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
