package kino.application.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import kino.application.MainViewLayout;
import kino.application.aggregation.MongoRevenueRepository;
import kino.application.aggregation.RevenueAggregate;
import kino.application.kafka.events.AggregationCommand;
import kino.application.kafka.producer.AggregationCommandProducer;
import kino.application.data.Auffuehrung;
import kino.application.data.AuffuehrungRepository;
import kino.application.data.Film;
import kino.application.data.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Route(value = "einnahmen", layout = MainViewLayout.class)
@PageTitle("Einnahmen")
@PermitAll
public class EinnahmenView extends VerticalLayout {

    private final MongoRevenueRepository mongoRepo;
    private final FilmRepository filmRepo;
    private final AuffuehrungRepository auffRepo;
    private final AggregationCommandProducer aggregationProducer;
    
    private final Grid<Film> grid = new Grid<>(Film.class, false);

    @Autowired
    public EinnahmenView(MongoRevenueRepository mongoRepo, 
                         FilmRepository filmRepo,
                         AuffuehrungRepository auffRepo,
                         AggregationCommandProducer aggregationProducer) {
        this.mongoRepo = mongoRepo;
        this.filmRepo = filmRepo;
        this.auffRepo = auffRepo;
        this.aggregationProducer = aggregationProducer;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // „Karte" wie bei anderen Views (weißer Block in der Mitte)
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
        Paragraph info = new Paragraph("Einnahmen basieren auf aggregierten Daten aus MongoDB. " +
                "Aggregation erfolgt täglich um 02:00 Uhr oder per Klick auf 'Jetzt aggregieren'.");
        info.getStyle().set("color", "#666").set("font-size", "14px");
        
        Button refreshBtn = new Button("📊 Jetzt aggregieren");
        refreshBtn.addClickListener(e -> triggerAggregation());
        
        card.add(heading, info, refreshBtn);

        configureGrid();
        updateGrid();

        card.add(grid);
        add(card);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // Spalte: Filmtitel
        grid.addColumn(Film::getTitel)
                .setHeader("Titel")
                .setAutoWidth(true)
                .setFlexGrow(1);

        // Spalte: Gesamteinnahmen (aus MongoDB)
        grid.addColumn(film -> {
                    double total = berechneGesamteinnahmen(film);
                    return String.format(Locale.GERMANY, "%.2f €", total);
                })
                .setHeader("Gesamteinnahmen")
                .setAutoWidth(true)
                .setFlexGrow(0);

        // Spalte: Aktionen (Aufführungen ansehen)
        grid.addColumn(new ComponentRenderer<>(film -> {
            Button btn = new Button("Aufführungen ansehen");
            btn.addClickListener(e -> showAuffuehrungenDialog(film));
            return btn;
        })).setHeader("Aktionen")
          .setAutoWidth(true)
          .setFlexGrow(0);
    }

    private void updateGrid() {
        List<Film> allFilms = filmRepo.findAll();
        grid.setItems(allFilms);
    }
    
    private kino.application.aggregation.AggregationUIEventBus.Registration aggReg;

