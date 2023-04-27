package InSys.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Route("manager")
public class Manager1View extends Div {
    private MongoCollection<Document> collection;

    public Manager1View() {
        // Initialize MongoDB connection and get the 'users' collection
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("test");
        collection = database.getCollection("item");

        // Create a new div to hold the chart
        Div chartDiv = new Div();
        chartDiv.setId("chart_div"); // Set the id of the div to "chart_div"

        // Add the chart div to the view
        add(chartDiv);

        // Create the JavaScript code to display the chart
        String js = "google.charts.load('current', {'packages':['corechart']});\n" +
                "google.charts.setOnLoadCallback(drawChart);\n" +
                "function drawChart() {\n" +
                "var data = google.visualization.arrayToDataTable(" + getBarChartData() + ");\n" +
                "var options = {\n" +
                "title: 'User Quantity',\n" +
                "chartArea: {width: '50%'},\n" +
                "hAxis: {\n" +
                "title: 'Quantity',\n" +
                "minValue: 0\n" +
                "},\n" +
                "vAxis: {\n" +
                "title: 'User'\n" +
                "}\n" +
                "};\n" +
                "var chart = new google.visualization.BarChart(document.getElementById('chart_div'));\n" +
                "chart.draw(data, options);\n" +
                "}";

        // Add the JavaScript code to the view
        getElement().executeJs(js);
    }

    public String getBarChartData() {
        // Fetch the data from MongoDB
        List<Document> results = collection.find().into(new ArrayList<>());
    
        // Build the data string for the chart
        StringBuilder data = new StringBuilder("[['User', 'Quantity'],");
        for (Document result : results) {
            String user = result.getString("name");
            Object quantityObj = result.get("quantity");
            int quantity = quantityObj == null ? 0 : ((Number) quantityObj).intValue();
            data.append("['").append(user).append("', ").append(quantity).append("],");
        }
        data.deleteCharAt(data.length() - 1);
        data.append("]");
    
        return data.toString();
    }
}
