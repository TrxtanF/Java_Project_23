package application.javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseController {
    @FXML
    private Button openStudentButton;

    public void openStudentPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Studentfx.fxml"));
            Parent root = loader.load();

            // Creazione di una nuova finestra per la pagina "Student"
            Stage stage = new Stage();
            stage.setTitle("Student Page");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            // Chiusura della finestra corrente (pagina "Course")
            ((Stage) openStudentButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
