package InSys.demo;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;

@Route("")
// @PWA(name = "Oil Transaction System", shortName = "OTS")
public class MainView extends VerticalLayout implements BeforeEnterObserver {

    public MainView(ItemService service) {
        H1 header = new H1("Oil Transaction System");
        add(header);
        // Add the rest of your UI components here
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Check if user is logged in, redirect to login page if not
        if (VaadinSession.getCurrent().getAttribute("user") == null) {
            event.rerouteTo(LoginView.class);
        } else {
            // Display the ItemView instead of the MainView
            event.forwardTo(ItemView.class);
        }
    }
}
