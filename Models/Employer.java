package Models;

public class Employer {
    private int employer_Id;
    private String company;

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
}
