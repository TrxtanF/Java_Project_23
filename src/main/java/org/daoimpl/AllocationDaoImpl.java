package org.daoimpl;

import org.dao.GenericDao;
import org.entity.Allocation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllocationDaoImpl implements GenericDao<Allocation> {
    private Connection connection;

    @Override
    public void insert(Allocation allocation) throws SQLException {
        //SQL-Query
        String query = "INSERT INTO allocation (student_fk, course_fk) VALUES (?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, allocation.getStudentFk());
        ps.setInt(2, allocation.getCourseFk());
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * getAll() returns a List<Allocation> of all the rows in the allocation table.
     * Every row is one object in the list.
     */
    @Override
    public List<Allocation> getAll() throws SQLException {
        List<Allocation> list = new ArrayList<>();

        //SQL-Query
        String query = "SELECT * FROM allocation";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();

        //Read Data
        while (resultSet.next()) {
            //Get Data from ResultSet
            int allocationId = resultSet.getInt("allocation_id");
            int studentFk = resultSet.getInt("student_fk");
            int courseFk = resultSet.getInt("course_fk");

            //Create Allocation object
            Allocation allocation = new Allocation(allocationId, studentFk, courseFk);
            list.add(allocation);
        }

        //Free resources
        resultSet.close();
        ps.close();

        return list;
    }

    /**
     * Summary:
     * deleteById(int id) gets an id and delete the row where the primary key equals allocationId.
     */
    @Override
    public void deleteById(int id) throws SQLException {
        //SQL-Query
        String query = "DELETE FROM allocation WHERE allocation_Id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * updateById(int id, Allocation allocation) gets and id and the updated allocation.
     * It replaces the row where the primary key equals the allocationId with the new allocation.
     * In this case only the courseId has to be changed.
     */
    @Override
    public void updateById(int id, Allocation allocation) throws SQLException {
        //SQL-Query
        String query = "UPDATE allocation SET student_fk = ?, course_fk = ? WHERE allocation_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, allocation.getStudentFk());
        ps.setInt(2, allocation.getCourseFk());
        ps.setInt(3, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
