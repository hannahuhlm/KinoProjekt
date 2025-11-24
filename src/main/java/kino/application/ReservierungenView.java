package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import kino.application.data.Kunde;
import kino.application.data.KundeRepository;
import kino.application.data.Reservierung;
import kino.application.data.ReservierungRepository;

@Route(value = "reservierungen", layout = MainViewLayout.class)
@PermitAll
public class ReservierungenView extends VerticalLayout {

    private final KundeRepository kundeRepository;
    private final ReservierungRepository reservierungRepository;

    private TextField nameField;
    private TextField emailField;
    private Button loginButton;
    private Button createButton;
    private Kunde aktuellerKunde;

    public ReservierungenView(KundeRepository kundeRepository, ReservierungRepository reservierungRepository) {
        this.kundeRepository = kundeRepository;
        this.reservierungRepository = reservierungRepository;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Minilogin-Felder
        nameField = new TextField("Name");
        emailField = new TextField("E-Mail");
        
        // Buttons
        loginButton = new Button("OK", e -> login());
        createButton = new Button("Neuen Kunden anlegen");

        loginButton.addClickListener(event -> login());
        createButton.addClickListener(event -> createKundeDialog());

        // Styling für die Buttons
        loginButton.getStyle()
                .set("background-color", "black")
                .set("color", "white")
                .set("border-radius", "10px")
                .set("padding", "10px 20px")
                .set("margin-right", "10px");

        createButton.getStyle()
                .set("background-color", "white")
                .set("color", "black")
                .set("border-radius", "10px")
                .set("padding", "10px 20px");

        // Layout für die Buttons
        HorizontalLayout buttonsLayout = new HorizontalLayout(loginButton, createButton);
        buttonsLayout.setSpacing(true);

        // Layout für die Eingabefelder und Buttons
        HorizontalLayout loginLayout = new HorizontalLayout(nameField, emailField, buttonsLayout);
        loginLayout.setSpacing(true);
        loginLayout.getStyle().set("background-color", "#d8c49c")
                             .set("border-radius", "10px")
                             .set("padding", "10px")
                             .set("margin-top", "20px");

        add(loginLayout);
    }

    private void login() {
        String email = emailField.getValue();

        // Überprüfe, ob der Kunde bereits existiert
        Kunde kunde = kundeRepository.findByEmail(email);
        if (kunde != null) {
            // Zeige die Reservierungen des Kunden an
            aktuellerKunde = kunde;
            showReservierungen();
        } else {
            // Falls der Kunde nicht existiert, dann neues Popup für die Erstellung anzeigen
            createKundeDialog();
        }
    }

    private void showReservierungen() {
        if (aktuellerKunde != null) {
            add(new H3("Reservierungen von " + aktuellerKunde.getName()));
            if (aktuellerKunde.getReservierungen() != null && !aktuellerKunde.getReservierungen().isEmpty()) {
                aktuellerKunde.getReservierungen().forEach(reservierung -> {
                    add(new Paragraph("Reservierung #" + reservierung.getReservierungsnummer() + " - Startzeit: " + reservierung.getStartZeitstempel()));
                });
            } else {
                add(new Paragraph("Keine Reservierungen vorhanden."));
            }
        }
    }

    private void createKundeDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);

        TextField nameInput = new TextField("Name");
        TextField emailInput = new TextField("E-Mail");

        Button createButton = new Button("Erstellen", e -> {
            if (nameInput.isEmpty() || emailInput.isEmpty()) {
                Notification.show("Bitte geben Sie alle Felder ein.", 3000, Notification.Position.MIDDLE);
            } else {
                Kunde neuerKunde = new Kunde();
                neuerKunde.setName(nameInput.getValue());
                neuerKunde.setEmail(emailInput.getValue());
                kundeRepository.save(neuerKunde);

                Notification.show("Kunde erstellt!", 3000, Notification.Position.MIDDLE);
                dialog.close();
                aktuellerKunde = neuerKunde;
                showReservierungen();
            }
        });

        dialog.add(nameInput, emailInput, createButton);
        dialog.open();
    }
}
