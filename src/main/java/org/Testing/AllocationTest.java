package org.Testing;

import org.daoimpl.AllocationDaoImpl;
import org.daoimpl.CompanyDaoImpl;
import org.daoimpl.CourseDaoImpl;
import org.daoimpl.StudentDaoImpl;
import org.databaseutils.H2Utils;
import org.entity.Allocation;
import org.entity.Company;
import org.entity.Course;
import org.entity.Student;

import java.sql.SQLException;
import java.util.List;

public class AllocationTest {
    public static void main(String[] args) throws SQLException {
        H2Utils db = new H2Utils();

        CompanyDaoImpl companyDao = new CompanyDaoImpl();
        companyDao.setConnection(db.getConnection());

        StudentDaoImpl studentDao = new StudentDaoImpl();
        studentDao.setConnection(db.getConnection());

        CourseDaoImpl courseDao = new CourseDaoImpl();
        courseDao.setConnection(db.getConnection());

        Company company = new Company(1, "ABB");
        Student student = new Student(1, "Tristan Finke", 7, 1);
        Course course = new Course(1, "Mathematik I", "C038");

        companyDao.insert(company);
        studentDao.insert(student);
        courseDao.insert(course);

        /////////

        AllocationDaoImpl allocationDao = new AllocationDaoImpl();
        allocationDao.setConnection(db.getConnection());

        /////////

        if (allocationDao.getAll().size() == 0) {
            System.out.println("The database is empty");
        }

        Allocation allocation = new Allocation(1, 1, 1);
        allocationDao.insert(allocation);
        if (allocationDao.getAll().size() == 1) {
            List<Allocation> list = allocationDao.getAll();
            Allocation other = list.get(0);
            boolean correct = true;
            if (allocation.getStudentFk() != other.getStudentFk()) correct = false;
            if (allocation.getCourseFk() != other.getCourseFk()) correct = false;
            if (correct) {
                System.out.println("The Allocation was successfully inserted");
            } else {
                System.out.println("The insert failed");
            }
        }

        int id = 1;
        Student updatedStudent = new Student(2, "Gianluca Battisti", 7, 1);
        Course updatedCourse = new Course(2, "Java", "C189");
        studentDao.insert(updatedStudent);
        courseDao.insert(updatedCourse);
        Allocation updatedAllocation = new Allocation(2, 2, 2);
        allocationDao.updateById(id, updatedAllocation);
        if(allocationDao.getAll().size() == 1){
            List<Allocation> list = allocationDao.getAll();
            Allocation other = list.get(0);
            boolean correct = true;
            if(updatedAllocation.getStudentFk() != other.getStudentFk()) correct = false;
            if(updatedAllocation.getCourseFk() != other.getCourseFk()) correct = false;
            if(correct){
                System.out.println("The Allocation was successfully updated");
            }else{
                System.out.println("The update failed");
            }
        }

        allocationDao.deleteById(id);
        if(allocationDao.getAll().size() == 0){
            System.out.println("The allocation was successfully deleted");
        }else{
            System.out.println("The delete failed");
        }
    }
}
