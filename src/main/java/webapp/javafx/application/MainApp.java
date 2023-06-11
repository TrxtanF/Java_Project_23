package webapp.javafx.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management App");


        WebView webView = new WebView();
        webView.getEngine().loadContent("<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <link rel=\"icon\" type=\"image/x-icon\" href=\"/img/dhbw-logo.png\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "    <title>student management</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<nav class=\"navigation\">\n" +
                "    <a href=\"https://www.mannheim.dhbw.de/startseite\">\n" +
                "        <svg class=\"logo-img\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:cc=\"http://creativecommons.org/ns#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:sodipodi=\"http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd\" xmlns:inkscape=\"http://www.inkscape.org/namespaces/inkscape\" id=\"svg2\" version=\"1.1\" inkscape:version=\"0.91 r13725\" xml:space=\"preserve\" width=\"471.70001\" height=\"72.650002\" viewBox=\"0 0 471.70001 72.650002\" sodipodi:docname=\"DHBW_d_MA_rgb_WEB2_ohneZusatz_ohneAlles.svg\"><metadata id=\"metadata8\"><rdf:rdf><cc:work rdf:about=\"\"><dc:format>image/svg+xml</dc:format><dc:type rdf:resource=\"http://purl.org/dc/dcmitype/StillImage\"></dc:type><dc:title></dc:title></cc:work></rdf:rdf></metadata><defs id=\"defs6\"></defs><sodipodi:namedview pagecolor=\"#ffffff\" bordercolor=\"#666666\" borderopacity=\"1\" objecttolerance=\"10\" gridtolerance=\"10\" guidetolerance=\"10\" inkscape:pageopacity=\"0\" inkscape:pageshadow=\"2\" inkscape:window-width=\"640\" inkscape:window-height=\"480\" id=\"namedview4\" showgrid=\"false\" inkscape:zoom=\"0.29658229\" inkscape:cx=\"182.84184\" inkscape:cy=\"39.782671\" inkscape:window-x=\"0\" inkscape:window-y=\"0\" inkscape:window-maximized=\"0\" inkscape:current-layer=\"svg2\"/></svg>\n" +
                "    </a>\n" +
                "    <h1 class=\"nav-title\">Student Management</h1>\n" +
                "    <ul class=\"nav-links\">\n" +
                "        <li class=\"nav-link\"><a href=\"#\">Home</a></li>\n" +
                "        <li class=\"nav-link\"><a href=\"#\">Students</a></li>\n" +
                "        <li class=\"nav-link\"><a href=\"#\">Courses</a></li>\n" +
                "        <li class=\"nav-link\"><a href=\"#\">Grades</a></li>\n" +
                "    </ul>\n" +
                "</nav>\n" +
                "<h2>Welcome to Student Management App</h2>\n" +
                "<p>This is a JavaFX application.</p>\n" +
                "<script src=\"js/script.js\"></script>\n" +
                "</body>\n" +
                "</html>");

        // Creazione di un layout VBox per contenere il WebView
        VBox root = new VBox();
        root.getChildren().add(webView);

        // Creazione di una scena e impostazione della dimensione
        Scene scene = new Scene(root, 800, 600);

        // Impostazione della scena primaria e visualizzazione
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
