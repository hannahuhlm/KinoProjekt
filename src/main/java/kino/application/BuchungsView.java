package kino.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;

import kino.application.data.Buchung;
import kino.application.data.BuchungRepository;
import kino.application.data.Film;

@Route(value = "buchung/:buchungId", layout = MainViewLayout.class)
@PageTitle("Buchungsbestätigung")
@AnonymousAllowed
public class BuchungsView extends VerticalLayout implements BeforeEnterObserver {

        private final BuchungRepository buchungRepository;
        private Buchung buchung;

        private final DateTimeFormatter auffuehrungsFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        @Autowired
        public BuchungsView(BuchungRepository buchungRepository) {
                this.buchungRepository = buchungRepository;
                setSizeFull();
                setPadding(true);
                setSpacing(true);
                getStyle().set("background-color", "#201c19");
        }

        @Override
        public void beforeEnter(BeforeEnterEvent event) {
                String idStr = event.getRouteParameters().get("buchungId").orElse(null);
                if (idStr == null) {
                        showNotFound();
                        return;
                }
                try {
                        Long id = Long.valueOf(idStr);
                        this.buchung = buchungRepository.findById(id).orElse(null);
                } catch (NumberFormatException ex) {
                        this.buchung = null;
                }
                if (this.buchung == null) {
                        showNotFound();
                } else {
                        buildUI();
                }
        }

        private void showNotFound() {
                removeAll();
                add(new H2("Buchung nicht gefunden"));
                add(new Paragraph("Die angegebene Buchung konnte nicht geladen werden."));
                add(new Button("Zur Startseite", e -> UI.getCurrent().navigate("")));
        }

        private void buildUI() {
                removeAll();

                H2 heading = new H2("Vielen Dank für Ihre Buchung!");
                heading.getStyle().set("color", "#f5f1e6").set("margin-top", "0");
                add(heading);

                Film film = buchung.getAuffuehrung() != null ? buchung.getAuffuehrung().getFilm() : null;

                HorizontalLayout details = new HorizontalLayout();
                details.setWidthFull();
                details.setPadding(true);
                details.setSpacing(true);
                details.setAlignItems(FlexComponent.Alignment.START);
                details.getStyle().set("background-color", "#2c2723").set("border-radius", "10px");

                Image poster = new Image(film != null ? film.getPosterUrl() : "", "Poster");
                poster.setWidth("160px");
                poster.setHeight("240px");
                poster.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.4)").set("border-radius", "8px");

                VerticalLayout info = new VerticalLayout();
                info.setPadding(false);
                info.setSpacing(true);

                H2 title = new H2(film != null ? film.getTitel() : "Buchung");
                title.getStyle().set("color", "#f5f1e6").set("margin-top", "0").set("text-shadow", "0 2px 6px rgba(0,0,0,0.7)");

                HorizontalLayout metaRow = new HorizontalLayout();
                metaRow.setSpacing(true);
                Div dauerBox = createInfoBox(film != null ? (film.getDauer() + " Minuten") : "-");
                String auffText = buchung.getAuffuehrung().getStartzeitpunkt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(auffuehrungsFormatter);
                Div auffBox = createInfoBox("Vorstellung: " + auffText);
                metaRow.add(dauerBox, auffBox);
                info.add(title, metaRow);

                details.add(poster, info);
                details.expand(info);
                add(details);

                add(new Hr());

                Paragraph nrInfo = new Paragraph("Ihre Buchungsnummer: " + (buchung.getBuchungsnummer() != null ? buchung.getBuchungsnummer() : "-"));
                Paragraph kundeInfo = new Paragraph("Kunde: " + (buchung.getKunde() != null ? buchung.getKunde().getName() : "-") + " (" + (buchung.getKunde() != null ? buchung.getKunde().getEmail() : "-") + ")");

                String plaetze = buchung.getBuchungSitzplaetze() != null ?
                                buchung.getBuchungSitzplaetze().stream()
                                                .map(bs -> bs.getSitzplatz() != null ? ("Reihe " + bs.getSitzplatz().getReihe().getReihennummer() + ", Platz " + bs.getSitzplatz().getPlatznummer()) : "?")
                                                .reduce((a, b) -> a + " | " + b)
                                                .orElse("-") : "-";
                Paragraph platzInfo = new Paragraph("Ausgewählte Plätze: " + plaetze);
                Paragraph preisInfo = new Paragraph("Gesamtpreis: " + String.format("%.2f", buchung.getGesamtpreis()) + " €");

                Button close = new Button("Zur Startseite", e -> UI.getCurrent().navigate(""));
                close.getStyle().set("background-color", "#f5e1a4").set("color", "black").set("font-weight", "bold");

                add(nrInfo, kundeInfo, platzInfo, preisInfo, close);
        }

        private Div createInfoBox(String text) {
                Div box = new Div();
                box.setText(text);
                box.getStyle()
                                .set("background-color", "#3a332f")
                                .set("color", "#f5f1e6")
                                .set("padding", "6px 12px")
                                .set("border-radius", "6px")
                                .set("font-size", "13px");
                return box;
        }
}
