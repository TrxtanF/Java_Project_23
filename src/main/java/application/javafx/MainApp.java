package application.javafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;





import java.util.List;
import java.util.Optional;

public class MainApp extends Application {

    private TableView<Student> studentTable;
    private ToggleButton toggleButton;
    private Button addButton;
    private StudentManager studentManager; // Student manager

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management");

        // basic layout
        BorderPane root = new BorderPane();

        // search bar
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(10));

        // student table
        studentTable = new TableView<>();
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, String> courseColumn = new TableColumn<>("Course");
        TableColumn<Student, String> companyColumn = new TableColumn<>("Company");
        TableColumn<Student, Integer> skillColumn = new TableColumn<>("Java Skill");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        skillColumn.setCellValueFactory(new PropertyValueFactory<>("javaSkill"));

        studentTable.getColumns().addAll(nameColumn, courseColumn, companyColumn, skillColumn);

        // toggle
        toggleButton = new ToggleButton("Toggle Courses");
        toggleButton.setPadding(new Insets(10));

        // student button
        addButton = new Button("Add Student");
        addButton.setPadding(new Insets(10));

        // add content
        root.setTop(searchBox);
        root.setCenter(studentTable);
        root.setBottom(addButton);
        root.setRight(toggleButton);

        // visual app
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();


        studentManager = new StudentManager("John Doe", "Computer Science", "ABC Company", 8);

        //  Student example
        ObservableList<Student> students = FXCollections.observableArrayList(
                new Student("John Doe", "Computer Science", "ABC Company", 8),
                new Student("Jane Smith", "Mathematics", "XYZ Company", 9),
                new Student("Mark Johnson", "Physics", "DEF Company", 7)
        );

        // students data
        studentTable.setItems(students);

        // event Handler
        searchButton.setOnAction(event -> {
            String searchTerm = searchField.getText();
            List<Student> searchResults = studentManager.searchStudents(searchTerm);
            updateStudentTable(searchResults);
        });

        toggleButton.setOnAction(event -> {
            if (toggleButton.isSelected()) {
                showCourses();
            } else {
                showStudents();
            }
        });

        addButton.setOnAction(event -> showAddStudentPopup());
    }

    private void updateStudentTable(List<Student> students) {
        ObservableList<Student> studentList = FXCollections.observableArrayList(students);
        studentTable.setItems(studentList);
    }


    private void showCourses() {
    }

    private void showStudents() {
        // show students
    }

    private void showAddStudentPopup() {
        // Dialog window
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add Student");

        // imput for students
        TextField nameField = new TextField();
        TextField courseField = new TextField();
        TextField companyField = new TextField();
        Slider skillSlider = new Slider(0, 10, 0);
        Label skillValueLabel = new Label("Java Skill: " + skillSlider.getValue());

        // add and cancel
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        skillSlider.valueProperty().addListener((observable, oldValue, newValue) -> skillValueLabel.setText("Java Skill: " + (int) newValue.doubleValue()));

        // Layout popup
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, new Label("Name:"), nameField);
        gridPane.addRow(1, new Label("Course:"), courseField);
        gridPane.addRow(2, new Label("Company:"), companyField);
        gridPane.addRow(3, new Label("Java Skill:"), skillSlider);
        gridPane.addRow(4, skillValueLabel);
        dialog.getDialogPane().setContent(gridPane);

        // User can´t add if a field is empty
        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.disableProperty().bind(
                nameField.textProperty().isEmpty()
                        .or(courseField.textProperty().isEmpty())
                        .or(companyField.textProperty().isEmpty())
        );

        // control
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButton) {
                String name = nameField.getText();
                String course = courseField.getText();
                String company = companyField.getText();
                int javaSkill = (int) skillSlider.getValue();

                // new student
                Student newStudent = new Student(name, course, company, javaSkill);

                // new student
                studentTable.getItems().add(newStudent);
                studentManager.addStudent(newStudent);

                return newStudent;
            }
            return null;
        });



        // new student
        Optional<Student> result = dialog.showAndWait();
        result.ifPresent(student -> studentManager.addStudent(student));
    }


}

