package application.javafx2.AddParticipantsFolder;

import application.javafx2.ShowParticipantsFolder.ShowParticipants2;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
 * The AddParticipants class is responsible for adding participants to a course.
 * It displays a TableView of students who are not yet enrolled in the course,
 * allowing the user to select and add them as participants.
 *
 * <p>The class extends the JavaFX Application class to create a graphical user interface.
 * It initializes a TableView to display the available students and handles the logic
 * for adding participants to the course.
 */
public class AddParticipants extends Application {

    protected TableView<Student> tableView;
    protected AllocationCheck allocationCheck;
    protected StudentCheck studentCheck;
    protected Course course;
    protected ObservableList<Student> studentList;
    protected ObservableList<Allocation> courseAllocations;
    public FilteredList<Student> filteredList;
    private Connection connection;

    /**
     * Constructs a new AddParticipants object.
     *
     * @param connection the database connection
     * @param course     the course to add participants to
     */
    public AddParticipants(Connection connection, Course course) {
        this.connection = connection;
        this.course = course;
        allocationCheck = new AllocationCheck(connection);
        studentCheck = new StudentCheck(connection);
    }

    /**
     * The main method of the AddParticipants2 class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the graphical user interface by setting up the TableView and the stage.
     *
     * @param stage the primary stage for the application
     */
    @Override
    public void start(Stage stage) {
        setupTableView(stage);
        setupStage(stage);
    }

    /**
     * Sets up the TableView to display the available students and the Add button column.
     * It filters the students who are not yet enrolled in the course and populates the TableView.
     *
     * @param stage the primary stage for the application
     */
    private void setupTableView(Stage stage) {
        tableView = new TableView<>();

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Void> addActionColumn = new TableColumn<>("Add");
        addActionColumn.setCellFactory(param -> new ButtonCell(this));

        tableView.getColumns().addAll(nameColumn, addActionColumn);

        //Filter allocationList to get only the allocations for this course
        ObservableList<Allocation> allocationList = allocationCheck.getAll();
        courseAllocations = allocationList.filtered(p -> p.getCourseFk() == course.getCourseId());

        //Filter courseAllocations to get the students who are NOT in the course
        studentList = studentCheck.getAll();
        filteredList = studentList.filtered(student ->
                courseAllocations.stream()
                        .noneMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
        );

        tableView.setItems(filteredList);

        stage.setOnCloseRequest(event -> {
            ShowParticipants2 showParticipants = new ShowParticipants2(connection, course);
            showParticipants.start(new Stage());
        });
    }

    /**
     * Sets up the stage by setting the title, creating the root VBox, and showing the scene.
     *
     * @param stage the primary stage for the application
     */
    private void setupStage(Stage stage){
        stage.setTitle("Add Participants");

        VBox root = new VBox(10, tableView);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
