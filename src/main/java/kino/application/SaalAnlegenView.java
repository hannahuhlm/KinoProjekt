package kino.application;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import kino.application.data.*;

@Route("saal-anlegen")
@PageTitle("Saal anlegen")
@PermitAll
public class SaalAnlegenView extends AppLayout {

    private final KinosaalRepository saalRepo;
    private final SitzreiheRepository reiheRepo;
    private final SitzplatzRepository platzRepo;

    @Autowired
    public SaalAnlegenView(
            KinosaalRepository saalRepo,
            SitzreiheRepository reiheRepo,
            SitzplatzRepository platzRepo) {

        this.saalRepo = saalRepo;
        this.reiheRepo = reiheRepo;
        this.platzRepo = platzRepo;

        menueLeisteErstellen();
        inhaltErstellen();
    }

    // menü
    private void menueLeisteErstellen() {

        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setPadding(true);
        menuLayout.setSpacing(true);

        Paragraph filmListe = new Paragraph("Filmliste");
        filmListe.getStyle().set("font-size", 1.3 + "em").set("cursor", "pointer");
        filmListe.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("filmliste")));

        Paragraph reservierungen = new Paragraph("Reservierungen");
        reservierungen.getStyle().set("font-size", 1.3 + "em").set("cursor", "pointer");
        reservierungen.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("reservierungen")));

        Paragraph kontakt = new Paragraph("Kontakt");
        kontakt.getStyle().set("font-size", 1.3 + "em").set("cursor", "pointer");
        kontakt.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("kontakt")));

        menuLayout.add(filmListe, reservierungen, kontakt);
        addToDrawer(menuLayout);

        Button menuButton = new Button(new Icon(VaadinIcon.MENU));
        menuButton.addClickListener(e -> setDrawerOpened(!isDrawerOpened()));

        Image img = new Image("images/logoLang.png", "Logo");
        img.setHeight("40px");
        Button homeBtn = new Button(img, e -> getUI().ifPresent(ui -> ui.navigate("")));
        homeBtn.getStyle().set("background", "none").set("border", "none");

        HorizontalLayout navbar = new HorizontalLayout(menuButton, homeBtn);
        navbar.setAlignItems(FlexComponent.Alignment.CENTER);

        addToNavbar(navbar);
    }

    // seite füllen
    private void inhaltErstellen() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);

        Button saalBtn = new Button("Saal hinzufügen", e -> oeffneErstesPopup());
        saalBtn.getStyle()
                .set("background", "#ff1744")
                .set("color", "white")
                .set("border-radius", "10px");

        content.add(saalBtn);
        setContent(content);
    }

    // 1. Popup: Name + Anzahl 
    private void oeffneErstesPopup() {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Neuen Saal anlegen");

        TextField saalName = new TextField("Saalname");
        IntegerField reihen = new IntegerField("Anzahl Reihen");
        reihen.setMin(1);

        IntegerField plaetze = new IntegerField("Plätze pro Reihe");
        plaetze.setMin(1);

        VerticalLayout layout = new VerticalLayout(saalName, reihen, plaetze);
        layout.setPadding(false);

        Button weiter = new Button("Weiter", e -> {
            if (saalName.isEmpty() || reihen.isEmpty() || plaetze.isEmpty()) return;

            dialog.close();
            oeffneZweitesPopup(
                    saalName.getValue(),
                    reihen.getValue(),
                    plaetze.getValue()
            );
        });

        Button abbrechen = new Button("Abbrechen", e -> dialog.close());

        dialog.getFooter().add(abbrechen, weiter);
        dialog.add(layout);
        dialog.open();
    }

    // --- 2. Popup: Kategorien auswählen & speichern ---
    private void oeffneZweitesPopup(String saalName, int reihenAnzahl, int plaetzeProReihe) {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Reihen kategorisieren");

        VerticalLayout layout = new VerticalLayout();

        ComboBox<SitzreihenKategorie>[] kategorien = new ComboBox[reihenAnzahl];

        for (int i = 0; i < reihenAnzahl; i++) {

            Label label = new Label("Reihe " + (i + 1));

            ComboBox<SitzreihenKategorie> box = new ComboBox<>();
            box.setItems(SitzreihenKategorie.values());
            box.setPlaceholder("Kategorie wählen");

            kategorien[i] = box;

            HorizontalLayout row = new HorizontalLayout(label, box);
            row.setAlignItems(FlexComponent.Alignment.CENTER);

            layout.add(row);
        }

        Button speichern = new Button("Saal freigeben", e -> {

            // Saal erstellen
            Kinosaal saal = new Kinosaal();
            saal.setName(saalName);
            saal.setFreigegeben(true);
            saal = saalRepo.save(saal);

            // Reihen + Plätze erzeugen
            for (int i = 0; i < reihenAnzahl; i++) {

                Sitzreihe reihe = new Sitzreihe();
                reihe.setReihennummer(i + 1);
                reihe.setAnzahlSitze(plaetzeProReihe);
                reihe.setKategorie(kategorien[i].getValue());
                reihe.setSaal(saal);

                reihe = reiheRepo.save(reihe);

                // Plätze erzeugen
                for (int p = 1; p <= plaetzeProReihe; p++) {
                    Sitzplatz platz = new Sitzplatz();
                    platz.setPlatznummer(p);
                    platz.setReihe(reihe);
                    platzRepo.save(platz);
                }
            }

            dialog.close();
        });

        Button abbrechen = new Button("Abbrechen", e -> dialog.close());

        dialog.add(layout);
        dialog.getFooter().add(abbrechen, speichern);
        dialog.open();
    }
}
