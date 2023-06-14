package org.entity;

public class Course {
    private int courseId;
    private String subject;
    private int roomFk;

    public Course(int courseId, String subject, int roomFk) {
        this.courseId = courseId;
        this.subject = subject;
        this.roomFk = roomFk;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getSubject() {
        return subject;
    }

    public int getRoomFk() {
        return roomFk;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setRoomFk(int roomFk) {
        this.roomFk = roomFk;
    }
}
