import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentSubjectDAO {

    private final Connection connection;

    public StudentSubjectDAO(Connection connection){
        this.connection = connection;
    }

    public boolean assignSubjectToStudent(Student student, Subject subject){
        String sql = "INSERT INTO student_subjects (student_id, subject_id) VALUES (?,?)";



        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, student.getId());
            statement.setInt(2, subject.getId());


            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e){
            System.out.println("❌ Error assign subject to student: " + e.getMessage());
            return false;
        }

    }



    public List<Subject> getSubjectsByStudentId(int studentId){
        String sql = "SELECT s.id, s.title \n" +
                "FROM subjects s\n" +
                "JOIN student_subjects ss ON s.id = ss.subject_id\n" +
                "WHERE ss.student_id = ?\n";

        List<Subject> subjects = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, studentId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");

                subjects.add(new Subject(id, title));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching subjects: " + e.getMessage());
        }

        return subjects;
    }

    public List<Student> getStudentsBySubjectId(int subjectId){
        String sql = "SELECT s.id, s.name, s.surname, s.date_of_birth \n" +
                "FROM students s\n" +
                "JOIN student_subjects ss ON s.id = ss.student_id\n" +
                "WHERE ss.subject_id = ?\n";

        List<Student> students = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, subjectId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String dateOfBirth = resultSet.getString("date_of_birth");


                students.add(new Student(id, name, surname, dateOfBirth));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching subjects: " + e.getMessage());
        }
        return students;
    }

}
