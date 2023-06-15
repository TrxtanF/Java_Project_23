package org.entity;

public class TetraAssociation {

    private String name;
    private String course;
    private String company;
    private int javaSkills;

    public TetraAssociation(String name, String course, String company, int javaSkills) {
        this.name = name;
        this.course = course;
        this.company = company;
        this.javaSkills = javaSkills;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getCompany() {
        return company;
    }

    public int getJavaSkills() {
        return javaSkills;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setJavaSkills(int javaSkills) {
        this.javaSkills = javaSkills;
    }

    public String toString(){
        String name = ((this.name == null) ? "N/A" : this.name);
        String course = ((this.course == null) ? "N/A" : this.course);
        String company = ((this.company == null) ? "N/A" : this.company);
        return "name: "+ name + " course: "+ course + " company: "+ company + javaSkills;
    }



}
