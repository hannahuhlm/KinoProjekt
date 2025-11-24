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
import kino.application.data.*;

import java.util.ArrayList;
import java.util.Comparator;

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

    public AdminSaalAnlegenView(KinosaalRepository kinosaalRepository) {
        this.kinosaalRepository = kinosaalRepository;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Admin: Kinosaal verwalten"));

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

    // Layout für das Formular zusammenbauen
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
        return formLayout;
    }

    // Grid Spalten konfigurieren und Auswahl-Listener setzen
    private void configureGrid() {
        grid.addColumn(Kinosaal::getName).setHeader("Name").setAutoWidth(true);
        grid.addColumn(k -> k.getReihen() != null ? k.getReihen().size() : 0)
                .setHeader("Anzahl Reihen").setAutoWidth(true);
        grid.addColumn(Kinosaal::isFreigegeben).setHeader("Freigegeben").setAutoWidth(true);
        grid.setSizeFull();

        // Wenn ein Saal ausgewählt wird, Formular laden
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                selectKinosaal(event.getValue());
            }
        });
    }

    // Formularfelder binden und Buttons konfigurieren
    private void configureForm() {
        binder.forField(name)
                .asRequired("Name darf nicht leer sein")
                .bind(Kinosaal::getName, (k, v) -> {
                    k.setName(v);
                    k.setFreigegeben(false);
                    updateFreiGegebenStatus();
                });

        binder.forField(anzahlReihen)
                .withNullRepresentation(0)
                .bind(k -> k.getReihen() != null ? k.getReihen().size() : 0,
                        (k, value) -> {
                            if (k.getReihen() == null) k.setReihen(new ArrayList<>());
                            int aktuelle = k.getReihen().size();
                            if (value == null) value = 0;
                            if (value > aktuelle) {
                                for (int i = aktuelle + 1; i <= value; i++) {
                                    Sitzreihe r = new Sitzreihe();
                                    r.setReihennummer(i);
                                    r.setSaal(k);
                                    k.getReihen().add(r);
                                }
                                k.setFreigegeben(false);
                            }
                            updateFreiGegebenStatus();
                        });

        // Button für das Bearbeiten der Reihen
        reihenBearbeitenButton.addClickListener(e -> openReihenDialog());

        istFreiGegeben.setEnabled(false);
        // Freigabe ändern wenn Checkbox aktiviert ist
        istFreiGegeben.addValueChangeListener(e -> {
            if (currentKinosaal != null && istFreiGegeben.isEnabled()) {
                currentKinosaal.setFreigegeben(istFreiGegeben.getValue());
                kinosaalRepository.save(currentKinosaal);
                updateGrid();
            }
        });

        neuButton.addClickListener(e -> clearForm());
        speichernButton.addClickListener(e -> saveKinosaal());
        loeschenButton.addClickListener(e -> deleteKinosaal());
    }

    // Prüfen, ob Freigabe-Checkbox aktiviert werden kann
    private void updateFreiGegebenStatus() {
        if (currentKinosaal == null) {
            istFreiGegeben.setEnabled(false);
            istFreiGegeben.setValue(false);
            return;
        }
        boolean ready = isSaalFreiGebenBereit();
        istFreiGegeben.setEnabled(ready);
        // Checkbox anpassen je nach Status
        istFreiGegeben.setValue(currentKinosaal.isFreigegeben() && ready);
    }

    // Prüfen, ob alle Reihen und Plätze korrekt angelegt sind
    private boolean isSaalFreiGebenBereit() {
        if (currentKinosaal.getName() == null || currentKinosaal.getName().trim().isEmpty()) return false;
        if (currentKinosaal.getReihen() == null || currentKinosaal.getReihen().isEmpty()) return false;

        for (Sitzreihe r : currentKinosaal.getReihen()) {
            if (r.getKategorie() == null || r.getAnzahlSitze() <= 0) return false;
            if (r.getPlaetze() == null || r.getPlaetze().size() != r.getAnzahlSitze()) return false;
        }
        return true;
    }

    // Dialog öffnen um Reihen zu bearbeiten
    private void openReihenDialog() {
        if (currentKinosaal == null) {
            Notification.show("Bitte zuerst Saal auswählen oder anlegen.", 2000, Notification.Position.MIDDLE);
            return;
        }

        Dialog dialog = new Dialog();
        dialog.setWidth("700px");
        dialog.setHeight("500px");

        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2("Reihen bearbeiten für Saal: " + currentKinosaal.getName()));

        Grid<Sitzreihe> reihenGrid = new Grid<>();
        currentKinosaal.getReihen().sort(Comparator.comparingInt(Sitzreihe::getReihennummer));
        reihenGrid.setItems(currentKinosaal.getReihen());

        reihenGrid.addColumn(Sitzreihe::getReihennummer).setHeader("Reihe").setAutoWidth(true);

        reihenGrid.addColumn(new ComponentRenderer<>(reihe -> {
            ComboBox<SitzreihenKategorie> combo = new ComboBox<>();
            combo.setItems(SitzreihenKategorie.values());
            combo.setValue(reihe.getKategorie());
            combo.setWidth("180px");
            // Kategorie ändern
            combo.addValueChangeListener(ev -> {
                reihe.setKategorie(ev.getValue());
                currentKinosaal.setFreigegeben(false);
                updateFreiGegebenStatus();
                binder.setBean(currentKinosaal);
            });
            return combo;
        })).setHeader("Kategorie").setAutoWidth(false);

        reihenGrid.addColumn(new ComponentRenderer<>(reihe -> {
            IntegerField platzField = new IntegerField();
            platzField.setMin(0);
            platzField.setValue(reihe.getAnzahlSitze());
            platzField.setWidth("120px");
            // Sitzplätze anpassen
            platzField.addValueChangeListener(ev -> {
                int newCount = ev.getValue() == null ? 0 : ev.getValue();
                if (newCount == reihe.getAnzahlSitze()) return;

                if (reihe.getPlaetze() == null) reihe.setPlaetze(new ArrayList<>());
                int aktuelle = reihe.getPlaetze().size();
                if (newCount > aktuelle) {
                    for (int i = aktuelle; i < newCount; i++) {
                        Sitzplatz p = new Sitzplatz();
                        p.setPlatznummer(i + 1);
                        p.setFrei(true);
                        p.setReihe(reihe);
                        reihe.getPlaetze().add(p);
                    }
                } else {
                    for (int i = aktuelle - 1; i >= newCount; i--) {
                        reihe.getPlaetze().remove(i);
                    }
                }
                reihe.setAnzahlSitze(newCount);
                currentKinosaal.setFreigegeben(false);
                binder.setBean(currentKinosaal);
                updateFreiGegebenStatus();
            });
            return platzField;
        })).setHeader("Sitzplätze").setAutoWidth(false);

        reihenGrid.setHeight("320px");
        layout.add(reihenGrid);

        // Dialog schließen Button
        Button close = new Button("Schließen", ev -> {
            binder.setBean(currentKinosaal);
            updateFreiGegebenStatus();
            dialog.close();
        });
        layout.add(new HorizontalLayout(close));

        dialog.add(layout);
        dialog.open();
    }

    // Saal auswählen aus der Tabelle
    private void selectKinosaal(Kinosaal kinosaal) {
        if (kinosaal != null && kinosaal.getId() != null) {
            currentKinosaal = kinosaalRepository.findById(kinosaal.getId()).orElse(kinosaal);
            binder.setBean(currentKinosaal);
            updateFreiGegebenStatus();
        } else {
            clearForm();
        }
    }

    // Formular leeren und neuen Saal vorbereiten
    private void clearForm() {
        currentKinosaal = new Kinosaal();
        binder.setBean(currentKinosaal);
        grid.asSingleSelect().clear();
        updateFreiGegebenStatus();
    }

    // Saal speichern
    private void saveKinosaal() {
        if (!binder.validate().isOk()) return;
        kinosaalRepository.save(currentKinosaal);
        updateGrid();
        Notification.show("Kinosaal gespeichert", 2000, Notification.Position.MIDDLE);
    }

    // Saal löschen
    private void deleteKinosaal() {
        if (currentKinosaal == null || currentKinosaal.getId() == null) return;
        kinosaalRepository.delete(currentKinosaal);
        updateGrid();
        clearForm();
        Notification.show("Kinosaal gelöscht", 2000, Notification.Position.MIDDLE);
    }

    // Grid aktualisieren
    private void updateGrid() {
        grid.setItems(kinosaalRepository.findAll());
    }
}
