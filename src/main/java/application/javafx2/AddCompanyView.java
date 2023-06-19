package application.javafx2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entity.Company;
import org.inputCheck.CompanyCheck;

import java.sql.Connection;

public class AddCompanyView extends Application {
    ObservableList<Company> companyList;
    Company newCompany;
    Connection connection;

    CompanyCheck companyCheck;

    public AddCompanyView(ObservableList<Company> companyList, Connection connection) {
        this.companyList = companyList;
        this.connection = connection;
        companyCheck = new CompanyCheck(connection);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Add Company");

        // Label for company name
        Label companyNameLabel = new Label("Company name:");

        // Textfield for name
        TextField companyNameTextField = new TextField();
        companyNameTextField.setPromptText("Enter name");

        // Buttons for save and cancel
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        // Layout for label and textfield
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(companyNameLabel, companyNameTextField);
        inputBox.setAlignment(Pos.CENTER);

        // Layout for save and cancel buttons
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create layout for the scene
        VBox root = new VBox(10);
        root.getChildren().addAll(inputBox, buttonBox);
        root.setAlignment(Pos.CENTER);

        // Event handler for save button
        saveButton.setOnAction(event -> {
            // Perform save operation
            String companyName = companyNameTextField.getText();

            newCompany = new Company(3, companyName);

            if (!companyCheck.insert(newCompany)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText(companyCheck.getValidationProblemDetails());
                alert.show();
                System.out.println("Yes");
            } else {
                companyList.add(newCompany);
            }

            // Close the window
            stage.close();
        });

        // Event handler for cancel button
        cancelButton.setOnAction(event -> stage.close());

        // Create the scene
        Scene scene = new Scene(root, 250, 80);

        // Set the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.setResizable(false);
        stage.show();
    }
}
