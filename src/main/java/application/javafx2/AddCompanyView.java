package application.javafx2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entity.Company;
import org.inputCheck.CompanyCheck;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

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

        // Textfield for name
        TextField companyNameTextField = new TextField();
        companyNameTextField.setPromptText("Enter name");

        // Buttons for save and cancel
        javafx.scene.control.Button saveButton = new javafx.scene.control.Button("Save");
        javafx.scene.control.Button cancelButton = new Button("Cancel");

        // Event handler for save button
        saveButton.setOnAction(event -> {
            // Perform save operation
            String companyName = companyNameTextField.getText();

            newCompany = new Company(3, companyName);
            try {
                if(!companyCheck.insert(newCompany)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText(companyCheck.getValidationProblemDetails());
                    alert.show();
                    System.out.println("Yes");
                }else{
                    companyList.add(newCompany);
                }

            } catch (Exception e) {

            }



            // Close the window
            stage.close();
        });

        // Event handler for cancel button
        cancelButton.setOnAction(event -> stage.close());

        // Create layout for the scene
        VBox root = new VBox(10); // Use a VBox with spacing of 10 pixels
        root.getChildren().addAll(companyNameTextField, saveButton, cancelButton);
        root.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(root, 300, 250);

        // Set the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }
}
