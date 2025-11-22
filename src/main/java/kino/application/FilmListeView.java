package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kino.application.data.Film;
import kino.application.data.FilmRepository;

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

        // Überschrift
        H2 heading = new H2("Aktuelles Programm");
        heading.getStyle().set("margin-bottom", "20px");
        add(heading);

        // Filme laden
        filmRepository.findAll().forEach(film -> add(createFilmRow(film)));

        // Zurück zur Startseite Button
        Button back = new Button("Zur Startseite",
                e -> getUI().ifPresent(ui -> ui.navigate("")));
        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.getStyle().set("margin-top", "30px");
        add(back);
    }

    private HorizontalLayout createFilmRow(Film film) {
        HorizontalLayout row = new HorizontalLayout();
        row.addClassName("film-row");
        row.setWidthFull();
        row.setSpacing(true);

        // -----------------------------
        // POSTER
        // -----------------------------
        String posterUrl = film.getPosterUrl() != null
                ? film.getPosterUrl()
                : "images/default-poster.jpg";

        Image poster = new Image(posterUrl, "Filmplakat " + film.getTitel());
        poster.addClassName("film-poster");
        poster.setWidth("180px");
        poster.setHeight("260px");

        // -----------------------------
        // INFOSPALTE
        // -----------------------------
        VerticalLayout info = new VerticalLayout();
        info.addClassName("film-info");
        info.setPadding(false);
        info.setSpacing(true);
        info.setWidthFull();

        H3 title = new H3(film.getTitel());
        title.addClassName("film-title");

        // Filmstart / Dauer
        String startText = (film.getFilmstart() != null)
                ? "Filmstart: " + film.getFilmstart().format(dateFormatter)
                : "Dauer: " + film.getDauer() + " Minuten";
        Span filmstart = new Span(startText);

        Paragraph description = new Paragraph(film.getBeschreibung());
        description.addClassName("film-description");

        Button more = new Button("Mehr lesen");
        more.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        // TODO: navigation zu Detailansicht:
        // more.addClickListener(...)

        HorizontalLayout bottom = new HorizontalLayout(more);
        bottom.setWidthFull();
        bottom.setJustifyContentMode(JustifyContentMode.END);

        info.add(title, filmstart, description, bottom);

        row.add(poster, info);
        row.setFlexGrow(0, poster);
        row.setFlexGrow(1, info);

        return row;
    }
}
