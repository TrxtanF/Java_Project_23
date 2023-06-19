package application.javafx2;

import javafx.application.Application;
import javafx.collections.ObservableList;
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

import java.sql.Connection;

public class AddParticipants extends Application {
    TableView<Student> tableView;
    ObservableList<Allocation> allocationList;
    ObservableList<Student> studentList;
    ObservableList<Student> filteredList;
    ObservableList<Allocation> courseAllocation;
    private Course course;
    Connection connection;
    AllocationCheck allocationCheck;

    public AddParticipants(ObservableList<Allocation> allocationList, ObservableList<Student> studentList, Course course, Connection connection) {
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
        primaryStage.setTitle("Add Participants");

        tableView = new TableView<>();
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, Void> addActionColumn = new TableColumn<>("Add");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        addActionColumn.setCellFactory(param -> new ButtonCell());

        tableView.getColumns().addAll(nameColumn, addActionColumn);

        //Filter allocation List
        courseAllocation = allocationList.filtered(p ->
                p.getCourseFk() == course.getCourseId());

        // Filtern der Studentenliste basierend auf der studentId
        filteredList = studentList.filtered(student ->
                courseAllocation.stream()
                        .noneMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
        );

        tableView.setItems(filteredList);

        VBox root = new VBox(10, tableView);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        // If we do this the insert doesnt work anymore
        /*primaryStage.setOnCloseRequest(event -> {
            ShowParticipants showParticipants = new ShowParticipants(allocationList, studentList, course, connection);
            showParticipants.start(new Stage());
        });*/
    }

    // Innere Klasse für die Button-Zellen der "Add"-Spalte
    private class ButtonCell extends TableCell<Student, Void> {
        private final Button addButton = new Button("Add");

        public ButtonCell() {
            // Event handler for the add button
            addButton.setOnAction(event -> {
                // Hole den ausgewählten Studenten
                Student selectedStudent = getTableRow().getItem();

                if (selectedStudent != null) {

                    // Erzeuge eine neue Allocation
                    Allocation newAllocation = new Allocation(1, selectedStudent.getStudentId(), course.getCourseId());

                    // Füge die neue Allocation zur allocationList hinzu
                    allocationCheck.insert(newAllocation);
                    allocationList.add(newAllocation);

                    // filteredList.remove(selectedStudent) doesent work so:
                    filteredList = studentList.filtered(student ->
                            courseAllocation.stream()
                                    .noneMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
                    );
                    // Aktualisiere die Tabelle
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
                setGraphic(addButton);
            }
        }
    }
}
