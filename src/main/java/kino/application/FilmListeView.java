package kino.application;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.applayout.AppLayout;
import kino.application.data.Film;
import kino.application.data.FilmRepository;

import java.util.List;

@Route("filmliste")
@PageTitle("Filmliste")
public class FilmListeView extends AppLayout {

    private final FilmRepository filmRepository;

    //Spring kümmert sich automatisch um die Erstellung des Film Repositories
    public FilmListeView(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;

        menueLeisteErstellen();
        mainBereichErstellen();
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


    private void mainBereichErstellen() {
        // Main-Content als VerticalLayout
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.setSizeFull();
        mainContent.setPadding(true);
        mainContent.setSpacing(true);

        // Grid für Filme
        Grid<Film> grid = new Grid<>(Film.class, false);
        grid.addColumn(Film::getTitel).setHeader("Titel");
        grid.addColumn(Film::getDauer).setHeader("Dauer (Minuten)");
        grid.addColumn(Film::getBeschreibung).setHeader("Beschreibung").setAutoWidth(true);

        // Daten aus Repository
        List<Film> filme = filmRepository.findAll();
        grid.setItems(filme);

        grid.setSizeFull();

        mainContent.add(grid);
        setContent(mainContent);
    }
}
