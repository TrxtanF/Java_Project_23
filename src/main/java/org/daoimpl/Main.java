package org.daoimpl;

import org.dao.GenericDao;
import org.databaseutils.H2Utils;
import org.entity.Company;
import org.entity.Student;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        H2Utils tentativo = new H2Utils();

        GenericDao company = new CompanyDaoImpl();

        company.setConnection(tentativo.getConnection());


        System.out.println(company.getAll().size());
    }
}
