package application.javafx2.EditViews;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.entity.Company;
import org.entity.Student;
import org.inputCheck.StudentCheck;
import javafx.scene.control.Label;

import java.sql.Connection;

/**
 * The EditStudentView class is responsible for displaying a form to edit student details using JavaFX.
 * It allows the user to modify the student's name, associated company, and Java skills, and save the changes.
 */

public class EditStudentView extends Application {
    private ObservableList<Company> companyList;
    private ObservableList<Student> studentList;
    private Connection connection;
    private StudentCheck studentCheck;
    private Student student;
    private TextField nameTextField;
    private ComboBox<String> companyComboBox;
    private Slider skillsSlider;
    private Label skillValueLabel;
    private Button saveButton;
    TableView tableView;

    /**
     * Constructs an EditStudentView object with the specified company list, student list, and database connection.
     *
     * @param companyList    the list of companies
     * @param connection     the database connection
     */
    public EditStudentView(ObservableList<Company> companyList, Connection connection, TableView tableView) {
        this.tableView = tableView;
        this.companyList = companyList;
        this.connection = connection;
        studentCheck = new StudentCheck(connection);
        nameTextField = new TextField();
        companyComboBox = new ComboBox<>();
        skillsSlider = new Slider();
        skillValueLabel = new Label();

    }

    /**
     * The main entry point for the application.
     *
     * @param args  command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and sets up the stage for editing the student.
     *
     * @param stage  the primary stage for the JavaFX application
     */
    @Override
    public void start(Stage stage) {
        studentList = studentCheck.getAll();
        stage.setTitle("Edit Student");
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nameLabel = new Label("Name:");
        nameTextField.setPrefWidth(200);

        Label companyLabel = new Label("Company:");
        companyComboBox = new ComboBox<>();
        ObservableList<String> nameList = FXCollections.observableArrayList();
        for (Company company : companyList) {
            nameList.add(company.getCompanyName());
        }
        companyComboBox.setItems(nameList);
        companyComboBox.setPromptText("Select company");

        // company listener
        companyComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //  company info update
            updateCompanySelection(newValue);
        });

        Label skillsLabel = new Label("Java Skills:");
        skillsSlider = new Slider(0, 10, 0);
        skillsSlider.setShowTickLabels(true);
        skillsSlider.setShowTickMarks(true);

        skillValueLabel = new Label();
        skillsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            skillValueLabel.setText(String.valueOf(newValue.intValue()));
        });

        saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            updateStudent();
            stage.close();
        });

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(companyLabel, 0, 1);
        gridPane.add(companyComboBox, 1, 1);
        gridPane.add(skillsLabel, 0, 2);
        gridPane.add(skillsSlider, 1, 2);
        gridPane.add(skillValueLabel, 1, 3);
        gridPane.add(saveButton, 1, 4);

        setStudent(student);

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }


    private void updateCompanySelection(String newValue) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (newValue != null) {
            String companyName = companyComboBox.getValue();
            if (companyName == null) {
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("A company must be chosen");
                alert.show();
            } else {
                Company company = companyList.stream()
                        .filter(p -> p.getCompanyName().equals(companyName))
                        .findFirst()
                        .orElse(null);
                // actions on company
            }
        }
    }

    public void setStudent(Student student) {
        this.student = student;
        nameTextField.setText(student.getName());
        String companyName = companyList.stream()
                .filter(c -> c.getCompanyId() == student.getCompanyFk())
                .map(Company::getCompanyName)
                .findFirst()
                .orElse(null);
        companyComboBox.setValue(companyName);
        skillsSlider.setValue(student.getJavaSkills());
        skillValueLabel.setText(String.valueOf(student.getJavaSkills()));
    }

    private void updateStudent() {
        String name = nameTextField.getText();
        String companyName = companyComboBox.getValue();

        if (companyName != null) {
            Company company = companyList.stream()
                    .filter(c -> c.getCompanyName().equals(companyName))
                    .findFirst()
                    .orElse(null);
            int javaSkills = (int) skillsSlider.getValue();

            // Update student
            student.setName(name);
            student.setCompanyFk(company.getCompanyId());
            student.setJavaSkills(javaSkills);

            // Update the list of students in the table
            studentCheck.updateById(student.getStudentId(), student);
            studentList = studentCheck.getAll();
            tableView.setItems(studentList);
        }
    }
}
