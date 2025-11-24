package kino.application;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import kino.application.data.Auffuehrung;
import kino.application.data.AuffuehrungRepository;
import kino.application.data.Film;
import kino.application.data.FilmRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "film/:filmId", layout = MainView.class)
@PageTitle("Filmdetails")
@PermitAll
public class FilmDetailView extends VerticalLayout implements BeforeEnterObserver {

    private final FilmRepository filmRepository;
    private final AuffuehrungRepository auffuehrungRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter zeitFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final VerticalLayout inhaltLayout = new VerticalLayout();
    private final VerticalLayout auffuehrungContainer = new VerticalLayout();

    public FilmDetailView(FilmRepository filmRepository, AuffuehrungRepository auffuehrungRepository) {
        this.filmRepository = filmRepository;
        this.auffuehrungRepository = auffuehrungRepository;
        setPadding(false);
        setSpacing(false);
        setWidthFull();

        inhaltLayout.setWidth("90%");
        inhaltLayout.getStyle().set("margin", "0 auto");
        add(inhaltLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long filmId = event.getRouteParameters()
                .get("filmId")
                .map(Long::parseLong)
                .orElse(null);

        if (filmId != null) {
            filmRepository.findById(filmId).ifPresentOrElse(
                this::buildLayout,
                () -> inhaltLayout.add(new H2("Film nicht gefunden"))
            );
        }
    }

    private void buildLayout(Film film) {
        inhaltLayout.removeAll();

        // === Hero-Banner ===
        Div hero = new Div();
        hero.getStyle()
            .set("background-image", "url('" + film.getPosterUrl() + "')")
            .set("background-size", "cover")
            .set("background-position", "center")
            .set("height", "400px")
            .set("width", "100%")
            .set("position", "relative")
            .set("filter", "brightness(0.7)");
        inhaltLayout.add(hero);

        H3 sectionTitle = new H3("Filmdetails");
        sectionTitle.getStyle().set("margin-top", "30px");
        inhaltLayout.add(sectionTitle);

        // === Details mit Poster ===
        HorizontalLayout details = new HorizontalLayout();
        details.setWidthFull();
        details.setPadding(true);
        details.setSpacing(true);
        details.setAlignItems(Alignment.START);

        Image poster = new Image(film.getPosterUrl(), "Poster");
        poster.setWidth("160px");
        poster.setHeight("240px");
        poster.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.4)");

        VerticalLayout info = new VerticalLayout();
        info.setPadding(false);
        info.setSpacing(true);

        H2 title = new H2(film.getTitel());

        // === Info-Kacheln ===
        HorizontalLayout metaRow = new HorizontalLayout();
        metaRow.setSpacing(true);

        Div fskBox = createInfoBox("FSK 6");
        Div genreBox = createInfoBox("Abenteuer | Animation");
        Div dauerBox = createInfoBox(film.getDauer() + " Minuten");
        Div startBox = createInfoBox("Start: " + (film.getFilmstart() != null ? film.getFilmstart().format(dateFormatter) : "-"));

        metaRow.add(fskBox, genreBox, dauerBox, startBox);

        Paragraph desc = new Paragraph(film.getBeschreibung());
        desc.getStyle().set("max-width", "800px");

        info.add(title, metaRow, desc);
        details.add(poster, info);
        inhaltLayout.add(details);

        // === Vorstellungstage & Auswahlleiste ===
        H3 vorstellungenUeberschrift = new H3("Vorhandene Vorstellungen");
        vorstellungenUeberschrift.getStyle().set("margin-top", "40px").set("margin-bottom", "0px");

        HorizontalLayout tageZeile = new HorizontalLayout();
        tageZeile.setPadding(true);
        tageZeile.setSpacing(true);
        tageZeile.setAlignItems(Alignment.CENTER);
        tageZeile.getStyle()
                .set("background-color", "#d8c49c")
                .set("border-radius", "10px")
                .set("margin-top", "10px");

        LocalDate heute = LocalDate.now();

        for (int i = 0; i < 3; i++) {
            LocalDate tag = heute.plusDays(i);
            Button tagButton = new Button(tag.format(DateTimeFormatter.ofPattern("E dd.MM.", Locale.GERMAN)));
            tagButton.addClickListener(e -> zeigeVorstellungenAnTag(film, tag));
            tageZeile.add(tagButton);
        }

        DatePicker datePicker = new DatePicker();
        datePicker.setPlaceholder("Datum wählen");
        datePicker.setI18n(new DatePicker.DatePickerI18n().setToday("Heute").setCancel("Abbrechen"));
        datePicker.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                zeigeVorstellungenAnTag(film, e.getValue());
            }
        });
        tageZeile.add(datePicker);

        inhaltLayout.add(new Hr(), vorstellungenUeberschrift, tageZeile, auffuehrungContainer);

        zeigeVorstellungenAnTag(film, heute);
    }

    private void zeigeVorstellungenAnTag(Film film, LocalDate tag) {
        auffuehrungContainer.removeAll();

        List<Auffuehrung> auffuehrungen = film.getAuffuehrungen();
        if (auffuehrungen == null || auffuehrungen.isEmpty()) {
            return;
        }

        List<Auffuehrung> gefiltert = auffuehrungen.stream()
                .filter(a -> tag.equals(a.getStartzeitpunkt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
                .sorted(Comparator.comparing(Auffuehrung::getStartzeitpunkt))
                .collect(Collectors.toList());

        if (gefiltert.isEmpty()) {
            auffuehrungContainer.add(new Paragraph("Keine Vorstellungen an diesem Tag."));
        } else {
            HorizontalLayout kachelReihe = new HorizontalLayout();
            kachelReihe.setSpacing(true);
            for (Auffuehrung auff : gefiltert) {
            	Div kachel = new Div();
            	kachel.getStyle()
            	        .set("background-color", "#a18f66") // gleiche Farbe wie Datumsleiste
            	        .set("border-radius", "16px")
            	        .set("width", "100px")
            	        .set("height", "120px")
            	        .set("box-shadow", "0 4px 10px rgba(0,0,0,0.3)")
            	        .set("display", "flex")
            	        .set("flex-direction", "column")
            	        .set("justify-content", "space-between")
            	        .set("align-items", "center")
            	        .set("padding", "0")
            	        .set("overflow", "hidden");

            	// Saal-Banner oben
            	Div saalBanner = new Div();
            	saalBanner.setText(auff.getSaal().getName());
            	saalBanner.getStyle()
            	        .set("background-color", "white")
            	        .set("color", "black")
            	        .set("font-weight", "bold")
            	        .set("width", "100%")
            	        .set("text-align", "center")
            	        .set("padding", "4px 0");

            	// Uhrzeit unten
            	String uhrzeit = auff.getStartzeitpunkt().toInstant().atZone(ZoneId.systemDefault())
            	        .toLocalTime().format(zeitFormatter);
            	Span zeitText = new Span(uhrzeit);
            	zeitText.getStyle()
            	        .set("color", "white")
            	        .set("font-size", "20px")
            	        .set("margin-bottom", "12px")
            	        .set("font-weight", "bold");

            	kachel.add(saalBanner, zeitText);

            	// Kachel klickbar machen → Navigieren zu Sitzplatzwahl
            	kachel.getStyle().set("cursor", "pointer");

            	kachel.addClickListener(ev -> {
            	    // Route: sitzplatzwahl/:auffuehrungId
            	    getUI().ifPresent(ui ->
            	            ui.navigate("sitzplatzwahl/" + auff.getId())
            	    );
            	});


                kachelReihe.add(kachel);
            }
            auffuehrungContainer.add(kachelReihe);
        }
    }

    private Div createInfoBox(String text) {
        Div box = new Div();
        box.setText(text);
        box.getStyle()
                .set("background-color", "#e0e0e0")
                .set("padding", "6px 12px")
                .set("border-radius", "12px")
                .set("font-size", "14px")
                .set("font-weight", "500");
        return box;
    }
}
