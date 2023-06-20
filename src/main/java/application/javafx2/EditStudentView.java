package application.javafx2;

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

    public EditStudentView(ObservableList<Company> companyList, ObservableList<Student> studentList, Connection connection) {
        this.companyList = companyList;
        this.studentList = studentList;
        this.connection = connection;
        studentCheck = new StudentCheck(connection);
        nameTextField = new TextField();
        companyComboBox = new ComboBox<>();
        skillsSlider = new Slider();
        skillValueLabel = new Label();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
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

        // Aggiungi un listener per gestire la selezione dell'azienda
        companyComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Aggiorna le informazioni quando viene selezionata un'azienda diversa
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
                // Esegui le azioni necessarie con l'azienda selezionata
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

            // Aggiorna lo studente con le nuove informazioni
            student.setName(name);
            student.setCompanyFk(company.getCompanyId());
            student.setJavaSkills(javaSkills);

            // Update the list of students in the table
            studentList.set(studentList.indexOf(student), student);
            studentCheck.updateById(student.getStudentId(), student);
        }
    }
}
