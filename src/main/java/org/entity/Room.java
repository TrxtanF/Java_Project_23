package org.entity;

public class Room {

    private int roomid;
    private int room;

    public Room(int roomid, int room) {
        this.roomid = roomid;
        this.room = room;
    }

    public int getRoomid() {
        return roomid;
    }

    public int getRoom() {
        return room;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
