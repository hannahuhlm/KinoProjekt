package kino.application.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
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
@PageTitle("Admin – Filme verwalten")
public class AdminFilmAnlegenView extends VerticalLayout {

    private final FilmRepository filmRepository;

    private final Grid<Film> grid = new Grid<>(Film.class, false);

    private final TextField titel = new TextField("Titel");
    private final IntegerField dauer = new IntegerField("Dauer (Minuten)");
    private final DatePicker filmstart = new DatePicker("Filmstart");
    private final TextField posterUrl = new TextField("Poster-URL");
    private final TextArea beschreibung = new TextArea("Beschreibung");

    private final Button neuButton = new Button("Neu");
    private final Button speichernButton = new Button("Speichern");
    private final Button loeschenButton = new Button("Löschen");

    private final Binder<Film> binder = new Binder<>(Film.class);
    private Film currentFilm;

    public AdminFilmAnlegenView(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Admin: Filme verwalten"));

        configureGrid();
        configureForm();

        VerticalLayout formLayout = new VerticalLayout(
                titel,
                dauer,
                filmstart,
                posterUrl,
                beschreibung,
                new HorizontalLayout(neuButton, speichernButton, loeschenButton)
        );
        formLayout.setWidth("420px");

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
        grid.addColumn(Film::getDauer).setHeader("Dauer (Minuten)").setAutoWidth(true);
        grid.addColumn(f -> f.getFilmstart() != null ? f.getFilmstart().toString() : "")
                .setHeader("Filmstart").setAutoWidth(true);
        grid.addColumn(Film::getBeschreibung).setHeader("Beschreibung").setFlexGrow(1);

        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                selectFilm(event.getValue());
            }
        });
    }

    private void configureForm() {
        dauer.setMin(1);
        posterUrl.setWidthFull();
        beschreibung.setWidthFull();
        beschreibung.setMinHeight("120px");

        binder.forField(titel).asRequired("Titel darf nicht leer sein").bind(Film::getTitel, Film::setTitel);
        binder.forField(dauer).asRequired("Dauer muss gesetzt sein")
                .withValidator(v -> v != null && v > 0, "Dauer muss > 0 sein")
                .bind(Film::getDauer, Film::setDauer);
        binder.forField(filmstart).bind(Film::getFilmstart, Film::setFilmstart);
        binder.forField(posterUrl).bind(Film::getPosterUrl, Film::setPosterUrl);
        binder.forField(beschreibung).bind(Film::getBeschreibung, Film::setBeschreibung);

        neuButton.addClickListener(e -> clearForm());
        speichernButton.addClickListener(e -> saveFilm());
        loeschenButton.addClickListener(e -> deleteFilm());
    }

    private void selectFilm(Film film) {
        if (film != null && film.getId() != null) {
            currentFilm = filmRepository.findById(film.getId()).orElse(film);
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
        if (!binder.validate().isOk()) return;

        boolean isNew = currentFilm.getId() == null;
        Film saved = filmRepository.save(currentFilm);

        updateGrid();
        clearForm();

        Notification.show(isNew ? "Film erstellt" : "Film aktualisiert", 2000, Notification.Position.MIDDLE);
    }

    private void deleteFilm() {
        if (currentFilm == null || currentFilm.getId() == null) return;
        filmRepository.delete(currentFilm);
        updateGrid();
        clearForm();
        Notification.show("Film gelöscht", 2000, Notification.Position.MIDDLE);
    }

    private void updateGrid() {
        grid.setItems(filmRepository.findAll());
    }
}
