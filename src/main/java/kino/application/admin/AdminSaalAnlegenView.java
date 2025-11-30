package kino.application.admin;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import kino.application.MainViewLayout;
import kino.application.data.Kinosaal;
import kino.application.data.Sitzreihe;
import kino.application.data.Sitzplatz;
import kino.application.data.SitzreihenKategorie;
// Import an dein Repo anpassen:
import kino.application.data.KinosaalRepository;

@Route(value = "saal-anlegen", layout = MainViewLayout.class)
@PageTitle("Saal anlegen – Admin")
public class AdminSaalAnlegenView extends VerticalLayout {

    // --- Repositories / Datenzugriff ---
    private final KinosaalRepository kinosaalRepository;

    // --- UI-Komponenten für die Listenansicht ---
    private final Grid<Kinosaal> grid = new Grid<>(Kinosaal.class, false);
    private final Button neuerSaalButton = new Button("Neuen Saal anlegen");

    // --- Dialog & Formular-Komponenten ---
    private final Dialog saalDialog = new Dialog();
    private final Binder<Kinosaal> saalBinder = new Binder<>(Kinosaal.class);

    private Kinosaal aktuellerSaal;

    private TextField nameField;
    private Checkbox freigegebenField;
    private IntegerField anzahlReihenField;
    private VerticalLayout reihenContainer;

    // ---------------------------------------------------------
    // Konstruktor
    // ---------------------------------------------------------
    public AdminSaalAnlegenView(KinosaalRepository kinosaalRepository) {
        this.kinosaalRepository = kinosaalRepository;

        // Grundlayout-Einstellungen für die View
        setSizeFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#2c2723").set("color", "white");

        // Überschrift
        H2 heading = new H2("Kinosaal-Verwaltung");
        heading.getStyle()
                .set("color", "white")
                .set("margin-top", "0");

        // Grid & Dialog konfigurieren
        configureGrid();
        configureDialog();

        // Toolbar (Button "Neuen Saal anlegen")
        HorizontalLayout toolbar = createToolbar();

        // Alles in die View legen
        add(heading, toolbar, grid);

        // Daten ins Grid laden
        updateGrid();
    }

