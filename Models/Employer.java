package Models;

import java.util.ArrayList;
import java.util.List;

public class Employer {
    private int employer_Id;
    private String company;
    private List<Student> students = new ArrayList<>();

    public Employer(int employer_Id, String company) {
        this.employer_Id = employer_Id;
        this.company = company;
    }

    public int getEmployer_Id() {
        return employer_Id;
    }

    public void setEmployer_Id(int employer_Id) {
        this.employer_Id = employer_Id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Student> getStudentList(){
        return students;
    }
}
