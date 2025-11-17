package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainView() {
        Button button = new Button();
        button.addClickListener(e -> button.setText("Clicked!"));
        add(button);
    }
}