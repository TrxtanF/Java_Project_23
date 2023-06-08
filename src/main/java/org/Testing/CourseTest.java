package org.Testing;

import org.daoimpl.CourseDaoImpl;
import org.databaseutils.H2Utils;
import org.entity.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseTest {
    public static void main(String[] args) throws SQLException {
        H2Utils db = new H2Utils();

        CourseDaoImpl courseDao = new CourseDaoImpl();
        courseDao.setConnection(db.getConnection());

        /////////

        if(courseDao.getAll().size() == 0){
            System.out.println("The database is empty");
        }

        Course course = new Course(1, "Mathematik I", "C038");
        courseDao.insert(course);
        if(courseDao.getAll().size() == 1){
            List<Course> list = courseDao.getAll();
            Course other = list.get(0);
            boolean correct = true;
            if(!course.getSubject().equals(other.getSubject())) correct = false;
            if(!course.getRoom().equals(other.getRoom())) correct = false;
            if(correct){
                System.out.println("The Course was successfully inserted");
            }else{
                System.out.println("The insert failed");
            }
        }

        int id = 1;
        Course updatedCourse = new Course(1, "Theoretische Informatik II", "C038");
        courseDao.updateById(id, updatedCourse);
        if(courseDao.getAll().size() == 1){
            List<Course> list = courseDao.getAll();
            Course other = list.get(0);
            boolean correct = true;
            if(!updatedCourse.getSubject().equals(other.getSubject())) correct = false;
            if(!updatedCourse.getRoom().equals(other.getRoom())) correct = false;
            if(correct){
                System.out.println("The Course was successfully updated");
            }else{
                System.out.println("The update failed");
            }
        }

        courseDao.deleteById(id);
        if(courseDao.getAll().size() == 0){
            System.out.println("The course was successfully deleted");
        }else{
            System.out.println("The delete failed");
        }
    }
}
