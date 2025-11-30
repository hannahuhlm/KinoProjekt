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
import org.springframework.data.repository.CrudRepository;
import kino.application.service.ReservierungsService;
import kino.application.service.BuchungsService;

@Route(value = "sitzplatzwahl/:auffuehrungId", layout = MainViewLayout.class)
@PageTitle("Sitzplatzwahl")
@PermitAll
public class SitzplatzWahlView extends VerticalLayout implements BeforeEnterObserver {

    private final AuffuehrungRepository auffuehrungRepository;
    private final ReservierungsService reservierungsService;
    private final BuchungsService buchungsService;
    private Auffuehrung aktuelleAuffuehrung;
    private List<Sitzplatz> ausgewähltePlaetze = new ArrayList<>();
    private Kunde currentKunde;

    private final VerticalLayout content = new VerticalLayout();

    @Autowired
    private KundeRepository kundeRepository;
    @Autowired
    private SitzplatzRepository sitzplatzRepository;

    public SitzplatzWahlView(
            AuffuehrungRepository auffuehrungRepository,
            ReservierungsService reservierungsService,
            BuchungsService buchungsService) {
        this.auffuehrungRepository = auffuehrungRepository;
        this.reservierungsService = reservierungsService;
        this.buchungsService = buchungsService;

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

        // Buchung über Kafka senden
        List<Long> sitzplatzIds = ausgewähltePlaetze.stream()
                .map(Sitzplatz::getId)
                .toList();

        buchungsService.buchePlaetze(
                aktuelleAuffuehrung.getId(),
                currentKunde.getId(),
                sitzplatzIds
        );

        Notification.show("Buchung an Kafka gesendet!");

        // Auswahl leeren und UI aktualisieren
        ausgewähltePlaetze.clear();
        UI.getCurrent().getPage().reload();
    }

    private Button createSitzButton(Sitzplatz platz, SitzreihenKategorie kategorie) {
        HorizontalLayout iconLayout = new HorizontalLayout();
        iconLayout.setSpacing(false);
        iconLayout.setPadding(false);
        iconLayout.setAlignItems(Alignment.CENTER);

        Icon mainIcon;

        // Null-Check für kategorie
        if (kategorie == null) {
            mainIcon = VaadinIcon.USER.create();
        } else {
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

        // *** geändert: nicht mehr platz.isFrei(), sondern: belegt für diese Aufführung? ***
        if (isPlatzBelegtFuerAktuelleAuffuehrung(platz)) {
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

    Button weiterButton = new Button("Weiter");

    weiterButton.addClickListener(e -> {
        String name = nameField.getValue();
        String email = emailField.getValue();

        if (email == null || email.isBlank()) {
            Notification.show("Bitte E-Mail angeben.");
            return;
        }

        // 1. Versuchen, bestehenden Kunden zu finden
        Kunde kunde = kundeRepository.findByEmail(email);

        // 2. Wenn kein Kunde existiert -> neuen anlegen
        if (kunde == null) {
            if (name == null || name.isBlank()) {
                Notification.show("Bitte Name angeben, um einen neuen Kunden anzulegen.");
                return;
            }

            try {
                newCustomerButton.setEnabled(false);
                existingCustomerButton.setEnabled(false);
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
            } catch (Exception ex) {
                Notification.show("Fehler beim Speichern: " + ex.getMessage());
                dialog.close();
            } finally {
                newCustomerButton.setEnabled(true);
                existingCustomerButton.setEnabled(true);
            }
        });

        existingCustomerButton.addClickListener(e -> {
            String email = emailField.getValue();
            if (email == null || email.isBlank()) {
                Notification.show("Bitte E-Mail angeben.");
                return;
            }
            try {
                newCustomerButton.setEnabled(false);
                existingCustomerButton.setEnabled(false);
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
            } catch (Exception ex) {
                Notification.show("Fehler beim Laden/Reservieren: " + ex.getMessage());
                dialog.close();
            } finally {
                newCustomerButton.setEnabled(true);
                existingCustomerButton.setEnabled(true);
            }
        });

    layout.add(nameField, emailField, weiterButton);
    dialog.add(layout);
    dialog.open();
}


    private void saveReservierung(Kunde kunde) {
        if (aktuelleAuffuehrung == null || ausgewähltePlaetze.isEmpty()) {
            Notification.show("Bitte zuerst Sitzplätze auswählen.");
            return;
        }

        // Reservierung über Kafka senden
        List<Long> sitzplatzIds = ausgewähltePlaetze.stream()
                .map(Sitzplatz::getId)
                .toList();

        reservierungsService.reservierePlaetze(
                aktuelleAuffuehrung.getId(),
                kunde.getId(),
                kunde.getName(),
                sitzplatzIds
        );

        Notification.show("Reservierung wird verarbeitet...");

     // Auswahl leeren
        ausgewähltePlaetze.clear();

        // E-Mail in der Session merken, damit die ReservierungenView sie nutzen kann
        VaadinSession.getCurrent().setAttribute("kundenEmail", kunde.getEmail());

        // Zur Reservierungsseite navigieren
        UI.getCurrent().navigate(ReservierungenView.class);

    }

    private int generateReservierungsnummer() {
        return (int) (Math.random() * 10000); // Zufällige Reservierungsnummer
    }

    // *** NEU: Belegungsprüfung pro Aufführung ***
    private boolean isPlatzBelegtFuerAktuelleAuffuehrung(Sitzplatz platz) {
        if (aktuelleAuffuehrung == null || platz == null || platz.getId() == null) {
            return false;
        }

        Long platzId = platz.getId();

        // Reservierungen dieser Aufführung prüfen
        if (aktuelleAuffuehrung.getReservierungen() != null) {
            for (Reservierung r : aktuelleAuffuehrung.getReservierungen()) {
                if (r.getReservierungSitzplaetze() != null) {
                    for (ReservierungSitzplatz rsp : r.getReservierungSitzplaetze()) {
                        if (rsp.getSitzplatz() != null
                                && platzId.equals(rsp.getSitzplatz().getId())) {
                            return true;
                        }
                    }
                }
            }
        }

        // Buchungen dieser Aufführung prüfen
        if (aktuelleAuffuehrung.getBuchungen() != null) {
            for (Buchung b : aktuelleAuffuehrung.getBuchungen()) {
                if (b.getBuchungSitzplaetze() != null) {
                    for (BuchungSitzplatz bsp : b.getBuchungSitzplaetze()) {
                        if (bsp.getSitzplatz() != null
                                && platzId.equals(bsp.getSitzplatz().getId())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
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
