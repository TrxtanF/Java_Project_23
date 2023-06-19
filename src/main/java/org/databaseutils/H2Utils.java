package org.databaseutils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class H2Utils {
    private static String PROPERTIES_PATH = "application-properties.yaml";
    private static Connection connection;
    public H2Utils() {
        if (connection == null) {
            InputStream inputStream = H2Utils.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);

            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            String url = (String) data.get("host");
            System.out.println("Connecting database...");
            // load and register JDBC driver for MySQL Class.forName("com.mysql.jdbc.Driver");

            try {
                this.connection = DriverManager.getConnection(url);
                System.out.println("Database connected! =" + connection.isValid(0));
                System.out.println("Try insertion into database");

            } catch (SQLException e) {
                throw new IllegalStateException("Cannot connect the database!", e);

            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
            H2Utils test = new H2Utils();
    }

    public static void setPropertiesPath(String propertiesPath) {
        PROPERTIES_PATH = propertiesPath;
    }

    public static void setConnection(Connection connection) {
        H2Utils.connection = connection;
    }

    public static String getPropertiesPath() {
        return PROPERTIES_PATH;
    }

    public static Connection getConnection() {
        return connection;
    }
}
