package org.Testing;

import org.daoimpl.CompanyDaoImpl;
import org.databaseutils.H2Utils;
import org.entity.Company;

import javax.net.ssl.CertPathTrustManagerParameters;
import java.sql.SQLException;
import java.util.List;

public class CompanyTest {
    public static void main(String[] args) throws SQLException {
        H2Utils db = new H2Utils();

        CompanyDaoImpl companyDao = new CompanyDaoImpl();
        companyDao.setConnection(db.getConnection());

        /////////

        if (companyDao.getAll().size() == 0) {
            System.out.println("The database is empty");
        }

        Company company = new Company(1, "ABB");
        companyDao.insert(company);
        if (companyDao.getAll().size() == 1) {
            List<Company> list = companyDao.getAll();
            Company other = list.get(0);
            boolean correct = true;
            if (!company.getCompanyName().equals(other.getCompanyName())) correct = false;
            if (correct) {
                System.out.println("The company was successfully inserted");
            } else {
                System.out.println("The insert failed");
            }
        }

        int id = 1;
        Company updatedCompany = new Company(1, "Cisco");
        companyDao.updateById(id, updatedCompany);
        if (companyDao.getAll().size() == 1) {
            List<Company> list = companyDao.getAll();
            Company other = list.get(0);
            boolean correct = true;
            if (!updatedCompany.getCompanyName().equals(other.getCompanyName())) correct = false;
            if (correct) {
                System.out.println("The company was successfully updated");
            } else {
                System.out.println("The update failed");
            }
        }

        companyDao.deleteById(id);
        if (companyDao.getAll().size() == 0) {
            System.out.println("The Company was successfully deleted");
        } else {
            System.out.println("The delete failed");
        }
    }
}
