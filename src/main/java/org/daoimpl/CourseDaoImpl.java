package org.daoimpl;

import org.dao.GenericDao;
import org.entity.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements GenericDao<Course> {
    private Connection connection;

    /**
     * Summary:
     * insert(Course course) gets an object from type course and insert the
     * attributes into the columns in the course table.
     */
    @Override
    public void insert(Course course) throws SQLException {
        //SQL-Query
        String query = "INSERT INTO course (subject, room_fk) VALUES (?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, course.getSubject());
        ps.setInt(2, course.getRoomFk());
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * getAll() returns a List<Course> of all the rows in the course table.
     * Every row is one object in the list.
     */
    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> list = new ArrayList<>();

        //SQL-Statement
        String query = "SELECT * FROM course";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();

        //Read Data
        while (resultSet.next()) {
            //Get Data from ResultSet
            int courseId = resultSet.getInt("course_id");
            String subject = resultSet.getString("subject");
            int roomFk = resultSet.getInt("room_fk");

            //Create Course object
            Course course = new Course(courseId, subject, roomFk);
            list.add(course);
        }

        //Free resources
        resultSet.close();
        ps.close();

        return list;
    }

    /**
     * Summary:
     * deleteById(int id) gets an id and delete the row where the primary key equals courseId.
     */
    @Override
    public void deleteById(int id) throws SQLException {
        //SQL-Query
        String query = "DELETE FROM course WHERE course_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * updateById(int id, Course course) gets an id and the updated course.
     * It replaces the row where the primary key equals the courseId with the new course.
     */
    @Override
    public void updateById(int id, Course course) throws SQLException {
        //SQL-Query
        String query = "UPDATE course SET subject = ?, room_fk = ? WHERE course_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, course.getSubject());
        ps.setInt(2, course.getRoomFk());
        ps.setInt(3, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Course student) {

    }

    @Override
    public Course getById(int id) {
        return null;
    }

    @Override
    public void update(Course student) {

    }

    @Override
    public void delete(Course student) {

    }
}
