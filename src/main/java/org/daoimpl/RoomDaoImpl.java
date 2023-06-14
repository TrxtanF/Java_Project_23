package org.daoimpl;

import org.dao.GenericDao;
import org.entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements GenericDao<Room> {
    private Connection connection;

    /**
     * Summary:
     * insert(Room room) gets an object from type room and insert the attributes
     * intor the columns in the room table
     */
    @Override
    public void insert(Room room) throws SQLException {
        //SQL-Query
        String query = "INSERT INTO room (room_name) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, room.getRoom());
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * getAll() returns a List<Room> of all the rows in the room table
     * Every row is one object in the list.
     */
    @Override
    public List<Room> getAll() throws SQLException {
        List<Room> list = new ArrayList<>();

        //SQL-Query
        String query = "SELECT * FROM room";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();

        //Read Data
        while(resultSet.next()){
            //Get Data from ResultSet
            int roomId = resultSet.getInt("room_id");
            String roomName = resultSet.getString("room_name");

            //Create room object
            Room room = new Room(roomId, roomName);
            list.add(room);
        }

        //Free resources
        resultSet.close();
        ps.close();

        return list;
    }

    /**
     * Summary:
     * deleteById(int id) gets an id and delete the row where the primary key equals id.
     */
    @Override
    public void deleteById(int id) throws SQLException {
        //SQL-Query
        String query = "DELETE FROM room WHERE room_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    /**
     * Summary:
     * updateById(int id, Room room) gets an id and the updated room.
     * It replaces the row where the primary key equals the roomId with the new room.
     */
    @Override
    public void updateById(int id, Room room) throws SQLException {
        //SQL-Query
        String query = "UPDATE room SET room_name = ? Where room_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, room.getRoom());
        ps.setInt(2, id);
        ps.executeUpdate();

        //Free resources
        ps.close();
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
