package org.daoimpl;

import org.dao.GenericDao;
import org.entity.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompanyDaoImpl implements GenericDao <Company> {

    private Connection connection;


    @Override
    public void insert(Company company) throws SQLException {
        String companyName = company.getCompanyName();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO company (company_name) VALUES (?)");
        ps.setString(1, companyName);
        boolean resultSet = ps.execute();
        System.out.println(resultSet);
    }

    @Override
    public List<Company> getAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from company");
        ResultSet resultSet = ps.executeQuery();
        System.out.println(resultSet.toString());
        return null;
    }


    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
