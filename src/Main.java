import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){

        DBUtils dbUtils = new DBUtils();
//        Connection connection = null;
//        try {
//            connection = dbUtils.geConnection(dbUtils.url, dbUtils.user, dbUtils.password);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(connection);


        Connection connection = DBUtils.getConnection();
//        StudentDAO studentDAO = new StudentDAO(connection);
//        SubjectDAO subjectDAO = new SubjectDAO(connection);

//        Student student = new Student("Maria", "Papadopoulou", "10-03-1991", "Female", "Athens");

//        Subject subject = new Subject("History");
//        boolean success = subjectDAO.addSubject(subject);
//
//
//
//        System.out.println(success ? "✅ Subject added!" : "❌ Failed to add subject.");

//        new AddSubjectForm();

//        new AddStudentForm();
        
//        new AssignSubjectToStudentForm();


//        new AddGradeForm();


    }

}
