package kino.application.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kino.application.MainView;
import kino.application.data.Film;
import kino.application.data.FilmRepository;

@Route(value = "film-verwalten", layout = MainView.class)
@PageTitle("Admin – Filme")
//@RolesAllowed("ADMIN") // wirkt nur, wenn security configuriert, müssen wa noch machen
public class AdminFilmAnlegenView extends VerticalLayout {

    private final FilmRepository filmRepository;

    private Grid<Film> grid = new Grid<>(Film.class, false);

    // Formular-Felder
    private TextField titel = new TextField("Titel");
    private IntegerField dauer = new IntegerField("Dauer (Minuten)");
    private TextArea beschreibung = new TextArea("Beschreibung");

    private Button neuButton = new Button("Neu");
    private Button speichernButton = new Button("Speichern");
    private Button loeschenButton = new Button("Löschen");

    private Binder<Film> binder = new Binder<>(Film.class);
    private Film currentFilm;

    public AdminFilmAnlegenView(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Admin: Filme verwalten"));

        configureGrid();
        configureForm();

        // Layout: links Grid, rechts Formular
        VerticalLayout formLayout = new VerticalLayout(
                titel,
                dauer,
                beschreibung,
                new HorizontalLayout(neuButton, speichernButton, loeschenButton)
        );
        formLayout.setWidth("400px");

        HorizontalLayout content = new HorizontalLayout(grid, formLayout);
        content.setSizeFull();
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, formLayout);

        add(content);

        updateGrid();
        clearForm();
    }

    private void configureGrid() {
        grid.addColumn(Film::getTitel).setHeader("Titel").setAutoWidth(true);
        grid.addColumn(Film::getDauer).setHeader("Dauer (Minuten)");
        grid.addColumn(Film::getBeschreibung).setHeader("Beschreibung").setFlexGrow(1);

        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            Film selected = event.getValue();
            selectFilm(selected);
        });
    }

    private void configureForm() {
        dauer.setMin(1);

        beschreibung.setWidthFull();
        beschreibung.setMinHeight("120px");

        // Binder + Validierung
        binder.forField(titel)
                .asRequired("Titel darf nicht leer sein")
                .bind(Film::getTitel, Film::setTitel);

        binder.forField(dauer)
                .asRequired("Dauer muss gesetzt sein")
                .withValidator(v -> v != null && v > 0,
                        "Dauer muss größer als 0 sein")
                .bind(Film::getDauer, Film::setDauer);

        binder.forField(beschreibung)
                .bind(Film::getBeschreibung, Film::setBeschreibung);

        neuButton.addClickListener(e -> clearForm());
        speichernButton.addClickListener(e -> saveFilm());
        loeschenButton.addClickListener(e -> deleteFilm());
    }

    private void selectFilm(Film film) {
        if (film != null) {
            currentFilm = film;
            binder.setBean(currentFilm);
        } else {
            clearForm();
        }
    }

    private void clearForm() {
        currentFilm = new Film();
        binder.setBean(currentFilm);
        grid.asSingleSelect().clear();
    }

    private void saveFilm() {
        if (binder.validate().isOk()) {
            filmRepository.save(currentFilm);
            updateGrid();
            clearForm();
            Notification.show("Film gespeichert", 2000, Notification.Position.MIDDLE);
        }
    }

    private void deleteFilm() {
        if (currentFilm == null || currentFilm.getId() == null) {
            return; // nichts ausgewählt oder noch nicht gespeichert
        }
        filmRepository.delete(currentFilm);
        updateGrid();
        clearForm();
        Notification.show("Film gelöscht", 2000, Notification.Position.MIDDLE);
    }

    private void updateGrid() {
        grid.setItems(filmRepository.findAll());
    }
}
