package kino.application;

import java.util.List;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("CINEMAn Roll")
public class MainViewLayout extends AppLayout {
	private boolean adminItemsVisible = false;
	//Reiter die sichtbar und unsichtbar werden können
	private Paragraph saalAnlegen = new Paragraph("Saal anlegen");
	private Paragraph filmVerwalten = new Paragraph("Film einpflegen");
	private Paragraph einnahmenKalkulieren = new Paragraph("Einnahmen Kalkulieren");


    public MainViewLayout() {
    	//Hintergrund- und schriftfarbe setzen : 
    	getElement().getStyle().set("background-color", "#2c2723");
        getElement().getStyle().set("color", "white");
        
    	menueLeisteErstellen();
    	mainBereichErstellen();
        
    }
    
    private void menueLeisteErstellen() {
    	 // Menü füllen
        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setPadding(true);
        menuLayout.setSpacing(true);
        menuLayout.getStyle().set("background-color", "#2c2723").set("color", "white")
        .set("border-bottom", "4px solid #f5e1a4");  // Dickere, beige Linie unten im Menü


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
        //höhe ganz setzen
        menuLayout.getStyle().set("height", "100vh");
        
        //adminButton einfügen
        adminButtonhinzufuegen(menuLayout);

        // Burger Button hinzufügen
        Button menuButton = new Button(new Icon(VaadinIcon.MENU));
        menuButton.getStyle().set("margin-left", "20px");
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

        navbarLayout.getStyle().set("background-color", "#2c2723") // Dunkelbraun für den Header
        .set("padding", "10px 20px").set("width", "100%")
        .set("border-top", "1px solid #f5e1a4");

        
        addToNavbar(navbarLayout);
    }
    
    //
    private void mainBereichErstellen() {
    	VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(false);
        content.setSpacing(false);
        

        // Fullscreen Slider
        List<String> images = List.of("images/avatar.jpg", "images/zoomania.jpg", "images/nussknacker.jpg", "images/heldslider.jpg");
        ImageSlider slider = new ImageSlider(images);
        slider.setWidthFull();

        content.add(slider);

        
        // Titel 
        H1 title = new H1("CINEMAn Roll - das exklusive Kino");
        title.getStyle().set("text-align", "center").set("margin-top", "40px");
        content.add(title);
        
        //Programm Button
        Button programmButton = new Button("Zum Programm");
        programmButton.getStyle()
        .set("border-radius", "20px")
        .set("padding", "12px 24px")
        .set("background", "#ff1744")
        .set("color", "white")
        .set("font-size", "1.1em")
        .set("cursor", "pointer");
        programmButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("filmliste")));
        programmButton.getStyle().set("margin", "20px auto");
        content.add(programmButton);

        // Text block
        Div textBlock = new Div();
        textBlock.setWidth("80%");
        textBlock.getStyle().set("margin", "auto");
        textBlock.add(new Paragraph(
                "Willkommen im CINEMANn Roll"));

        content.add(textBlock);

        setContent(content);
        
    }
    
    private void adminButtonhinzufuegen(VerticalLayout layout) {
    	Button adminButton = new Button("Admin", new Icon(VaadinIcon.LOCK));
        adminButton.setWidthFull();
        adminButton.getStyle()
            .set("margin-top", "40px")
            .set("border-radius", "8px")
            .set("background", "#e0e0e0")
            .set("cursor", "pointer");

        adminButton.addClickListener(e -> addAdminMenuItems(layout));
        
        layout.add(adminButton);

    }
    
    private void addAdminMenuItems(VerticalLayout menuLayout) {
		

    	//wenn noch nicht eingeblendet- einblenden
    	if(!adminItemsVisible) {
            saalAnlegen.getStyle()
                    .set("font-size", "1.2em")
                    .set("cursor", "pointer")
                    .set("margin-left", "10px");
            saalAnlegen.addClickListener(e ->
                    getUI().ifPresent(ui -> ui.navigate("saal-anlegen"))
            );

            filmVerwalten.getStyle()
                    .set("font-size", "1.2em")
                    .set("cursor", "pointer")
                    .set("margin-left", "10px");
            filmVerwalten.addClickListener(e ->
                    getUI().ifPresent(ui -> ui.navigate("film-verwalten"))
            );		
		    einnahmenKalkulieren.getStyle()
		            .set("font-size", "1.2em")
		            .set("cursor", "pointer")
		            .set("margin-left", "10px");
		    einnahmenKalkulieren.addClickListener(e ->
		            getUI().ifPresent(ui -> ui.navigate("einnahmen-kalkulieren"))
		    );


            // Hinzufügen zum Menü
            menuLayout.add(saalAnlegen, filmVerwalten, einnahmenKalkulieren);
            
            adminItemsVisible = true;
    	}else {
    		//alle vier entfernen
    		menuLayout.remove(saalAnlegen);
            menuLayout.remove(filmVerwalten);
            menuLayout.remove(einnahmenKalkulieren);

            adminItemsVisible = false;
        }
    }
        
        
        
        

}
