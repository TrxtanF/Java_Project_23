package org.Testing;

import org.daoimpl.CourseDaoImpl;
import org.daoimpl.RoomDaoImpl;
import org.databaseutils.H2Utils;
import org.entity.Course;
import org.entity.Room;

import java.sql.SQLException;
import java.util.List;

public class CourseTest {
    public static void main(String[] args) throws SQLException {
        H2Utils db = new H2Utils();

        CourseDaoImpl courseDao = new CourseDaoImpl();
        courseDao.setConnection(db.getConnection());

        RoomDaoImpl roomDao = new RoomDaoImpl();
        roomDao.setConnection(db.getConnection());

        Room room = new Room(1, "200D");

        /////////

        if (courseDao.getAll().size() == 0) {
            System.out.println("The database is empty");
        }

        Course course = new Course(1, "Mathematik I", 1);
        courseDao.insert(course);
        if (courseDao.getAll().size() == 1) {
            List<Course> list = courseDao.getAll();
            Course other = list.get(0);
            boolean correct = true;
            if (!course.getSubject().equals(other.getSubject())) correct = false;
            if (course.getRoomFk() == other.getRoomFk()) correct = false;
            if (correct) {
                System.out.println("The Course was successfully inserted");
            } else {
                System.out.println("The insert failed");
            }
        }

        int id = 1;
        Course updatedCourse = new Course(1, "Theoretische Informatik II", 1);
        courseDao.updateById(id, updatedCourse);
        if (courseDao.getAll().size() == 1) {
            List<Course> list = courseDao.getAll();
            Course other = list.get(0);
            boolean correct = true;
            if (!updatedCourse.getSubject().equals(other.getSubject())) correct = false;
            if (updatedCourse.getRoomFk() == other.getRoomFk()) correct = false;
            if (correct) {
                System.out.println("The Course was successfully updated");
            } else {
                System.out.println("The update failed");
            }
        }

        courseDao.deleteById(id);
        if (courseDao.getAll().size() == 0) {
            System.out.println("The course was successfully deleted");
        } else {
            System.out.println("The delete failed");
        }
    }
}
