package kino.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.PermitAll;
import kino.application.buchung.BuchungContext;
import kino.application.data.*;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "sitzplatzwahl/:auffuehrungId", layout = MainViewLayout.class)
@PageTitle("Sitzplatzwahl")
@PermitAll
public class SitzplatzWahlView extends VerticalLayout implements BeforeEnterObserver {

    private final AuffuehrungRepository auffuehrungRepository;
    private Auffuehrung aktuelleAuffuehrung;
    private List<Sitzplatz> ausgewähltePlaetze = new ArrayList<>();
    private Kunde currentKunde;

    private final VerticalLayout content = new VerticalLayout();

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private ReservierungRepository reservierungRepository;

    @Autowired
    private ReservierungSitzplatzRepository reservierungSitzplatzRepository;

    public SitzplatzWahlView(AuffuehrungRepository auffuehrungRepository) {
        this.auffuehrungRepository = auffuehrungRepository;

        setSizeFull();
        setPadding(false);
        setSpacing(false);

        content.setWidth("90%");
        content.getStyle()
                .set("margin", "20px auto")
                .set("padding", "20px")
                .set("background", "white")
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 10px rgba(0,0,0,0.1)");

        add(content);
    }

    private HorizontalLayout createInfoLeiste(Auffuehrung auff) {
        HorizontalLayout bar = new HorizontalLayout();
        bar.setWidthFull();
        bar.setPadding(true);
        bar.setSpacing(true);
        bar.setAlignItems(Alignment.CENTER);
        bar.getStyle()
                .set("background", "#2c2723")
                .set("border-radius", "8px");

        VerticalLayout left = new VerticalLayout();
        left.setPadding(false);
        left.setSpacing(false);

        LocalDate datumLocal = auff.getStartzeitpunkt().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        String datum = datumLocal.format(DateTimeFormatter.ofPattern("E dd.MM.", Locale.GERMAN));

        String uhrzeit = auff.getStartzeitpunkt().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        String headerText = datum + " • " + uhrzeit + " Uhr • Saal " + auff.getSaal().getName();

        Paragraph header = new Paragraph(headerText);
        header.getStyle()
                .set("color", "#dcdcdc")
                .set("font-size", "14px");

        Paragraph titel = new Paragraph(auff.getFilm().getTitel());
        titel.getStyle()
                .set("color", "white")
                .set("font-size", "22px")
                .set("font-weight", "bold");

        left.add(header, titel);

        Image poster = new Image(auff.getFilm().getPosterUrl(), "Poster");
        poster.setHeight("80px");
        poster.getStyle()
                .set("border-radius", "6px")
                .set("box-shadow", "0 0 6px rgba(0,0,0,0.4)");

        bar.add(left, poster);
        bar.expand(left);

        return bar;
    }

    private void buildSitzplatzLayout(Kinosaal saal) {
        content.add(new Hr());

        VerticalLayout sitzLayout = new VerticalLayout();
        sitzLayout.setWidthFull();
        sitzLayout.setSpacing(true);

        List<Sitzreihe> reihen = saal.getReihen()
                .stream()
                .distinct()
                .sorted(Comparator.comparing(Sitzreihe::getReihennummer))
                .toList();

        for (Sitzreihe reihe : reihen) {
            HorizontalLayout reihenLayout = new HorizontalLayout();
            reihenLayout.setSpacing(false);
            reihenLayout.setPadding(false);
            reihenLayout.setAlignItems(Alignment.CENTER);
            reihenLayout.getStyle()
                    .set("margin-left", "40px")
                    .set("margin-right", "40px");

            Span reihenLabel = new Span(String.valueOf(reihe.getReihennummer()));
            reihenLabel.getStyle()
                    .set("font-weight", "bold")
                    .set("margin-right", "10px");

            reihenLayout.add(reihenLabel);

            for (Sitzplatz platz : reihe.getPlaetze()) {
                reihenLayout.add(createSitzButton(platz, reihe.getKategorie()));
            }

            sitzLayout.add(reihenLayout);
        }

        content.add(sitzLayout);

        // Reservierungs- und Warenkorb-Buttons
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        
        //reservierungsbuttons
     // Reservierungs-Button
        Button reservierungsButton = new Button("Reservieren");
        reservierungsButton.getStyle().set("background", "white").set("color", "black");
        reservierungsButton.addClickListener(event -> openCustomerDialog(false)); // <-- Reservierung

        // Direktbuchungs-Button
        Button buchungsButton = new Button("Direktbuchung");
        buchungsButton.getStyle().set("background", "#f5f5dc").set("color", "black");

        buchungsButton.addClickListener(e -> {
            if (aktuelleAuffuehrung == null || ausgewähltePlaetze.isEmpty()) {
                Notification.show("Bitte Aufführung und Sitzplätze auswählen!");
                return;
            }

            if (currentKunde == null) {
                // Popup im Direktbuchungs-Modus
                openCustomerDialog(true);
            } else {
                // Kunde schon gewählt → direkt los
                startDirektbuchung();
            }
        });


        buttonLayout.add(reservierungsButton, buchungsButton);
        content.add(buttonLayout);
    }

    private void startDirektbuchung() {
        if (aktuelleAuffuehrung == null || currentKunde == null || ausgewähltePlaetze.isEmpty()) {
            Notification.show("Bitte Kunde, Aufführung und Sitzplätze auswählen!");
            return;
        }

        BuchungContext ctx = new BuchungContext();
        ctx.setAuffuehrungId(this.aktuelleAuffuehrung.getId());
        ctx.setKundeId(currentKunde.getId());
        ctx.setSitzplatzIds(ausgewähltePlaetze.stream().map(Sitzplatz::getId).toList());

        VaadinSession.getCurrent().setAttribute(BuchungContext.class, ctx);
        UI.getCurrent().navigate(BuchungsView.class);
    }

