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
import org.inputCheck.StudentCheck;

import java.sql.Connection;

public class AddStudentView extends Application {

    ObservableList<Company> companyList;
    ObservableList<Student> studentList;
    Student newStudent;
    Connection connection;
    StudentCheck studentCheck;

    public AddStudentView(ObservableList<Company> companyList, ObservableList<Student> studentList, Connection connection) {
        this.companyList = companyList;
        this.studentList = studentList;
        this.connection = connection;
        studentCheck = new StudentCheck(connection);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Add Student");

        // Labels
        Label nameLabel = new Label("Name:");
        Label companyLabel = new Label("Company:");
        Label skillsLabel = new Label("Java Skills:");

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

        // Layout for labels and corresponding input fields
        GridPane inputGridPane = new GridPane();
        inputGridPane.setHgap(10);
        inputGridPane.setVgap(10);
        inputGridPane.addRow(0, nameLabel, nameTextField);
        inputGridPane.addRow(1, companyLabel, companyComboBox);
        inputGridPane.addRow(2, skillsLabel);
        inputGridPane.add(skillsSlider, 1, 2);
        inputGridPane.add(skillValueLabel, 1, 3);
        inputGridPane.setAlignment(Pos.CENTER);

        // Layout for save and cancel buttons
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Event handler for save button
        saveButton.setOnAction(event -> {
            // Perform save operation
            Alert alert = new Alert(Alert.AlertType.ERROR);

            String name = nameTextField.getText();
            String companyName = companyComboBox.getValue();
            if(companyName == null){
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("A company must be chosen");
                alert.show();
            }else{
                Company company = companyList.stream().filter(p -> p.getCompanyName().equals(companyName)).findFirst().orElse(null);
                int javaSkills = (int) skillsSlider.getValue();



                newStudent = new Student(4, name, javaSkills, company.getCompanyId());
                if (!studentCheck.insert(newStudent)) {

                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText(studentCheck.getValidationProblemDetails());
                    alert.show();
                    System.out.println(newStudent.getJavaSkills());
                } else {
                    studentList.add(newStudent); // Add the new student to the list
                }
            }
            // Close the window
            stage.close();
        });

        // Event handler for cancel button
        cancelButton.setOnAction(event -> stage.close());

        // Create layout for the scene
        VBox root = new VBox(10); // Use a VBox with spacing of 10 pixels
        root.getChildren().addAll(inputGridPane, buttonBox);
        root.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(root, 250, 200);

        // Set the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.setResizable(false);
        stage.show();
    }
}
