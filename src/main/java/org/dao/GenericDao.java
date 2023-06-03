package org.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao <T>{
    void insert(T t);

    List<T> getAll() throws SQLException;

    void setConnection(Connection connection);

}
