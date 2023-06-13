package application.javafx;

public class Student {
    private String name;
    private String course;
    private String company;
    private int javaSkill;

    public Student(String name, String course, String company, int javaSkill) {
        this.name = name;
        this.course = course;
        this.company = company;
        this.javaSkill = javaSkill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public String getCompany() {
        return company;
    }

    public int getJavaSkill() {
        return javaSkill;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setJavaSkill(int javaSkill) {
        this.javaSkill = javaSkill;
    }
}

