package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

import kino.application.data.*;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Route(value = "sitzplatzwahl/:auffuehrungId", layout = MainViewLayout.class)
@PageTitle("Sitzplatzwahl")
@PermitAll
public class SitzplatzWahlView extends VerticalLayout implements BeforeEnterObserver {

    private final AuffuehrungRepository auffuehrungRepository;
    private Auffuehrung aktuelleAuffuehrung;

    private final VerticalLayout content = new VerticalLayout();

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

    // ------------------------------------------------------
    // INFO-LEISTE OBEN
    // ------------------------------------------------------
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

    // ------------------------------------------------------
    // SITZPLATZ-DARSTELLUNG
    // ------------------------------------------------------
    private void buildSitzplatzLayout(Kinosaal saal) {

        content.add(new Hr());

        VerticalLayout sitzLayout = new VerticalLayout();
        sitzLayout.setWidthFull();
        sitzLayout.setSpacing(true);

        // Duplikate löschen
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
        //Hinzufügen des Reservieren Buttons
        Button reservierungsButton= new Button("Reservieren");
        reservierungsButton.addClickListener(event -> reservieren());
        content.add(reservierungsButton);
        
        // Hinzufügen des Bestätigungs-Buttons
        Button bestatigenButton = new Button("Zum Warenkorb");
        bestatigenButton.addClickListener(event -> openConfirmationDialog());
        content.add(bestatigenButton);
        
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
                btn.getStyle().set("background", "#2196f3");
            });
        }

        return btn;
    }
    //reservieren Logik
    private void reservieren() {
    	
    }
    
    // Dialog öffnen
    private void openConfirmationDialog() {
        Dialog dialog = new Dialog();

        // Dialog-Inhalt
        Div content = new Div();
        content.add(new H3("Bestätigung"));
        content.add(new Paragraph("Sind Sie sicher, dass Sie diese Auswahl bestätigen möchten?"));

        Button closeButton = new Button("Schließen", e -> dialog.close());
        content.add(closeButton);

        dialog.add(content);
        dialog.setWidth("90%"); // Breite des Dialogs
        dialog.setHeight("300px"); // Höhe des Dialogs, damit es in den sichtbaren Bereich passt

        // Dialog soll unter den Sitzreihen hochfahren
        dialog.getElement().getStyle().set("transition", "transform 0.5s ease-out");
        dialog.getElement().getStyle().set("transform", "translateY(100%)");
        dialog.open();

        // Animation, um das Popup hochzufahren
        dialog.getElement().getStyle().set("transform", "translateY(0%)");
    }

    // ------------------------------------------------------
    // beforeEnter: Daten laden
    // ------------------------------------------------------
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

                // Hier wird die Methode für die Info-Leiste aufgerufen
                content.add(createInfoLeiste(auff));

                buildSitzplatzLayout(auff.getSaal());
            },
            () -> content.add(new H2("Aufführung nicht gefunden"))
        );
    }
}
