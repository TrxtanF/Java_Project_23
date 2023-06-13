package application.javafx;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students;

    public StudentManager(String name, String course, String company, int javaSkill) {
        students = new ArrayList<>();
        Student student = new Student(name, course, company, javaSkill);
        students.add(student);
    }


    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> searchStudents(String searchTerm) {
        List<Student> searchResults = new ArrayList<>();

        for (Student student : students) {
            if (student.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchResults.add(student);
            }
        }

        return searchResults;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

}

