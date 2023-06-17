package application.javafx2;

import javafx.application.Application;
import javafx.collections.ObservableList;
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

public class ShowParticipants extends Application {

    ObservableList<Allocation> allocationList;
    ObservableList<Student> studentList;
    private Course course;

    public ShowParticipants(ObservableList<Allocation> allocationList, ObservableList<Student> studentList, Course course) {
        this.allocationList = allocationList;
        this.studentList = studentList;
        this.course = course;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Course Participants");

        TableView<Student> tableView = new TableView<>();

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.getColumns().add(nameColumn);

        //Filter allocation List
        ObservableList<Allocation> courseAllocation = allocationList.filtered(p ->
                p.getCourseFk() == course.getCourseId());

        // Filtern der Studentenliste basierend auf der allocationId
        ObservableList<Student> filteredList = studentList.filtered(student ->
                courseAllocation.stream()
                        .anyMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
        );

        tableView.setItems(filteredList);

        // Button "addStudent"
        Button addStudentButton = new Button("addStudent");
        addStudentButton.setOnAction(event -> {
            AddParticipants addParticipants = new AddParticipants(allocationList, studentList, course);
            addParticipants.start(new Stage());
        });

        VBox root = new VBox(20, addStudentButton, tableView);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20)); // Abstand von 20 Pixeln zum Beginn der Tabelle

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
