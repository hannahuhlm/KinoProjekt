package kino.application;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import kino.application.data.Kunde;
import kino.application.data.KundeRepository;
import kino.application.data.Reservierung;
import kino.application.data.ReservierungRepository;
import kino.application.data.ReservierungSitzplatz;
import kino.application.data.ReservierungSitzplatzRepository;
import kino.application.data.Sitzplatz;
import kino.application.data.SitzplatzRepository;
import kino.application.data.SitzreihenKategorie;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;


@Route(value = "reservierungen", layout = MainViewLayout.class)
@PageTitle("Reservierungen")
@PermitAll
public class ReservierungenView extends VerticalLayout {

    private final KundeRepository kundeRepository;
    private final ReservierungRepository reservierungRepository;
	private final SitzplatzRepository sitzplatzRepository;
	private final ReservierungSitzplatzRepository reservierungSitzplatzRepository;

    private TextField nameField;
    private TextField emailField;
    private Button suchenButton;
    private HorizontalLayout headerLayout;
    private VerticalLayout reservierungenLayout;
    private Div centralText; // Text für Anmeldung

    public ReservierungenView(KundeRepository kundeRepository,
                              ReservierungRepository reservierungRepository,
                              SitzplatzRepository sitzplatzRepository,
                              ReservierungSitzplatzRepository reservierungSitzplatzRepository) {
        this.kundeRepository = kundeRepository;
        this.reservierungRepository = reservierungRepository;
		this.sitzplatzRepository = sitzplatzRepository;
		this.reservierungSitzplatzRepository = reservierungSitzplatzRepository;

        setWidth("100%");

        // Anmeldebereich (Name + E-Mail + Button)
        headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.getStyle()
                .set("background-color", "#d8c49c")
                .set("border-radius", "10px")
                .set("padding-left", "20px");

        nameField = new TextField("Name");
        nameField.setWidth("300px");
        nameField.getStyle().set("border-radius", "5px").set("border", "1px solid #ccc");

        emailField = new TextField("E-Mail");
        emailField.setWidth("300px");
        emailField.getStyle().set("border-radius", "5px").set("border", "1px solid #ccc");

        suchenButton = new Button("Suchen");
        suchenButton.addClickListener(e -> login());

        headerLayout.add(nameField, emailField, suchenButton);
        headerLayout.setAlignItems(Alignment.CENTER);
        add(headerLayout);

        // Trendlinie unter der Leiste
        HorizontalLayout trendline = new HorizontalLayout();
        trendline.setWidthFull();
        trendline.getStyle()
                .set("border-top", "1px solid #b2b2b2")
                .set("margin", "20px 0");
        add(trendline);

        // Reservierungen Layout
        reservierungenLayout = new VerticalLayout();
        reservierungenLayout.setSpacing(true);
        reservierungenLayout.setWidthFull();
        add(reservierungenLayout);

        // Zentraler Text für Anmeldung
        centralText = new Div();
        centralText.setText("Bitte melden Sie sich an, um Ihre Reservierungen sehen zu können.");
        centralText.getStyle()
                .set("color", "#f5e1a4")
                .set("text-align", "center")
                .set("font-size", "1.2em");
        add(centralText);
    }

