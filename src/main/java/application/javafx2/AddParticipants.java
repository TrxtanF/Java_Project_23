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

public class AddParticipants extends Application {
    TableView<Student> tableView;
    ObservableList<Allocation> allocationList;
    ObservableList<Student> studentList;
    private Course course;

    public AddParticipants(ObservableList<Allocation> allocationList, ObservableList<Student> studentList, Course course) {
        this.allocationList = allocationList;
        this.studentList = studentList;
        this.course = course;
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
        ObservableList<Allocation> courseAllocation = allocationList.filtered(p ->
                p.getCourseFk() == course.getCourseId());

        // Filtern der Studentenliste basierend auf der studentId
        ObservableList<Student> filteredList = studentList.filtered(student ->
                courseAllocation.stream()
                        .noneMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
        );

        tableView.setItems(filteredList);

        VBox root = new VBox(10, tableView);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Innere Klasse für die Button-Zellen der "Add"-Spalte
    private class ButtonCell extends TableCell<Student, Void> {
        private final Button addButton = new Button("Add");

        public ButtonCell() {
            // Event handler for the add button
            addButton.setOnAction(event -> {
                // Hole den ausgewählten Studenten
                Student selectedStudent = tableView.getSelectionModel().getSelectedItem();

                if (selectedStudent != null) {

                    // Erzeuge eine neue Allocation
                    Allocation newAllocation = new Allocation(1, selectedStudent.getStudentId(), course.getCourseId());

                    // Füge die neue Allocation zur allocationList hinzu
                    allocationList.add(newAllocation);

                    // Aktualisiere die Tabelle
                    tableView.refresh();
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
