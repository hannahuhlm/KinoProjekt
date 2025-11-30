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
import com.vaadin.flow.component.DetachEvent;
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

import kino.application.MainViewLayout;
import kino.application.data.Film;
import kino.application.data.FilmRepository;
import kino.application.data.Kinosaal;
import kino.application.data.KinosaalRepository;
import kino.application.data.Auffuehrung;
import kino.application.kafka.events.AdminCommand;
import kino.application.kafka.events.AdminEvent;
import kino.application.kafka.producer.AdminCommandProducer;

import java.time.ZoneId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Route(value = "film-verwalten", layout = MainViewLayout.class)
@PageTitle("Admin – Filme")
public class AdminFilmAnlegenView extends VerticalLayout {

    private final FilmRepository filmRepository;
    private final kino.application.service.AdminService adminService;
    private final AdminCommandProducer adminCommandProducer;

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
    private AdminUIEventBus.Registration adminReg;
    private Dialog offeneAuffuehrungenDialog;
    private Film dialogFilm;


    public AdminFilmAnlegenView(FilmRepository filmRepository, KinosaalRepository kinosaalRepository, kino.application.service.AdminService adminService, AdminCommandProducer adminCommandProducer) {
        this.filmRepository = filmRepository;
        this.kinosaalRepository = kinosaalRepository;
        this.adminService = adminService;
        this.adminCommandProducer = adminCommandProducer;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        //weiße Überschrift
        H2 h2= new H2("Admin: Filme verwalten");
        h2.getStyle().set("color", "white");
        add(h2);

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

        // >>> Formular wie eine weiße Karte gestalten, die sich vom dunklen Hintergrund abhebt
        formLayout.getStyle()
                .set("background", "white")
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 10px rgba(0,0,0,0.1)")
                .set("padding", "20px");

        HorizontalLayout content = new HorizontalLayout(grid, formLayout);
        content.setSizeFull();
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, formLayout);

        add(content);

        updateGrid();
        clearForm();

