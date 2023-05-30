package Models;

public class Student {
    private int Student_Id;
    private String name;
    private int Employer_Id;

    public Student(int Student_Id, String name, int Employer_Id){
        this.Student_Id = Student_Id;
        this.name = name;
        this.Employer_Id = Employer_Id;
    }

    public int getStudent_Id() {
        return Student_Id;
    }

    public void setStudent_Id(int student_Id) {
        Student_Id = student_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployer_Id() {
        return Employer_Id;
    }

    public void setEmployer_Id(int employer_Id) {
        Employer_Id = employer_Id;
    }
}
