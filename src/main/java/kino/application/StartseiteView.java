package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "", layout = MainViewLayout.class)
@PageTitle("CINEMAn Roll")
public class StartseiteView extends VerticalLayout {

    public StartseiteView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Fullscreen Slider
        List<String> images = List.of(
                "images/avatar.jpg",
                "images/zoomania.jpg",
                "images/nussknacker.jpg",
                "images/heldslider.jpg"
        );
        ImageSlider slider = new ImageSlider(images);
        slider.setWidthFull();

        add(slider);

        // Titel
        H1 title = new H1("CINEMAn Roll - das exklusive Kino");
        title.getStyle()
                .set("text-align", "center")
                .set("margin-top", "40px");
        add(title);

        // Programm-Button
        Button programmButton = new Button("Zum Programm",
                e -> getUI().ifPresent(ui -> ui.navigate("filmliste")));
        programmButton.getStyle()
                .set("border-radius", "20px")
                .set("padding", "12px 24px")
                .set("background", "#ff1744")
                .set("color", "white")
                .set("font-size", "1.1em")
                .set("cursor", "pointer")
                .set("margin", "20px auto");
        add(programmButton);

        // Textblock
        Div textBlock = new Div();
        textBlock.setWidth("80%");
        textBlock.getStyle().set("margin", "auto");
        textBlock.add(new Paragraph("Willkommen im CINEMANn Roll"));
        add(textBlock);
    }
}
