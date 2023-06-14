package org.InputCheck;

import org.daoimpl.RoomDaoImpl;
import org.entity.Room;

import java.sql.SQLException;

/**
 * This class checks the user input. If this is correct, the command is to be executed on the database.
 * If this is not correct, an error is returned.
 */
public class RoomCheck {
    private RoomDaoImpl roomDao = new RoomDaoImpl();
    private String validationProblem = "";

    public String getValidationProblem() {
        return validationProblem;
    }

    /**
     * Checks if the user input is correct.
     * If it is not, it returns false and give a detailed validation Error.
     */
    public boolean insert(Room room) throws SQLException {
        //Check user Input
        if(!check(room))return false;

        //Insert room
        roomDao.insert(room);

        return true;
    }

    /**
     * deleteById(int id) test if the id is valid
     * If it is not, it returns false and give a detailed validation Error
     */
    public boolean deleteById(int id) throws SQLException {
        //Check user input
        if(id < 1){
            validationProblem = "Die Id muss größer als 0 sein.";
            return false;
        }

        //Delete room
        roomDao.deleteById(id);
        return true;
    }

    /**
     * Checks whether the user input is valid
     * If it is not, it returns false and give a detailed validation Error
     */
    public boolean updateById(int id, Room room) throws SQLException {
        //Check user Input
        if(!check(room))return false;
        if(id<1){
            validationProblem = "Die Id muss größer als 0 sein.";
            return false;
        }

        //update Room
        roomDao.updateById(id, room);
        return true;
    }

    private boolean check(Room room) {
        if (room.getRoom().length() != 4) {
            validationProblem = "Die Raumangabe muss vier Zeichen lang sein.";
            return false;
        }
        if (!Character.toString(room.getRoom().charAt(3)).matches("^[A-E]+$")) {
            validationProblem = "Das vierte Zeichen der Raumbezeichung muss ein Gebäude an der DHBW sein (A, B, C, D, E).";
            return false;
        }
        if (!room.getRoom().substring(0, 3).trim().matches("^[0-9]+$")) {
            validationProblem = "Die ersten drei Zeichen müssen Zahlen sein, für Etage und Raumnummer.";
            return false;
        }
        return true;
    }
}
