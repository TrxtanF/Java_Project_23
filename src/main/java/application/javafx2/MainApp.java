package application.javafx2;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.GridPane;
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
import org.inputCheck.AllocationCheck;
import org.inputCheck.CompanyCheck;
import org.inputCheck.CourseCheck;
import org.inputCheck.StudentCheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainApp extends Application {

    // Database and connection
    H2Utils database = new H2Utils();
    Connection connection = database.getConnection();

    // Methods to check user input and interact with the database
    StudentCheck studentCheck = new StudentCheck(connection);
    CompanyCheck companyCheck = new CompanyCheck(connection);
    CourseCheck courseCheck = new CourseCheck(connection);
    AllocationCheck allocationCheck = new AllocationCheck(connection);

    // Data lists
    ObservableList<Student> studentList = FXCollections.observableArrayList();
    ObservableList<Company> companyList = FXCollections.observableArrayList();
    ObservableList<Course> courseList = FXCollections.observableArrayList();
    ObservableList<Allocation> allocationList = FXCollections.observableArrayList();

    // Tab pane
    private TabPane tabPane;

    // Tables
    TableView<Student> studentTableView;
    TableView<Company> companyTableView;

    TableView<Course> courseTableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            companyList = companyCheck.getAll();
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
        studentTableView = new TableView<>();

        // Initialize list with data
        studentList = studentCheck.getAll();

        String[] columnNames = {"Name", "Java Skills", "Company"};
        String[] buttonNames = {"Edit", "Delete"};

        // Erstelle die Spalten
        CellValueFactoryCreator<Student> valueCreator = (celldata, columnName) -> {
            String value = "";
            switch (columnName) {
                case "Name":
                    value = String.valueOf(celldata.getValue().getName());
                    break;
                case "Java Skills":
                    value = String.valueOf(celldata.getValue().getJavaSkills());
                    break;
                case "Company":
                    int companyFk = (celldata.getValue().getCompanyFk());
                    for (Company company : companyList) {
                        if (company.getCompanyId() == companyFk) {
                            value = company.getCompanyName();
                            break;
                        }
                    }
                    break;
                default:
                    value = "";
            }
            return new SimpleStringProperty(value);
        };

        MainUtils.createColumn(columnNames, studentTableView, valueCreator);

        MainUtils.createButtonColumn(buttonNames, studentTableView, buttonName -> new ButtonCellStudent(buttonName));

        studentTableView.setItems(studentList);

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
                studentTableView.setItems(filteredList);
            } else {
                // Wenn das Textfeld leer ist, wird die ursprüngliche studentList angezeigt
                studentTableView.setItems(studentList);
            }
        });

        //Event-Handler für Button
        btn_addStudent.setOnAction(event -> {
            AddStudentView addStudentView = new AddStudentView(companyList, studentList, connection);
            addStudentView.start(new Stage());
        });


        // Erstellen der Scene und Hinzufügen zur Stage
        HBox hbox = new HBox();
        hbox.getChildren().addAll(txtField_search, btn_addStudent);
        hbox.setSpacing(20);
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(hbox, studentTableView);

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
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure to delete this student?");

                        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                        ButtonType cancelButton = new ButtonType("Yes", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(okButton, cancelButton);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == okButton) {
                            studentCheck.deleteById(student.getStudentId());
                            studentList = studentCheck.getAll();
                            studentTableView.setItems(studentList);
                        } else {
                            System.out.println("Clicked cancel");
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

    private ScrollPane createCompanyPage() {
        // Create the table view
        companyTableView = new TableView<>();

        String[] columnNames = {"Name"};
        String[] buttonNames = {"Edit", "Delete"};

        // Erstelle die Spalten
        CellValueFactoryCreator<Company> valueCreator = (celldata, columnName) -> {
            String value = String.valueOf(celldata.getValue().getCompanyName());
            return new SimpleStringProperty(value);
        };

        MainUtils.createColumn(columnNames, companyTableView, valueCreator);


        MainUtils.createButtonColumn(buttonNames, companyTableView, buttonName -> new ButtonCellCompany(buttonName));


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

                        // Pop Up elements
                        Label companyNameLabel = new Label("Company Name:");
                        TextField companyNameField = new TextField(company.getCompanyName());


                        // popup layout
                        GridPane gridPane = new GridPane();
                        gridPane.add(companyNameLabel, 0, 0);
                        gridPane.add(companyNameField, 1, 0);

                        // modify
                        Alert alert = new Alert(Alert.AlertType.NONE);
                        alert.setTitle("Edit Company");
                        alert.setHeaderText(null);
                        alert.getDialogPane().setContent(gridPane);

                        // confirm and cancel
                        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(confirmButton, cancelButton);

                        // Pop up Pops up
                        Optional<ButtonType> result = alert.showAndWait();


                        if (result.isPresent() && result.get() == confirmButton) {
                            // L'utente ha confermato, esegui l'azione di modifica qui
                            String newCompanyName = companyNameField.getText();
                            Company updatedComp = new Company(company.getCompanyId(), newCompanyName);
                            if (!companyCheck.updateById(company.getCompanyId(), updatedComp)) {
                                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                alert2.setTitle("Invalid Input");
                                alert2.setHeaderText(null);
                                alert2.setContentText(companyCheck.getValidationProblemDetails());
                                alert2.show();
                                System.out.println("Yes");
                            }
                            companyList = companyCheck.getAll();
                            companyTableView.setItems(companyList);

                        }
                    } else if (buttonText.equals("Delete")) {
                        System.out.println("Delete: " + company.getCompanyName());
                        // Fügen Sie hier den gewünschten Code für das Löschen hinzu
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure to delete this company? \nYou can't delete a company if there are students connected to");

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
        courseTableView = new TableView<>();

        //Initialize list with data
        courseList = courseCheck.getAll();
        allocationList = allocationCheck.getAll();

        // Create the company page content
        VBox content = new VBox();
        Label titleLabel = new Label("Course");
        content.getChildren().add(titleLabel);

        // Array x table
        String[] columnNames = {"Subject", "Room"};
        String[] buttonNames = {"Edit", "Delete", "Participants"};

        // Erstelle die Spalten
        CellValueFactoryCreator<Course> valueCreator = (celldata, columnName) -> {
            String value = "";
            switch (columnName) {
                case "Subject":
                    value = String.valueOf(celldata.getValue().getSubject());
                    break;
                case "Room":
                    value = String.valueOf(celldata.getValue().getRoom());
                    break;
                default:
                    value = "";
                    //String value = String.valueOf(celldata.getValue().getSubject());

            }
            return new SimpleStringProperty(value);
        };

        MainUtils.createColumn(columnNames, courseTableView, valueCreator);


        MainUtils.createButtonColumn(buttonNames, courseTableView, buttonName -> new ButtonCellCourse(buttonName));


        // Set the table data
        courseTableView.setItems(courseList);

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
                courseTableView.setItems(filteredList);
            } else {
                // Wenn das Textfeld leer ist, wird die ursprüngliche studentList angezeigt
                courseTableView.setItems(courseList);
            }
        });

        // Event-Handler für Button
        btn_addCourse.setOnAction(event -> {
            AddCourseView addCourseView = new AddCourseView(courseList, connection);
            addCourseView.start(new Stage());
        });

        // Erstellen der Scene und Hinzufügen zur Stage
        HBox hbox = new HBox();
        hbox.getChildren().addAll(txtField_search, btn_addCourse);
        hbox.setSpacing(20);
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(hbox, courseTableView);

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
        /*allocationList = FXCollections.observableArrayList();
        allocationList.add(new Allocation(1, 1, 1));
        allocationList.add(new Allocation(2, 1, 2));
        allocationList.add(new Allocation(3, 2, 2));*/
        allocationList = allocationCheck.getAll();
        ObservableList<Allocation> courseAllocation = allocationList.filtered(p -> p.getCourseFk() == course.getCourseId());
        if (courseAllocation.size() != 0) {
            ShowParticipants showParticipants = new ShowParticipants(allocationList, studentList, course, connection);
            showParticipants.start(new Stage());
        } else {
            // Erstelle einen Alert vom Typ INFORMATION
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No participants");
            alert.setHeaderText(null);
            alert.setContentText("So far there are no students in this course");

            // Zeige den Alert und warte, bis der Benutzer ihn schließt
            alert.showAndWait();

            AddParticipants addParticipants = new AddParticipants(allocationList, studentList, course, connection);
            addParticipants.start(new Stage());
        }
    }
}
