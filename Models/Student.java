package Models;

public class Student {
    private int student_Id;
    private String name;
    private int employer_Id;

    public Student(int student_Id, String name, int employer_Id){
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
}
