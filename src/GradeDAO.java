import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    private final Connection connection;

    public GradeDAO(Connection connection){
        this.connection = connection;
    }

    public boolean addGrade(Grade grade){
        String sql = "INSERT INTO grades (student_id, subject_id, grade) VALUES (?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, grade.getStudent().getId());
            statement.setInt(2, grade.getSubject().getId());
            statement.setDouble(3, grade.getGrade());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e){
            System.out.println("❌ Error adding grade: " + e.getMessage());
            return false;
        }

    }


    public List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();

        String sql = "SELECT g.grade, s.id AS subject_id, s.title " +
                "FROM grades g " +
                "JOIN subjects s ON g.subject_id = s.id " +
                "WHERE g.student_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double gradeValue = resultSet.getDouble("grade");
                int subjectId = resultSet.getInt("subject_id");
                String subjectTitle = resultSet.getString("title");

                Subject subject = new Subject(subjectId, subjectTitle);
                Grade grade = new Grade(gradeValue, null, subject); // student μπορεί να είναι null εδώ

                grades.add(grade);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching grades: " + e.getMessage());
        }

        return grades;
    }



}
