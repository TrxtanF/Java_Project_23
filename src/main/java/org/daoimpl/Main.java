package org.daoimpl;

import org.dao.GenericDao;
import org.databaseutils.H2Utils;
import org.entity.Company;
import org.entity.TetraAssociation;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        H2Utils tentativo = new H2Utils();

        GenericDao<Company> company = new CompanyDaoImpl();

        GenericDao<TetraAssociation> tetraAssociation = new TetraAssociationImpl();

        company.setConnection(tentativo.getConnection());

        tetraAssociation.setConnection(tentativo.getConnection());

        List<TetraAssociation> test = tetraAssociation.getAll();

        for(TetraAssociation t : test){
            System.out.println(t.toString());
        }


        System.out.println("Elements inserted: " + company.getAll().size());
    }
}
