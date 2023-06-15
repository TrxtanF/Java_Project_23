package org.entity;

import org.dao.GenericDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Test implements GenericDao <Company> {


    @Override
    public void insert(Company pippo) {

    }

    @Override
    public List<Company> getAll() {
        return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {

    }

    @Override
    public void updateById(int id, Company company) throws SQLException {

    }

    @Override
    public void setConnection(Connection connection) {

    }
}
