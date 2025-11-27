package kino.application.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kino.application.MainViewLayout;
import kino.application.data.Kinosaal;
import kino.application.data.KinosaalRepository;
import kino.application.data.Sitzplatz;
import kino.application.data.Sitzreihe;
import kino.application.data.SitzreihenKategorie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Route(value = "saal-anlegen", layout = MainViewLayout.class)
@PageTitle("Admin – Kinosäle verwalten")
public class AdminSaalAnlegenView extends VerticalLayout {

    private final KinosaalRepository kinosaalRepository;

    private final Grid<Kinosaal> grid = new Grid<>(Kinosaal.class, false);

    private final TextField name = new TextField("Name");
    private final IntegerField anzahlReihen = new IntegerField("Anzahl Reihen");
    private final Button reihenBearbeitenButton = new Button("Reihen bearbeiten");

    private final Checkbox istFreiGegeben = new Checkbox("Freigeben");

    private final Button neuButton = new Button("Neu");
    private final Button speichernButton = new Button("Speichern");
    private final Button loeschenButton = new Button("Löschen");

    private final Binder<Kinosaal> binder = new Binder<>(Kinosaal.class);
    private Kinosaal currentKinosaal;

    // Flag, um zu unterscheiden: Wert im Feld kommt vom Benutzer vs. vom Code
    private boolean internalUpdateAnzahlReihen = false;

    public AdminSaalAnlegenView(KinosaalRepository kinosaalRepository) {
        this.kinosaalRepository = kinosaalRepository;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 title = new H2("Admin: Kinosaal verwalten");
        title.getStyle().set("color", "white");
        add(title);

        configureGrid();
        configureForm();

        VerticalLayout formLayout = createFormLayout();

        HorizontalLayout content = new HorizontalLayout(grid, formLayout);
        content.setSizeFull();
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, formLayout);

        add(content);

