package kino.application;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;

import java.util.List;

public class ImageSlider extends VerticalLayout {

    /**
	 * 
	 */
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
        displayImage.setHeight("400px");
        if (!imageUrls.isEmpty()) {
            displayImage.setSrc(imageUrls.get(0));
        }

        // Navigation Buttons
        Button prev = new Button("⟵", e -> showPrevious(imageUrls));
        Button next = new Button("⟶", e -> showNext(imageUrls));
        HorizontalLayout buttons = new HorizontalLayout(prev, next);
        buttons.setJustifyContentMode(JustifyContentMode.CENTER);
        buttons.setWidthFull();

        add(displayImage, buttons);
    }

    private void showPrevious(List<String> imageUrls) {
        if (imageUrls.isEmpty()) return;
        currentIndex = (currentIndex - 1 + imageUrls.size()) % imageUrls.size();
        displayImage.setSrc(imageUrls.get(currentIndex));
    }

    private void showNext(List<String> imageUrls) {
        if (imageUrls.isEmpty()) return;
        currentIndex = (currentIndex + 1) % imageUrls.size();
        displayImage.setSrc(imageUrls.get(currentIndex));
    }
}
