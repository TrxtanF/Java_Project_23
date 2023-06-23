package application.javafx2.EditViews;

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

/**
 * The EditCourseView class is responsible for displaying a form to edit course details using JavaFX.
 * It allows the user to modify the subject, room, and building of a course and save the changes.
 */

public class EditCourseView extends Application {
    private ObservableList<Course> courseList;
    private CourseCheck courseCheck;
    private Course course;
    private TableView tableView;

    /**
     * Constructs an EditCourseView object with the specified course list, course, and database connection.
     *
     * @param course      the course to be edited
     * @param connection  the database connection
     */
    public EditCourseView(Course course, Connection connection, TableView tableView){
        this.tableView = tableView;
        this.course = course;
        courseCheck = new CourseCheck(connection);
    }

    /**
     * The main entry point for the application.
     *
     * @param args  command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and sets up the stage for editing the course.
     *
     * @param stage  the primary stage for the JavaFX application
     * @throws Exception  if an error occurs during initialization
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Edit Course");

        Label subjectLabel = new Label("Subject:");
        TextField subjectTextField = new TextField();
        subjectTextField.setPromptText("Enter subject");

        Label roomLabel = new Label("Room:");
        TextField roomTextField = new TextField();
        roomTextField.setPromptText("Enter room");

        Label buildingLabel = new Label("Building:");
        ComboBox<String> buildingComboBox = new ComboBox<>();
        buildingComboBox.getItems().addAll("A", "B", "C", "D", "E");
        buildingComboBox.setPromptText("Select building");

        courseList = courseCheck.getAll();
        subjectTextField.setText(course.getSubject());
        roomTextField.setText(course.getRoom().substring(0, 3));
        if (course.getRoom().length() >= 4) {
            String selectedBuilding = course.getRoom().substring(3, 4);
            buildingComboBox.getSelectionModel().select(selectedBuilding);
        }

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        saveButton.setOnAction(event -> {
            String subject = subjectTextField.getText();
            String room = roomTextField.getText();
            String building = buildingComboBox.getValue();

            String combinedRoom = room + building;

            course.setSubject(subject);
            course.setRoom(combinedRoom);
            courseCheck.updateById(course.getCourseId(), course);

            //courseList.set(courseList.indexOf(course), course);
            courseList = courseCheck.getAll();
            tableView.setItems(courseList);

            stage.close();
        });

        cancelButton.setOnAction(event -> stage.close());

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.add(subjectLabel, 0, 0);
        gridPane.add(subjectTextField, 1, 0);
        gridPane.add(roomLabel, 0, 1);
        gridPane.add(roomTextField, 1, 1);
        gridPane.add(buildingLabel, 0, 2);
        gridPane.add(buildingComboBox, 1, 2);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(10);
        root.getChildren().addAll(gridPane, buttonBox);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 230, 200);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
