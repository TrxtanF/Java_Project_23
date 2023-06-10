package org.entity;

public class Student {
    private int studentId;
    private String name;
    private int javaSkills;
    private int companyFk;


    public Student(int studentId, String name, int javaSkills, int companyFk) {
        this.studentId = studentId;
        this.name = name;
        this.javaSkills = javaSkills;
        this.companyFk = companyFk;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getCompanyFk() {
        return companyFk;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyFk(int companyFk) {
        this.companyFk = companyFk;
    }

    public void setJavaSkills(int javaSkills){
        this.javaSkills = javaSkills;
    }

    public int getJavaSkills(){
        return javaSkills;
    }
}
