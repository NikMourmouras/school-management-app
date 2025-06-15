public class Grade {

    private int id;
    private double grade;
    private Student student;
    private Subject subject;

    public Grade(double grade, Student student, Subject subject) {
        this.grade = grade;
        this.student = student;
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public Subject getSubject() {
        return subject;
    }

    public double getGrade() {
        return grade;
    }
}
