package application.javafx2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entity.Course;
import org.inputCheck.CourseCheck;

import java.sql.Connection;

public class AddCourseView extends Application {
    ObservableList<Course> courseList;
    Course newCourse;

    Connection connection;

    CourseCheck courseCheck;

    public AddCourseView(ObservableList<Course> courseList, Connection connection){
        this.courseList = courseList;
        this.connection = connection;
        courseCheck= new CourseCheck(connection);
    }



    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage){
        stage.setTitle("Add Course");

        // TextField for name
        TextField subjectTextField = new TextField();
        subjectTextField.setPromptText("Enter name");

        // TextField for room
        TextField roomTextField = new TextField();
        subjectTextField.setPromptText("Enter name");

        // Buttons for save and cancel
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        // Event handler for save button
        saveButton.setOnAction(event -> {
            // Perform save operation
            String subject = subjectTextField.getText();
            String room = roomTextField.getText();

            newCourse = new Course(3, subject, room);
            try {
                if(!courseCheck.insert(newCourse)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText(courseCheck.getValidationProblemDetails());
                    alert.show();
                    System.out.println("Yes");
                }else{
                    courseList.addAll(newCourse);
                }

            } catch (Exception e) {

            }


            // Close the window
            stage.close();
        });

        // Event handler for cancel button
        cancelButton.setOnAction(event -> stage.close());

        // Create layout for the scene
        VBox root = new VBox(10); // Use a VBox with spacing of 10 pixels
        root.getChildren().addAll(subjectTextField, roomTextField, saveButton, cancelButton);
        root.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(root, 300, 250);

        // Set the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }
}
