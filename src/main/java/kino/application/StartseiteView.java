package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "", layout = MainViewLayout.class)
@PageTitle("CINEMAn Roll")
@CssImport("./styles/startseite.css")
public class StartseiteView extends VerticalLayout {

    public StartseiteView() {
        addClassName("startseite-view");
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);

        // Fullscreen Slider
        List<String> images = List.of(
                "images/avatar.jpg",
                "images/zoomania.jpg",
                "images/nussknacker.jpg",
                "images/heldslider.jpg"
        );
        ImageSlider slider = new ImageSlider(images);
        slider.setWidthFull();
        slider.addClassName("startseite-slider");

        add(slider);

        // Bereich unter dem Slider (Titel + Text + Button)
        VerticalLayout hero = new VerticalLayout();
        hero.addClassName("startseite-hero");
        hero.setWidthFull();
        hero.setMaxWidth("900px");
        hero.setPadding(false);
        hero.setSpacing(true);
        hero.setAlignItems(Alignment.CENTER);

        // Titel
        H1 title = new H1("CINEMAn Roll - das exklusive Kino");
        title.addClassName("startseite-title");

        // Programm-Button
        Button programmButton = new Button("Zum Programm",
                e -> getUI().ifPresent(ui -> ui.navigate("filmliste")));
        programmButton.addClassName("startseite-button");

        // Textblock
        Div textBlock = new Div();
        textBlock.addClassName("startseite-textblock");
        textBlock.add(new Paragraph("Willkommen im CINEMANn Roll"));

        hero.add(title, programmButton, textBlock);
        add(hero);
    }
}
