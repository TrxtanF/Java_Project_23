package application.javafx2.AddViews;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entity.Company;
import org.entity.Student;
import org.inputCheck.CompanyCheck;
import org.inputCheck.StudentCheck;

import java.sql.Connection;

public class AddStudentView2 extends Application {
    private StudentCheck studentCheck;
    private CompanyCheck companyCheck;
    private TableView tableView;

    public AddStudentView2(Connection connection, TableView tableView){
        studentCheck = new StudentCheck(connection);
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
        ObservableList<Company> companyList = companyCheck.getAll();
        ObservableList<String> companyNameList = FXCollections.observableArrayList();
        companyList.forEach(company -> companyNameList.add(company.getCompanyName()));
        companyComboBox.setItems(companyNameList);
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
            // Get Data for Student
            String name = nameTextField.getText();
            int javaSkills = (int) skillsSlider.getValue();
            Company company = companyList.stream().filter(p -> p.getCompanyName().equals(companyComboBox.getValue())).findFirst().orElse(null);

            // Create Student
            Student student = new Student(1, name, javaSkills, company.getCompanyId());

            clickSave(student);
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

    private void clickSave(Student student){
        if(studentCheck.insert(student)){
            System.out.println("Student FK: "+student.getCompanyFk());
            ObservableList<Student> studentList = studentCheck.getAll();
            tableView.setItems(studentList);
        }else{
            throwAllert();
        }
    }

    private void throwAllert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(studentCheck.getValidationProblemDetails());
        alert.show();
    }
}
