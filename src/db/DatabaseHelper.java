package db;


import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:mariadb://localhost:3306/buvette";
    private static final String DB_USER = "stof"; // Change to your MariaDB username
    private static final String DB_PASS = "bennasser"; // Change to your MariaDB password

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if a row is returned

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
