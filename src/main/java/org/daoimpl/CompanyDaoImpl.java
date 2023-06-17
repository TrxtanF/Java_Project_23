package org.daoimpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dao.GenericDao;
import org.entity.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements GenericDao<Company> {

    private Connection connection;

    public CompanyDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Summary:
     * insert(Company company) gets an object of typ company and insert the
     * attributes into the columns in the company table.
     */
    @Override
    public void insert(Company company) throws SQLException {
        //SQL-Query
        String query = "INSERT INTO company (company_name) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, company.getCompanyName());
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * getAll() returns a List<Company> of all the rows of the company table.
     * Every row is one Company object.
     */
    @Override
    public ObservableList<Company> getAll() throws SQLException {
        ObservableList<Company> list = FXCollections.observableArrayList();

        //SQL-query
        String query = "SELECT company_id, company_name FROM company";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();

        //Read Data
        while (resultSet.next()) {
            //Get Data from ResultSet
            int companyId = resultSet.getInt("company_id");
            String companyName = resultSet.getString("company_name");

            //Create Company object
            Company company = new Company(companyId, companyName);
            list.add(company);
        }

        //Free resources
        resultSet.close();
        ps.close();

        return list;
    }

    /**
     * Summary:
     * delete(int id) gets an id and delete the row with the primary key of the companyId.
     */
    @Override
    public void deleteById(int id) throws SQLException {
        //SQL-Query
        String query = "DELETE FROM company WHERE company_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * updateById(int id, Company company) gets an id and the updated company.
     * It replaces the row where the primary key equals the companyId with the new company.
     */
    @Override
    public void updateById(int id, Company company) throws SQLException {
        //SQL-Query
        String query = "UPDATE company SET company_name = ? WHERE company_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, company.getCompanyName());
        ps.setInt(2, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }


    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
