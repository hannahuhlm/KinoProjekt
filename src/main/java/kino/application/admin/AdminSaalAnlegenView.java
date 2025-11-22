package kino.application.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kino.application.MainView;
import kino.application.data.Kinosaal;
import kino.application.data.KinosaalRepository;
import kino.application.data.Sitzreihe;

import java.util.ArrayList;
import java.util.List;

@Route(value = "saal-anlegen", layout = MainView.class)
@PageTitle("Admin – Kinosäle verwalten")
//@RolesAllowed("ADMIN")
public class AdminSaalAnlegenView extends VerticalLayout {

    private KinosaalRepository kinosaalRepository;

    private Grid<Kinosaal> grid = new Grid<>(Kinosaal.class, false);

    // Formular-Felder
    private TextField name = new TextField("Name");
    private IntegerField anzahlReihen = new IntegerField("Anzahl Reihen");
    private Button reihenBearbeitenButton = new Button("Reihen bearbeiten");
    private Checkbox istFreiGegeben = new Checkbox("Ist frei gegeben");

    private Button neuButton = new Button("Neu");
    private Button speichernButton = new Button("Speichern");
    private Button loeschenButton = new Button("Löschen");

    private Binder<Kinosaal> binder = new Binder<>(Kinosaal.class);
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

    private void configureGrid() {
        grid.addColumn(Kinosaal::getName)
                .setHeader("Name")
                .setAutoWidth(true);

        grid.addColumn(k -> k.getReihen() != null ? k.getReihen().size() : 0)
                .setHeader("Anzahl Reihen")
                .setAutoWidth(true);

        grid.addColumn(Kinosaal::isFreigegeben)
                .setHeader("Freigegeben")
                .setAutoWidth(true);

        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> selectKinosaal(event.getValue()));
    }

    private void configureForm() {
        binder.forField(name)
                .asRequired("Name darf nicht leer sein")
                .bind(Kinosaal::getName, Kinosaal::setName);

        binder.forField(anzahlReihen)
                .withNullRepresentation(0)
                .bind(k -> k.getReihen() != null ? k.getReihen().size() : 0,
                        (k, value) -> {
                            if (k.getReihen() == null) {
                                k.setReihen(new ArrayList<>());
                            }
                            int aktuelleAnzahl = k.getReihen().size();
                            if (value == null) value = 0;

                            if (value > aktuelleAnzahl) {
                                // Neue Reihen hinzufügen
                                for (int i = aktuelleAnzahl + 1; i <= value; i++) {
                                    Sitzreihe reihe = new Sitzreihe();
                                    reihe.setReihennummer(i);
                                    reihe.setSaal(k);
                                    k.getReihen().add(reihe);
                                }
                            } else if (value < aktuelleAnzahl) {
                                Notification.show(
                                        "Um Reihen zu reduzieren, bitte die Sitzreihen bearbeiten.",
                                        3000,
                                        Notification.Position.MIDDLE
                                );
                            }
                        });

        reihenBearbeitenButton.addClickListener(e -> openReihenDialog());

        istFreiGegeben.setEnabled(false);
        name.addValueChangeListener(e -> updateFreiGegebenStatus());
        anzahlReihen.addValueChangeListener(e -> updateFreiGegebenStatus());

        neuButton.addClickListener(e -> clearForm());
        speichernButton.addClickListener(e -> saveKinosaal());
        loeschenButton.addClickListener(e -> deleteKinosaal());
    }

    private void updateFreiGegebenStatus() {
        boolean enabled = !name.getValue().isEmpty()
                && anzahlReihen.getValue() != null && anzahlReihen.getValue() > 0;
        istFreiGegeben.setEnabled(enabled);
    }

    private void openReihenDialog() {
        if (currentKinosaal == null) return;

        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setHeight("300px");
        dialog.add(new H2("Reihen bearbeiten für Saal: " + currentKinosaal.getName()));
        dialog.add(new Button("Schließen", e -> dialog.close()));
        dialog.open();
    }

    private void selectKinosaal(Kinosaal kinosaal) {
        if (kinosaal != null) {
            currentKinosaal = kinosaal;
            binder.setBean(currentKinosaal);
        } else {
            clearForm();
        }
    }

    private void clearForm() {
        currentKinosaal = new Kinosaal();
        binder.setBean(currentKinosaal);
        grid.asSingleSelect().clear();
    }

    private void saveKinosaal() {
        if (!binder.validate().isOk()) return;

        // Speichern: unterscheiden zwischen Neu & Bearbeiten
        if (currentKinosaal.getId() != null) {
            // bestehender Saal → update
            kinosaalRepository.save(currentKinosaal);
            Notification.show("Kinosaal aktualisiert", 2000, Notification.Position.MIDDLE);
        } else {
            // neuer Saal
            kinosaalRepository.save(currentKinosaal);
            Notification.show("Neuer Kinosaal erstellt", 2000, Notification.Position.MIDDLE);
        }

        updateGrid(); // Grid mit aktuellen Werten neu füllen
        clearForm();
    }

    private void deleteKinosaal() {
        if (currentKinosaal == null || currentKinosaal.getId() == null) return;

        kinosaalRepository.delete(currentKinosaal);
        updateGrid();
        clearForm();
        Notification.show("Kinosaal gelöscht", 2000, Notification.Position.MIDDLE);
    }

    private void updateGrid() {
        grid.setItems(kinosaalRepository.findAll());
    }
}
