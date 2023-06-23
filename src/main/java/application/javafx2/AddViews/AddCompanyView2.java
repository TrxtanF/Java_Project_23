package application.javafx2.AddViews;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entity.Company;
import org.inputCheck.CompanyCheck;

import java.sql.Connection;

public class AddCompanyView2 extends Application {
    private CompanyCheck companyCheck;
    private TableView tableView;
    TextField companyNameTextField;

    public AddCompanyView2(Connection connection, TableView tableView){
        companyCheck = new CompanyCheck(connection);
        this.tableView = tableView;
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        run(stage);
    }

    private void run(Stage stage){
        stage.setTitle("Add Company");

        // Label for company name
        Label companyNameLabel = new Label("Company name:");

        // Textfield for name
        companyNameTextField = new TextField();
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
            clickSave();
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

    private void clickSave(){
        String companyName = companyNameTextField.getText();

        Company company = new Company(1, companyName);

        if(companyCheck.insert(company)){
            ObservableList<Company> list = companyCheck.getAll();
            tableView.setItems(list);
        }else{
            throwAllert();
        }
    }

    private void throwAllert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(companyCheck.getValidationProblemDetails());
        alert.show();
    }
}
