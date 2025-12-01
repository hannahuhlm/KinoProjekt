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
import com.vaadin.flow.component.DetachEvent;
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
import kino.application.service.ReservierungsService;
import kino.application.service.BuchungsService;
import kino.application.admin.AdminUIEventBus;
import kino.application.kafka.events.AdminCommand;
import kino.application.kafka.events.AdminEvent;
import kino.application.kafka.producer.AdminCommandProducer;
import kino.application.kafka.producer.CustomerCommandProducer;

@Route(value = "sitzplatzwahl/:auffuehrungId", layout = MainViewLayout.class)
@PageTitle("Sitzplatzwahl")
@PermitAll
public class SitzplatzWahlView extends VerticalLayout implements BeforeEnterObserver {

    private final AuffuehrungRepository auffuehrungRepository;
    private final AdminCommandProducer adminCommandProducer;
    private final CustomerCommandProducer customerCommandProducer;
    private final ReservierungsService reservierungsService;
    private final BuchungsService buchungsService;
    private final kino.application.service.CustomerService customerService;
    private Auffuehrung aktuelleAuffuehrung;
    private List<Sitzplatz> ausgewähltePlaetze = new ArrayList<>();
    private Kunde currentKunde;
    private String lastCustomerEmail;

    private final VerticalLayout content = new VerticalLayout();

    @Autowired
    private KundeRepository kundeRepository;

    public SitzplatzWahlView(
            AuffuehrungRepository auffuehrungRepository,
            ReservierungsService reservierungsService,
            BuchungsService buchungsService,
            kino.application.service.CustomerService customerService,
            AdminCommandProducer adminCommandProducer,
            CustomerCommandProducer customerCommandProducer) {
        this.auffuehrungRepository = auffuehrungRepository;
        this.reservierungsService = reservierungsService;
        this.buchungsService = buchungsService;
        this.customerService = customerService;
        this.adminCommandProducer = adminCommandProducer;
        this.customerCommandProducer = customerCommandProducer;

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

        // React to CustomerEvents to show feedback when a customer is created/exists/fails
        customerReg = kino.application.customer.CustomerUIEventBus.register(ev -> {
            if (ev == null || ev.getEmail() == null) return;
            String email = ev.getEmail();
            getUI().ifPresent(ui -> ui.access(() -> {
                if (lastCustomerEmail != null && lastCustomerEmail.equalsIgnoreCase(email)) {
                    switch (ev.getStatus()) {
                        case SUCCESS -> com.vaadin.flow.component.notification.Notification.show(
                                "Kunde OK: " + email, 2000,
                                com.vaadin.flow.component.notification.Notification.Position.MIDDLE);
                        case FAILURE -> com.vaadin.flow.component.notification.Notification.show(
                                "Kunden-Anlage fehlgeschlagen: " + (ev.getMessage() != null ? ev.getMessage() : "Unbekannter Fehler"),
                                3000, com.vaadin.flow.component.notification.Notification.Position.MIDDLE);
                        case NOT_FOUND -> com.vaadin.flow.component.notification.Notification.show(
                                "Kunde nicht gefunden: " + email,
                                2000,
                                com.vaadin.flow.component.notification.Notification.Position.MIDDLE);
                    }
                }
            }));
        });
    }

    private kino.application.customer.CustomerUIEventBus.Registration customerReg;
    private AdminUIEventBus.Registration adminReg;

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

            // Plaetze deterministisch nach Platznummer sortieren, damit Links/Rechts konsistent ist
            reihe.getPlaetze().stream()
                    .sorted(Comparator.comparing(Sitzplatz::getPlatznummer))
                    .forEach(platz -> reihenLayout.add(createSitzButton(platz, reihe.getKategorie())));

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

