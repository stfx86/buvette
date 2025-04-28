package DB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        loadEnv();
    }

    private static void loadEnv() {
        try (BufferedReader reader = new BufferedReader(new FileReader("DB/.env"))) {
            URL = reader.readLine();
            USER = reader.readLine();
            PASSWORD = reader.readLine();
        } catch (IOException e) {
            System.out.println("Failed to load .env file!");
            e.printStackTrace();
        }
    }

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
