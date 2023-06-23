package application.javafx2.ShowParticipantsFolder;

import application.javafx2.AddParticipantsFolder.AddParticipants2;
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

public class ShowParticipants2 extends Application {
    private Connection connection;
    protected AllocationCheck allocationCheck;
    protected StudentCheck studentCheck;
    protected TableView tableView;
    protected Course course;
    protected ObservableList<Student> studentList;
    protected ObservableList<Allocation> courseAllocations;
    public FilteredList<Student> filteredList;

    public ShowParticipants2(Connection connection, Course course){
        this.connection = connection;
        this.course = course;
        allocationCheck = new AllocationCheck(connection);
        studentCheck = new StudentCheck(connection);
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        run(stage);
    }

    private void run(Stage stage){
        tableView = new TableView<>();

        stage.setTitle("Course Participants");

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, Void> addActionColumn = new TableColumn<>("Remove");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addActionColumn.setCellFactory(param -> new ButtonCell(this));

        tableView.getColumns().addAll(nameColumn, addActionColumn);

        //Filter allocationList to get only the allocations for this course
        ObservableList<Allocation> allocationList = allocationCheck.getAll();
        courseAllocations = allocationList.filtered(p -> p.getCourseFk() == course.getCourseId());

        //Filter courseAllocations to get the students who are NOT in the course
        studentList = studentCheck.getAll();
        filteredList = studentList.filtered(student ->
                courseAllocations.stream()
                        .anyMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
        );

        tableView.setItems(filteredList);

        // Button "addStudent"
        Button addStudentButton = new Button("addStudent");
        addStudentButton.setOnAction(event -> {
            //AddParticipants addParticipants = new AddParticipants(allocationList, studentList, course, connection);
            AddParticipants2 addParticipants = new AddParticipants2(connection, course);
            addParticipants.start(new Stage());
            stage.close();
        });

        VBox root = new VBox(20, addStudentButton, tableView);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20)); // Abstand von 20 Pixeln zum Beginn der Tabelle

        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
