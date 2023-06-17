package org.inputCheck;

import javafx.collections.ObservableList;
import org.daoimpl.AllocationDaoImpl;
import org.entity.Allocation;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class AllocationCheck {
    private Connection connection;
    private AllocationDaoImpl allocationDao;
    private String validationProblem = "";

    public AllocationCheck(Connection connection){
        this.connection = connection;
        allocationDao = new AllocationDaoImpl(connection);
    }

    public String getValidationProblemDetails(){
        return validationProblem;
    }

    /**
     * Checks if the user input is correct.
     * If it is not, it returns false and give a detailed validation Error.
     */
    public boolean insert(Allocation allocation){
        //Check user input
        if(!check(allocation)) return false;

        //Insert allocation
        try {
            allocationDao.insert(allocation);
        } catch (SQLException e) {
            validationProblem = "Data could not be inserted into the database";
        }

        return true;
    }

    public ObservableList getAll(){
        try {
            return allocationDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the user input is correct.
     * If it is not, it returns false and give a detailed validation Error.
     */
    public boolean deleteById(int id){
        //Check user input
        if(id<1){
            validationProblem ="Die Id muss größer sein als 0";
            return false;
        }

        //Delete allocation
        try {
            allocationDao.deleteById(id);
        } catch (SQLException e) {
            validationProblem ="Data could not be inserted into the database";
        }

        return true;
    }

    public boolean updateById(int id, Allocation allocation) {
        //Check user input
        if(!check(allocation)) return false;
        if(id < 1){
            validationProblem = "Die Id muss größer sein als 0.";
            return false;
        }

        //Update allocation
        try {
            allocationDao.updateById(id, allocation);
        } catch (SQLException e) {
            validationProblem ="Data could not be inserted into the database";
        }

        return true;
    }

    private boolean check(Allocation allocation){
        if(allocation.getStudentFk() < 1){
            validationProblem = "Die Studenten Id muss größer sein als 0.";
            return false;
        }
        if(allocation.getCourseFk() < 1){
            validationProblem = "Die Kurs Id muss größer sein als 0.";
            return false;
        }
        return true;
    }
}
