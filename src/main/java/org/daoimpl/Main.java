package org.daoimpl;

import org.dao.GenericDao;
import org.databaseutils.H2Utils;
import org.entity.Company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        H2Utils tentativo = new H2Utils();

        GenericDao<Company> company = new CompanyDaoImpl();

        company.setConnection(tentativo.getConnection());


        System.out.println("Elements inserted: " + company.getAll().size());
    }
}