    private void triggerAggregation() {
        LocalDate today = LocalDate.now();
        String corr = java.util.UUID.randomUUID().toString();

        // Listen for result event matching correlationId
        if (aggReg != null) { aggReg.remove(); aggReg = null; }
        aggReg = kino.application.aggregation.AggregationUIEventBus.register(ev -> {
            if (ev == null) return;
            if (ev.getCorrelationId() != null && ev.getCorrelationId().equals(corr)
                    && ev.getDay() != null && ev.getDay().equals(today)
                    && ev.getOperation() == kino.application.kafka.events.AggregationResultEvent.Operation.INSERT) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    updateGrid();
                    String msg = (ev.getStatus() == kino.application.kafka.events.AggregationResultEvent.Status.SUCCESS)
                            ? ("✅ Aggregation abgeschlossen: " + ev.getCount() + " Einträge gespeichert")
                            : ("❌ Aggregation fehlgeschlagen: " + (ev.getMessage() != null ? ev.getMessage() : "Unbekannter Fehler"));
                    com.vaadin.flow.component.notification.Notification.show(
                            msg,
                            3500,
                            com.vaadin.flow.component.notification.Notification.Position.BOTTOM_END
                    );
                }));
                // one-shot listener
                if (aggReg != null) { aggReg.remove(); aggReg = null; }
            }
        });

        AggregationCommand cmd = new AggregationCommand(today, corr);
        aggregationProducer.send(cmd);
    }

    @Override
    protected void onDetach(com.vaadin.flow.component.DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        if (aggReg != null) { aggReg.remove(); aggReg = null; }
    }
    
    /**
     * Berechnet Gesamteinnahmen für einen Film aus MongoDB-Aggregaten
     */
    private double berechneGesamteinnahmen(Film film) {
        List<RevenueAggregate> aggregates = mongoRepo.findByFilmId(film.getId());
        return aggregates.stream().mapToDouble(RevenueAggregate::getRevenue).sum();
    }
    
    /**
     * Zeigt Dialog mit Aufführungen und deren Einnahmen aus MongoDB
     */
    private void showAuffuehrungenDialog(Film film) {
        Dialog dialog = new Dialog();
        dialog.setWidth("1100px");
        dialog.setHeight("700px");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(true);
        layout.setSpacing(true);

        H3 title = new H3("Aufführungen für: " + film.getTitel());
        layout.add(title);

        // Lade Aufführungen
        List<Auffuehrung> auffuehrungen = auffRepo.findByFilmOrderByStartzeitpunktAsc(film);

        if (auffuehrungen.isEmpty()) {
            layout.add(new Paragraph("Keine Aufführungen vorhanden."));
        } else {
            // Grid für Aufführungen mit Details-Button
            Grid<Auffuehrung> auffGrid = new Grid<>(Auffuehrung.class, false);
            auffGrid.setWidthFull();
            auffGrid.setHeight("250px");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            auffGrid.addColumn(a -> a.getStartzeitpunkt()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                            .format(formatter))
                    .setHeader("Startzeitpunkt")
                    .setAutoWidth(true);

            auffGrid.addColumn(a -> a.getSaal() != null ? a.getSaal().getName() : "-")
                    .setHeader("Saal")
                    .setAutoWidth(true);

            auffGrid.addColumn(a -> {
                        List<RevenueAggregate> aggs = mongoRepo.findAll().stream()
                                .filter(agg -> a.getId().equals(agg.getAuffuehrungId()))
                                .toList();
                        double sum = aggs.stream().mapToDouble(RevenueAggregate::getRevenue).sum();
                        return String.format(Locale.GERMANY, "%.2f €", sum);
                    })
                    .setHeader("Gesamteinnahmen")
                    .setAutoWidth(true);

                // Statt Anzahl Aggregationen jetzt Anzahl Buchungen anzeigen
                auffGrid.addColumn(a -> {
                    int count = (a.getBuchungen() != null) ? a.getBuchungen().size() : 0;
                    return count + " Buchung(en)";
                    })
                    .setHeader("Buchungen")
                    .setAutoWidth(true);

                    // Belegungsspalte basierend auf letztem Aggregat
                    auffGrid.addColumn(a -> {
                        // letztes Aggregat für Aufführung holen
                        RevenueAggregate latest = mongoRepo.findAll().stream()
                            .filter(agg -> a.getId().equals(agg.getAuffuehrungId()))
                            .sorted(Comparator.comparing(RevenueAggregate::getAggregatedAt,
                                Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                            .findFirst().orElse(null);
                        if (latest == null) {
                            return "-";
                        }
                        int occ = latest.getOccupiedSeatsCount();
                        int total = latest.getTotalSeatsCount();
                        double percent = latest.getOccupancyPercent();
                        return String.format(Locale.GERMANY, "%d / %d (%.0f%%)", occ, total, percent);
                        })
                        .setHeader("Belegung")
                        .setAutoWidth(true);

                        auffGrid.addColumn(new ComponentRenderer<>(auffuehrung -> {
                                Button detailsBtn = new Button("Details anzeigen");
                                detailsBtn.addClickListener(e -> showAggregationDetails(auffuehrung));
                                return detailsBtn;
                        })).setHeader("Details")
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
    
    /**
     * Zeigt alle Aggregationen für eine Aufführung sortiert nach Zeit
     */
        private void showAggregationDetails(Auffuehrung auffuehrung) {
        Dialog detailDialog = new Dialog();
        detailDialog.setWidth("700px");
        detailDialog.setHeight("500px");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(true);
        layout.setSpacing(true);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String auffTime = auffuehrung.getStartzeitpunkt()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .format(timeFormatter);

        H3 title = new H3("Details zur Aufführung am " + auffTime);
        layout.add(title);

        // Hole (aktuell genau eine) Aggregation für diese Aufführung
        List<RevenueAggregate> aggregates = mongoRepo.findAll().stream()
            .filter(agg -> auffuehrung.getId().equals(agg.getAuffuehrungId()))
            .sorted(Comparator.comparing(RevenueAggregate::getAggregatedAt,
                Comparator.nullsLast(Comparator.naturalOrder())).reversed())
            .toList();

        RevenueAggregate agg = aggregates.isEmpty() ? null : aggregates.get(0);

        int buchungen = (auffuehrung.getBuchungen() != null) ? auffuehrung.getBuchungen().size() : 0;
        double revenue = (agg != null) ? agg.getRevenue() : 0.0;
        String aggregatedAt = (agg != null && agg.getAggregatedAt() != null)
            ? agg.getAggregatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
            : "Noch nicht aggregiert";

        int occ = (agg != null) ? agg.getOccupiedSeatsCount() : 0;
        int total = (agg != null) ? agg.getTotalSeatsCount() : 0;
        double percent = (agg != null) ? agg.getOccupancyPercent() : 0.0;

        if (agg == null) {
            layout.add(new Paragraph("Keine Aggregationsdaten vorhanden."));
        } else {
            Paragraph pRevenue = new Paragraph("💰 Einnahmen: " + String.format(Locale.GERMANY, "%.2f €", revenue));
            Paragraph pBookings = new Paragraph("🎫 Buchungen: " + buchungen);
            Paragraph pOccupancy = new Paragraph("🪑 Belegung: " + String.format(Locale.GERMANY, "%d / %d (%.0f%%)", occ, total, percent));
            Paragraph pAggregatedAt = new Paragraph("🕒 Aggregiert am: " + aggregatedAt);
            pRevenue.getStyle().set("font-weight", "bold");
            pBookings.getStyle().set("font-weight", "bold");
            pOccupancy.getStyle().set("font-weight", "bold");
            pAggregatedAt.getStyle().set("color", "#555");
            layout.add(pRevenue, pBookings, pOccupancy, pAggregatedAt);
        }

        // Ergänzende Hinweise
        Paragraph hint = new Paragraph("Die Anzeige basiert auf der letzten täglichen Aggregation.");
        hint.getStyle().set("font-size", "12px").set("color", "#666");
        layout.add(hint);

        Button close = new Button("Schließen", e -> detailDialog.close());
        layout.add(close);

        detailDialog.add(layout);
        detailDialog.open();
        }
}
