package application.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add Student");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Student Name
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);

        // Kurs
        Label courseLabel = new Label("Course:");
        TextField courseTextField = new TextField();
        gridPane.add(courseLabel, 0, 1);
        gridPane.add(courseTextField, 1, 1);

        //Java Skills
        Label skillLabel = new Label("Java Skills:");
        Slider skillSlider = new Slider(0, 10, 0);
        skillSlider.setShowTickLabels(true);
        skillSlider.setShowTickMarks(true);
        gridPane.add(skillLabel, 0, 2);
        gridPane.add(skillSlider, 1, 2);

        // Slider view
        Label sliderValueLabel = new Label("0");
        sliderValueLabel.setStyle("-fx-font-size: 14px;");

    // slider update
        skillSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            sliderValueLabel.setText(String.valueOf(newValue.intValue()));
        });

        // value
        gridPane.add(sliderValueLabel, 2, 2);


        // add button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameTextField.getText();
            String course = courseTextField.getText();
            int skillLevel = (int) skillSlider.getValue();

            // new student object
            Student student = new Student(name, course, skillLevel);



            // after add
            nameTextField.clear();
            courseTextField.clear();
            skillSlider.setValue(0);
        });
        gridPane.add(addButton, 1, 3);

        Scene scene = new Scene(gridPane, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}