package kino.application;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.menubar.MenuBar;
import kino.application.*;

@Route("")
public class MainView extends VerticalLayout {
    /**
	 * 
	 * 				
	 */
	private static final long serialVersionUID = 1L;

	public MainView() {
		H1 text = new H1("Kinotastisch");
		add(text);
		H2 whatsNext = new H2("Was möchten Sie tun?");
		add(whatsNext);
		tabsBasic();
    }
	
	public void tabsBasic() {
		Tab details = new Tab("Kinosaal anlegen");
		Tab payment = new Tab("Filmzuordnung");
		Tab shipping = new Tab("Sitzplatz reservieren");
		Tab booking= new Tab("Sitzplatz buchen");
		Tabs tabs = new Tabs(details, payment, shipping, booking);
		add(tabs);
	}
}