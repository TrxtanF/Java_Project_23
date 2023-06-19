package org.inputCheck;

import javafx.collections.ObservableList;
import org.daoimpl.CourseDaoImpl;
import org.entity.Course;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class CourseCheck {
    private Connection connection;
    private CourseDaoImpl courseDao;
    private String validationProblem = "";

    public CourseCheck(Connection connection) {
        this.connection = connection;
        courseDao = new CourseDaoImpl(connection);
    }

    public String getValidationProblemDetails() {
        return validationProblem;
    }

    /**
     * Checks if the user input is correct.
     * If it is not, it returns false and give a detailed validation Error.
     */
    public boolean insert(Course course) {
        //Checks user input
        if (!check(course)) return false;

        // Insert course
        try {
            courseDao.insert(course);
        } catch (SQLException e) {
            validationProblem = "Data could not be inserted into the database";
        }

        return true;
    }

    public ObservableList getAll() {
        try {
            return courseDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * deleteById(int id) test if the id is valid
     * If it is not, it returns false and give a detailed validation Error
     */
    public boolean deleteById(int id) {
        //Check user input
        if (id < 1) {
            validationProblem = "Die Id muss größer als 0 sein.";
            return false;
        }

        //Delete Course
        try {
            courseDao.deleteById(id);
        } catch (SQLException e) {
            validationProblem = "Data could not be inserted into the database";
        }

        return true;
    }

    /**
     * Checks whether the user input is valid
     * If it is not, it returns false and give a detailed validation Error
     */
    public boolean updateById(int id, Course course) {
        //Check user Input
        if (!check(course)) return false;
        if (id < 1) {
            validationProblem = "Die Id muss größer als 0 sein.";
            return false;
        }

        //update Course
        try {
            courseDao.updateById(id, course);
        } catch (SQLException e) {
            validationProblem = "Data could not be inserted into the database";
        }

        return true;
    }

    private boolean check(Course course) {
        //Check user input
        if (!course.getSubject().trim().matches(".*[a-zA-Z].*")) {
            validationProblem = "Die Kursbezeichnung muss mindestend aus Buchstaben bestehen und darf darüber hinaus maximal noch Zahlen beinhalten.";
            return false;
        }
        if (course.getRoom().length() != 4) {
            validationProblem = "Die Raumangabe muss 3 Zahlen lang sein und es muss ein Gebäude angegeben werden.";
            return false;
        }
        if (!Character.toString(course.getRoom().charAt(3)).matches("^[A-E]+$")) {
            validationProblem = "Das vierte Zeichen der Raumbezeichung muss ein Gebäude an der DHBW sein (A, B, C, D, E).";
            return false;
        }
        if (!course.getRoom().substring(0, 3).trim().matches("^[0-9]+$")) {
            validationProblem = "Die ersten drei Zeichen müssen Zahlen sein, für Etage und Raumnummer.";
            return false;
        }
        return true;
    }
}
