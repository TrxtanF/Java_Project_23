package application.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Optional;


public class StudentController {

    @FXML
    private TableView<?> studentTable;

    @FXML
    private Button openCourseButton;

    @FXML
    public void openCoursePage(ActionEvent event) {
        try {
            //  FXML "Course"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Coursefx.fxml"));
            Parent root = loader.load();

            // new window for "Course"
            Stage stage = new Stage();
            stage.setTitle("Course Page");
            stage.setScene(new Scene(root));
            stage.show();

            // close window
            ((Stage) openCourseButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void openPopUp() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddStudentPopUp.fxml"));

        DialogPane dialogPane;
        try {
            dialogPane = new DialogPane(); // new object
            dialogPane.setContent(loader.load()); // set BorderPane
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to close the window?");
            alert.setContentText("All unsaved changes will be lost.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // The user confirmed the closure, you can perform cleanup actions or close the window
                System.out.println("Dialog closed");
            } else {
                // The user canceled the closure, so prevent the window from closing
                event.consume();
            }
        });

        dialog.showAndWait();
    }



    //open in course control class

    @FXML
    public void addCourse() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCourse.fxml"));
        AddCourseController controller = new AddCourseController();
        loader.setController(controller);

        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Puoi accedere ai valori inseriti nel pop-up attraverso il controller di AddCourse
            String courseName = controller.getCourseName();
            String room = controller.getRoom();

            // Fai qualcosa con i valori...
            System.out.println("Course Name: " + courseName);
            System.out.println("Room: " + room);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
