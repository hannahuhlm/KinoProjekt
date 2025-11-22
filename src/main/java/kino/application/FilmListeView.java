package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kino.application.data.Film;
import kino.application.data.FilmRepository;
import com.vaadin.flow.component.dependency.CssImport;
import java.time.format.DateTimeFormatter;

@CssImport("./styles/film-list.css")
@Route(value = "filmliste", layout = MainView.class)
@PageTitle("Filmliste")
public class FilmListeView extends VerticalLayout {

    private final FilmRepository filmRepository;

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public FilmListeView(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;

        setSizeFull();
        setPadding(true);
        setSpacing(false);
        addClassName("film-list-view");

        H2 heading = new H2("Aktuelles Programm");
        add(heading);

        // alle Filme laden und als reihen anzeigen
        filmRepository.findAll().forEach(film -> add(createFilmRow(film)));

        // zurück zur Startseite
        Button back = new Button("Zur Startseite",
                e -> getUI().ifPresent(ui -> ui.navigate("")));
        back.addClassName("film-back-button");
        add(back);
    }

    private HorizontalLayout createFilmRow(Film film) {
        HorizontalLayout row = new HorizontalLayout();
        row.addClassName("film-row");
        row.setWidthFull();
        row.setSpacing(true);

        // Bild / Poster
        String posterUrl = film.getPosterUrl() != null
                ? film.getPosterUrl()
                : "images/default-poster.jpg"; // fallback

        Image poster = new Image(posterUrl, "Filmplakat " + film.getTitel());
        poster.addClassName("film-poster");
        poster.setWidth("180px");
        poster.setHeight("260px");

        //Text-Spalte
        VerticalLayout info = new VerticalLayout();
        info.addClassName("film-info");
        info.setPadding(false);
        info.setSpacing(true);
        info.setWidthFull();

        H3 title = new H3(film.getTitel().toUpperCase());
        title.addClassName("film-title");

        // Filmstart oder Dauer
        String startText;
        if (film.getFilmstart() != null) {
            startText = "Filmstart: " + film.getFilmstart().format(dateFormatter);
        } else {
            // Fallback
            startText = "Dauer: " + film.getDauer() + " Minuten";
        }
        Span filmstart = new Span(startText);
        filmstart.addClassName("film-start");

        Paragraph description = new Paragraph(film.getBeschreibung());
        description.addClassName("film-description");

        Button more = new Button("Mehr lesen");
        more.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        more.addClassName("film-more-button");
        // TODO: hier könnte man auf eine Detail-View navigieren:
        // more.addClickListener(e ->
        //     getUI().ifPresent(ui -> ui.navigate(FilmDetailView.class, film.getId().toString()))
        // );

        HorizontalLayout bottom = new HorizontalLayout(more);
        bottom.setWidthFull();
        bottom.setJustifyContentMode(JustifyContentMode.END);
        bottom.setPadding(false);
        bottom.setSpacing(false);

        info.add(title, filmstart, description, bottom);

        row.add(poster, info);
        row.setFlexGrow(0, poster);
        row.setFlexGrow(1, info);

        return row;
    }
}
