package kino.application;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import kino.impl.KinoImpl;
import kino.Kino;

import java.util.List;

@Route("filmliste")
public class FilmlisteView extends AppLayout {

	private static final long serialVersionUID = -3842231281780942154L;

	public FilmlisteView() {
       
        // Alle Film-Daten
//        List<Film> filme = KinoImpl.getProgramm();

        // Jeden Film als eigenen Block hinzufügen
//        for (Film film : filme) {
//            add(createFilmBlock(film));
        }
//    }

    private HorizontalLayout createFilmBlock(Film film) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setPadding(true);
        layout.getStyle().set("border-bottom", "1px solid #ccc");
//        layout.setAlignItems(Alignment.CENTER);

        // Cover
        Image cover = new Image(film.getCoverUrl(), film.getTitel());
        cover.setWidth("150px");
        cover.setHeight("auto");

        // Titel + Beschreibung + Uhrzeiten
        VerticalLayout info = new VerticalLayout();
        info.setSpacing(false);
        info.setPadding(false);

        H3 titel = new H3(film.getTitel());
        Paragraph beschreibung = new Paragraph(film.getBeschreibung());

        // Uhrzeiten als Text
        String zeiten = String.join(", ", film.getAuffuehrungen());
        Paragraph auffuehrungen = new Paragraph("Aufführungen: " + zeiten);

        info.add(titel, beschreibung, auffuehrungen);

        layout.add(cover, info);
        layout.setFlexGrow(1, info);

        return layout;
    }

    // Hilfsklasse für Film-Daten
    private static class Film {
        private final String titel;
        private final String coverUrl;
        private final String beschreibung;
        private final List<String> auffuehrungen;

        public Film(String titel, String coverUrl, String beschreibung, List<String> auffuehrungen) {
            this.titel = titel;
            this.coverUrl = coverUrl;
            this.beschreibung = beschreibung;
            this.auffuehrungen = auffuehrungen;
        }

        public String getTitel() { return titel; }
        public String getCoverUrl() { return coverUrl; }
        public String getBeschreibung() { return beschreibung; }
        public List<String> getAuffuehrungen() { return auffuehrungen; }
    }
}
