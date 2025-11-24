package kino.application;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

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

    public ReservierungenView(KundeRepository kundeRepository, ReservierungRepository reservierungRepository, SitzplatzRepository sitzplatzRepository, ReservierungSitzplatzRepository reservierungSitzplatzRepository) {
        this.kundeRepository = kundeRepository;
        this.reservierungRepository = reservierungRepository;
		this.sitzplatzRepository = sitzplatzRepository;
		this.reservierungSitzplatzRepository = reservierungSitzplatzRepository;

        setWidth("100%");

        // Anmeldebereich (Name + E-Mail + Button)
        headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.getStyle().set("background-color", "#d8c49c").set("border-radius", "10px").set("padding-left", "20px");

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
        trendline.getStyle().set("border-top", "1px solid #b2b2b2").set("margin", "20px 0");
        add(trendline);

        // Reservierungen Layout
        reservierungenLayout = new VerticalLayout();
        reservierungenLayout.setSpacing(true);
        reservierungenLayout.setWidthFull();
        add(reservierungenLayout);

        // Zentraler Text für Anmeldung
        centralText = new Div();
        centralText.setText("Bitte melden Sie sich an, um Ihre Reservierungen sehen zu können.");
        centralText.getStyle().set("color", "#f5e1a4").set("text-align", "center").set("font-size", "1.2em");
        add(centralText);
    }

    private void login() {
        String email = emailField.getValue();
        String name = nameField.getValue();

        Kunde kunde = kundeRepository.findByEmail(email);

        if (kunde != null) {
            // Kunden gefunden, Reservierungen anzeigen
            showReservierungen(kunde);
            centralText.setVisible(false); // Text ausblenden nach Anmeldung
        } else {
            // Kein Kunde gefunden, Pop-Up für Kundenanmeldung
            showCustomerNotFoundPopup();
        }
    }

    private void showReservierungen(Kunde kunde) {
        reservierungenLayout.removeAll();

        // Überschrift für die Reservierungen
        H3 reservierungenHeader = new H3("Reservierungen von " + kunde.getName());
        reservierungenHeader.getStyle().set("color", "#d1b58d");  // Beige Farbe für Überschrift
        reservierungenLayout.add(reservierungenHeader);

        // Reservierungen des Kunden abrufen und in Kacheln anzeigen
        List<Reservierung> reservierungen = kunde.getReservierungen();
        for (Reservierung reservierung : reservierungen) {
        	// Reservierungskarte erstellen
        	Div reservierungCard = new Div();
        	reservierungCard.addClassName("reservation-card");
        	reservierungCard.getStyle().set("padding", "20px")
        	    .set("background-color", "#e0e0e0") // Helles Grau für eine angenehme Farbe
        	    .set("border-radius", "8px")
        	    .set("margin-bottom", "15px")
        	    .set("display", "flex")
        	    .set("align-items", "center")
        	    .set("position", "relative"); // Relative Positionierung für Button innerhalb der Kachel

        	// Cover-Bild der Aufführung
        	Image coverImage = new Image(reservierung.getAuffuehrung().getFilm().getPosterUrl(), "Film Cover");
        	coverImage.setWidth("100px");
        	coverImage.setHeight("150px");

        	// Text-Bereich für Titel, Datum, Startzeit und Reservierungsnummer
        	VerticalLayout textLayout = new VerticalLayout();
        	textLayout.setSpacing(false);
        	textLayout.getStyle().set("margin-left", "15px");

        	// Film Titel
        	Paragraph filmTitle = new Paragraph(reservierung.getAuffuehrung().getFilm().getTitel());
        	filmTitle.getStyle().set("font-weight", "bold").set("font-size", "1.1em").set("color", "black");

        	// Datum der Reservierung
        	Paragraph reservierungsDatum = new Paragraph("Datum: " + reservierung.getAuffuehrung().getStartzeitpunkt());
        	reservierungsDatum.getStyle().set("color", "black");

        	// Startzeit und Reservierungsnummer
        	Paragraph startzeitReservierung = new Paragraph("Reservierung #" + reservierung.getReservierungsnummer());
        	startzeitReservierung.getStyle().set("color", "black");

        	textLayout.add(filmTitle, reservierungsDatum, startzeitReservierung);

        	// Vertikale Trennlinie
        	Div verticalLine = new Div();
        	verticalLine.getStyle().set("width", "1px").set("background-color", "#b2b2b2").set("height", "100px");

        	// Platzinformationen und Preis
        	VerticalLayout platzLayout = new VerticalLayout();
        	platzLayout.getStyle().set("margin-left", "15px");

        	// Plätze formatieren
        	String platzInfo = formatPlatzInformationen(reservierung);
        	Paragraph platzParagraph = new Paragraph("Plätze: " + platzInfo);
        	platzParagraph.getStyle().set("color", "black");

        	// Preis berechnen
        	double preis = berechnePreis(reservierung);  // Berechnung des Preises
        	Paragraph preisParagraph = new Paragraph("Preis: " + String.format("%.2f", preis) + " €");
        	preisParagraph.getStyle().set("color", "black");

        	platzLayout.add(platzParagraph, preisParagraph);

        	// Löschen-Button hinzufügen
        	Button deleteButton = new Button();
        	deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
        	deleteButton.getStyle().set("position", "absolute")
        	    .set("top", "10px")  // Positioniert den Button 10px vom oberen Rand der Kachel
        	    .set("right", "10px");  // Positioniert den Button 10px vom rechten Rand der Kachel

        	deleteButton.addClickListener(e -> deleteReservierung(reservierung)); // Klick-Listener für Löschen

        	// Kombiniere Cover, Text und Platzinformationen
        	HorizontalLayout reservierungContent = new HorizontalLayout(coverImage, textLayout, verticalLine, platzLayout);
        	reservierungCard.add(reservierungContent, deleteButton);  // Button hier mit hinzuzufügen

        	reservierungenLayout.add(reservierungCard);
        }
    }
    
    private void deleteReservierung(Reservierung reservierung) {
        // Reservierung löschen
        List<ReservierungSitzplatz> sitzplaetze = reservierung.getReservierungSitzplaetze();
        
        // Sitzplätze freigeben
        for (ReservierungSitzplatz rsp : sitzplaetze) {
            Sitzplatz sitzplatz = rsp.getSitzplatz();
            sitzplatz.setFrei(true); // Setzt den Sitzplatz auf "frei"
            sitzplatzRepository.save(sitzplatz); // Sitzplatz in der DB speichern
            reservierungSitzplatzRepository.delete(rsp); // Löschen der ReservierungSitzplatz-Referenz
        }

        // Reservierung löschen
        reservierungRepository.delete(reservierung);

        Notification.show("Reservierung gelöscht und Plätze freigegeben.");
        
        // Die Reservierungen neu laden, um die gelöschte Reservierung zu entfernen
        Kunde kunde = reservierung.getKunde();
        showReservierungen(kunde);
    }

    
    private String formatPlatzInformationen(Reservierung reservierung) {
        StringBuilder platzInfo = new StringBuilder();

        long logeCount = reservierung.getReservierungSitzplaetze().stream()
                .filter(r -> r.getSitzplatz().getReihe().getKategorie() == SitzreihenKategorie.LOGE).count();
        long parkettCount = reservierung.getReservierungSitzplaetze().stream()
                .filter(r -> r.getSitzplatz().getReihe().getKategorie() == SitzreihenKategorie.PARKETT).count();
        long logeMitServiceCount = reservierung.getReservierungSitzplaetze().stream()
                .filter(r -> r.getSitzplatz().getReihe().getKategorie() == SitzreihenKategorie.LOGE_MIT_SERVICE).count();

        if (logeCount > 0) platzInfo.append(logeCount).append("x Loge");
        if (parkettCount > 0) platzInfo.append(parkettCount).append("x Parkett");
        if (logeMitServiceCount > 0) platzInfo.append(logeMitServiceCount).append("x Loge mit Service");

        return platzInfo.toString();
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
