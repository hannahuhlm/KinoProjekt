package kino.application;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ImageSlider extends VerticalLayout {

    private static final long serialVersionUID = 2L;
    private int currentIndex = 0;
    private final Image displayImage;

    public ImageSlider(List<String> imageUrls) {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        // Erstes Bild
        displayImage = new Image();
        displayImage.setWidth("100%");
        displayImage.setHeight("500px");

        // sanfter Übergang
        displayImage.getStyle()
                .set("transition", "opacity 1s ease-in-out")
                .set("opacity", "1")
                .set("position", "relative")
                .set("display", "block");

        if (!imageUrls.isEmpty()) {
            displayImage.setSrc(imageUrls.get(0));
        }

        // Buttons erstellen
        Button prev = new Button("⟵", e -> zurueckSkippen(imageUrls));
        Button next = new Button("⟶", e -> weiterSkippen(imageUrls));

        // Pfeile ohne Hintergrund und Rahmen
        for (Button b : new Button[]{prev, next}) {
            b.getStyle()
                    .set("background", "transparent")
                    .set("border", "none")
                    .set("font-size", "2.5em")
                    .set("color", "white")
                    .set("cursor", "pointer")
                    .set("position", "absolute")
                    .set("top", "50%")
                    .set("transform", "translateY(-50%)");
        }

        prev.getStyle().set("left", "10px");
        next.getStyle().set("right", "10px");

        // Container für Bild und Buttons
        VerticalLayout imageContainer = new VerticalLayout();
        imageContainer.setWidthFull();
        imageContainer.setHeight(displayImage.getHeight());
        imageContainer.getStyle().set("position", "relative");
        imageContainer.setPadding(false);
        imageContainer.setSpacing(false);

        imageContainer.add(displayImage, prev, next);
        add(imageContainer);

        
//        //Automatisches weiterskippen konfigurieren
//        Timer timer = new Timer(true);
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                getUI().ifPresent(ui -> ui.access(() -> weiterSkippen(imageUrls)));
//            }
//        }, 5000, 5000); // 5 Sekunden Delay

    }

    private void zurueckSkippen(List<String> imageUrls) {
        if (imageUrls.isEmpty()) return;
        displayImage.getStyle().set("opacity", "0");
        getUI().ifPresent(ui -> ui.getPage().executeJs(
                "setTimeout(() => { $0.src = $1; $0.style.opacity = '1'; }, 300);",
                displayImage.getElement(),
                imageUrls.get((currentIndex - 1 + imageUrls.size()) % imageUrls.size())
        ));
        currentIndex = (currentIndex - 1 + imageUrls.size()) % imageUrls.size();
    }

    private void weiterSkippen(List<String> imageUrls) {
        if (imageUrls.isEmpty()) return;
        displayImage.getStyle().set("opacity", "0");
        getUI().ifPresent(ui -> ui.getPage().executeJs(
                "setTimeout(() => { $0.src = $1; $0.style.opacity = '1'; }, 300);",
                displayImage.getElement(),
                imageUrls.get((currentIndex + 1) % imageUrls.size())
        ));
        currentIndex = (currentIndex + 1) % imageUrls.size();
    }
}