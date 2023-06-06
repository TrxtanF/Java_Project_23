package org.entity;

public class Student {
    private int studentsId;
    private String name;
    private int companyFk;

    public Student(int studentsId, String name, int companyFk) {
        this.studentsId = studentsId;
        this.name = name;
        this.companyFk = companyFk;
    }

    public int getStudentsId() {
        return studentsId;
    }

    public String getName() {
        return name;
    }

    public int getCompanyFk() {
        return companyFk;
    }

    public void setStudentsId(int studentsId) {
        this.studentsId = studentsId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyFk(int companyFk) {
        this.companyFk = companyFk;
    }
}
