package org.Testing;

import org.dao.GenericDao;
import org.daoimpl.CompanyDaoImpl;
import org.daoimpl.StudentDaoImpl;
import org.databaseutils.H2Utils;
import org.entity.Company;
import org.entity.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentTest {
    public static void main(String[] args) throws SQLException {
        H2Utils db = new H2Utils();

        CompanyDaoImpl companyDao = new CompanyDaoImpl();
        companyDao.setConnection(db.getConnection());

        StudentDaoImpl studentDao = new StudentDaoImpl();
        studentDao.setConnection(db.getConnection());

        companyDao.insert(new Company(1, "ABB"));

        /////////

        if (studentDao.getAll().size() == 0) {
            System.out.println("The database is empty");
        }

        Student student = new Student(1, "Tristan Finke", 7, 1);
        studentDao.insert(student);
        if (studentDao.getAll().size() == 1) {
            List<Student> list = studentDao.getAll();
            Student other = list.get(0);
            boolean correct = true;
            if (!student.getName().equals(other.getName())) correct = false;
            if (student.getJavaSkills() != other.getJavaSkills()) correct = false;
            if (student.getCompanyFk() != other.getCompanyFk()) correct = false;
            if(correct){
                System.out.println("The student was successfully inserted");
            }else{
                System.out.println("The insert failed");
            }
        }

        int id = 1;
        Student updatedStudent = new Student(id, "Tristan Finke", 5, 1);
        studentDao.updateById(id, updatedStudent);
        if (studentDao.getAll().size() == 1) {
            List<Student> list = studentDao.getAll();
            Student other = list.get(0);
            boolean correct = true;
            if (!updatedStudent.getName().equals(other.getName())) correct = false;
            if (updatedStudent.getJavaSkills() != other.getJavaSkills()) correct = false;
            if (updatedStudent.getCompanyFk() != other.getCompanyFk()) correct = false;
            if(correct){
                System.out.println("The student was successfully updated");
            }else{
                System.out.println("The update failed");
            }
        }

        studentDao.deleteById(id);
        if(studentDao.getAll().size() == 0){
            System.out.println("The student was successfully deleted");
        }else{
            System.out.println("The delete failed");
        }
    }
}
