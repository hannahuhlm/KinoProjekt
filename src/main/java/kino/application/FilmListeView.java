package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kino.application.kafka.dto.FilmDTO;
import kino.application.kafka.events.AdminCommand;
import kino.application.kafka.events.AdminEvent;
import kino.application.kafka.producer.AdminCommandProducer;
import kino.application.admin.AdminUIEventBus;

import java.time.format.DateTimeFormatter;

@CssImport("./styles/film-list.css")
@Route(value = "filmliste", layout = MainViewLayout.class)
@PageTitle("Filmliste")
public class FilmListeView extends VerticalLayout {

        private final AdminCommandProducer adminCommandProducer;
        private AdminUIEventBus.Registration adminReg;
        private String correlationId;
    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

        public FilmListeView(AdminCommandProducer adminCommandProducer) {
                this.adminCommandProducer = adminCommandProducer;

        setSizeFull();
        setPadding(true);
        setSpacing(false);
        addClassName("film-list-view");

        // Überschrift
        H2 heading = new H2("Aktuelles Programm");
        heading.getStyle().set("margin-bottom", "20px");
        add(heading);

        // Loading placeholder while Kafka query runs
        Div loading = new Div(new Span("Lade Filme…"));
        loading.getStyle().set("margin", "10px 0");
        add(loading);

                // Subscribe to admin events for vollständige Liste (keine Pagination)
                adminReg = AdminUIEventBus.register(ev -> {
                        System.out.println(">>> FilmListeView received event: " + ev);
                        if (ev == null || ev.getCorrelationId() == null) {
                                System.out.println(">>> Event null or no correlationId");
                                return;
                        }
                        System.out.println(">>> Event correlationId: " + ev.getCorrelationId() + ", expected: " + correlationId);
                        if (!ev.getCorrelationId().equals(correlationId)) {
                                System.out.println(">>> CorrelationId mismatch");
                                return;
                        }
                        System.out.println(">>> Event entity: " + ev.getEntity() + ", action: " + ev.getAction());
                        if (ev.getEntity() != AdminEvent.Entity.FILM || ev.getAction() != AdminEvent.Action.QUERY) {
                                System.out.println(">>> Not a FILM QUERY event");
                                return;
                        }
                        System.out.println(">>> Processing films, status: " + ev.getStatus() + ", films: " + (ev.getFilms() != null ? ev.getFilms().size() : "null"));
                        getUI().ifPresent(ui -> ui.access(() -> {
                                // Keep header and base layout, append films under it
                                // Remove previous film rows but keep the heading and back button area intact
                                // Remove film rows (HorizontalLayout) and transient placeholders (Div)
                                getChildren().filter(c -> c instanceof HorizontalLayout || c instanceof Div)
                                                .forEach(this::remove);
                                // Append film rows
                                if (ev.getStatus() == AdminEvent.Status.OK && ev.getFilms() != null) {
                                        ev.getFilms().forEach(f -> add(createFilmRow(f)));
                                } else {
                                        add(new Paragraph("Keine Filme gefunden"));
                                }
                        }));
                });

                // Request vollständige Liste der Filme via Admin QUERY
                correlationId = java.util.UUID.randomUUID().toString();
                System.out.println(">>> FilmListeView sending query with correlationId: " + correlationId);
                AdminCommand cmd = new AdminCommand(AdminCommand.Entity.FILM, AdminCommand.Action.QUERY);
                AdminCommand.QueryPayload q = new AdminCommand.QueryPayload();
                q.setType(AdminCommand.QueryPayload.Type.LIST_ALL);
                q.setCorrelationId(correlationId);
                cmd.setQuery(q);
                System.out.println(">>> Sending command: " + cmd);
                this.adminCommandProducer.send(cmd);

        // Zurück zur Startseite Button (keine automatische Navigation)
        Button back = new Button("Startseite",
                e -> getUI().ifPresent(ui -> ui.navigate("")));
        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.getStyle().set("margin-top", "30px");
        add(back);
    }

        // Pagination entfernt

    private HorizontalLayout createFilmRow(FilmDTO film) {
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

        // INFOSPALTE
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
        
        //Platz begrenzen
        info.getStyle().set("max-width", "calc(100%)"); // oder 100% für flexible Breite
        description.getStyle()
                .set("overflow-wrap", "break-word")
                .set("word-break", "break-word")
                .set("white-space", "normal");


        Button more = new Button("Mehr lesen");
        more.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        more.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("film/" + film.getId()));
        });


        HorizontalLayout bottom = new HorizontalLayout(more);
        bottom.setWidthFull();
        bottom.setJustifyContentMode(JustifyContentMode.END);

        info.add(title, filmstart, description, bottom);

        row.add(poster, info);
        row.setFlexGrow(0, poster);
        row.setFlexGrow(1, info);

        return row;
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        try {
            if (adminReg != null) {
                adminReg.remove();
                adminReg = null;
            }
        } finally {
            super.onDetach(detachEvent);
        }
    }
}
