package application.javafx2.ShowParticipantsFolder;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import org.entity.Allocation;
import org.entity.Student;

public class ButtonCell extends TableCell<Student, Void> {
    private final Button removeButton = new Button("Remove");

    public ButtonCell(ShowParticipants2 showParticipants2) {
        removeButton.setOnAction(event -> {
            Student selectedStudent = getTableRow().getItem();
            if (selectedStudent != null) {
                ObservableList<Allocation> allocationList = showParticipants2.allocationCheck.getAll();
                Allocation allocation = allocationList.stream().filter(p -> p.getCourseFk() == showParticipants2.course.getCourseId() && p.getStudentFk() == selectedStudent.getStudentId()).findFirst().orElse(null);
                if (allocation != null) {
                    showParticipants2.allocationCheck.deleteById(allocation.getAllocationId());
                    allocationList.remove(allocation);

                    showParticipants2.courseAllocations = allocationList.filtered(p -> p.getCourseFk() == showParticipants2.course.getCourseId());
                    showParticipants2.filteredList = showParticipants2.studentList.filtered(student ->
                            showParticipants2.courseAllocations.stream()
                                    .anyMatch(p -> p.getStudentFk() == student.getStudentId())
                    );
                    showParticipants2.tableView.setItems(showParticipants2.filteredList);
                }
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





