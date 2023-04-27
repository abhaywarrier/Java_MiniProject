package InSys.demo;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
@PageTitle("Login")
public class LoginView extends Div {

    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");

    public LoginView() {
        addClassName("login-form");

        H1 title = new H1("Oil Transaction System");

        Div loginForm = new Div();
        loginForm.addClassName("login-form-wrapper");
        loginForm.getStyle().set("display", "flex");
        loginForm.getStyle().set("justify-content", "center");
        loginForm.getStyle().set("align-items", "center");
        loginForm.add(createLoginForm());

        Button registerButton = new Button("Register", event -> {
            getUI().ifPresent(ui -> ui.navigate("register"));
        });

        Div registerForm = new Div();
        registerForm.add(registerButton);

        add(title, loginForm, registerForm);
    }

    private FormLayout createLoginForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(username, password, createLoginButton());
        return formLayout;
    }

    private Button createLoginButton() {
        return new Button("Login", event -> {
            String enteredUsername = username.getValue();
            String enteredPassword = password.getValue();
    
            // Step 2: Connect to MongoDB
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    
            // Step 3: Select the database and collections
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> userCollection = database.getCollection("users");
            MongoCollection<Document> traderCollection = database.getCollection("trader");
    
            // Step 4: Find the document with the matching username and password in both collections
            Document userQuery = new Document("username", enteredUsername)
                .append("password", enteredPassword);
            Document userResult = userCollection.find(userQuery).first();
            Document traderQuery = new Document("username", enteredUsername)
                    .append("password", enteredPassword);
            Document traderResult = traderCollection.find(traderQuery).first();

            if (userResult != null) {
                // Step 5: Login successful for user, navigate to main view for users
                VaadinSession.getCurrent().setAttribute("user", enteredUsername);
                getUI().ifPresent(ui -> ui.navigate(""));
            } else if (traderResult != null) {
                // Step 5: Login successful for trader, navigate to main view for traders
                VaadinSession.getCurrent().setAttribute("trader", enteredUsername);
                getUI().ifPresent(ui -> ui.navigate("trader"));
            } else {
                Notification.show("Invalid username or password.");
            }

            // Step 6: Close the MongoDB client 
            mongoClient.close();
        });
    }
}
