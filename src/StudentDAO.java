import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private final Connection connection;

    public StudentDAO(Connection connection){
        this.connection = connection;
    }

    public boolean addStudent(Student student){
        String sql = "INSERT INTO students (name, surname, date_of_birth, gender, address) VALUES (?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setString(3, student.getDateOfBirth());
            statement.setString(4, student.getGender());
            statement.setString(5, student.getAddress());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e){
            System.out.println("❌ Error adding student: " + e.getMessage());
            return false;
        }

    }

    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, name, surname FROM students";

        try (PreparedStatement statement = connection.prepareStatement(sql) ){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                students.add(new Student(id, name, surname));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching students: " + e.getMessage());
        }

        return students;
    }

    public boolean deleteStudentCompletely(int studentId) {
        String deleteGradesSQL = "DELETE FROM grades WHERE student_id = ?";
        String deleteAssignmentsSQL = "DELETE FROM student_subjects WHERE student_id = ?";
        String deleteStudentSQL = "DELETE FROM students WHERE id = ?";

        try (
                PreparedStatement deleteGrades = connection.prepareStatement(deleteGradesSQL);
                PreparedStatement deleteAssignments = connection.prepareStatement(deleteAssignmentsSQL);
                PreparedStatement deleteStudent = connection.prepareStatement(deleteStudentSQL)
        ) {

            deleteGrades.setInt(1, studentId);
            deleteGrades.executeUpdate();

            deleteAssignments.setInt(1, studentId);
            deleteAssignments.executeUpdate();

            deleteStudent.setInt(1, studentId);
            int rowsAffected = deleteStudent.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting student completely: " + e.getMessage());
            return false;
        }
    }

}
