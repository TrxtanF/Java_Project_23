package org.entity;

public class Allocation {
    private int allocationId;
    private int studentFk;
    private int courseFk;

    public Allocation(int allocationId, int studentFk, int courseFk) {
        this.allocationId = allocationId;
        this.studentFk = studentFk;
        this.courseFk = courseFk;
    }

    public int getAllocationId() {
        return allocationId;
    }

    public int getStudentFk() {
        return studentFk;
    }

    public int getCourseFk() {
        return courseFk;
    }

    public void setStudentFk(int studentFk) {
        this.studentFk = studentFk;
    }

    public void setCourseFk(int courseFk) {
        this.courseFk = courseFk;
    }
}
