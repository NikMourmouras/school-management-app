import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


}
