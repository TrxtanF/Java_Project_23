package application.javafx2.AddParticipantsFolder;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
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

public class AddParticipants2 extends Application {

    protected TableView tableView;
    protected AllocationCheck allocationCheck;
    protected StudentCheck studentCheck;
    protected Course course;
    protected ObservableList<Student> studentList;
    protected ObservableList<Allocation> courseAllocations;
    public FilteredList<Student> filteredList;

    public AddParticipants2(Connection connection, Course course) {
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

    private void run(Stage stage) {
        tableView = new TableView<>();

        stage.setTitle("Add Participants");

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

        VBox root = new VBox(10, tableView);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
