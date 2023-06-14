package org.entity;

public class Room {
    private int roomid;
    private String room;

    public Room(int roomid, String room) {
        this.roomid = roomid;
        this.room = room;
    }

    public int getRoomid() {
        return roomid;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