    private void login() {
        String email = emailField.getValue();
        String name = nameField.getValue(); // aktuell noch nicht genutzt

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

        	// --- NEU: Buchen-Button in jeder Bubble ---
        	Button buchenButton = new Button("Buchen");
        	// optionales Styling
        	buchenButton.getStyle()
        	        .set("margin-top", "10px")
        	        .set("border-radius", "20px");
        	// Noch keine Logik ("mehr noch nicht") – Klick-Listener kommt später
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

    /**
     * Entfernt eine Reservierung aus dem Kunden-Objekt (Beziehung lösen).
     * Die Reservierung bleibt in der DB bestehen, ist aber dem Kunden nicht mehr zugeordnet.
     * (Falls dein Mapping anders ist, kannst du hier auch komplett löschen.)
     */
    private void entferneReservierungAusKunde(Kunde kunde, Reservierung reservierung) {
        // Beziehung in beide Richtungen lösen, falls bidirektional
        if (kunde.getReservierungen() != null) {
            kunde.getReservierungen().remove(reservierung);
        }
        // Wenn Reservierung eine setKunde-Methode hat, hier auf null setzen:
        // reservierung.setKunde(null);

        // Änderungen speichern
        kundeRepository.save(kunde);
        // reservierungRepository.save(reservierung); // nur nötig, wenn setKunde(null) genutzt wird
    }

   private void deleteReservierung(Reservierung reservierung) {
    // 1) Reservierung zur Sicherheit frisch aus der DB holen
    Reservierung res = reservierungRepository.findById(reservierung.getId()).orElse(null);
    if (res == null) {
        Notification.show("Reservierung existiert bereits nicht mehr.");
        return;
    }

    // 2) Zugehörigen Kunden merken
    Kunde kunde = res.getKunde();

    // 3) Alle ReservierungSitzplatz-Einträge zu dieser Reservierung frisch aus der DB holen
    List<ReservierungSitzplatz> rspList = reservierungSitzplatzRepository.findByReservierung(res);

    // 4) Sitzplätze freigeben
    for (ReservierungSitzplatz rsp : rspList) {
        Sitzplatz sitzplatz = rsp.getSitzplatz();
        if (sitzplatz != null) {
            sitzplatz.setFrei(true);          // Platz wieder freigeben
            sitzplatzRepository.save(sitzplatz);
        }
    }

    // 5) Alle ReservierungSitzplatz-Einträge auf einmal löschen
    reservierungSitzplatzRepository.deleteAll(rspList);

    // 6) Reservierung aus der Kundenliste entfernen
    if (kunde != null && kunde.getReservierungen() != null) {
        kunde.getReservierungen()
                .removeIf(r -> r.getId() != null && r.getId().equals(res.getId()));
        kundeRepository.save(kunde);
    }

    // 7) Reservierung selbst löschen
    reservierungRepository.delete(res);

    // 8) Hinweis anzeigen
    Notification.show("Reservierung gelöscht und Plätze freigegeben.");

    // 9) UI aktualisieren – Kunde frisch laden und Reservierungen neu anzeigen
    if (kunde != null && kunde.getId() != null) {
        Kunde refreshed = kundeRepository.findById(kunde.getId()).orElse(kunde);
        showReservierungen(refreshed);
    } else {
        reservierungenLayout.removeAll();
    }
}





    private String formatPlatzInformationen(Reservierung reservierung) {
        StringBuilder platzInfo = new StringBuilder();

        long logeCount = reservierung.getReservierungSitzplaetze().stream()
                .filter(r -> r.getSitzplatz().getReihe().getKategorie() == SitzreihenKategorie.LOGE)
                .count();
        long parkettCount = reservierung.getReservierungSitzplaetze().stream()
                .filter(r -> r.getSitzplatz().getReihe().getKategorie() == SitzreihenKategorie.PARKETT)
                .count();
        long logeMitServiceCount = reservierung.getReservierungSitzplaetze().stream()
                .filter(r -> r.getSitzplatz().getReihe().getKategorie() == SitzreihenKategorie.LOGE_MIT_SERVICE)
                .count();

        if (logeCount > 0) platzInfo.append(logeCount).append("x Loge ");
        if (parkettCount > 0) platzInfo.append(parkettCount).append("x Parkett ");
        if (logeMitServiceCount > 0) platzInfo.append(logeMitServiceCount).append("x Loge mit Service");

        return platzInfo.toString().trim();
    }

    private double berechnePreis(Reservierung reservierung) {
        double preis = 0.0;
        for (ReservierungSitzplatz rsp : reservierung.getReservierungSitzplaetze()) {
            SitzreihenKategorie platzTyp = rsp.getSitzplatz().getReihe().getKategorie();
            if (SitzreihenKategorie.LOGE.equals(platzTyp)) {
                preis += 10.50;
            } else if (SitzreihenKategorie.PARKETT.equals(platzTyp)) {
                preis += 9.50;
            } else if (SitzreihenKategorie.LOGE_MIT_SERVICE.equals(platzTyp)) {
                preis += 12.00;
            }
        }
        return preis;
    }

    private void showCustomerNotFoundPopup() {
        Dialog dialog = new Dialog();
        dialog.add(new H3("Kein Konto gefunden"));
        dialog.add(new Paragraph("Es existiert kein Kundenkonto mit dieser E-Mail-Adresse."));

        Button closeButton = new Button("Schließen", e -> dialog.close());
        dialog.add(closeButton);

        dialog.open();
    }
}
