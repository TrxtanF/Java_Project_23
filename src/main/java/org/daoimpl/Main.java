package org.daoimpl;

import org.dao.GenericDao;
import org.dao.IStudent;
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

        company.getAll();
        company.insert(new Company(1, "ABB"));
        company.getAll();

        //////////////////
        System.out.println("-------------");

        StudentDaoImpl studentDao = new StudentDaoImpl();
        studentDao.setConnection(tentativo.getConnection());

        studentDao.insert(new Student(1, "Tristan Finke", 7, 1));
        studentDao.insert(new Student(2, "Gianluca Battisti", 9, 1));
        List<Student> list = studentDao.getAll();

        for (Student student : list) {
            System.out.println(
                    "Id: " + student.getStudentsId() +
                            "\nName: " + student.getName() +
                            "\nJava Skills: " + student.getJavaSkills() +
                            "\nEmployer Id: " + student.getCompanyFk() +
                            "\n-----"
            );
        }

        //studentDao.deleteById(1);
        studentDao.updateById(1, new Student(1, "Tristan Finke", 5, 1));

        List<Student> list2 = studentDao.getAll();
        System.out.println("List after update Java Skills from 7 to 5 where Id = 1: \n");
        for (Student student : list2) {
            System.out.println(
                    "Id: " + student.getStudentsId() +
                            "\nName: " + student.getName() +
                            "\nJava Skills: " + student.getJavaSkills() +
                            "\nEmployer Id: " + student.getCompanyFk() +
                            "\n-----"
            );
        }
    }
}
