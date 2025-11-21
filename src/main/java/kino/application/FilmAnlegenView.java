package kino.application;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("film-anlegen")
@PageTitle("Film anlegen")
public class FilmAnlegenView extends AppLayout{

	public FilmAnlegenView() {
		menueLeisteErstellen();
	}
	
	 private void menueLeisteErstellen() {
	        //layout erstellen
	        VerticalLayout menuLayout = new VerticalLayout();
	        menuLayout.setPadding(true);
	        menuLayout.setSpacing(true);
	        //füllen
	        Paragraph filmListe = new Paragraph("Filmliste");
	        filmListe.getStyle().set("font-size", "1.3em").set("cursor", "pointer");
	        filmListe.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("filmliste")));

	        Paragraph reservierungen = new Paragraph("Reservierungen");
	        reservierungen.getStyle().set("font-size", "1.3em").set("cursor", "pointer");
	        reservierungen.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("reservierungen")));

	        Paragraph kontakt = new Paragraph("Kontakt");
	        kontakt.getStyle().set("font-size", "1.3em").set("cursor", "pointer");
	        kontakt.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("kontakt")));

	        menuLayout.add(filmListe, reservierungen, kontakt);
	        addToDrawer(menuLayout);

	        // Burger Button
	        Button menuButton = new Button(new Icon(VaadinIcon.MENU));
	        menuButton.getStyle().set("margin-left", "0px");
	        menuButton.addClickListener(e -> setDrawerOpened(!isDrawerOpened()));

	        // Branding-Bild als Button
	        Image brandingImage = new Image("images/logoLang.png", "CINEMANn Logo");
	        brandingImage.setHeight("40px"); 
	        
	        Button homeButton = new Button(brandingImage, e -> getUI().ifPresent(ui -> ui.navigate(""))); 
	        homeButton.getStyle().set("background", "none").set("border", "none").set("cursor", "pointer");

	        // Burger + Branding nebeneinander
	        HorizontalLayout navbarLayout = new HorizontalLayout(menuButton, homeButton);
	        navbarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
	        navbarLayout.setSpacing(true);

	        addToNavbar(navbarLayout);
	    }

	
	
	
	
	
	
	
	
}
