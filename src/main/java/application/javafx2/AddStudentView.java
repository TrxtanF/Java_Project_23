package application.javafx2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.entity.Company;
import org.entity.Student;

public class AddStudentView extends Application {

    ObservableList<Company> companyList;
    ObservableList<Student> studentList;
    Student newStudent;

    public AddStudentView(ObservableList<Company> companyList, ObservableList<Student> studentList) {
        this.companyList = companyList;
        this.studentList = studentList;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Add Student");

        // TextField for name
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Enter name");

        // ComboBox for company selection
        ComboBox<String> companyComboBox = new ComboBox<>();
        ObservableList<String> nameList = FXCollections.observableArrayList();
        for (Company company : companyList) {
            nameList.add(company.getCompanyName());
        }
        companyComboBox.setItems(nameList);
        companyComboBox.setPromptText("Select company");

        // Slider for Java skills
        Slider skillsSlider = new Slider(0, 10, 0);
        skillsSlider.setShowTickLabels(true);
        skillsSlider.setShowTickMarks(true);

        // Label to display current skill value
        Label skillValueLabel = new Label();
        skillsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            skillValueLabel.setText(String.valueOf(newValue.intValue()));
        });

        // Buttons for save and cancel
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        // Event handler for save button
        saveButton.setOnAction(event -> {
            // Perform save operation
            String name = nameTextField.getText();
            String companyName = companyComboBox.getValue();
            Company company = companyList.stream().filter(p -> p.getCompanyName().equals(companyName)).findFirst().orElse(null);
            int javaSkills = (int) skillsSlider.getValue();

            newStudent = new Student(4, name, javaSkills, company.getCompanyId());
            studentList.add(newStudent); // Add the new student to the list


            // Close the window
            stage.close();
        });

        // Event handler for cancel button
        cancelButton.setOnAction(event -> stage.close());

        // Create layout for the scene
        VBox root = new VBox(10); // Use a VBox with spacing of 10 pixels
        root.getChildren().addAll(nameTextField, companyComboBox, skillsSlider, skillValueLabel, saveButton, cancelButton);
        root.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(root, 300, 250);

        // Set the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }
}
