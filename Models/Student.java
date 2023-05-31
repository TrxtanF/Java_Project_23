package Models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int student_Id;
    private String name;
    private int employer_Id;
    private int javaKnowing;
    private Employer employer;
    private List<Allocation> allocations = new ArrayList<>();

    public Student(int student_Id, String name, int employer_Id) {
        this.student_Id = student_Id;
        this.name = name;
        this.employer_Id = employer_Id;
    }

    public int getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(int student_Id) {
        this.student_Id = student_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployer_Id() {
        return employer_Id;
    }

    public void setEmployer_Id(int employer_Id) {
        this.employer_Id = employer_Id;
    }

    public int getJavaKnowing() {
        return javaKnowing;
    }

    public void setJavaKnowing(int javaKnowing) {
        this.javaKnowing = javaKnowing;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Allocation> getAllocationList() {
        return allocations;
    }
}
