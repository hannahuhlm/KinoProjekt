package kino.application.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import kino.application.MainViewLayout;
import kino.application.data.Auffuehrung;
import kino.application.data.AuffuehrungRepository;
import kino.application.data.Buchung;
import kino.application.data.BuchungRepository;
import kino.application.data.Film;
import kino.application.data.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Route(value = "einnahmen", layout = MainViewLayout.class)
@PageTitle("Einnahmen")
@PermitAll
public class EinnahmenView extends VerticalLayout {

    private final FilmRepository filmRepository;
    private final BuchungRepository buchungRepository;
    private final AuffuehrungRepository auffuehrungRepository;

    private final Grid<Film> grid = new Grid<>(Film.class, false);

    @Autowired
    public EinnahmenView(FilmRepository filmRepository,
                         BuchungRepository buchungRepository,
                         AuffuehrungRepository auffuehrungRepository) {
        this.filmRepository = filmRepository;
        this.buchungRepository = buchungRepository;
        this.auffuehrungRepository = auffuehrungRepository;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // „Karte“ wie bei anderen Views (weißer Block in der Mitte)
        VerticalLayout card = new VerticalLayout();
        card.setWidth("90%");
        card.setHeight("90%");

        card.getStyle()
                .set("margin", "20px auto")
                .set("padding", "20px")
                .set("background", "white")
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 10px rgba(0,0,0,0.1)");

        H2 heading = new H2("Einnahmenübersicht");
        card.add(heading);

        configureGrid();
        updateGrid();

        card.add(grid);
        add(card);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // Spalte Titel
        grid.addColumn(Film::getTitel)
                .setHeader("Titel")
                .setAutoWidth(true)
                .setFlexGrow(1);

        // Neue Spalte: Gesamteinnahmen (über alle Aufführungen und deren Buchungen)
        grid.addColumn(film ->
                        String.format(Locale.GERMANY, "%.2f €", berechneGesamteinnahmen(film)))
                .setHeader("Gesamteinnahmen")
                .setAutoWidth(true)
                .setFlexGrow(0);

        // Spalte mit Aktionen (nur noch: Aufführungen ansehen)
        grid.addColumn(new ComponentRenderer<>(film -> {
            Button auffuehrungenBtn = new Button("Aufführungen ansehen");
            auffuehrungenBtn.addClickListener(e -> showAuffuehrungenDialog(film));

            HorizontalLayout layout = new HorizontalLayout(auffuehrungenBtn);
            layout.setSpacing(true);
            return layout;
        })).setHeader("Aktionen")
          .setAutoWidth(true)
          .setFlexGrow(0);
    }

    private void updateGrid() {
        grid.setItems(filmRepository.findAll());
    }

    /**
     * Berechnet die Gesamteinnahmen für einen Film:
     * Summe über alle Aufführungen dieses Films und deren Buchungen (gesamtpreis).
     */
    private double berechneGesamteinnahmen(Film film) {
        double sum = 0.0;

        // Aufführungen explizit über das Repository laden (wie in der Admin-View)
        List<Auffuehrung> auffuehrungen =
                auffuehrungRepository.findByFilmOrderByStartzeitpunktAsc(film);

        for (Auffuehrung a : auffuehrungen) {
            List<Buchung> buchungen = a.getBuchungen();
            if (buchungen != null) {
                for (Buchung b : buchungen) {
                    sum += b.getGesamtpreis();
                }
            }
        }

        return sum;
    }

    private void showGesamteinnahmen(Film film) {
        List<Auffuehrung> auff = film.getAuffuehrungen();
//        double sum = auff.stream()
        //TODO einnahemn holen

    }

    /**
     * Öffnet ein Popup, das alle Aufführungen des übergebenen Films anzeigt.
     * Die Aufführungen werden explizit über das Repository geladen.
     * Für jede Aufführung werden außerdem die Einnahmen aus allen zugehörigen
     * Buchungen (Summe von gesamtpreis) berechnet und angezeigt.
     */
    private void showAuffuehrungenDialog(Film film) {
        Dialog dialog = new Dialog();
        dialog.setWidth("800px");
        dialog.setHeight("500px");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(true);
        layout.setSpacing(true);

        H3 title = new H3("Aufführungen für: " + film.getTitel());
        layout.add(title);

        // Aufführungen explizit über das Repository laden
        List<Auffuehrung> auffuehrungen =
                auffuehrungRepository.findByFilmOrderByStartzeitpunktAsc(film);

        if (auffuehrungen.isEmpty()) {
            layout.add(new Paragraph("Keine Aufführungen vorhanden."));
        } else {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            // Grid für eine übersichtliche Darstellung
            Grid<Auffuehrung> auffGrid = new Grid<>(Auffuehrung.class, false);
            auffGrid.setWidthFull();
            auffGrid.setHeight("320px");

            auffGrid.addColumn(a -> a.getStartzeitpunkt()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                            .format(formatter))
                    .setHeader("Startzeitpunkt")
                    .setAutoWidth(true);

            auffGrid.addColumn(a ->
                            a.getSaal() != null ? a.getSaal().getName() : "-")
                    .setHeader("Saal")
                    .setAutoWidth(true);

            // Spalte: Einnahmen pro Aufführung (Summe der gesamtpreis aller Buchungen dieser Aufführung)
            auffGrid.addColumn(a -> {
                        List<Buchung> buchungen = a.getBuchungen();
                        double sum = 0.0;
                        if (buchungen != null) {
                            for (Buchung b : buchungen) {
                                sum += b.getGesamtpreis();
                            }
                        }
                        return String.format(Locale.GERMANY, "%.2f €", sum);
                    })
                    .setHeader("Einnahmen")
                    .setAutoWidth(true);

            auffGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

            auffGrid.setItems(auffuehrungen);

            layout.add(auffGrid);
        }

        Button close = new Button("Schließen", e -> dialog.close());
        layout.add(close);

        dialog.add(layout);
        dialog.open();
    }
}
