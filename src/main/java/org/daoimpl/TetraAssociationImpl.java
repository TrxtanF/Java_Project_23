package org.daoimpl;

import org.dao.GenericDao;
import org.entity.TetraAssociation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TetraAssociationImpl implements GenericDao<TetraAssociation> {

    private Connection connection;

    @Override
    public void insert(TetraAssociation tetraAssociation) throws SQLException {
        // Implement database
    }

    @Override
    public List<TetraAssociation> getAll() throws SQLException {
        List<TetraAssociation> list = new ArrayList<>();

        // SQL-Query
        String query = "SELECT * FROM allocation al, student st, company com, course cou where st.company_fk = com.company_id and st.student_id = al.student_fk and al.course_fk = cou.course_id";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();

        // read data
        while (resultSet.next()) {
            // Get ResultSet data
            String name = resultSet.getString("name");
            String course = resultSet.getString("subject");
            String company = resultSet.getString("company_name");
            int javaSkill = resultSet.getInt("java_skills");

            // generete TetraAssociation object
            TetraAssociation tetraAssociation = new TetraAssociation(name, course, company, javaSkill);
            list.add(tetraAssociation);
        }

        // release resource
        resultSet.close();
        ps.close();

        return list;
    }

    @Override
    public void deleteById(int id) throws SQLException {
        // delete from database
    }

    @Override
    public void updateById(int id, TetraAssociation tetraAssociation) throws SQLException {
        // update database
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
