package application.javafx2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    public AddCourseView(ObservableList<Course> courseList, Connection connection) {
        this.courseList = courseList;
        this.connection = connection;
        courseCheck = new CourseCheck(connection);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Add Course");

        // Label for subject
        Label subjectLabel = new Label("Subject:");
        // TextField for subject
        TextField subjectTextField = new TextField();
        subjectTextField.setPromptText("Enter subject");

        // Label for room
        Label roomLabel = new Label("Room:");
        // TextField for room
        TextField roomTextField = new TextField();
        roomTextField.setPromptText("Enter room");

        // Label for building
        Label buildingLabel = new Label("Building:");
        // ComboBox for building selection
        ComboBox<String> buildingComboBox = new ComboBox<>();
        buildingComboBox.getItems().addAll("A", "B", "C", "D", "E");
        buildingComboBox.setPromptText("Select building");

        // Buttons for save and cancel
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        // Event handler for save button
        saveButton.setOnAction(event -> {
            // Perform save operation
            String subject = subjectTextField.getText();
            String room = roomTextField.getText();
            String building = buildingComboBox.getValue();

            String combinedRoom = room + building;

            newCourse = new Course(3, subject, combinedRoom);

            if (!courseCheck.insert(newCourse)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText(courseCheck.getValidationProblemDetails());
                alert.show();
                System.out.println("Yes");
            } else {
                courseList.add(newCourse);
            }

            courseList = courseCheck.getAll();

            // Close the window
            stage.close();
        });

        // Event handler for cancel button
        cancelButton.setOnAction(event -> stage.close());

        // Create a GridPane to hold the elements
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        // Add the elements to the GridPane
        gridPane.add(subjectLabel, 0, 0);
        gridPane.add(subjectTextField, 1, 0);
        gridPane.add(roomLabel, 0, 1);
        gridPane.add(roomTextField, 1, 1);
        gridPane.add(buildingLabel, 0, 2);
        gridPane.add(buildingComboBox, 1, 2);

        // Create an HBox for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create a VBox to hold the GridPane and buttonBox
        VBox root = new VBox(10);
        root.getChildren().addAll(gridPane, buttonBox);
        root.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(root, 230, 200);

        // Set the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.setResizable(false);
        stage.show();
    }
}