        // Register to Admin events to refresh UI without delays
        adminReg = AdminUIEventBus.register(ev -> {
            getUI().ifPresent(ui -> ui.access(() -> {
                if (ev.getEntity() == AdminEvent.Entity.FILM && ev.getAction() == AdminEvent.Action.QUERY) {
                    if (ev.getFilms() != null) {
                        java.util.List<Film> items = ev.getFilms().stream().map(dto -> {
                            Film f = new Film();
                            f.setId(dto.getId());
                            f.setTitel(dto.getTitel());
                            f.setDauer(dto.getDauer());
                            f.setBeschreibung(dto.getBeschreibung());
                            f.setPosterUrl(dto.getPosterUrl());
                            f.setFilmstart(dto.getFilmstart());
                            return f;
                        }).toList();
                        grid.setItems(items);
                    }
                } else if (ev.getEntity() == AdminEvent.Entity.FILM && ev.getAction() == AdminEvent.Action.CREATE) {
                    // After create, re-query
                    updateGrid();
                } else if (ev.getEntity() == AdminEvent.Entity.FILM && ev.getAction() == AdminEvent.Action.DELETE) {
                    // After delete, refresh list and clear form if deleted film was selected
                    if (ev.getFilmId() != null && currentFilm != null && ev.getFilmId().equals(currentFilm.getId())) {
                        clearForm();
                    }
                    updateGrid();
                } else if (ev.getEntity() == AdminEvent.Entity.AUFFUEHRUNG 
                        && (ev.getAction() == AdminEvent.Action.CREATE || ev.getAction() == AdminEvent.Action.DELETE)
                        && ev.getStatus() == AdminEvent.Status.SUCCESS) {
                    // Only on CREATE/DELETE success, refresh dialog
                    updateGrid();
                    // Lokale Kopie zur Vermeidung von Race Conditions
                    Film film = dialogFilm;
                    if (offeneAuffuehrungenDialog != null && offeneAuffuehrungenDialog.isOpened() && film != null) {
                        offeneAuffuehrungenDialog.close();
                        filmRepository.findById(film.getId()).ifPresent(this::openAuffuehrungenDialog);
                    }
                }
            }));
        });
    }

    private void configureGrid() {
        grid.addColumn(Film::getTitel).setHeader("Titel").setAutoWidth(true);
        grid.addColumn(Film::getDauer).setHeader("Dauer (Minuten)");
        grid.addColumn(f -> f.getFilmstart() != null ? f.getFilmstart().format(dateFormatter) : "")
                .setHeader("Filmstart");
        grid.addColumn(Film::getBeschreibung).setHeader("Beschreibung").setFlexGrow(1);

        grid.addColumn(new ComponentRenderer<>(film -> {
            Button auffuehrungenButton = new Button("Aufführungen planen");
            // >>> Button in der Tabelle blau wie die anderen
            auffuehrungenButton.getStyle().set("background-color", "#1976d2");
            auffuehrungenButton.getStyle().set("color", "white");

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

        // >>> Formular-Buttons ebenfalls blau und einheitlich
        neuButton.getStyle().set("background-color", "#1976d2").set("color", "white");
        speichernButton.getStyle().set("background-color", "#1976d2").set("color", "white");
        loeschenButton.getStyle().set("background-color", "#1976d2").set("color", "white");

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
            adminService.saveFilm(currentFilm);
            // UI aktualisiert sich über AdminEvent (event-driven)
            Notification.show("Film wird gespeichert…", 1500, Notification.Position.MIDDLE);
        }
    }

    private void deleteFilm() {
        if (currentFilm == null || currentFilm.getId() == null) return;
        adminService.deleteFilm(currentFilm.getId());
        // UI aktualisiert sich über AdminEvent (event-driven)
        Notification.show("Film wird gelöscht…", 1500, Notification.Position.MIDDLE);
    }

    private String correlationId;
    private void updateGrid() {
        correlationId = java.util.UUID.randomUUID().toString();
        AdminCommand cmd = new AdminCommand(AdminCommand.Entity.FILM, AdminCommand.Action.QUERY);
        AdminCommand.QueryPayload q = new AdminCommand.QueryPayload();
        q.setType(AdminCommand.QueryPayload.Type.LIST_ALL);
        q.setCorrelationId(correlationId);
        cmd.setQuery(q);
        adminCommandProducer.send(cmd);
    }

    // ------------------ Aufführungen-Popup -------------------
    private void openAuffuehrungenDialog(Film film) {
        Dialog dialog = new Dialog();
        this.offeneAuffuehrungenDialog = dialog;
        this.dialogFilm = film;
        dialog.setWidth("900px");
        dialog.setHeight("600px");
        dialog.setModal(true);
        dialog.setDraggable(false);
        dialog.setCloseOnOutsideClick(false);
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnEsc(true);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(new H2("Aufführungen planen für: " + film.getTitel()));

        // *** HIER: Aufführungen explizit laden ***
        // Query Aufführungen via Kafka
        String corr = java.util.UUID.randomUUID().toString();
        AdminCommand cmd = new AdminCommand(AdminCommand.Entity.AUFFUEHRUNG, AdminCommand.Action.QUERY);
        AdminCommand.QueryPayload q = new AdminCommand.QueryPayload();
        q.setType(AdminCommand.QueryPayload.Type.LIST_BY_FILM);
        q.setFilmId(film.getId());
        q.setCorrelationId(corr);
        cmd.setQuery(q);
        adminCommandProducer.send(cmd);

        TreeGrid<Object> treeGrid = new TreeGrid<>();
        treeGrid.setWidthFull();
        treeGrid.setHeight("400px");

        treeGrid.addHierarchyColumn(obj -> {
            if (obj instanceof Integer) return "KW " + obj;
            if (obj instanceof Auffuehrung) return "Aufführung: " + ((Auffuehrung) obj).getStartzeitpunkt();
            return "";
        }).setHeader("Kalenderwoche / Aufführung");

        // Column shows count per calendar week (based on received data)
        java.util.List<Auffuehrung> loadedAuffuehrungen = new java.util.ArrayList<>();
        treeGrid.addColumn(obj -> {
            if (obj instanceof Integer) {
            int week = (Integer) obj;
            long cnt = loadedAuffuehrungen.stream()
                .filter(a -> getKalenderwoche(a) == week)
                .count();
            return cnt;
            }
            return "";
        }).setHeader("Anzahl").setAutoWidth(true);

        treeGrid.addComponentColumn(obj -> {
            if (obj instanceof Auffuehrung) {
                Auffuehrung auff = (Auffuehrung) obj;
                Button loeschen = new Button("Löschen");

                loeschen.addClickListener(ev -> {
                    try {
                        Long auffId = auff.getId();
                        adminService.deleteAuffuehrung(auffId);
                        // Event-Listener schließt und lädt Dialog neu
                        Notification.show("Aufführung wird gelöscht…", 1500, Notification.Position.MIDDLE);
                    } catch (Exception ex) {
                        Notification.show("Fehler beim Löschen: " + ex.getMessage(), 4000, Notification.Position.MIDDLE);
                    }
                });

                return loeschen;
            }
            return null;
        }).setHeader("Aktionen").setAutoWidth(true);

        // Data will be set when AdminEvent with aufführungen arrives
        AdminUIEventBus.Registration dialogReg = AdminUIEventBus.register(ev -> {
            if (ev.getEntity() == AdminEvent.Entity.AUFFUEHRUNG && ev.getAction() == AdminEvent.Action.QUERY && ev.getCorrelationId() != null && ev.getCorrelationId().equals(corr)) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    if (ev.getAuffuehrungen() != null) {
                        // Map DTOs to lightweight Auffuehrung for display
                        java.util.List<Auffuehrung> auffuehrungen = ev.getAuffuehrungen().stream().map(dto -> {
                            Auffuehrung a = new Auffuehrung();
                            a.setId(dto.getId());
                            a.setStartzeitpunkt(dto.getStartzeitpunkt());
                            Kinosaal s = new Kinosaal();
                            s.setId(dto.getSaalId());
                            s.setName(dto.getSaalName());
                            a.setSaal(s);
                            Film f2 = new Film();
                            f2.setId(dto.getFilmId());
                            a.setFilm(f2);
                            return a;
                        }).toList();
                        loadedAuffuehrungen.clear();
                        loadedAuffuehrungen.addAll(auffuehrungen);
                        TreeData<Object> treeData = buildTreeData(auffuehrungen);
                        treeGrid.setDataProvider(new TreeDataProvider<>(treeData));
                    }
                }));
            }
        });

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
        dialog.addOpenedChangeListener(e -> {
            if (!e.isOpened()) {
                offeneAuffuehrungenDialog = null;
                dialogFilm = null;
                if (dialogReg != null) dialogReg.remove();
            }
        });
        dialog.open();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        if (adminReg != null) {
            adminReg.remove();
            adminReg = null;
        }
    }


    private void openNeueAuffuehrungDialog(Film film, Dialog parentDialog) {
        Dialog dialog = new Dialog();
        dialog.setWidth("900px");
        dialog.setHeight("600px");
        dialog.setModal(true);
        dialog.setDraggable(false);
        dialog.setCloseOnOutsideClick(false);
        dialog.setCloseOnEsc(true);
        dialog.getElement().getStyle().set("margin-left", "40px");
        dialog.getElement().getStyle().set("margin-top", "40px");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(new H2("Neue Aufführung planen für: " + film.getTitel()));

        DatePicker datumPicker = new DatePicker("Datum wählen");
        TextField startzeitField = new TextField("Startzeit (HH:mm)");

        ComboBox<Kinosaal> saalCombo = new ComboBox<>("Kinosaal wählen");
        //nur freigegbeene Säle anzeigen 
        saalCombo.setItems(kinosaalRepository.findAll().stream()
                .filter(Kinosaal::isFreigegeben)
                .toList());

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

        // Event-Listener für erfolgreiche Erstellung
        String createCorr = UUID.randomUUID().toString();
        AdminUIEventBus.Registration createReg = AdminUIEventBus.register(event -> {
            if (event.getEntity() == AdminEvent.Entity.AUFFUEHRUNG 
                && event.getAction() == AdminEvent.Action.CREATE
                && event.getCorrelationId() != null
                && event.getCorrelationId().equals(createCorr)
                && event.getStatus() == AdminEvent.Status.SUCCESS) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    dialog.close();
                    // Parent-Dialog schließen und neu öffnen um aktualisierte Liste zu laden
                    if (parentDialog != null) {
                        parentDialog.close();
                        openAuffuehrungenDialog(film);
                    }
                    Notification.show("Aufführung erfolgreich angelegt!", 2000, Notification.Position.MIDDLE);
                }));
            } else if (event.getEntity() == AdminEvent.Entity.AUFFUEHRUNG 
                && event.getAction() == AdminEvent.Action.CREATE
                && event.getCorrelationId() != null
                && event.getCorrelationId().equals(createCorr)
                && event.getStatus() == AdminEvent.Status.FAILURE) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    Notification.show("Fehler beim Anlegen: " + event.getMessage(), 4000, Notification.Position.MIDDLE);
                }));
            }
        });

        dialog.addOpenedChangeListener(e -> {
            if (!e.isOpened() && createReg != null) {
                createReg.remove();
            }
        });

        speichern.addClickListener(ev -> {
            LocalDate datum = datumPicker.getValue();
            String zeitText = startzeitField.getValue();
            Kinosaal ausgewaehlterSaal = saalCombo.getValue();

            if (datum == null || zeitText == null || zeitText.isEmpty() || ausgewaehlterSaal == null) {
                Notification.show("Bitte Datum, Uhrzeit und Kinosaal angeben",
                        3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date startzeit = format.parse(datum.toString() + " " + zeitText);

                //Film als managed Entity neu laden
                Film managedFilm = filmRepository.findById(film.getId())
                        .orElseThrow(() -> new IllegalStateException("Film nicht mehr vorhanden"));

                // Saalbelegungs Check
                Date neueStartzeit = startzeit;
                Date neueEndzeit = new Date(startzeit.getTime() + managedFilm.getDauer() * 60L * 1000L);
                //alle Aufführungen dieses Saals
                List<Auffuehrung> vorhandeneAuffuehrungen = ausgewaehlterSaal.getAuffuehrungen();
                //gucken ob sich zeiten überschneiden
                boolean konflikt = vorhandeneAuffuehrungen.stream().anyMatch(a -> {
                    Date existingStart = a.getStartzeitpunkt();
                    Date existingEnd = new Date(existingStart.getTime() + a.getFilm().getDauer() * 60L * 1000L);
                    return existingStart.before(neueEndzeit) && existingEnd.after(neueStartzeit);
                });

                if (konflikt) {
                    Notification.show("In diesem Saal findet zu dieser Zeit bereits eine Aufführung statt.",
                            4000, Notification.Position.MIDDLE);
                    return;
                }

                // Über Kafka-AdminService anlegen lassen mit Korrelations-ID
                adminService.createAuffuehrung(managedFilm.getId(), ausgewaehlterSaal.getId(), startzeit, createCorr);

                // Button deaktivieren um Doppelklicks zu vermeiden
                speichern.setEnabled(false);
                Notification.show("Aufführung wird gespeichert…", 1500, Notification.Position.MIDDLE);

            } catch (ParseException e) {
                Notification.show("Ungültiges Zeitformat, bitte HH:mm eingeben",
                        3000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Fehler beim Speichern: " + e.getMessage(),
                        4000, Notification.Position.MIDDLE);
                e.printStackTrace();
            }
        });

        dialog.add(layout);
        dialog.open();
    }

    private TreeData<Object> buildTreeData(List<Auffuehrung> auffuehrungen) {
        TreeData<Object> treeData = new TreeData<>();

        List<Integer> wochen = auffuehrungen.stream()
                .map(this::getKalenderwoche)
                .distinct()
                .sorted()
                .toList();

        for (Integer woche : wochen) {
            treeData.addItem(null, woche);

            List<Auffuehrung> auffInWoche = auffuehrungen.stream()
                    .filter(a -> getKalenderwoche(a) == woche)
                    .toList();

            auffInWoche.forEach(auff -> treeData.addItem(woche, auff));
        }

        return treeData;
    }

    private int getKalenderwoche(Auffuehrung auff) {
        LocalDate datum = auff.getStartzeitpunkt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return datum.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }
}
