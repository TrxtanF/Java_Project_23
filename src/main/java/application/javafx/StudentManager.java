package application.javafx;

import org.databaseutils.H2Utils;
import org.entity.TetraAssociation;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;


public class StudentManager {
    private List<StudentTow> studentTows;
    private Connection connection;


    public StudentManager() {
        try {
            //Get the connection from H2Utils
            connection = H2Utils.getConnection();

            //Check whether the connection has been correctly established
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connessione al database H2 stabilita con successo!");
            }

            //Other initialisation codes...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public StudentManager(String name, String course, String company, int javaSkill) {
        studentTows = new ArrayList<>();
        StudentTow studentTow = new StudentTow(name, course, company, javaSkill);
        studentTows.add(studentTow);
    }


    public void addStudent(StudentTow studentTow) {
        studentTows.add(studentTow);
    }

    public List<StudentTow> searchStudents(String searchTerm) {
        List<StudentTow> searchResults = new ArrayList<>();

        for (StudentTow studentTow : studentTows) {
            if (studentTow.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchResults.add(studentTow);
            }
        }

        return searchResults;
    }

    public List<StudentTow> getAllStudents() {
        return studentTows;
    }

    public void removeStudent(TetraAssociation student) {
        studentTows.remove(student);
    }

    public void updateStudent(TetraAssociation student) {/*
        try {
            //Create the update query
            String query = "UPDATE student SET name=?, company_fk=?, java_skills=? WHERE student_id=?";

            //Prepare the PreparedStatement declaration with parameters
            PreparedStatement statement = H2Utils.getConnection().prepareStatement(query);
            statement.setString(1, student.getName());
            statement.setInt(2, student.getCompanyId());
            statement.setInt(3, student.getJavaSkill());
            statement.setInt(4, student.getId());

            //Perform the update
            statement.executeUpdate();

            //Other management codes...
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }


}

