package application.javafx2.AddParticipantsFolder;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import org.entity.Allocation;
import org.entity.Student;

public class ButtonCell extends TableCell<Student, Void> {
    private final Button addButton = new Button("Add");

    public ButtonCell(AddParticipants2 addParticipants2) {
        // Event handler for the add button
        addButton.setOnAction(event -> {
            // Hole den ausgewählten Studenten
            Student selectedStudent = getTableRow().getItem();

            if (selectedStudent != null) {

                // Erzeuge eine neue Allocation
                Allocation newAllocation = new Allocation(1, selectedStudent.getStudentId(), addParticipants2.course.getCourseId());

                // Füge die neue Allocation zur allocationList hinzu
                addParticipants2.allocationCheck.insert(newAllocation);
                //allocationList.add(newAllocation);

                // filteredList.remove(selectedStudent) doesent work so:
                ObservableList<Allocation> allocationList = addParticipants2.allocationCheck.getAll();
                addParticipants2.courseAllocations = allocationList.filtered(p -> p.getCourseFk() == addParticipants2.course.getCourseId());
                addParticipants2.filteredList = addParticipants2.studentList.filtered(student ->
                        addParticipants2.courseAllocations.stream()
                                .noneMatch(allocation -> allocation.getStudentFk() == student.getStudentId())
                );
                // Aktualisiere die Tabelle
                addParticipants2.tableView.setItems(addParticipants2.filteredList);
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
