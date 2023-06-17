package org.entity;

public class Course {
    private int courseId;
    private String subject;
    private String room;

    public Course(int courseId, String subject, String room) {
        this.courseId = courseId;
        this.subject = subject;
        this.room = room;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getSubject() {
        return subject;
    }

    public String getRoom() {
        return room;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
