package Models;

import java.util.ArrayList;
import java.util.List;

public class Allocation {
    private int allocation_Id;
    private int student_Id;
    private int course_Id;
    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public Allocation(int allocation_Id, int student_Id, int course_Id) {
        this.allocation_Id = allocation_Id;
        this.student_Id = student_Id;
        this.course_Id = course_Id;
    }

    public int getAllocation_Id() {
        return allocation_Id;
    }

    public void setAllocation_Id(int allocation_Id) {
        this.allocation_Id = allocation_Id;
    }

    public int getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(int student_Id) {
        this.student_Id = student_Id;
    }

    public int getCourse_Id() {
        return course_Id;
    }

    public void setCourse_Id(int course_Id) {
        this.course_Id = course_Id;
    }

    public List<Student> getStudentList() {
        return students;
    }

    public List<Course> getCourseList(){
        return courses;
    }
}
