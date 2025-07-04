import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection getConnection(){

        String url = "jdbc:mysql://localhost:3306/school_db?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect: " + e.getMessage());
            return null;
        }

    }


}
