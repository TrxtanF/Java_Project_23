package application.javafx2.AddViews;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.entity.Course;
import org.inputCheck.CourseCheck;

import java.sql.Connection;

public class AddCourseView2 extends Application {
    private CourseCheck courseCheck;
    private TableView tableView;

    public AddCourseView2(Connection connection, TableView tableView) {
        courseCheck = new CourseCheck(connection);
        this.tableView = tableView;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        run(stage);
    }

    private void run(Stage stage) {
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

        saveButton.setOnAction(event -> {
            // Perform save operation
            String subject = subjectTextField.getText();
            String room = roomTextField.getText();
            String building = buildingComboBox.getValue();

            String combinedRoom = room + building;

            Course course = new Course(1, subject, combinedRoom);

            if (courseCheck.insert(course)) {
                ObservableList<Course> courseList = courseCheck.getAll();
                tableView.setItems(courseList);
            } else {
                throwAllert();
            }

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

    private void throwAllert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(courseCheck.getValidationProblemDetails());
        alert.show();
    }
}