    // ---------------------------------------------------------
    // Toolbar oben über dem Grid
    // ---------------------------------------------------------
    private HorizontalLayout createToolbar() {
        // Styling für "Neuen Saal anlegen"-Button
        neuerSaalButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        neuerSaalButton.getStyle()
                .set("background", "#ff1744")
                .set("color", "white")
                .set("border-radius", "20px");

        // Klick öffnet den Dialog im "Neu"-Modus
        neuerSaalButton.addClickListener(e -> openCreateDialog());

        HorizontalLayout toolbar = new HorizontalLayout(neuerSaalButton);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);
        toolbar.getStyle().set("margin-bottom", "10px");
        return toolbar;
    }

    // ---------------------------------------------------------
    // Grid-Konfiguration (Liste aller Säle)
    // ---------------------------------------------------------
    private void configureGrid() {
        grid.setSizeFull();
        grid.addThemeVariants(
                GridVariant.LUMO_ROW_STRIPES,
                GridVariant.LUMO_COLUMN_BORDERS
        );

        // Spalte: Name
        grid.addColumn(Kinosaal::getName)
                .setHeader("Name")
                .setAutoWidth(true)
                .setFlexGrow(1);

        // Spalte: Anzahl Reihen (reihen.size())
        grid.addColumn(kinosaal -> kinosaal.getReihen() != null ? kinosaal.getReihen().size() : 0)
                .setHeader("Anzahl Reihen")
                .setAutoWidth(true);

        // Spalte: freigegeben (Ja/Nein)
        grid.addColumn(kinosaal -> kinosaal.isFreigegeben() ? "Ja" : "Nein")
                .setHeader("Freigegeben")
                .setAutoWidth(true);

        // Spalte: Aktionen („Saal bearbeiten“-Button pro Zeile)
        grid.addColumn(new ComponentRenderer<>(kinosaal -> {
            Button bearbeiten = new Button("Saal bearbeiten");
            bearbeiten.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            bearbeiten.getStyle().set("color", "#f5e1a4");
            bearbeiten.addClickListener(e -> openEditDialog(kinosaal));
            return bearbeiten;
        }))
        .setHeader("Aktionen")
        .setAutoWidth(true);
    }

    // ---------------------------------------------------------
    // Dialog + Formular für Saal bearbeiten/neu anlegen
    // ---------------------------------------------------------
    private void configureDialog() {
        saalDialog.setModal(true);
        saalDialog.setDraggable(true);
        saalDialog.setResizable(true);
        saalDialog.setWidth("800px");

        // --- Formularfelder für Saal-Basisdaten ---
        nameField = new TextField("Name");
        freigegebenField = new Checkbox("Freigegeben");

        // Anzahl der Reihen (steuert die Länge der Liste aktuellerSaal.getReihen())
        anzahlReihenField = new IntegerField("Anzahl Reihen");
        anzahlReihenField.setMin(0);
        anzahlReihenField.setStepButtonsVisible(true);

        // Wenn sich die Anzahl ändert, passen wir die Liste der Sitzreihen am Saal an
        anzahlReihenField.addValueChangeListener(e -> {
            if (aktuellerSaal == null) {
                return;
            }
            Integer value = e.getValue();
            if (value == null || value < 0) {
                return;
            }
            adjustReihenAnzahl(value);
            buildReihenForm();
        });

        // Binder verbindet Felder mit Kinosaal-Attributen
        saalBinder.bind(nameField, Kinosaal::getName, Kinosaal::setName);
        saalBinder.bind(freigegebenField, Kinosaal::isFreigegeben, Kinosaal::setFreigegeben);

        // Container, in den wir die Formularzeilen für jede Sitzreihe legen
        reihenContainer = new VerticalLayout();
        reihenContainer.setPadding(false);
        reihenContainer.setSpacing(true);

        // Inhalt des Dialogs
        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);
        content.getStyle()
        .set("background-color", "white")
        .set("color", "black");

        content.add(
                new H2("Saal bearbeiten"),
                nameField,
                freigegebenField,
                anzahlReihenField,
                new Span("Reihen:"),
                reihenContainer
        );

        saalDialog.add(content);

        // Footer mit "Abbrechen" und "Speichern"
        Button speichern = new Button("Speichern", e -> saveSaal());
        speichern.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button abbrechen = new Button("Abbrechen", e -> saalDialog.close());
        abbrechen.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        saalDialog.getFooter().add(abbrechen, speichern);
    }

    // ---------------------------------------------------------
    // Hilfsmethode: Anzahl der Reihen anpassen (Liste erweitern/kürzen)
    // ---------------------------------------------------------
    private void adjustReihenAnzahl(int neueAnzahl) {
        List<Sitzreihe> reihen = aktuellerSaal.getReihen();

        if (reihen == null) {
            reihen = new ArrayList<>();
            aktuellerSaal.setReihen(reihen);
        }

        int aktuelleAnzahl = reihen.size();

        // Reihen hinzufügen (neue Sitzreihe-Objekte anhängen)
        if (neueAnzahl > aktuelleAnzahl) {
            for (int i = aktuelleAnzahl + 1; i <= neueAnzahl; i++) {
                Sitzreihe reihe = new Sitzreihe();
                reihe.setSaal(aktuellerSaal);
                reihe.setReihennummer(i);     // Reihen werden 1..n durchnummeriert
                reihe.setAnzahlSitze(10);     // Default-Wert, kann im UI geändert werden
                reihen.add(reihe);
            }
        }

        // Reihen entfernen (von hinten, orphanRemoval = true kümmert sich um DB)
        if (neueAnzahl < aktuelleAnzahl) {
            for (int i = aktuelleAnzahl - 1; i >= neueAnzahl; i--) {
                reihen.remove(i);
            }
        }

        // Reihennummern sicherheitshalber nochmal durchzählen
        for (int i = 0; i < reihen.size(); i++) {
            reihen.get(i).setReihennummer(i + 1);
        }
    }

    // ---------------------------------------------------------
    // UI für die Sitzreihen im Dialog neu aufbauen
    // ---------------------------------------------------------
    private void buildReihenForm() {
        reihenContainer.removeAll();

        List<Sitzreihe> reihen = aktuellerSaal.getReihen();
        if (reihen == null) {
            return;
        }

        for (Sitzreihe reihe : reihen) {
            HorizontalLayout rowLayout = new HorizontalLayout();
            rowLayout.setWidthFull();
            rowLayout.setAlignItems(FlexComponent.Alignment.END);

            // Label "Reihe X"
            Span label = new Span("Reihe " + reihe.getReihennummer());
            label.getStyle()
            .set("min-width", "80px")
            .set("color", "black");

            // Kategorie-Auswahl (Enum SitzreihenKategorie)
            ComboBox<SitzreihenKategorie> kategorieField =
                    new ComboBox<>("Kategorie");
            kategorieField.setItems(SitzreihenKategorie.values());
            kategorieField.setValue(reihe.getKategorie());
            kategorieField.getStyle().set("min-width", "180px");

            // Anzahl Sitze in dieser Reihe
            IntegerField sitzeField = new IntegerField("Anzahl Sitze");
            sitzeField.setMin(1);
            sitzeField.setStepButtonsVisible(true);
            sitzeField.setValue(reihe.getAnzahlSitze() > 0 ? reihe.getAnzahlSitze() : 10);

            // Änderungen im UI direkt in das Entity zurückschreiben
            kategorieField.addValueChangeListener(e -> reihe.setKategorie(e.getValue()));
            sitzeField.addValueChangeListener(e -> {
                Integer v = e.getValue();
                if (v != null && v > 0) {
                    reihe.setAnzahlSitze(v);
                }
            });

            rowLayout.add(label, kategorieField, sitzeField);
            reihenContainer.add(rowLayout);
        }
    }

    // ---------------------------------------------------------
    // Öffnet den Dialog für einen neuen Saal
    // ---------------------------------------------------------
    private void openCreateDialog() {
        aktuellerSaal = new Kinosaal();
        aktuellerSaal.setReihen(new ArrayList<>());

        // Basisfelder aus leerem Objekt befüllen
        saalBinder.readBean(aktuellerSaal);

        // Standardmäßig z. B. 5 Reihen anlegen
        int defaultReihen = 5;
        adjustReihenAnzahl(defaultReihen);
        anzahlReihenField.setValue(defaultReihen);

        buildReihenForm();

        saalDialog.setHeaderTitle("Neuen Saal anlegen");
        saalDialog.open();
    }

    // ---------------------------------------------------------
    // Öffnet den Dialog zum Bearbeiten eines bestehenden Saals
    // ---------------------------------------------------------
    private void openEditDialog(Kinosaal kinosaal) {
        aktuellerSaal = kinosaal;

        // Formularfelder mit Werten aus dem bestehenden Saal befüllen
        saalBinder.readBean(aktuellerSaal);

        int anzahl = aktuellerSaal.getReihen() != null ? aktuellerSaal.getReihen().size() : 0;
        anzahlReihenField.setValue(anzahl);

        buildReihenForm();

        saalDialog.setHeaderTitle("Saal bearbeiten: " + kinosaal.getName());
        saalDialog.open();
    }

    // ---------------------------------------------------------
    // Speichern-Logik: Saal + Reihen + Sitzplätze
    // ---------------------------------------------------------
    private void saveSaal() {
        try {
            // Basisfelder (Name, freigegeben) in aktuellerSaal zurückschreiben
            saalBinder.writeBean(aktuellerSaal);

            // Für jede Sitzreihe Sitzplätze anpassen (Anzahl Sitzplätze synchronisieren)
            if (aktuellerSaal.getReihen() != null) {
                for (Sitzreihe reihe : aktuellerSaal.getReihen()) {
                                        // Falls keine Kategorie gewählt wurde, setze Parkett als Default
                                        if (reihe.getKategorie() == null) {
                                            reihe.setKategorie(SitzreihenKategorie.PARKETT);
                                        }
                    syncSitzplaetze(reihe);
                }
            }

            // Saal inklusive aller abhängigen Entitäten speichern
            kinosaalRepository.save(aktuellerSaal);

            saalDialog.close();
            updateGrid();
        } catch (ValidationException e) {
            // Falls du später Validierungen einbaust, kannst du hier z. B. eine Notification anzeigen
            e.printStackTrace();
        }
    }

    // Hilfsmethode: Sitzplätze einer Reihe an AnzahlSitze anpassen
    private void syncSitzplaetze(Sitzreihe reihe) {
        int gewuenschteAnzahl = reihe.getAnzahlSitze();
        List<Sitzplatz> plaetze = reihe.getPlaetze();

        if (plaetze == null) {
            plaetze = new ArrayList<>();
            reihe.setPlaetze(plaetze);
        }

        // Wenn zu wenig Plätze existieren → neue hinzufügen
        while (plaetze.size() < gewuenschteAnzahl) {
            Sitzplatz platz = new Sitzplatz();
            platz.setReihe(reihe);
            platz.setPlatznummer(plaetze.size() + 1);
            plaetze.add(platz);
        }

        // Wenn zu viele Plätze existieren → von hinten löschen
        while (plaetze.size() > gewuenschteAnzahl) {
            plaetze.remove(plaetze.size() - 1);
        }

        // Platznummern zur Sicherheit nochmal sauber durchzählen
        for (int i = 0; i < plaetze.size(); i++) {
            plaetze.get(i).setPlatznummer(i + 1);
        }
    }

    // Grid-Daten neu laden
    private void updateGrid() {
        grid.setItems(kinosaalRepository.findAll());
    }
}