        openConfirmDialogFuerDirektbuchung();
    }

    private void openConfirmDialogFuerDirektbuchung() {
        List<Long> sitzplatzIds = ausgewähltePlaetze.stream()
                .map(Sitzplatz::getId)
                .toList();

        double total;
        try {
            total = buchungsService.berechneGesamtpreis(sitzplatzIds);
        } catch (Exception ex) {
            Notification.show("Preisberechnung fehlgeschlagen: " + ex.getMessage());
            return;
        }

        String plaetzeText = ausgewähltePlaetze.stream()
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

            // Direkt über BuchungsView abschließen
            BuchungContext ctx = new BuchungContext();
            ctx.setAuffuehrungId(aktuelleAuffuehrung.getId());
            ctx.setKundeId(currentKunde.getId());
            ctx.setSitzplatzIds(sitzplatzIds);
            VaadinSession.getCurrent().setAttribute(BuchungContext.class, ctx);

            Notification.show("Kauf wird ausgeführt...");
            ausgewähltePlaetze.clear();
            UI.getCurrent().navigate("buchung");
        });
        actions.add(abbrechen, bestaetigen);

        layout.add(p1, p2, actions);
        dialog.add(layout);
        dialog.open();
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
            // Auch für belegte Plätze den Preis anzeigen (informativ)
            try {
                double preis = buchungsService.berechneGesamtpreis(java.util.List.of(platz.getId()));
                btn.getElement().setProperty("title", "Preis: " + String.format("%.2f", preis) + " €");
            } catch (Exception ignored) { }
        } else {
            btn.getStyle().set("background", "#4caf50").set("color", "white");
            // Preis im Tooltip anzeigen
            try {
                double preis = buchungsService.berechneGesamtpreis(java.util.List.of(platz.getId()));
                btn.getElement().setProperty("title", "Preis: " + String.format("%.2f", preis) + " €");
            } catch (Exception ignored) { }
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

            lastCustomerEmail = email;

            // Query customer via Kafka by email
            String corr = java.util.UUID.randomUUID().toString();
            final kino.application.customer.CustomerUIEventBus.Registration[] tmpRegHolder = new kino.application.customer.CustomerUIEventBus.Registration[1];
            tmpRegHolder[0] = kino.application.customer.CustomerUIEventBus.register(ev -> {
                if (ev == null || ev.getCorrelationId() == null || !ev.getCorrelationId().equals(corr)) return;
                getUI().ifPresent(ui2 -> ui2.access(() -> {
                    if (ev.getStatus() == kino.application.kafka.events.CustomerEvent.Status.SUCCESS && ev.getKundeId() != null) {
                        // Load Kunde entity by id to reuse existing flows
                        kundeRepository.findById(ev.getKundeId()).ifPresentOrElse(k -> {
                            this.currentKunde = k;
                            dialog.close();
                            if (isDirektBuchung) {
                                startDirektbuchung();
                            } else {
                                saveReservierung(k.getId(), k.getName(), k.getEmail());
                            }
                        }, () -> Notification.show("Kunde nicht gefunden"));
                    } else if (ev.getStatus() == kino.application.kafka.events.CustomerEvent.Status.NOT_FOUND) {
                        // Create via Kafka ensureCustomer - wartet auf CREATE Event
                        if (name == null || name.isBlank()) {
                            Notification.show("Bitte Name angeben, um einen neuen Kunden anzulegen.");
                            if (tmpRegHolder[0] != null) {
                                tmpRegHolder[0].remove();
                                tmpRegHolder[0] = null;
                            }
                            return;
                        }
                        
                        // Query-Listener entfernen bevor wir CREATE-Listener registrieren
                        if (tmpRegHolder[0] != null) {
                            tmpRegHolder[0].remove();
                            tmpRegHolder[0] = null;
                        }
                        
                        // Registriere Listener für CREATE Event
                        String createCorr = java.util.UUID.randomUUID().toString();
                        System.out.println(">>> Registriere CREATE-Listener mit correlationId: " + createCorr);
                        final kino.application.customer.CustomerUIEventBus.Registration[] createRegHolder = new kino.application.customer.CustomerUIEventBus.Registration[1];
                        createRegHolder[0] = kino.application.customer.CustomerUIEventBus.register(createEv -> {
                            System.out.println(">>> CREATE Event empfangen: status=" + (createEv != null ? createEv.getStatus() : "null") + ", corrId=" + (createEv != null ? createEv.getCorrelationId() : "null") + ", expected=" + createCorr);
                            if (createEv == null || createEv.getCorrelationId() == null || !createEv.getCorrelationId().equals(createCorr)) return;
                            getUI().ifPresent(ui3 -> ui3.access(() -> {
                                if (createEv.getStatus() == kino.application.kafka.events.CustomerEvent.Status.SUCCESS && createEv.getKundeId() != null) {
                                    System.out.println(">>> CREATE Event SUCCESS: kundeId=" + createEv.getKundeId() + ", isDirektBuchung=" + isDirektBuchung);
                                    kundeRepository.findById(createEv.getKundeId()).ifPresentOrElse(k -> {
                                        System.out.println(">>> Kunde geladen: id=" + k.getId() + ", name=" + k.getName());
                                        this.currentKunde = k;
                                        System.out.println(">>> currentKunde gesetzt: " + (this.currentKunde != null));
                                        dialog.close();
                                        if (isDirektBuchung) {
                                            System.out.println(">>> Rufe startDirektbuchung() auf...");
                                            System.out.println(">>> aktuelleAuffuehrung: " + (aktuelleAuffuehrung != null));
                                            System.out.println(">>> currentKunde: " + (currentKunde != null));
                                            System.out.println(">>> ausgewähltePlaetze: " + ausgewähltePlaetze.size());
                                            startDirektbuchung();
                                        } else {
                                            saveReservierung(k.getId(), k.getName(), k.getEmail());
                                        }
                                    }, () -> {
                                        System.out.println(">>> FEHLER: Kunde konnte nicht aus Repository geladen werden für ID: " + createEv.getKundeId());
                                        Notification.show("Kunde konnte nicht geladen werden");
                                    });
                                } else {
                                    Notification.show("Kunde konnte nicht angelegt werden: " + (createEv.getMessage() != null ? createEv.getMessage() : "Unbekannter Fehler"));
                                }
                                if (createRegHolder[0] != null) {
                                    createRegHolder[0].remove();
                                    createRegHolder[0] = null;
                                }
                            }));
                        });
                        
                        // Jetzt Kunde anlegen mit Correlation ID (gibt null zurück, Event wird erwartet)
                        System.out.println(">>> Sende CREATE Command für Kunde: name=" + name + ", email=" + email + ", correlationId=" + createCorr);
                        customerService.ensureCustomer(name, email, createCorr);
                        System.out.println(">>> CREATE Command gesendet");
                    } else {
                        Notification.show("Kundenabfrage fehlgeschlagen");
                        if (tmpRegHolder[0] != null) {
                            tmpRegHolder[0].remove();
                            tmpRegHolder[0] = null;
                        }
                    }
                }));
            });

            kino.application.kafka.events.CustomerCommand cmd = new kino.application.kafka.events.CustomerCommand();
            cmd.setAction(kino.application.kafka.events.CustomerCommand.Action.QUERY);
            cmd.setEmail(email);
            cmd.setCorrelationId(corr);
            // Reuse existing producer via CustomerService or inject a dedicated one; using service for consistency
            // If CustomerService has only ensureCustomer, we can directly use producer
            // Here, we send via a helper: customerService will not handle QUERY, so use producer bean
            customerCommandProducer.send(cmd);
        });

        layout.add(nameField, emailField, weiterButton);
        dialog.add(layout);
        dialog.open();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        if (customerReg != null) {
            customerReg.remove();
            customerReg = null;
        }
        if (adminReg != null) {
            adminReg.remove();
            adminReg = null;
        }
    }


    private void saveReservierung(Long kundeId, String kundeName, String kundeEmail) {
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
                kundeId,
                kundeName,
                kundeEmail,
                sitzplatzIds
        );

        Notification.show("Reservierung wird verarbeitet...");

     // Auswahl leeren
        ausgewähltePlaetze.clear();

        // E-Mail in der Session merken, damit die ReservierungenView sie nutzen kann
        String email = kundeEmail;
        VaadinSession.getCurrent().setAttribute("kundenEmail", email);

        // Direkt zur Reservierungen-Ansicht navigieren; die View lädt nach kurzer Verzögerung selbst
        getUI().ifPresent(ui -> ui.navigate(ReservierungenView.class));

    }

    // generateReservierungsnummer nicht mehr benötigt

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

        // Query Aufführung via Kafka and render when received
        String correlationId = java.util.UUID.randomUUID().toString();

        // Listen for the matching response on Admin UI event bus
        adminReg = AdminUIEventBus.register(ev -> {
            if (ev == null) return;
            if (ev.getAction() != AdminEvent.Action.QUERY) return;
            if (ev.getEntity() != AdminEvent.Entity.AUFFUEHRUNG) return;
            if (ev.getCorrelationId() == null || !ev.getCorrelationId().equals(correlationId)) return;

            getUI().ifPresent(ui -> ui.access(() -> {
                if (ev.getStatus() == AdminEvent.Status.OK && ev.getAuffuehrungen() != null && !ev.getAuffuehrungen().isEmpty()) {
                    Long receivedId = ev.getAuffuehrungen().get(0).getId();
                    auffuehrungRepository.findById(receivedId).ifPresentOrElse(auff -> {
                        this.aktuelleAuffuehrung = auff;
                        content.add(createInfoLeiste(auff));
                        buildSitzplatzLayout(auff.getSaal());
                    }, () -> content.add(new H2("Aufführung nicht gefunden")));
                } else {
                    content.add(new H2("Aufführung nicht gefunden"));
                }
            }));
        });

        AdminCommand cmd = new AdminCommand(AdminCommand.Entity.AUFFUEHRUNG, AdminCommand.Action.QUERY);
        AdminCommand.QueryPayload qp = new AdminCommand.QueryPayload();
        qp.setType(AdminCommand.QueryPayload.Type.GET_BY_ID);
        qp.setId(auffId);
        qp.setCorrelationId(correlationId);
        cmd.setQuery(qp);
        adminCommandProducer.send(cmd);
    }
}
