package org.daoimpl;

import org.dao.GenericDao;
import org.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements GenericDao<Student> {
    private Connection connection;

    /**
     * Summary:
     * insert(Student student) gets an object from type student and insert the
     * attributes into the columns in the student table.
     */
    @Override
    public void insert(Student student) throws SQLException {
        //SQL-Query
        String query = "INSERT INTO student (name, java_skills, company_fk) VALUES (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.setInt(2, student.getJavaSkills());
        ps.setInt(3, student.getCompanyFk());
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * getAll() returns a List<Student> of all rows in the student table.
     * Every row is one object in the list.
     */
    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> list = new ArrayList<>();

        //SQL-Query
        String query = "SELECT * FROM student";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();

        //Read Data
        while (resultSet.next()) {
            //Get Data from ResultSet
            int studentId = resultSet.getInt("student_id");
            String name = resultSet.getString("name");
            int javaSkills = resultSet.getInt("java_skills");
            int companyFk = resultSet.getInt("company_fk");

            //Create student Object
            Student student = new Student(studentId, name, javaSkills, companyFk);
            list.add(student);
        }

        //Free resources
        resultSet.close();
        ps.close();

        return list;
    }

    /**
     * Summary:
     * delete(int id) gets an id and delete the row with the primary key of the studentId.
     */
    @Override
    public void deleteById(int id) throws SQLException {
        //SQL-Query
        String query = "DELETE FROM student WHERE student_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * updateById(int id, Student student) gets an id and the updated student.
     * It replaces the row where the primary key equals the studentId with the new student.
     */
    @Override
    public void updateById(int id, Student student) throws SQLException {
        //SQL-Query
        String query = "UPDATE student SET name = ?, java_skills = ?, company_fk = ? WHERE student_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.setInt(2, student.getJavaSkills());
        ps.setInt(3, student.getCompanyFk());
        ps.setInt(4, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
