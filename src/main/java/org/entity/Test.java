package org.entity;

import org.dao.GenericDao;

import java.sql.Connection;
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
    public void setConnection(Connection connection) {

    }
}
