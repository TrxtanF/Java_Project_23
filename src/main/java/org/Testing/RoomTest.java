package org.Testing;

import org.daoimpl.RoomDaoImpl;
import org.databaseutils.H2Utils;
import org.entity.Room;

import java.sql.SQLException;
import java.util.List;

public class RoomTest {
    public static void main(String[] args) throws SQLException {
        H2Utils db = new H2Utils();

        RoomDaoImpl roomDao = new RoomDaoImpl();
        roomDao.setConnection(db.getConnection());

        /////////

        if (roomDao.getAll().size() == 0) {
            System.out.println("The database is empty");
        }

        Room room = new Room(1, "234D");
        roomDao.insert(room);
        if (roomDao.getAll().size() == 1) {
            List<Room> list = roomDao.getAll();
            Room other = list.get(0);
            boolean correct = true;
            if (!room.getRoom().equals(other.getRoom())) correct = false;
            if (correct) {
                System.out.println("The room was successfully inserted");
            } else {
                System.out.println("The insert failed");
            }
        }

        int id = 1;
        Room updatedRoom = new Room(1, "111E");
        roomDao.updateById(id, updatedRoom);
        if (roomDao.getAll().size() == 1) {
            List<Room> list = roomDao.getAll();
            Room other = list.get(0);
            boolean correct = true;
            if (!updatedRoom.getRoom().equals(other.getRoom())) correct = false;
            if (correct) {
                System.out.println("The room was successfully updated");
            } else {
                System.out.println("The update failed");
            }
        }

        roomDao.deleteById(id);
        if (roomDao.getAll().size() == 0) {
            System.out.println("The room was successfully deleted");
        } else {
            System.out.println("The delete failed");
        }
    }
}
