package org.daoimpl;

import org.dao.GenericDao;
import org.databaseutils.H2Utils;
import org.entity.Company;
import org.entity.Course;
import org.entity.Student;
import org.entity.TetraAssociation;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        H2Utils tentativo = new H2Utils();
        GenericDao<Company> company = new CompanyDaoImpl(tentativo.getConnection());
        company.setConnection(tentativo.getConnection());

        GenericDao<Course> course = new CourseDaoImpl(tentativo.getConnection());
        course.setConnection(tentativo.getConnection());

        List<Course> secondlist = course.getAll();
        for(Course course1:secondlist){
            System.out.println(course1.getSubject());
            System.out.println(course1.getRoom());
        }

        List<Company> list = company.getAll();
        for(Company company1:list){
            System.out.println(company1.getCompanyName());
        }
    }
}


     /*

        GenericDao<TetraAssociation> tetraAssociation = new TetraAssociationImpl();



        tetraAssociation.setConnection(tentativo.getConnection());

        List<TetraAssociation> test = tetraAssociation.getAll();

        for(TetraAssociation t : test){
            System.out.println(t.toString());
        }

      System.out.println("Elements inserted: " + company.getAll().size());
    }*/

