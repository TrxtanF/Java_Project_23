package org.InputCheck;

import org.daoimpl.AllocationDaoImpl;
import org.entity.Allocation;

import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class AllocationCheck {
    private AllocationDaoImpl allocationDao = new AllocationDaoImpl();
    private String validationProblem = "";

    public String getValidationProblem(){
        return validationProblem;
    }

    /**
     * Checks if the user input is correct.
     * If it is not, it returns false and give a detailed validation Error.
     */
    public boolean insert(Allocation allocation) throws SQLException {
        //Check user input
        if(!check(allocation)) return false;

        //Insert allocation
        allocationDao.insert(allocation);

        return true;
    }

    /**
     * Checks if the user input is correct.
     * If it is not, it returns false and give a detailed validation Error.
     */
    public boolean deleteById(int id) throws SQLException {
        //Check user input
        if(id<1){
            validationProblem ="Die Id muss größer sein als 0";
            return false;
        }

        //Delete allocation
        allocationDao.deleteById(id);

        return true;
    }

    public boolean updateById(int id, Allocation allocation) throws SQLException {
        //Check user input
        if(!check(allocation)) return false;
        if(id < 1){
            validationProblem = "Die Id muss größer sein als 0.";
            return false;
        }

        //Update allocation
        allocationDao.updateById(id, allocation);

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
