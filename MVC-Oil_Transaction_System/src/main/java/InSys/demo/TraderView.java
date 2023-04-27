package InSys.demo;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("trader")
@PageTitle("Trader")
public class TraderView extends HorizontalLayout {

    private final TextField searchField = new TextField("Search by Name or ID");
    private final Button clearButton = new Button("Clear");
    private final Grid<Trader> grid = new Grid<>(Trader.class);

    public TraderView(TraderService traderService) {

        H1 oilTransactionSystem = new H1("Oil Transaction System");
        H2 temp = new H2("Trader");

        VerticalLayout headerLayout = new VerticalLayout(oilTransactionSystem,temp);

        grid.setColumns("id", "name", "quantity");

        List<Trader> traders = traderService.getAllTraders();
        ListDataProvider<Trader> dataProvider = new ListDataProvider<>(traders);
        grid.setDataProvider(dataProvider);

        searchField.addValueChangeListener(event -> {
            String filter = event.getValue().toLowerCase();
            dataProvider.setFilter(trader -> {
                String name = trader.getName().toLowerCase();
                String id = trader.getId().toLowerCase();
                return name.contains(filter) || id.contains(filter);
            });
        });

        clearButton.addClickListener(e -> {
            searchField.setValue("");
            dataProvider.clearFilters();
        });

        VerticalLayout contentLayout = new VerticalLayout(searchField, clearButton, grid);
        add(headerLayout, contentLayout);
        setFlexGrow(1, grid);
    }
}
