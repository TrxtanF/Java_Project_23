package org.inputCheck;

import javafx.collections.ObservableList;
import org.daoimpl.CompanyDaoImpl;
import org.entity.Company;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class CompanyCheck {
    private Connection connection;
    private CompanyDaoImpl companyDao;
    private String validationProblem = "";

    public CompanyCheck(Connection connection) {
        this.connection = connection;
        companyDao = new CompanyDaoImpl(connection);
    }

    public String getValidationProblemDetails() {
        return validationProblem;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean insert(Company company) {
        //Check user input
        if (!check(company)) return false;

        //Insert company
        try {
            companyDao.insert(company);
        } catch (SQLException e) {
            validationProblem = "Data could not be inserted into the database";
        }

        return true;
    }

    public ObservableList getAll() {
        try {
            return companyDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean deleteById(int id) {
        //Check user input
        if (id < 1) {
            validationProblem = "Die Id muss größer sein als 0.";
            return false;
        }

        //delete company
        try {
            companyDao.deleteById(id);
        } catch (SQLException e) {
            validationProblem = "Data could not be inserted into the database";
        }

        return true;
    }

    /**
     * Checks whether the user input is valid.
     * If it is not it returns false and give a detailed Validation Error.
     */
    public boolean updateById(int id, Company company) {
        //Check user input
        if (!check(company)) return false;
        if (id < 1) {
            validationProblem = "Die Id muss größer sein als 0.";
            return false;
        }

        //Update company
        try {
            companyDao.updateById(id, company);
        } catch (SQLException e) {
            validationProblem = "Data could not be inserted into the database";
        }

        return true;
    }


    private boolean check(Company company) {
        if (!company.getCompanyName().trim().matches("[a-zA-Z\\s]+")) {
            validationProblem = "Der Name des Unternehmens darf nur aus Buchstaben bestehen.";
            return false;
        }
        return true;
    }
}
