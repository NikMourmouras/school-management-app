import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    private final Connection connection;
    public SubjectDAO(Connection connection) {
        this.connection = connection;
    }


    public boolean addSubject (Subject subject) {
        String sql = "INSERT INTO subjects (title) VALUES (?)";


        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getTitle());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;


        } catch (SQLException e) {
            System.out.println("❌ Error adding subject: " + e.getMessage());
            return false;
        }
    }

    public List<Subject> getAllSubjects(){

        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT id, title FROM subjects";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
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

    public boolean deleteSubjectCompletely (int subjectID){

        String deleteGradesSQL = "DELETE FROM grades WHERE subject_id = ?";
        String deleteAssignmentSQL = "DELETE FROM student_subjects WHERE subject_id = ?";
        String deleteSubjectSQL = "DELETE FROM subjects WHERE id = ?";

        try (
                PreparedStatement deleteGrades = connection.prepareStatement(deleteGradesSQL);
                PreparedStatement deleteAssignment = connection.prepareStatement(deleteAssignmentSQL);
                PreparedStatement deleteSubject = connection.prepareStatement(deleteSubjectSQL);
                ) {
            deleteGrades.setInt(1, subjectID);
            deleteGrades.executeUpdate();

            deleteAssignment.setInt(1, subjectID);
            deleteAssignment.executeUpdate();

            deleteSubject.setInt(1, subjectID);
            int rowsAffected = deleteSubject.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting subject completely: " + e.getMessage());
            return false;
        }
    }

}
