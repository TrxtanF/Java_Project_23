package Models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int course_Id;
    private String subject;
    private String room;
    private List<Allocation> allocations = new ArrayList<>();

    public Course(int course_Id, String subject, String room) {
        this.course_Id = course_Id;
        this.subject = subject;
        this.room = room;
    }

    public int getCourse_Id() {
        return course_Id;
    }

    public void setCourse_Id(int course_Id) {
        this.course_Id = course_Id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<Allocation> getAllocationList(){
        return allocations;
    }
}
