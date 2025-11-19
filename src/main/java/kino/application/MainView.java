package kino.application;

import java.util.List;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        setPadding(false);
        setSpacing(false);
        setWidthFull();

        
        // Der Banner
        List<String> images = List.of(
        		"images/avatar.jpg"
         
        );
        ImageSlider slider = new ImageSlider(images);
        add(slider);

        // Titel für Textfeld
        H1 title = new H1("Kinotastisch – Willkommen!");
        title.getStyle().set("text-align", "center");
        add(title);

        // Menü links
        HorizontalLayout menu = new HorizontalLayout();
        menu.setWidthFull();
        menu.setJustifyContentMode(JustifyContentMode.CENTER);

        Paragraph filmListe = new Paragraph("➡ Filmliste anzeigen");
        filmListe.getStyle().set("font-size", "1.3em").set("cursor", "pointer");
        filmListe.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("filmliste")));

        Paragraph reservierung = new Paragraph("➡ Meine Reservierungen");
        reservierung.getStyle().set("font-size", "1.3em").set("cursor", "pointer");
        reservierung.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("reservierungen")));

        menu.add(filmListe, reservierung);
        add(menu);

        // Freier Textbereich
        Div textBlock = new Div();
        textBlock.setWidth("80%");
        textBlock.getStyle().set("margin", "auto");
        textBlock.add(new Paragraph("Hier kann das Kino aktuelle Informationen, Werbung oder Hinweise einfügen. Dieser Textbereich ist frei anpassbar und kann für News oder Events genutzt werden."));
        add(textBlock);
    }
}
