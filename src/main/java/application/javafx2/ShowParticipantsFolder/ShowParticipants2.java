package application.javafx2.ShowParticipantsFolder;

import application.javafx2.AddParticipantsFolder.AddParticipants;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entity.Allocation;
import org.entity.Course;
import org.entity.Student;
import org.inputCheck.AllocationCheck;
import org.inputCheck.StudentCheck;

import java.sql.Connection;

/**
 * The ShowParticipants2 class is responsible for displaying the participants of a course in a JavaFX TableView.
 * It provides methods for setting up the table, configuring the stage, and handling user actions.
 */
public class ShowParticipants2 extends Application {
    private Connection connection;
    protected AllocationCheck allocationCheck;
    protected StudentCheck studentCheck;
    protected TableView<Student> tableView;
    protected Course course;
    protected ObservableList<Student> studentList;
    protected ObservableList<Allocation> courseAllocations;
    public FilteredList<Student> filteredList;
    private Button addStudentButton;

    /**
     * Constructs a ShowParticipants2 object with the specified connection and course.
     *
     * @param connection the database connection
     * @param course     the course for which to display the participants
     */
    public ShowParticipants2(Connection connection, Course course){
        this.connection = connection;
        this.course = course;
        allocationCheck = new AllocationCheck(connection);
        studentCheck = new StudentCheck(connection);
    }

    /**
     * The main entry point for the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and sets up the stage for displaying the participants.
     *
     * @param stage the primary stage for the JavaFX application
     */
    @Override
    public void start(Stage stage) {
        setupTable(stage);
        setupStage(stage);
    }

    /**
     * Sets up the table view and its columns.
     *
     * @param stage the primary stage for the JavaFX application
     */
    private void setupTable(Stage stage){
        tableView = new TableView<>();

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, Void> removeActionColumn = new TableColumn<>("Remove");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeActionColumn.setCellFactory(param -> new ButtonCell(this));

        tableView.getColumns().addAll(nameColumn, removeActionColumn);

        //Filter allocationList to get only the allocations for this course
        ObservableList<Allocation> allocationList = allocationCheck.getAll();
        courseAllocations = allocationList.filtered(p -> p.getCourseFk() == course.getCourseId());

        // Filter courseAllocations to get the students who are in the course
        studentList = studentCheck.getAll();
        filteredList = studentList.filtered(student ->
                courseAllocations.stream()
                        .anyMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
        );

        tableView.setItems(filteredList);

        // Button "addStudent"
        addStudentButton = new Button("AddStudent");
        addStudentButton.setOnAction(event -> {
            AddParticipants addParticipants = new AddParticipants(connection, course);
            addParticipants.start(new Stage());
            stage.close();
        });
    }

    /**
     * Sets up the stage with the necessary properties and displays it.
     *
     * @param stage the primary stage for the JavaFX application
     */
    private void setupStage(Stage stage){
        stage.setTitle("Course Participants");

        VBox root = new VBox(20, addStudentButton, tableView);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
