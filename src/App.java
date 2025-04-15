import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//
public class App {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/buvette";
        String user = "stof";
        String password = "bennasser";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MariaDB!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Connection failed!");
            e.printStackTrace();
        }
    }
}
