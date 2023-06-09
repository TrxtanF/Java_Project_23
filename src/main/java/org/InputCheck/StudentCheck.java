package org.InputCheck;

import org.daoimpl.StudentDaoImpl;
import org.entity.Student;

import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class StudentCheck {
    private StudentDaoImpl studentDao = new StudentDaoImpl();
    private String validationProblem ="";

    public String getValidationProblem(){
        return validationProblem;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean insert(Student student) throws SQLException {
        //Check the user input
        if(!check(student)) return false;

        //Insert student
        studentDao.insert(student);

        return true;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean deleteById(int id) throws SQLException {
        //Check user input
        if (id < 1) {
            validationProblem = "Die id muss größer als 0 sein.";
            return false;
        }

        //delete student
        studentDao.deleteById(id);

        return true;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean updateById(int id, Student student) throws SQLException {
        //Check user Input
        if(!check(student)) return false;
        if (id < 1) {
            validationProblem = "Die id muss größer sein als 0.";
            return false;
        }

        //update student
        studentDao.updateById(id, student);

        return true;
    }

    private boolean check(Student student){
        //Check user input
        if (!student.getName().trim().matches("[a-zA-Z ]+")){
            validationProblem = "Der Name des Studenten darf nur aus Buchstaben bestehen.";
            return false;
        }
        if (student.getJavaSkills() < 1 || student.getJavaSkills() > 10){
            validationProblem = "Die Java Kenntnisse müssen zwischen 1 und 10 liegen.";
            return false;
        }
        if (student.getCompanyFk() < 1) {
            validationProblem = "Die Unternehmens Id muss größer als 0 sein.";
            return false;
        }
        return true;
    }
}
