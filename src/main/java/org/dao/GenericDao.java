package org.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao <T>{
    void insert(T t) throws SQLException;
    List<T> getAll() throws SQLException;
    void deleteById(int id) throws SQLException;
    void updateById(int id, T t) throws SQLException;

    void setConnection(Connection connection);

}
