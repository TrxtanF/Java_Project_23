package org.dao;
import org.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IStudent {
    //The I in front of Student stands for Interface

    void insert(Student student) throws SQLException;
    List<Student> getAll() throws SQLException;
    void deleteById(int studentId) throws SQLException;
    void updateById(int studentId, Student student) throws SQLException;

    void setConnection(Connection connection);
}
