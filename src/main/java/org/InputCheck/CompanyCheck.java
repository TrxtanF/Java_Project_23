package org.InputCheck;

import org.daoimpl.CompanyDaoImpl;
import org.entity.Allocation;
import org.entity.Company;

import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class CompanyCheck {
    private CompanyDaoImpl companyDao = new CompanyDaoImpl();
    private String validationProblem ="";

    public String getValidationProblem(){
        return validationProblem;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean insert(Company company) throws SQLException {
        //Check user input
        if(!check(company)) return false;

        //Insert company
        companyDao.insert(company);

        return true;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean deleteById(int id) throws SQLException {
        //Check user input
        if(id < 1){
            validationProblem ="Die Id muss größer sein als 0.";
            return false;
        }

        //delete company
        companyDao.deleteById(id);

        return true;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean updateById(int id, Company company) throws SQLException {
        //Check user input
        if(!check(company)) return false;
        if(id < 1){
            validationProblem ="Die Id muss größer sein als 0.";
            return false;
        }

        //Update company
        companyDao.updateById(id, company);

        return true;
    }


    private boolean check(Company company){
        if (!company.getCompanyName().trim().matches("[a-zA-Z ]+")){
            validationProblem = "Der Name des Unternehmens darf nur aus Buchstaben bestehen.";
            return false;
        }
        return true;
    }
}
