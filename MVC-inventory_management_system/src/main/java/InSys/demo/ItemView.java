package InSys.demo;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import org.vaadin.crudui.crud.impl.GridCrud;

@Route("inventory")
@PageTitle("Inventory")

public class ItemView extends VerticalLayout {
    public ItemView(ItemService service){
        GridCrud crud = new GridCrud<Item>(Item.class,service);

        H1 oilTransactionSystem = new H1("Oil Transaction System");
        H1 user = new H1("User");

        // get user ID and name from VaadinSession
        // String userId = (String) VaadinSession.getCurrent().getAttribute("userId");
        String userName = (String) VaadinSession.getCurrent().getAttribute("userName");

        // set default values for new items
        Item.setDefaultValues(userName);

        VerticalLayout headerLayout = new VerticalLayout(oilTransactionSystem, user);
        headerLayout.getStyle().set("text-align", "center");

        VerticalLayout contentLayout = new VerticalLayout(crud);
        contentLayout.getStyle().set("flex-grow", "1");
        contentLayout.getStyle().set("overflow", "auto");

        VerticalLayout mainLayout = new VerticalLayout(headerLayout, contentLayout);
        mainLayout.getStyle().set("height", "100%");
        mainLayout.getStyle().set("justify-content", "flex-start");
        mainLayout.getStyle().set("align-items", "center");

        add(mainLayout);
        setSizeFull();
    }
}