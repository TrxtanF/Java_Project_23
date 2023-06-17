package application.javafx.controller;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddCourseController {

        @FXML
        private TextField courseNameTextField;

        @FXML
        private TextField roomTextField;

        @FXML
        private void handleSave(ActionEvent event) {
            String courseName = courseNameTextField.getText();
            String room = roomTextField.getText();

            // save name try
            System.out.println("Course Name: " + courseName);
            System.out.println("Room: " + room);

            // close
            closePopUp();
        }

        private void closePopUp() {
            // pop-up reference
            VBox root = (VBox) courseNameTextField.getScene().getRoot();


            root.getScene().getWindow().hide();
        }

    public String getCourseName() {
        return courseNameTextField.getText();
    }

    public String getRoom() {
        return roomTextField.getText();
    }
}


