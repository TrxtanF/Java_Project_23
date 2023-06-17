package org.inputCheck;

import javafx.collections.ObservableList;
import org.daoimpl.StudentDaoImpl;
import org.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class StudentCheck {
    private Connection connection;
    private StudentDaoImpl studentDao;
    private String validationProblem ="";

    public StudentCheck(Connection connection){
        this.connection = connection;
        studentDao = new StudentDaoImpl(connection);
    }

    public String getValidationProblemDetails(){
        return validationProblem;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean insert(Student student){
        //Check the user input
        if(!check(student)) return false;

        //Insert student
        try {
            studentDao.insert(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public ObservableList getAll() {
        try {
            return studentDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean deleteById(int id){
        //Check user input
        if (id < 1) {
            validationProblem = "Die id muss größer als 0 sein.";
            return false;
        }

        //delete student
        try {
            studentDao.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean updateById(int id, Student student){
        //Check user Input
        if(!check(student)) return false;
        if (id < 1) {
            validationProblem = "Die id muss größer sein als 0.";
            return false;
        }

        //update student
        try {
            studentDao.updateById(id, student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private boolean check(Student student){
        //Check user input
        if(student.getName().length() >= 50){
            validationProblem = "Der Name darf maximal aus 50 Zeichen bestehen.";
            return false;
        }
        if (!student.getName().trim().matches("[a-zA-Z ]+")){
            validationProblem = "Der Name darf nur aus Buchstaben bestehen.";
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
