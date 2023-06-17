package application.javafx;

import application.javafx.controller.StudentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Studentfx.fxml"));
            Parent root = loader.load();

            StudentController controller = loader.getController();

            // new scene
            Scene scene = new Scene(root);

            // stage scene
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}


/*
    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("Student Management");

        // basic layout
        BorderPane root = new BorderPane();

        // search bar
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(10));

        // student table
        TableView<TetraAssociation> studentTable = new TableView<>();
        TableColumn<TetraAssociation, Void> editColumn = new TableColumn<>("Edit");
        TableColumn<TetraAssociation, Void> deleteColumn = new TableColumn<>("Delete");
        TableColumn<TetraAssociation, String> nameColumn = new TableColumn<>("Name");
        TableColumn<TetraAssociation, String> courseColumn = new TableColumn<>("Course");
        TableColumn<TetraAssociation, String> companyColumn = new TableColumn<>("Company");
        TableColumn<TetraAssociation, String> javaSkillColumn = new TableColumn<>("Java Skill");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        javaSkillColumn.setCellValueFactory(new PropertyValueFactory<>("javaSkill"));

        studentTable.getColumns().addAll(editColumn, deleteColumn, nameColumn, courseColumn, companyColumn, javaSkillColumn);


        // toggle
        toggleButton = new ToggleButton("Toggle Courses");
        toggleButton.setPadding(new Insets(10));

        // student button
        addButton = new Button("Add Student");
        addButton.setPadding(new Insets(10));

        // add content
        root.setTop(searchBox);
        root.setCenter(studentTable);
        root.setBottom(addButton);
        root.setRight(toggleButton);

        // visual app
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();


        studentManager = new StudentManager("John Doe", "Computer Science", "ABC Company", 8);

        //  Student example
        ObservableList<TetraAssociation> tetraAssociationList = FXCollections.observableArrayList(
                tetraAssociation.getAll()
        );

        // students data
        studentTable.setItems(tetraAssociationList);

        // event Handler
        searchButton.setOnAction(event -> {
            String searchTerm = searchField.getText();
            List<Student> searchResults = studentManager.searchStudents(searchTerm);
            updateStudentTable(searchResults);
        });

        toggleButton.setOnAction(event -> {
            if (toggleButton.isSelected()) {
                showCourses();
            } else {
                showStudents();
            }
        });

        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    TetraAssociation student = getTableView().getItems().get(getIndex());
                    showEditStudentPopup(student);
                });



            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    TetraAssociation student = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(student);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        studentTable.getColumns().addAll(editColumn, deleteColumn);

        addButton.setOnAction(event -> showAddStudentPopup());
    }

    private void showEditStudentPopup(TetraAssociation student) {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Edit Student");

        // Aggiungi i campi di input per la modifica degli studenti
        TextField nameField = new TextField(student.getName());
        TextField courseField = new TextField(student.getCourse());
        TextField companyField = new TextField(student.getCompany());
        gridPane.addRow(0, new Label("Name:"), nameField);
        gridPane.addRow(1, new Label("Course:"), courseField);
        gridPane.addRow(2, new Label("Company:"), companyField);
        gridPane.addRow(3, new Label("Java Skill:"), skillSlider);
        gridPane.addRow(4, skillValueLabel);
        dialog.getDialogPane().setContent(gridPane);

        // Configura il controllo dei dati di input
        Node saveButtonNode = dialog.getDialogPane().lookupButton(saveButton);
        saveButtonNode.disableProperty().bind(
                nameField.textProperty().isEmpty()
                        .or(courseField.textProperty().isEmpty())
                        .or(companyField.textProperty().isEmpty())
        );

        // Aggiorna gli studenti nella tabella dopo la modifica
        dialog.setResultConverter(buttonType -> {
            if (buttonType == saveButton) {
                String name = nameField.getText();
                String course = courseField.getText();
                String company = companyField.getText();
                int javaSkill = (int) skillSlider.getValue();

                student.setName(name);
                student.setCourse(course);
                student.setCompany(company);
                student.setJavaSkill(javaSkill);

                studentTable.refresh(); // Aggiorna la tabella degli studenti

                // Aggiorna gli studenti nel manager, se necessario
                studentManager.updateStudent(student);

                return student;
            }
            return null;
        });

        // Aggiungi un listener per l'evento close del dialogo
        dialog.setOnCloseRequest(event -> {
            studentTable.refresh(); // Aggiorna la tabella degli studenti anche quando si chiude il dialogo senza salvare
        });

        dialog.showAndWait();
    }




    private void showDeleteConfirmationDialog(TetraAssociation student) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("Are you sure you want to delete this student?");
        alert.setContentText("Student: " + student.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            studentTable.getItems().remove(student);
            studentManager.removeStudent(student);
        }
    }

    private void updateStudentTable(List<Student> students) {
        ObservableList<TetraAssociation> studentList = FXCollections.observableArrayList(students);
        studentTable.setItems(studentList);
    }


    private void showCourses() {
    }

    private void showStudents() {
        // show students
    }

    private void showAddStudentPopup() {
        // Dialog window
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add Student");

        // imput for students
        TextField nameField = new TextField();
        TextField courseField = new TextField();
        TextField companyField = new TextField();
        Slider skillSlider = new Slider(0, 10, 0);
        Label skillValueLabel = new Label("Java Skill: " + skillSlider.getValue());

        // add and cancel
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        skillSlider.valueProperty().addListener((observable, oldValue, newValue) -> skillValueLabel.setText("Java Skill: " + (int) newValue.doubleValue()));

        // Layout popup
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, new Label("Name:"), nameField);
        gridPane.addRow(1, new Label("Course:"), courseField);
        gridPane.addRow(2, new Label("Company:"), companyField);
        gridPane.addRow(3, new Label("Java Skill:"), skillSlider);
        gridPane.addRow(4, skillValueLabel);
        dialog.getDialogPane().setContent(gridPane);

        // User can´t add if a field is empty
        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.disableProperty().bind(
                nameField.textProperty().isEmpty()
                        .or(courseField.textProperty().isEmpty())
                        .or(companyField.textProperty().isEmpty())
        );

        // control
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButton) {
                String name = nameField.getText();
                String course = courseField.getText();
                String company = companyField.getText();
                int javaSkill = (int) skillSlider.getValue();

                // new student
                Student newStudent = new Student(name, course, company, javaSkill);

                // new student
                studentTable.getItems().add(newStudent);
                studentManager.addStudent(newStudent);

                return newStudent;
            }
            return null;
        });



        // new student
        Optional<Student> result = dialog.showAndWait();
        result.ifPresent(student -> studentManager.addStudent(student));
    }*/



