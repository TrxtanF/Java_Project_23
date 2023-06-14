package org.InputCheck;

import org.daoimpl.CourseDaoImpl;
import org.entity.Course;

import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class CourseCheck {
    private CourseDaoImpl courseDao = new CourseDaoImpl();
    private String validationProblem = "";

    public String getValidationProblem() {
        return validationProblem;
    }

    /**
     * Checks if the user input is correct.
     * If it is not, it returns false and give a detailed validation Error.
     */
    public boolean insert(Course course) throws SQLException {
        //Checks user input
        if (!check(course)) return false;

        // Insert course
        courseDao.insert(course);

        return true;
    }

    /**
     * deleteById(int id) test if the id is valid
     * If it is not, it returns false and give a detailed validation Error
     */
    public boolean deleteById(int id) throws SQLException {
        //Check user input
        if (id < 1) {
            validationProblem = "Die Id muss größer als 0 sein.";
            return false;
        }

        //Delete Course
        courseDao.deleteById(id);

        return true;
    }

    /**
     * Checks whether the user input is valid
     * If it is not, it returns false and give a detailed validation Error
     */
    public boolean updateById(int id, Course course) throws SQLException {
        //Check user Input
        if (!check(course)) return false;
        if (id < 1) {
            validationProblem = "Die Id muss größer als 0 sein.";
            return false;
        }

        //update Course
        courseDao.updateById(id, course);

        return true;
    }

    private boolean check(Course course) {
        //Check user input
        if (!course.getSubject().trim().matches(".*[a-zA-Z].*")) {
            validationProblem = "Die Kursbezeichnung muss mindestend aus Buchstaben bestehen und darf darüber hinaus maximal noch Zahlen beinhalten.";
            return false;
        }
        if (course.getRoomFk() < 1) {
            validationProblem = "Die RoomId muss größer 0 sein";
            return false;
        }


        return true;
    }
}
