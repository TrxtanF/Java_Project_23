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

    private static final String USER_NAME = "Sebastian";
    private static final String PASSWORD = "SUPERSICHER!!!";
    private Label lbName;
    private Label lbPasswort;
    private TextField tfName;
    private PasswordField passwordField;
    private Button btLogin;
    private GridPane gridPane;

    @Override
    public void start(Stage stage) throws IOException {
        lbName = new Label("Name:");
        lbPasswort = new Label("Passwort:");

        tfName = new TextField();
        passwordField = new PasswordField();

        buildLoginButton();
        buildGrid();

        Scene scene = new Scene(gridPane);
        stage.setTitle("JavaFX Login");
        stage.setScene(scene);
        stage.show();
    }

    private void buildGrid() {
        gridPane = new GridPane();
        gridPane.add(lbName, 0, 0);
        gridPane.add(tfName, 1, 0);
        gridPane.add(lbPasswort, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(btLogin, 0, 2, 2, 1);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
//        gridPane.setGridLinesVisible(true);
    }

    private void buildLoginButton() {
        btLogin = new Button("Login");
        btLogin.setMinWidth(150);
        btLogin.setOnAction(loginAction());
    }

    private EventHandler<ActionEvent> loginAction() {
        return actionEvent -> {
            String inputName = tfName.getText();
            String inputPassword = passwordField.getText();

            if (USER_NAME.equals(inputName) && PASSWORD.equals(inputPassword)) {
                new Alert(Alert.AlertType.INFORMATION, "Erfolgreich eingelogt :)").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Name/Passwort sind falsch").show();
            }
        };
    }

    public static void main(String[] args) {
        launch();
    }

    public void onHelloButtonClick(ActionEvent actionEvent) {
    }
}