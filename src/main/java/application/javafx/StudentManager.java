package application.javafx;

import org.databaseutils.H2Utils;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;



public class StudentManager {
    private List<Student> students;
    private Connection connection;


    public StudentManager() {
        try {
            // Ottieni la connessione dallo H2Utils
            connection = H2Utils.getConnection();

            // Verifica se la connessione è stata stabilita correttamente
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connessione al database H2 stabilita con successo!");
            }

            // Altri codici di inizializzazione...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public StudentManager(String name, String course, String company, int javaSkill) {
        students = new ArrayList<>();
        Student student = new Student(name, course, company, javaSkill);
        students.add(student);
    }


    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> searchStudents(String searchTerm) {
        List<Student> searchResults = new ArrayList<>();

        for (Student student : students) {
            if (student.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchResults.add(student);
            }
        }

        return searchResults;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void updateStudent(Student student) {
        try {
            // Crea la query di aggiornamento
            String query = "UPDATE student SET name=?, company_fk=?, java_skills=? WHERE student_id=?";

            // Prepara la dichiarazione PreparedStatement con i parametri
            PreparedStatement statement = H2Utils.getConnection().prepareStatement(query);
            statement.setString(1, student.getName());
            statement.setInt(2, student.getCompanyId());
            statement.setInt(3, student.getJavaSkill());
            statement.setInt(4, student.getId());

            // Esegui l'aggiornamento
            statement.executeUpdate();

            // Altri codici di gestione...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

