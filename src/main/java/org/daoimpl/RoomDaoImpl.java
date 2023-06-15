package org.daoimpl;

import org.dao.GenericDao;
import org.entity.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RoomDaoImpl implements GenericDao {
    private Connection connection;


    @Override
    public void insert(Object o) throws SQLException {

    }

    @Override
    public List getAll() throws SQLException {
        return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {

    }

    @Override
    public void updateById(int id, Object o) throws SQLException {

    }

    @Override
    public void setConnection(Connection connection) {

    }
}
