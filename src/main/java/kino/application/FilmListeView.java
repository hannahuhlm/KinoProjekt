package kino.application;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kino.application.data.Film;
import kino.application.data.FilmRepository;

import java.util.List;

/**
 * View, die alle Filme aus der Datenbank als Tabelle anzeigt.
 *
 * Erreichbar unter: http://localhost:8080/filmliste
 */
@Route("filmliste")
@PageTitle("Filmliste")
public class FilmListeView extends VerticalLayout {

    private final FilmRepository filmRepository;

    /**
     * Der Konstruktor bekommt das FilmRepository von Spring automatisch
     * übergeben (Dependency Injection).
     *
     * Wichtig: Es gibt KEIN "new FilmRepository()" – das macht Spring für uns.
     */
    public FilmListeView(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Grid zum Anzeigen der Filme
        Grid<Film> grid = new Grid<>(Film.class, false);

        // Spalten explizit definieren (nur was ihr sehen wollt)
        grid.addColumn(Film::getTitel).setHeader("Titel");
        grid.addColumn(Film::getDauer).setHeader("Dauer (Minuten)");
        grid.addColumn(Film::getBeschreibung).setHeader("Beschreibung").setAutoWidth(true);

        // HIER werden die Daten aus der Datenbank geholt:
        List<Film> filme = filmRepository.findAll();  // SELECT * FROM film
        grid.setItems(filme);

        grid.setSizeFull();

        // Optional: Zur Startseite zurück
        Button zurStartseite = new Button("Zur Startseite", e ->
                getUI().ifPresent(ui -> ui.navigate(""))
        );

        add(zurStartseite, grid);
        expand(grid);
    }
}
