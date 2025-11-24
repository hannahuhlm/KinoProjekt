package kino.application.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kino.application.MainView;
import kino.application.data.Film;
import kino.application.data.FilmRepository;
import kino.application.data.Kinosaal;
import kino.application.data.KinosaalRepository;
import kino.application.data.Auffuehrung;
import kino.application.data.AuffuehrungRepository;

import java.time.ZoneId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Route(value = "film-verwalten", layout = MainView.class)
@PageTitle("Admin – Filme")
public class AdminFilmAnlegenView extends VerticalLayout {

    private final FilmRepository filmRepository;
    private final AuffuehrungRepository auffuehrungRepository;

    private Grid<Film> grid = new Grid<>(Film.class, false);

    private TextField titel = new TextField("Titel");
    private IntegerField dauer = new IntegerField("Dauer (Minuten)");
    private DatePicker filmstart = new DatePicker("Filmstart");
    private TextField posterUrl = new TextField("Poster-URL");
    private TextArea beschreibung = new TextArea("Beschreibung");

    private Button neuButton = new Button("Neu");
    private Button speichernButton = new Button("Speichern");
    private Button loeschenButton = new Button("Löschen");

    private Binder<Film> binder = new Binder<>(Film.class);
    private Film currentFilm;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final KinosaalRepository kinosaalRepository;


    public AdminFilmAnlegenView(FilmRepository filmRepository, KinosaalRepository kinosaalRepository, AuffuehrungRepository auffuehrungRepository) {
        this.filmRepository = filmRepository;
        this.auffuehrungRepository = auffuehrungRepository;
		this.kinosaalRepository = kinosaalRepository;

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
        grid.addColumn(Film::getDauer).setHeader("Dauer (Minuten)");
        grid.addColumn(f -> f.getFilmstart() != null ? f.getFilmstart().format(dateFormatter) : "")
                .setHeader("Filmstart");
        grid.addColumn(Film::getBeschreibung).setHeader("Beschreibung").setFlexGrow(1);

        grid.addColumn(new ComponentRenderer<>(film -> {
            Button auffuehrungenButton = new Button("Aufführungen planen");
            auffuehrungenButton.addClickListener(ev -> openAuffuehrungenDialog(film));
            return auffuehrungenButton;
        })).setHeader("Aktionen").setAutoWidth(true);

        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        grid.asSingleSelect().addValueChangeListener(event -> selectFilm(event.getValue()));
    }

    private void configureForm() {
        dauer.setMin(1);
        posterUrl.setWidthFull();
        beschreibung.setWidthFull();
        beschreibung.setMinHeight("120px");

        binder.forField(titel).asRequired("Titel darf nicht leer sein").bind(Film::getTitel, Film::setTitel);
        binder.forField(dauer).asRequired("Dauer muss gesetzt sein")
                .withValidator(v -> v != null && v > 0, "Dauer muss größer als 0 sein")
                .bind(Film::getDauer, Film::setDauer);
        binder.forField(filmstart).bind(Film::getFilmstart, Film::setFilmstart);
        binder.forField(posterUrl).bind(Film::getPosterUrl, Film::setPosterUrl);
        binder.forField(beschreibung).bind(Film::getBeschreibung, Film::setBeschreibung);

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
        if (currentFilm == null || currentFilm.getId() == null) return;
        filmRepository.delete(currentFilm);
        updateGrid();
        clearForm();
        Notification.show("Film gelöscht", 2000, Notification.Position.MIDDLE);
    }

    private void updateGrid() {
        grid.setItems(filmRepository.findAll());
    }