        updateGrid();
        clearForm();
    }

    // --- Layout rechts: Formular in weißer Karte ---
    private VerticalLayout createFormLayout() {
        HorizontalLayout reihenLayout = new HorizontalLayout(anzahlReihen, reihenBearbeitenButton);
        reihenLayout.setAlignItems(Alignment.END);
        reihenLayout.setSpacing(true);

        VerticalLayout formLayout = new VerticalLayout(
                name,
                reihenLayout,
                istFreiGegeben,
                new HorizontalLayout(neuButton, speichernButton, loeschenButton)
        );
        formLayout.setWidth("420px");
        formLayout.getStyle()
                .set("background", "white")
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 10px rgba(0,0,0,0.1)")
                .set("padding", "20px");
        return formLayout;
    }

    // --- Tabelle links ---
    private void configureGrid() {
        grid.addColumn(Kinosaal::getName)
                .setHeader("Name")
                .setAutoWidth(true);

        // Anzahl Reihen: immer aktuelle Größe der Liste
        grid.addColumn(k -> k.getReihen() != null ? k.getReihen().size() : 0)
                .setHeader("Anzahl Reihen")
                .setAutoWidth(true);

        grid.addColumn(Kinosaal::isFreigegeben)
                .setHeader("Freigegeben")
                .setAutoWidth(true);

        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                selectKinosaal(event.getValue());
            }
        });
    }

    // --- Formular-Logik ---
    private void configureForm() {
        // Nur Name wird über Binder verwaltet
        binder.forField(name)
                .asRequired("Name darf nicht leer sein")
                .bind(Kinosaal::getName, (k, v) -> {
                    k.setName(v);
                    markSaalAsChanged();
                });

        // AnzahlReihen NICHT an Binder binden → wir steuern alles manuell
        anzahlReihen.setMin(0);

        anzahlReihen.addValueChangeListener(ev -> {
            // Nur reagieren, wenn der Benutzer den Wert ändert
            if (!ev.isFromClient() || internalUpdateAnzahlReihen) {
                return;
            }
            if (currentKinosaal == null) {
                return;
            }
            if (currentKinosaal.getReihen() == null) {
                currentKinosaal.setReihen(new ArrayList<>());
            }

            int aktuelleAnzahl = currentKinosaal.getReihen().size();
            Integer neueAnzahlObj = ev.getValue();
            int neueAnzahl = neueAnzahlObj == null ? 0 : neueAnzahlObj;

            if (neueAnzahl == aktuelleAnzahl) {
                return; // nichts zu tun
            }

            if (neueAnzahl > aktuelleAnzahl) {
                // Differenz leere Reihen hinten anfügen
                for (int i = aktuelleAnzahl + 1; i <= neueAnzahl; i++) {
                    Sitzreihe r = new Sitzreihe();
                    r.setReihennummer(i);
                    r.setSaal(currentKinosaal);
                    // Kategorie / Plätze bleiben leer
                    currentKinosaal.getReihen().add(r);
                }
                markSaalAsChanged();
            } else {
                // neueAnzahl < aktuelleAnzahl:
                // alle existierenden Reihen vollständig verwerfen
                for (Sitzreihe r : new ArrayList<>(currentKinosaal.getReihen())) {
                    if (r.getPlaetze() != null) {
                        r.getPlaetze().clear();
                    }
                    r.setSaal(null); // Beziehung lösen (für JPA)
                }
                currentKinosaal.getReihen().clear();

                // und neueAnzahl leere Reihen 1..neueAnzahl anlegen
                for (int i = 1; i <= neueAnzahl; i++) {
                    Sitzreihe r = new Sitzreihe();
                    r.setReihennummer(i);
                    r.setSaal(currentKinosaal);
                    currentKinosaal.getReihen().add(r);
                }
                markSaalAsChanged();
            }
        });

        // Reihen-Dialog
        reihenBearbeitenButton.addClickListener(e -> openReihenDialog());

        // Freigeben: immer klickbar
        istFreiGegeben.addValueChangeListener(ev -> {
            if (currentKinosaal == null) return;
            if (!ev.isFromClient()) return;

            boolean requested = Boolean.TRUE.equals(ev.getValue());

            if (requested) {
                // prüfen, ob alle Reihen komplett eingerichtet
                if (isSaalKonfigVollstaendig()) {
                    currentKinosaal.setFreigegeben(true);
                    // Speichern, damit Zustand sofort im Grid und nach Reload sichtbar ist
                    kinosaalRepository.save(currentKinosaal);
                    // programmatic setValue → löst Listener nicht erneut aus
                    istFreiGegeben.setValue(true);
                    updateGrid();
                } else {
                    Notification.show(
                            "Bitte alle Reihen einrichten (Kategorie und Sitzplätze), bevor der Saal freigegeben wird.",
                            3000, Notification.Position.MIDDLE
                    );
                    currentKinosaal.setFreigegeben(false);
                    istFreiGegeben.setValue(false);
                }
            } else {
                // Freigabe zurücknehmen
                currentKinosaal.setFreigegeben(false);
                kinosaalRepository.save(currentKinosaal);
                istFreiGegeben.setValue(false);
                updateGrid();
            }

            updateEditabilityForCurrentSaal();
        });

        neuButton.addClickListener(e -> clearForm());
        speichernButton.addClickListener(e -> saveKinosaal());
        loeschenButton.addClickListener(e -> deleteKinosaal());
    }

    // Sobald etwas geändert wird, ist der Saal nicht mehr freigegeben
    private void markSaalAsChanged() {
        if (currentKinosaal == null) return;

        if (currentKinosaal.isFreigegeben()) {
            currentKinosaal.setFreigegeben(false);
            istFreiGegeben.setValue(false);
        }
        updateEditabilityForCurrentSaal();
    }

    // Prüfen, ob alle Reihen (Kategorie + Sitzplätze) vollständig eingerichtet sind
    private boolean isSaalKonfigVollstaendig() {
        if (currentKinosaal == null) return false;
        if (currentKinosaal.getReihen() == null || currentKinosaal.getReihen().isEmpty()) return false;

        for (Sitzreihe r : currentKinosaal.getReihen()) {
            if (r.getKategorie() == null) return false;
            if (r.getAnzahlSitze() <= 0) return false;
            List<Sitzplatz> plaetze = r.getPlaetze();
            if (plaetze == null || plaetze.size() != r.getAnzahlSitze()) return false;
        }
        return true;
    }

    // Dialog zum Bearbeiten der Reihen (Kategorie + Sitzplätze)
    private void openReihenDialog() {
        if (currentKinosaal == null) {
            Notification.show("Bitte zuerst Saal auswählen oder anlegen.", 2000, Notification.Position.MIDDLE);
            return;
        }
        if (currentKinosaal.getReihen() == null) {
            currentKinosaal.setReihen(new ArrayList<>());
        }

        Dialog dialog = new Dialog();
        dialog.setWidth("700px");
        dialog.setHeight("500px");

        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2("Reihen bearbeiten für Saal: " + currentKinosaal.getName()));

        Grid<Sitzreihe> reihenGrid = new Grid<>();
        currentKinosaal.getReihen().sort(Comparator.comparingInt(Sitzreihe::getReihennummer));
        reihenGrid.setItems(currentKinosaal.getReihen());

        reihenGrid.addColumn(Sitzreihe::getReihennummer)
                .setHeader("Reihe")
                .setAutoWidth(true);

        reihenGrid.addColumn(new ComponentRenderer<>(reihe -> {
            ComboBox<SitzreihenKategorie> combo = new ComboBox<>();
            combo.setItems(SitzreihenKategorie.values());
            combo.setValue(reihe.getKategorie());
            combo.setWidth("180px");
            combo.addValueChangeListener(ev -> {
                reihe.setKategorie(ev.getValue());
                markSaalAsChanged();
            });
            return combo;
        })).setHeader("Kategorie").setAutoWidth(false);

        reihenGrid.addColumn(new ComponentRenderer<>(reihe -> {
            IntegerField platzField = new IntegerField();
            platzField.setMin(0);
            platzField.setValue(reihe.getAnzahlSitze());
            platzField.setWidth("120px");
            platzField.addValueChangeListener(ev -> {
                Integer newCountObj = ev.getValue();
                int newCount = (newCountObj == null) ? 0 : newCountObj;
                if (newCount == reihe.getAnzahlSitze()) return;

                if (reihe.getPlaetze() == null) reihe.setPlaetze(new ArrayList<>());
                List<Sitzplatz> plaetze = reihe.getPlaetze();
                int aktuelle = plaetze.size();

                if (newCount > aktuelle) {
                    for (int i = aktuelle; i < newCount; i++) {
                        Sitzplatz p = new Sitzplatz();
                        p.setPlatznummer(i + 1);
                        p.setFrei(true);
                        p.setReihe(reihe);
                        plaetze.add(p);
                    }
                } else {
                    for (int i = aktuelle - 1; i >= newCount; i--) {
                        plaetze.remove(i);
                    }
                }
                reihe.setAnzahlSitze(newCount);
                markSaalAsChanged();
                reihenGrid.getDataProvider().refreshItem(reihe);
            });
            return platzField;
        })).setHeader("Sitzplätze").setAutoWidth(false);

        reihenGrid.setHeight("320px");
        layout.add(reihenGrid);

        Button close = new Button("Schließen", ev -> dialog.close());
        layout.add(new HorizontalLayout(close));

        dialog.add(layout);
        dialog.open();
    }

    // Saal aus Grid auswählen
    private void selectKinosaal(Kinosaal kinosaal) {
        if (kinosaal != null && kinosaal.getId() != null) {
            currentKinosaal = kinosaalRepository.findById(kinosaal.getId()).orElse(kinosaal);
            if (currentKinosaal.getReihen() == null) {
                currentKinosaal.setReihen(new ArrayList<>());
            }

            binder.setBean(currentKinosaal);

            // AnzahlReihen-Feld programmgesteuert setzen → Listener nicht ausführen
            internalUpdateAnzahlReihen = true;
            anzahlReihen.setValue(currentKinosaal.getReihen().size());
            internalUpdateAnzahlReihen = false;

            istFreiGegeben.setValue(currentKinosaal.isFreigegeben());

            updateEditabilityForCurrentSaal();
        } else {
            clearForm();
        }
    }

    // Neuer leerer Saal
    private void clearForm() {
        currentKinosaal = new Kinosaal();
        currentKinosaal.setReihen(new ArrayList<>());

        binder.setBean(currentKinosaal);
        grid.asSingleSelect().clear();

        internalUpdateAnzahlReihen = true;
        anzahlReihen.setValue(0);
        internalUpdateAnzahlReihen = false;

        istFreiGegeben.setValue(false);

        updateEditabilityForCurrentSaal();
    }

    // Freigegebene Säle sperren Felder/Buttons
    private void updateEditabilityForCurrentSaal() {
        boolean freigegeben = currentKinosaal != null && currentKinosaal.isFreigegeben();

        name.setReadOnly(freigegeben);
        anzahlReihen.setReadOnly(freigegeben);
        reihenBearbeitenButton.setEnabled(!freigegeben);

        speichernButton.setEnabled(!freigegeben);
        loeschenButton.setEnabled(!freigegeben);

        neuButton.setEnabled(true);
        // istFreiGegeben bleibt immer enabled
    }

    // Speichern
    private void saveKinosaal() {
        if (!binder.validate().isOk()) {
            return;
        }

        kinosaalRepository.save(currentKinosaal);
        updateGrid();
        Notification.show("Kinosaal gespeichert", 2000, Notification.Position.MIDDLE);
    }

    // Löschen
    private void deleteKinosaal() {
        if (currentKinosaal == null || currentKinosaal.getId() == null) return;

        kinosaalRepository.delete(currentKinosaal);
        updateGrid();
        clearForm();

        Notification.show("Kinosaal gelöscht", 2000, Notification.Position.MIDDLE);
    }

    // Grid aus DB neu befüllen
    private void updateGrid() {
        grid.setItems(kinosaalRepository.findAll());
    }
}
