package kino.application;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "kontakt", layout = MainViewLayout.class)
@PageTitle("Kontakt")
@CssImport("./styles/kontakt.css")
public class KontaktView extends VerticalLayout {

    public KontaktView() {
        addClassName("kontakt-view");
        setSizeFull();
        setPadding(true);
        setSpacing(false);

        // Überschrift
        H2 heading = new H2("Kontakt & Anfahrt");
        heading.addClassName("kontakt-heading");
        add(heading);

        // linke Spalte:Kontaktdaten
        VerticalLayout contactColumn = new VerticalLayout();
        contactColumn.addClassName("kontakt-column");
        contactColumn.setPadding(false);
        contactColumn.setSpacing(false);

        H3 kinoName = new H3("CINEMAn Roll");
        kinoName.addClassName("kontakt-kino-name");

        Paragraph adresse = new Paragraph(
                "Jonasstraße 7\n" +
                        "31985 Maxstadt"
        );
        adresse.addClassName("kontakt-text");

        // E-Mail Tele als klickbare Links
        Html email = new Html(
                "<p class='kontakt-text'>E-Mail: " +
                        "<a href='mailto:info@cineman-roll.de'>info@cineman-roll.de</a></p>"
        );
        Html telefon = new Html(
                "<p class='kontakt-text'>Telefon: " +
                        "<a href='tel:+491234567890'>+49 123 456 7890</a></p>"
        );

        Paragraph oeffnungszeitenHeader = new Paragraph("Öffnungszeiten:");
        oeffnungszeitenHeader.addClassName("kontakt-subheading");

        Paragraph oeffnungszeiten = new Paragraph(
                "Mo–Do: 16:00 – 23:00 Uhr\n" +
                        "Fr–Sa: 14:00 – 01:00 Uhr\n" +
                        "So:    14:00 – 22:00 Uhr"
        );
        oeffnungszeiten.addClassName("kontakt-text");

        contactColumn.add(kinoName, adresse, email, telefon,
                oeffnungszeitenHeader, oeffnungszeiten);

        // rechte Spalte: Anreise /Info
        VerticalLayout infoColumn = new VerticalLayout();
        infoColumn.addClassName("kontakt-column");
        infoColumn.setPadding(false);
        infoColumn.setSpacing(false);

        H3 anfahrtHeader = new H3("Anfahrt");
        anfahrtHeader.addClassName("kontakt-subheading");

        Paragraph anfahrt = new Paragraph(
                "Sie finden uns direkt am Hauptbahnhof von Maxstadt. " +
                        "Tram- und Bushaltestellen liegen in Laufweite, " +
                        "das Parkhaus »Hannah-Parkdeck« ist in der Straße gegenüber." +
                        " Vor Ort versorgen wir sie mit Leckeren Snacks aus dem Kiosk-Lars."
        );
        anfahrt.addClassName("kontakt-text");

        Paragraph parkhinweis = new Paragraph(
                "Tipp: Ab 18:00 Uhr ist das Parken im Parkdeck für Kinobesucher " +
                        "für 3 Stunden vergünstigt."
        );
        parkhinweis.addClassName("kontakt-text");

        infoColumn.add(anfahrtHeader, anfahrt, parkhinweis);

        // Spalten nebeneinander
        HorizontalLayout contentRow = new HorizontalLayout(contactColumn, infoColumn);
        contentRow.addClassName("kontakt-content-row");
        contentRow.setWidthFull();
        contentRow.setSpacing(true);

        add(contentRow);

        // Button zurück zur Startseite
        Button back = new Button("Zur Startseite", new Icon(VaadinIcon.ARROW_LEFT));
        back.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));
        back.addClassName("kontakt-back-button");
        add(back);
    }
}