    private Button createSitzButton(Sitzplatz platz, SitzreihenKategorie kategorie) {
        HorizontalLayout iconLayout = new HorizontalLayout();
        iconLayout.setSpacing(false);
        iconLayout.setPadding(false);
        iconLayout.setAlignItems(Alignment.CENTER);

        Icon mainIcon;

        switch (kategorie) {
            case PARKETT -> mainIcon = VaadinIcon.USER.create();
            case LOGE -> mainIcon = VaadinIcon.GROUP.create();
            case LOGE_MIT_SERVICE -> {
                mainIcon = VaadinIcon.GROUP.create();
                Icon service = VaadinIcon.COFFEE.create();
                service.setSize("10px");
                iconLayout.add(service);
            }
            default -> mainIcon = VaadinIcon.USER.create();
        }

        mainIcon.setSize("14px");
        iconLayout.addComponentAsFirst(mainIcon);

        Button btn = new Button(iconLayout);
        btn.getStyle()
                .set("border-radius", "4px")
                .set("padding", "2px")
                .set("height", "30px")
                .set("width", "30px")
                .set("margin", "2px");

        if (!platz.isFrei()) {
            btn.getStyle().set("background", "#9e9e9e");
            btn.setEnabled(false);
        } else {
            btn.getStyle().set("background", "#4caf50").set("color", "white");
            btn.addClickListener(e -> {
                if (ausgewähltePlaetze.contains(platz)) {
                    // Sitz wird abgewählt
                    ausgewähltePlaetze.remove(platz);
                    btn.getStyle().set("background", "#4caf50");  // zurück zur grünen Farbe
                    System.out.println("Sitz entfernt: " + platz.getPlatznummer());
                } else {
                    // Sitz wird ausgewählt
                    ausgewähltePlaetze.add(platz);
                    btn.getStyle().set("background", "#ff9800");  // Markierung für ausgewählt (z.B. orange)
                    System.out.println("Sitz hinzugefügt: " + platz.getPlatznummer());
                }
            });

            System.out.println("Ausgewählte Plätze: " + ausgewähltePlaetze.size());

        }

        return btn;
    }

    private void openCustomerDialog(Boolean isDirektBuchung) {
    	Dialog dialog = new Dialog();

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);

        TextField nameField = new TextField("Name");
        TextField emailField = new TextField("E-Mail");
        Button newCustomerButton = new Button("Neuen Kunden anlegen");
        Button existingCustomerButton = new Button("Bereits vorhandenen Kunden wählen");

        newCustomerButton.addClickListener(e -> {
            String name = nameField.getValue();
            String email = emailField.getValue();

            if (name == null || name.isBlank() || email == null || email.isBlank()) {
                Notification.show("Bitte Name und E-Mail angeben.");
                return;
            }

            Kunde newKunde = new Kunde();
            newKunde.setName(name);
            newKunde.setEmail(email);
            kundeRepository.save(newKunde);
            this.currentKunde = newKunde;

            if (isDirektBuchung) {
                dialog.close();
                startDirektbuchung();
            } else {
                saveReservierung(newKunde);
                dialog.close();
            }
        });

        existingCustomerButton.addClickListener(e -> {
            String email = emailField.getValue();
            if (email == null || email.isBlank()) {
                Notification.show("Bitte E-Mail angeben.");
                return;
            }

            Kunde existingKunde = kundeRepository.findByEmail(email);
            if (existingKunde != null) {
                this.currentKunde = existingKunde;

                if (isDirektBuchung) {
                    dialog.close();
                    startDirektbuchung();
                } else {
                    saveReservierung(existingKunde);
                    dialog.close();
                }
            } else {
                Notification.show("Kunde nicht gefunden. Bitte einen neuen Kunden anlegen.");
            }
        });

        layout.add(nameField, emailField, newCustomerButton, existingCustomerButton);
        dialog.add(layout);
        dialog.open();
    }

    private void saveReservierung(Kunde kunde) {
        // Reservierung erstellen
        Reservierung reservierung = new Reservierung();
        reservierung.setKunde(kunde);
        reservierung.setAuffuehrung(aktuelleAuffuehrung);
        reservierung.setStartZeitstempel(new java.util.Date());
        reservierung.setReservierungsnummer(generateReservierungsnummer());

        reservierungRepository.save(reservierung);

        // Sitzplätze reservieren
        for (Sitzplatz platz : ausgewähltePlaetze) {
            ReservierungSitzplatz reservierungSitzplatz = new ReservierungSitzplatz();
            reservierungSitzplatz.setReservierung(reservierung);
            reservierungSitzplatz.setSitzplatz(platz);
            reservierungSitzplatzRepository.save(reservierungSitzplatz);
        }

        Notification.show("Reservierung erfolgreich!");
    }

    private int generateReservierungsnummer() {
        return (int) (Math.random() * 10000); // Zufällige Reservierungsnummer
    }

    
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long auffId = event.getRouteParameters()
                .get("auffuehrungId")
                .map(Long::parseLong)
                .orElse(null);

        content.removeAll();

        if (auffId == null) {
            content.add(new H2("Ungültige Aufführung-ID"));
            return;
        }

        auffuehrungRepository.findById(auffId).ifPresentOrElse(
            auff -> {
                this.aktuelleAuffuehrung = auff;

                content.add(createInfoLeiste(auff));
                buildSitzplatzLayout(auff.getSaal());
            },
            () -> content.add(new H2("Aufführung nicht gefunden"))
        );
    }
}
