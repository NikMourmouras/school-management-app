public class Grade {

    private String grade;
    private Student student;
    private Subject subject;

    public Grade(String grade, Student student, Subject subject) {
        this.grade = grade;
        this.student = getStudent();
        this.subject = getSubject();
    }

    public Student getStudent() { return student; }
    public Subject getSubject() { return subject; }

}
