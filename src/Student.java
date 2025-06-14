import java.util.ArrayList;

public class Student {

    private String name;
    private String surname;
    private String dateOfBirth;
    private String gender;
    private String address;

    private ArrayList<Subject> subjects;
    private ArrayList<Grade> grades;

    public Student(String name, String surname, String dateOfBirth, String gender, String address) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;


    }



}
