package org.daoimpl;

import org.dao.GenericDao;
import org.databaseutils.H2Utils;
import org.entity.*;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        H2Utils tentativo = new H2Utils();
        GenericDao<Company> company = new CompanyDaoImpl(tentativo.getConnection());
        company.setConnection(tentativo.getConnection());

        GenericDao<Course> course = new CourseDaoImpl(tentativo.getConnection());
        course.setConnection(tentativo.getConnection());

        StudentDaoImpl studentDao = new StudentDaoImpl(tentativo.getConnection());
        AllocationDaoImpl allocationDaol = new AllocationDaoImpl(tentativo.getConnection());

        /*allocationDaol.insert(new Allocation(1,2,1));
        allocationDaol.insert(new Allocation(2,3,3));
        allocationDaol.insert(new Allocation(3,4,4));*/

        List<Allocation> allocationList = allocationDaol.getAll();
        for(Allocation allocation:allocationList){
            System.out.print(allocation.getAllocationId());
            System.out.println(allocation.getStudentFk());
        }


        /*List<Course> secondlist = course.getAll();
        for(Course course1:secondlist){
            System.out.println(course1.getCourseId());
        }


        /*List<Company> list = company.getAll();
        for(Company company1:list){
            System.out.println(company1.getCompanyId());
        }*/
        System.out.println("--");
        //studentDao.insert(new Student(1, "Tristan Finke", 7, 7));
        List<Student> studentList = studentDao.getAll();
        for(Student student:studentList){
            System.out.println(student.getStudentId());
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

