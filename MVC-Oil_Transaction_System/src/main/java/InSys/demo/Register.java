package InSys.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("register")
@PageTitle("Register")
public class Register extends VerticalLayout {
    private final MongoClient mongoClient;

    public Register(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        
        TextField username = new TextField("Name");
        PasswordField password = new PasswordField("Password");

        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(username, "Name");
        formLayout.addFormItem(password, "Password");

        Button registerButton = new Button("Register", event -> {
            String firstNameValue = username.getValue();
            String passwordValue = password.getValue();

            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("users");

            Document newUser = new Document();
            newUser.append("username", firstNameValue);
            newUser.append("password", passwordValue);

            collection.insertOne(newUser);

            Notification.show("User registered successfully!");
        });

        add(formLayout, registerButton);
    }
}