    // ------------------ Aufführungen-Popup -------------------
    private void openAuffuehrungenDialog(Film film) {
        Dialog dialog = new Dialog();
        dialog.setWidth("900px");
        dialog.setHeight("600px");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(new H2("Aufführungen planen für: " + film.getTitel()));

        TreeGrid<Object> treeGrid = new TreeGrid<>();
        treeGrid.setWidthFull();
        treeGrid.setHeight("400px");

        treeGrid.addHierarchyColumn(obj -> {
            if (obj instanceof Integer) return "KW " + obj;
            if (obj instanceof Auffuehrung) return "Aufführung: " + ((Auffuehrung) obj).getStartzeitpunkt();
            return "";
        }).setHeader("Kalenderwoche / Aufführung");

        treeGrid.addColumn(obj -> {
            if (obj instanceof Integer) {
                int week = (Integer) obj;
                long count = film.getAuffuehrungen().stream().filter(a -> getKalenderwoche(a) == week).count();
                return count;
            }
            return "";
        }).setHeader("Anzahl").setAutoWidth(true);

        treeGrid.addComponentColumn(obj -> {
            if (obj instanceof Auffuehrung) {
                Auffuehrung auff = (Auffuehrung) obj;
                Button loeschen = new Button("Löschen");
                loeschen.addClickListener(ev -> {
                    film.getAuffuehrungen().remove(auff);
                    auffuehrungRepository.delete(auff);
                    treeGrid.setDataProvider(new TreeDataProvider<>(buildTreeData(film)));
                });
                return loeschen;
            }
            return null;
        }).setHeader("Aktionen").setAutoWidth(true);

        TreeData<Object> treeData = buildTreeData(film);
        treeGrid.setDataProvider(new TreeDataProvider<>(treeData));

        layout.add(treeGrid);

        Button neueAuffuehrung = new Button("Neue Aufführung planen");
        neueAuffuehrung.getStyle().set("background-color", "#1976d2");
        neueAuffuehrung.getStyle().set("color", "white");
        neueAuffuehrung.setWidthFull();
        neueAuffuehrung.addClickListener(ev -> openNeueAuffuehrungDialog(film, dialog));

        Button schliessen = new Button("Schließen", ev -> dialog.close());

        VerticalLayout buttonLayout = new VerticalLayout(neueAuffuehrung, schliessen);
        buttonLayout.setWidthFull();
        layout.add(buttonLayout);

        dialog.add(layout);
        dialog.open();
    }

    private void openNeueAuffuehrungDialog(Film film, Dialog parentDialog) {
        Dialog dialog = new Dialog();
        dialog.setWidth("900px");
        dialog.setHeight("600px");
        dialog.getElement().getStyle().set("margin-left", "40px");
        dialog.getElement().getStyle().set("margin-top", "40px");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(new H2("Neue Aufführung planen für: " + film.getTitel()));

        DatePicker datumPicker = new DatePicker("Datum wählen");
        TextField startzeitField = new TextField("Startzeit (HH:mm)");

        ComboBox<Kinosaal> saalCombo = new ComboBox<>("Kinosaal wählen");
        saalCombo.setItems(kinosaalRepository.findAll());
        saalCombo.setItemLabelGenerator(Kinosaal::getName);

        layout.add(datumPicker, startzeitField, saalCombo);

        Button speichern = new Button("Speichern");
        speichern.getStyle().set("background-color", "#1976d2");
        speichern.getStyle().set("color", "white");
        speichern.setWidthFull();

        Button schliessen = new Button("Schließen", ev -> dialog.close());

        VerticalLayout buttons = new VerticalLayout(speichern, schliessen);
        buttons.setWidthFull();
        layout.add(buttons);

        speichern.addClickListener(ev -> {
            LocalDate datum = datumPicker.getValue();
            String zeitText = startzeitField.getValue();
            Kinosaal ausgewaehlterSaal = saalCombo.getValue();

            if (datum == null || zeitText.isEmpty() || ausgewaehlterSaal == null) {
                Notification.show("Bitte Datum, Uhrzeit und Kinosaal angeben", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date startzeit = format.parse(datum.toString() + " " + zeitText);

                Auffuehrung neue = new Auffuehrung();
                neue.setStartzeitpunkt(startzeit);
                neue.setFilm(film);
                neue.setSaal(ausgewaehlterSaal);

                auffuehrungRepository.save(neue);

                // Optional: für bidirektionale Beziehung hinzufügen
                film.getAuffuehrungen().add(neue);

                dialog.close();
                parentDialog.close();
                openAuffuehrungenDialog(film); // UI aktualisieren

                Notification.show("Aufführung gespeichert", 2000, Notification.Position.MIDDLE);

            } catch (ParseException e) {
                Notification.show("Ungültiges Zeitformat, bitte HH:mm eingeben", 3000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Fehler beim Speichern: " + e.getMessage(), 4000, Notification.Position.MIDDLE);
                e.printStackTrace();
            }
        });

        dialog.add(layout);
        dialog.open();
    }

    private TreeData<Object> buildTreeData(Film film) {
        TreeData<Object> treeData = new TreeData<>();

        List<Integer> wochen = film.getAuffuehrungen().stream()
                .map(this::getKalenderwoche)
                .distinct()
                .sorted()
                .toList();

        for (Integer woche : wochen) {
            treeData.addItem(null, woche);
            List<Auffuehrung> auffuehrungen = film.getAuffuehrungen().stream()
                    .filter(a -> getKalenderwoche(a) == woche)
                    .toList();
            auffuehrungen.forEach(auff -> treeData.addItem(woche, auff));
        }

        return treeData;
    }

    private int getKalenderwoche(Auffuehrung auff) {
        LocalDate datum = auff.getStartzeitpunkt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return datum.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }
}