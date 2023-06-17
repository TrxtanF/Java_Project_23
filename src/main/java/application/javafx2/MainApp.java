package application.javafx2;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.databaseutils.H2Utils;
import org.entity.Allocation;
import org.entity.Company;
import org.entity.Course;
import org.entity.Student;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.inputCheck.CompanyCheck;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class MainApp extends Application {

    H2Utils database = new H2Utils();
    Connection connection = database.getConnection();

    CompanyCheck companyCheck = new CompanyCheck(connection);

    private TabPane tabPane;

    TableView<Company> companyTableView;

    ObservableList<Student> studentList;
    ObservableList<Company> companyList =  FXCollections.observableArrayList();

    ObservableList<Course> courseList;
    ObservableList<Allocation> allocationList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            companyList  = companyCheck.getAll();
        } catch (Exception e) {

        }

        primaryStage.setTitle("Student Management System");

        // Create the main layout using BorderPane
        BorderPane mainLayout = new BorderPane();

        // Create the tab pane for navigation
        tabPane = new TabPane();

        // Create tabs for different pages
        Tab studentTab = new Tab("Students");
        studentTab.setContent(createStudentPage());
        studentTab.setClosable(false); // Disable closing the tab

        Tab companyTab = new Tab("Companies");
        companyTab.setContent(createCompanyPage());
        companyTab.setClosable(false); // Disable closing the tab

        Tab courseTab = new Tab("Courses");
        courseTab.setContent(createCoursePage());
        courseTab.setClosable(false); // Disable closing the tab

        // Add tabs to the tab pane
        tabPane.getTabs().addAll(studentTab, companyTab, courseTab);

        // Add tab pane to the top of the main layout
        mainLayout.setTop(tabPane);

        // Create the scene
        Scene scene = new Scene(mainLayout, 800, 500);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Show the primary stage
        primaryStage.show();
    }

    private ScrollPane createStudentPage() {

        studentList = FXCollections.observableArrayList();
        studentList.add(new Student(1, "Tristan Finke", 7, 1));
        studentList.add(new Student(2, "Gianluca Battisti", 8, 1));
        studentList.add(new Student(3, "Niklas", 8, 1));

        TableView<Student> tableView = new TableView<>();
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Student, String> javaSkillsColumn = new TableColumn<>("Java Skills");
        javaSkillsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getJavaSkills())));

        TableColumn<Student, String> companyColumn = new TableColumn<>("Company");
        companyColumn.setCellValueFactory(cellData -> {
            int companyFk = cellData.getValue().getCompanyFk();
            String companyName = "";

            for (Company company : companyList) {
                if (company.getCompanyId() == companyFk) {
                    companyName = company.getCompanyName();
                    break;
                }
            }

            return new SimpleStringProperty(companyName);
        });

        TableColumn<Student, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setCellFactory(param -> new ButtonCellStudent("Edit"));

        TableColumn<Student, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(param -> new ButtonCellStudent("Delete"));

        tableView.getColumns().addAll(nameColumn, javaSkillsColumn, companyColumn, editColumn, deleteColumn);

        tableView.setItems(studentList);

        // TextField und Button erstellen
        TextField txtField_search = new TextField();
        txtField_search.setPromptText("Searching...");
        txtField_search.setPrefWidth(100);
        Button btn_addStudent = new Button("Add student");

        // Event-Handler für den das Search Field
        txtField_search.setOnKeyReleased(event -> {
            String text = txtField_search.getText();
            if (!text.isEmpty()) {
                // Filtern der Studentenliste basierend auf dem eingegebenen Text
                ObservableList<Student> filteredList = studentList.filtered(student -> student.getName().toLowerCase().contains(text.toLowerCase()));

                // Setzen der gefilterten Liste als Datenquelle für die TableView
                tableView.setItems(filteredList);
            } else {
                // Wenn das Textfeld leer ist, wird die ursprüngliche studentList angezeigt
                tableView.setItems(studentList);
            }
        });

        //Event-Handler für Button
        btn_addStudent.setOnAction(event -> {
            AddStudentView addStudentView = new AddStudentView(companyList, studentList);
            addStudentView.start(new Stage());
        });


        // Erstellen der Scene und Hinzufügen zur Stage
        HBox hbox = new HBox();
        hbox.getChildren().addAll(txtField_search, btn_addStudent);
        hbox.setSpacing(20);
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(hbox, tableView);

        // Wrap the table view in a VBox
        //VBox content = new VBox();
        //content.getChildren().add(tableView);

        // Wrap the content in a scroll pane
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    private class ButtonCellStudent extends TableCell<Student, Void> {
        private final Button button;

        public ButtonCellStudent(String buttonText) {
            button = new Button(buttonText);
            button.setOnAction(event -> {
                Student student = getTableRow().getItem();
                if (student != null) {
                    if (buttonText.equals("Edit")) {
                        System.out.println("Edit: " + student.getName());
                        // Fügen Sie hier den gewünschten Code für die Bearbeitung hinzu
                    } else if (buttonText.equals("Delete")) {
                        System.out.println("Delete: " + student.getName());
                        // Fügen Sie hier den gewünschten Code für das Löschen hinzu
                    }
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(button);
            } else {
                setGraphic(null);
            }
        }
    }

    private ScrollPane createCompanyPage() {
        // Create the table view
        companyTableView = new TableView<>();

        // Define the columns
        TableColumn<Company, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(celldata -> new SimpleStringProperty(String.valueOf(celldata.getValue().getCompanyName())));

        TableColumn<Company, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setCellFactory(param -> new ButtonCellCompany("Edit"));

        TableColumn<Company, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(param -> new ButtonCellCompany("Delete"));

        // Add the columns to the table view
        companyTableView.getColumns().addAll(nameColumn, editColumn, deleteColumn);

        // Set data source for the tabel view
        companyTableView.setItems(companyList);

        // TextField und Button erstellen
        TextField txtField_search = new TextField();
        txtField_search.setPromptText("Searching...");
        txtField_search.setPrefWidth(100);
        Button btn_addCompany = new Button("Add company");

        // Event-Handler für den das Search Field
        txtField_search.setOnKeyReleased(event -> {
            String text = txtField_search.getText();
            if (!text.isEmpty()) {
                // Filtern der Studentenliste basierend auf dem eingegebenen Text
                ObservableList<Company> filteredList = companyList.filtered(course ->
                        course.getCompanyName()
                                .toLowerCase()
                                .contains(text.toLowerCase())
                );

                // Setzen der gefilterten Liste als Datenquelle für die TableView
                companyTableView.setItems(filteredList);
            } else {
                // Wenn das Textfeld leer ist, wird die ursprüngliche studentList angezeigt
                companyTableView.setItems(companyList);
            }
        });

        // Event-Handler für Button
        btn_addCompany.setOnAction(event -> {
            AddCompanyView addCompanyView = new AddCompanyView(companyList, connection);
            addCompanyView.start(new Stage());
        });

        // Erstellen der Scene und Hinzufügen zur Stage
        HBox hbox = new HBox();
        hbox.getChildren().addAll(txtField_search, btn_addCompany);
        hbox.setSpacing(20);
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(hbox, companyTableView);

        // Wrap the content in a scroll pane
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    private class ButtonCellCompany extends TableCell<Company, Void> {
        private final Button button;

        public ButtonCellCompany(String buttonText) {
            button = new Button(buttonText);
            button.setOnAction(event -> {
                Company company = getTableRow().getItem();
                if (company != null) {
                    if (buttonText.equals("Edit")) {
                        System.out.println("Edit: " + company.getCompanyName());
                        // Fügen Sie hier den gewünschten Code für die Bearbeitung hinzu
                    } else if (buttonText.equals("Delete")) {
                        System.out.println("Delete: " + company.getCompanyName());
                        // Fügen Sie hier den gewünschten Code für das Löschen hinzu
                        try {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("Are you sure to delete this company?");

                            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                            ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(okButton, cancelButton);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == okButton) {
                                companyCheck.deleteById(company.getCompanyId());
                                companyList = companyCheck.getAll();
                                companyTableView.setItems(companyList);
                                System.out.println("OK wurde geklickt");
                            } else {
                                // Code ausführen, wenn der Benutzer auf "Abbrechen" klickt oder den Dialog schließt
                                System.out.println("Abbrechen wurde geklickt oder Dialog geschlossen");
                            }
                        } catch (Exception e) {
                            System.out.println("Failed");
                        }
                    }
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(button);
            } else {
                setGraphic(null);
            }
        }
    }

    private ScrollPane createCoursePage() {
        courseList = FXCollections.observableArrayList();
        courseList.add(new Course(1, "Mathematik", "220D"));
        courseList.add(new Course(2, "Logik", "220D"));

        // Create the company page content
        VBox content = new VBox();
        Label titleLabel = new Label("Companies");
        content.getChildren().add(titleLabel);

        // Create the table view
        TableView<Course> tableView = new TableView<>();

        // Create the columns
        TableColumn<Course, String> nameColumn = new TableColumn<>("Course name");
        nameColumn.setCellValueFactory(celldata -> new SimpleStringProperty(String.valueOf(celldata.getValue().getSubject())));

        TableColumn<Course, String> roomColumn = new TableColumn<>("Room");
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));

        TableColumn<Course, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setCellFactory(param -> new ButtonCellCourse("Edit"));

        TableColumn<Course, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(param -> new ButtonCellCourse("Delete"));

        TableColumn<Course, Void> participantsColumn = new TableColumn<>("Participants");
        participantsColumn.setCellFactory(param -> new ButtonCellCourse("Participants"));

        // Add the columns to the table view
        tableView.getColumns().addAll(nameColumn, roomColumn, editColumn, deleteColumn, participantsColumn);

        // Set the table data
        tableView.setItems(courseList);

        // TextField und Button erstellen
        TextField txtField_search = new TextField();
        txtField_search.setPromptText("Searching...");
        txtField_search.setPrefWidth(100);
        Button btn_addCourse = new Button("Add course");

        // Event-Handler für den das Search Field
        txtField_search.setOnKeyReleased(event -> {
            String text = txtField_search.getText();
            if (!text.isEmpty()) {
                // Filtern der Studentenliste basierend auf dem eingegebenen Text
                ObservableList<Course> filteredList = courseList.filtered(course ->
                        course.getSubject()
                                .toLowerCase()
                                .contains(text.toLowerCase()) ||
                                course.getRoom()
                                        .toLowerCase()
                                        .contains(text.toLowerCase())
                );

                // Setzen der gefilterten Liste als Datenquelle für die TableView
                tableView.setItems(filteredList);
            } else {
                // Wenn das Textfeld leer ist, wird die ursprüngliche studentList angezeigt
                tableView.setItems(courseList);
            }
        });

        // Event-Handler für Button
        btn_addCourse.setOnAction(event -> {
            AddCourseView addCourseView = new AddCourseView(courseList);
            addCourseView.start(new Stage());
        });

        // Erstellen der Scene und Hinzufügen zur Stage
        HBox hbox = new HBox();
        hbox.getChildren().addAll(txtField_search, btn_addCourse);
        hbox.setSpacing(20);
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(hbox, tableView);

        // Wrap the table view in a scroll pane
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        // Add the scroll pane to the content
        content.getChildren().add(scrollPane);

        return scrollPane;
    }

    private class ButtonCellCourse extends TableCell<Course, Void> {
        private final Button button;

        public ButtonCellCourse(String buttonText) {
            button = new Button(buttonText);
            button.setOnAction(event -> {
                Course course = getTableRow().getItem();
                if (course != null) {
                    if (buttonText.equals("Edit")) {
                        System.out.println("Edit: " + course.getSubject());
                        // Add your code for editing the course here
                    } else if (buttonText.equals("Delete")) {
                        System.out.println("Delete: " + course.getSubject());
                        // Add your code for deleting the course here
                    } else if (buttonText.equals("Participants")) {
                        System.out.println("Participants: " + course.getCourseId());
                        // Add your code for handling participants here
                        getParitcipants(course);
                    }
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(button);
            } else {
                setGraphic(null);
            }
        }
    }

    private void getParitcipants(Course course) {
        allocationList = FXCollections.observableArrayList();
        allocationList.add(new Allocation(1, 1, 1));
        allocationList.add(new Allocation(2, 1, 2));
        allocationList.add(new Allocation(3, 2, 2));

        ObservableList<Allocation> courseAllocation = allocationList.filtered(p -> p.getCourseFk() == course.getCourseId());
        if (courseAllocation.size() != 0) {
            ShowParticipants showParticipants = new ShowParticipants(allocationList, studentList, course);
            showParticipants.start(new Stage());
        } else {
            // Erstelle einen Alert vom Typ INFORMATION
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No participants");
            alert.setHeaderText(null);
            alert.setContentText("So far there are no students in this course");

            // Zeige den Alert und warte, bis der Benutzer ihn schließt
            alert.showAndWait();

            AddParticipants addParticipants = new AddParticipants(allocationList, studentList, course);
            addParticipants.start(new Stage());
        }
    }
}
