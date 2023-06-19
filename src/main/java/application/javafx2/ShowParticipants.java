package application.javafx2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

import java.sql.Connection;

public class ShowParticipants extends Application {
    TableView<Student> tableView;
    ObservableList<Allocation> allocationList;
    ObservableList<Student> studentList;
    ObservableList<Student> filteredList;
    ObservableList<Allocation> courseAllocation;
    private Course course;
    AllocationCheck allocationCheck;
    Connection connection;

    public ShowParticipants(ObservableList<Allocation> allocationList, ObservableList<Student> studentList, Course course, Connection connection) {
        this.allocationList = allocationList;
        this.studentList = studentList;
        this.course = course;
        this.connection = connection;
        allocationCheck = new AllocationCheck(connection);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Course Participants");

        tableView = new TableView<>();

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, Void> addActionColumn = new TableColumn<>("Remove");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addActionColumn.setCellFactory(param -> new ButtonCell());

        tableView.getColumns().addAll(nameColumn, addActionColumn);

        //Filter allocation List
        courseAllocation = allocationList.filtered(p ->
                p.getCourseFk() == course.getCourseId());

        // Filtern der Studentenliste basierend auf der allocationId
        filteredList = studentList.filtered(student ->
                courseAllocation.stream()
                        .anyMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
        );

        tableView.setItems(filteredList);

        // Button "addStudent"
        Button addStudentButton = new Button("addStudent");
        addStudentButton.setOnAction(event -> {
            AddParticipants addParticipants = new AddParticipants(allocationList, studentList, course, connection);
            addParticipants.start(new Stage());
            primaryStage.close();
        });

        VBox root = new VBox(20, addStudentButton, tableView);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20)); // Abstand von 20 Pixeln zum Beginn der Tabelle

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonCell extends TableCell<Student, Void> {
        private final Button removeButton = new Button("Remove");

        public ButtonCell() {
            removeButton.setOnAction(event -> {
                Student selectedStudent = getTableRow().getItem();
                if (selectedStudent != null) {
                    Allocation allocation = allocationList.stream().filter(p -> p.getCourseFk() == course.getCourseId() && p.getStudentFk() == selectedStudent.getStudentId()).findFirst().orElse(null);
                    if (allocation != null) {
                        allocationCheck.deleteById(allocation.getAllocationId());
                        allocationList.remove(allocation);
                        // This code doesnt work: filteredList.remove(selectedStudent);
                        filteredList = studentList.filtered(student ->
                                courseAllocation.stream()
                                        .anyMatch(p -> p.getStudentFk() == student.getStudentId())
                        );
                    }
                    tableView.setItems(filteredList);
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(removeButton);
            }
        }
    }
}
