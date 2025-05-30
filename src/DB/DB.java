package DB;
import Vue.* ;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DB {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        loadEnv();
    }

    private static void loadEnv() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/DB/.env"))) {
            URL = reader.readLine();
            USER = reader.readLine();
            PASSWORD = reader.readLine();
        } catch (IOException e) {
            System.out.println("Failed to load .env file!");
            e.printStackTrace();
        }
    }

    public static boolean testConnection() {
        System.out.println("/" + URL + "/");
        System.out.println("/" + USER + "/");
        System.out.println("/" + PASSWORD + "/");
        try (Connection conn = connect()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
                return true;
            } else {
                System.out.println("Database connection failed!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Database connection error:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyUser(String name, String password) {
        String sql = "SELECT * FROM buvette.users WHERE name = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error verifying user:");
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    public static String userEmail(String name) {
        String sql = "SELECT email FROM buvette.users WHERE name = ? ";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
          
            try (ResultSet rs = stmt.executeQuery()) {
                 rs.next();
                 return rs.getString("email") ;
            }
        } catch (SQLException e) {
            System.out.println("Error de selcting email ");
            e.printStackTrace();
            return null ;
        }
    }
    
    
    
    
    public static List<Plat> searchPlats(String query, String type) {
        List<Plat> results = new ArrayList<>();
        String sql;
        query = query.toLowerCase();
        try (Connection conn = connect(); // Assume getConnection() exists
             PreparedStatement stmt = conn.prepareStatement(getSearchQuery(type))) {
            switch (type) {
                case "Nom":
                    sql = "SELECT * FROM plats WHERE LOWER(nom) LIKE ?";
                    stmt.setString(1, "%" + query + "%");
                    break;
                case "Catégorie":
                    sql = "SELECT * FROM plats WHERE LOWER(categorie) LIKE ?";
                    stmt.setString(1, "%" + query + "%");
                    break;
                case "Prix":
                    try {
                        double price = Double.parseDouble(query);
                        sql = "SELECT * FROM plats WHERE prix <= ?";
                        stmt.setDouble(1, price);
                    } catch (NumberFormatException e) {
                        return results; // Return empty list for invalid price
                    }
                    break;
                default: // "Tous"
                    sql = "SELECT * FROM plats WHERE LOWER(nom) LIKE ? OR LOWER(categorie) LIKE ? OR LOWER(description) LIKE ?";
                    stmt.setString(1, "%" + query + "%");
                    stmt.setString(2, "%" + query + "%");
                    stmt.setString(3, "%" + query + "%");
                    break;
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new Plat(
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("description"),
                        rs.getString("categorie"),
                        rs.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    private static String getSearchQuery(String type) {
        switch (type) {
            case "Nom":
                return "SELECT * FROM plats WHERE LOWER(nom) LIKE ?";
            case "Catégorie":
                return "SELECT * FROM plats WHERE LOWER(categorie) LIKE ?";
            case "Prix":
                return "SELECT * FROM plats WHERE prix <= ?";
            default:
                return "SELECT * FROM plats WHERE LOWER(nom) LIKE ? OR LOWER(categorie) LIKE ? OR LOWER(description) LIKE ?";
        }
    }
    
    
    
    
    
    
    
          

    public static boolean chekPassword(String name, String password) {
        String sql = "SELECT password from users WHERE name = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                return (password.equals(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error verifying admin:");
            e.printStackTrace();
            return false;
        }

    }

    // here 
    public static boolean changePassword(String username, String newPassword) {
        String sql = "UPDATE USERS SET password = ? WHERE name = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // In a real application, you should hash the password before storing it
            String hashedPassword = newPassword; // Implement this method

            pstmt.setString(1, hashedPassword); // new password (hashed)
            pstmt.setString(2, username);       // username as key

            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0; // true if password was updated successfully

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeName(String oldName, String newName) {
        String sql = "UPDATE USERS SET name = ? WHERE name = ?";

        try (Connection conn = connect(); // ← utilise ta méthode de connexion
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);  // nouveau nom
            pstmt.setString(2, oldName);  // ancien nom

            int rowsUpdated = pstmt.executeUpdate();  // exécute la requête

            return rowsUpdated > 0;  // retourne true si au moins une ligne modifiée

        } catch (SQLException e) {
            e.printStackTrace();  // pour voir l'erreur dans la console
            return false;
        }
    }

   
    
    public static boolean isAdmin(String name, String password) {
    String sql = "SELECT * FROM buvette.users WHERE name = ? AND password = ?";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, name);
        stmt.setString(2, password);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("type").equals("admin");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error verifying admin:");
        e.printStackTrace();
    }
    return false;
}

    
    
    public static List<String> getUsersEmails() {
    List<String> emails = new ArrayList<>();
    String sql = "SELECT email FROM buvette.users WHERE email IS NOT NULL";
    
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            String email = rs.getString("email");
            if (email != null && !email.trim().isEmpty()) {
                emails.add(email.trim());
            }
        }
    } catch (SQLException e) {
        System.err.println("Error fetching user emails: " + e.getMessage());
        // Vous pourriez aussi logger cette erreur
    }
    
    return emails;
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    

    public static boolean verifyAdmin(String name, String password) {
        String sql = "SELECT * FROM buvette.users WHERE name = ? AND password = ? AND type = 'admin'";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error verifying admin:");
            e.printStackTrace();
            return false;
        }
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ======== USERS METHODS ========
    public static boolean addUser(String name, String password, String type) {
        String sql = "INSERT INTO buvette.users (name, password, TYPE) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, type);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding user:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addPlat(Plat plat) {
    String sql = "INSERT INTO buvette.plat (nom, prix, descrp, cat, image) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        // Insertion du plat dans la base de données
        stmt.setString(1, plat.getNom());
        stmt.setDouble(2, plat.getPrix());
        stmt.setString(3, plat.getDescription());
        stmt.setString(4, plat.getCategorie());
        stmt.setString(5, plat.getImagePath());
        stmt.executeUpdate();
        
        // Récupération des emails des utilisateurs
        List<String> emails = getUsersEmails();
        
        // Récupération des noms d'utilisateurs associés aux emails
        Map<String, String> userEmailsWithNames = getUsersEmailsWithNames();
        
       
        
        // Envoi d'email à chaque utilisateur
        for (String em : emails) {
            String userName = userEmailsWithNames.getOrDefault(em, "Client");
            String  Email = "Nouveau plat ajouté: " + plat.getNom()+
                "Bonjour " + userName + ",\n\n" +
                "Un nouveau plat a été ajouté à notre menu:\n" +
                "Nom: " + plat.getNom() + "\n" +
                "Prix: " + plat.getPrix() + " DH\n" +
                "Catégorie: " + plat.getCategorie() + "\n\n" +
                "Cordialement,\nL'équipe de la Buvette" ;
            
            
           
                
                CompletableFuture.runAsync(() -> {
                     email.SendEmail.envoyerEmail(em , Email) ;
  
        });
           
        }
        
        return true;
    } catch (SQLException e) {
        System.out.println("Error adding plat:");
        e.printStackTrace();
        return false;
    }
}

// Méthode pour récupérer les emails avec les noms d'utilisateurs
public static Map<String, String> getUsersEmailsWithNames() {
    Map<String, String> emailNameMap = new HashMap<>();
    String sql = "SELECT name, email FROM users WHERE email IS NOT NULL";
    
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            String name = rs.getString("name");
            String email = rs.getString("email");
            if (email != null && !email.trim().isEmpty()) {
                emailNameMap.put(email, name);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error fetching user emails with names: " + e.getMessage());
    }
    
    return emailNameMap;
}

    public static ResultSet getUser(String name) {
        String sql = "SELECT * FROM buvette.users WHERE name = ?";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error getting user:");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateUserPassword(String name, String newPassword) {
        String sql = "UPDATE buvette.users SET password = ? WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, name);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating password:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUserType(String name, String newType) {
        String sql = "UPDATE buvette.users SET TYPE = ? WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newType);
            stmt.setString(2, name);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating user type:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(String name) {
        String sql = "DELETE FROM buvette.users WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting user:");
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> listUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT name FROM buvette.users";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error listing users:");
            e.printStackTrace();
        }
        return users;
    }

    // ======== PLATS METHODS ========
    
    
     public static boolean updatePlat(Plat oldPlat, Plat newPlat) {
    StringBuilder sql = new StringBuilder("UPDATE buvette.plat SET ");
    List<Object> values = new ArrayList<>();

    // Vérifie les champs à mettre à jour
    boolean changed = false;

    if (newPlat.getNom() != null && !newPlat.getNom().equals(oldPlat.getNom())) {
        sql.append("nom = ?, ");
        values.add(newPlat.getNom());
        changed = true;
    }
    if (newPlat.getPrix() != oldPlat.getPrix()) {
        sql.append("prix = ?, ");
        values.add(newPlat.getPrix());
        changed = true;
    }
    if (newPlat.getDescription() != null && !newPlat.getDescription().equals(oldPlat.getDescription())) {
        sql.append("descrp = ?, ");
        values.add(newPlat.getDescription());
        changed = true;
    }
    if (newPlat.getCategorie() != null && !newPlat.getCategorie().equals(oldPlat.getCategorie())) {
        sql.append("cat = ?, ");
        values.add(newPlat.getCategorie());
        changed = true;
    }
    if (newPlat.getImagePath() != null && !newPlat.getImagePath().equals(oldPlat.getImagePath())) {
        sql.append("image = ?, ");
        values.add(newPlat.getImagePath());
        changed = true;
    }

    if (!changed) {
        System.out.println("Aucun changement détecté.");
        return false;
    }

    // Supprimer la virgule finale
    sql.setLength(sql.length() - 2);
    sql.append(" WHERE nom = ?");
    values.add(oldPlat.getNom()); // old key value (important!)

    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
        for (int i = 0; i < values.size(); i++) {
            stmt.setObject(i + 1, values.get(i));
        }
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.out.println("Erreur lors de la mise à jour du plat :");
        e.printStackTrace();
        return false;
    }
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static boolean addPlat(String nomp, String descp, float prixp, byte[] imgp, String catp, Boolean disp) {
        String sql = "INSERT INTO buvette.plats (nomp, descp, prixp, imgp, catp, disp) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomp);
            stmt.setString(2, descp);
            stmt.setFloat(3, prixp);
            stmt.setBytes(4, imgp);
            stmt.setString(5, catp);
            if (disp != null) {
                stmt.setBoolean(6, disp);
            } else {
                stmt.setNull(6, Types.BOOLEAN);
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding plat:");
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet getPlat(String nomp) {
        String sql = "SELECT * FROM buvette.plats WHERE nomp = ?";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomp);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error getting plat:");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updatePlatPrice(String nomp, float newPrice) {
        String sql = "UPDATE buvette.plats SET prixp = ? WHERE nomp = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, newPrice);
            stmt.setString(2, nomp);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating plat price:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePlatAvailability(String nomp, Boolean disp) {
        String sql = "UPDATE buvette.plats SET disp = ? WHERE nomp = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (disp != null) {
                stmt.setBoolean(1, disp);
            } else {
                stmt.setNull(1, Types.BOOLEAN);
            }
            stmt.setString(2, nomp);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating plat availability:");
            e.printStackTrace();
            return false;
        }
    }
    
  
    
    public static boolean updateUserInformation(String oldNom, String newNom, String password, String email) {
    String sql = "UPDATE buvette.users SET name = ?, password = ?, email = ? WHERE name = ?";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, newNom);
        stmt.setString(2, password);
        stmt.setString(3, email);
        stmt.setString(4, oldNom); // filtre WHERE name = oldNom

        stmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.out.println("Error updating user information:");
        e.printStackTrace();
        return false;
    }
}
   
    
    static public boolean  updateEmail(String name  , String email ){
    
    String sql = "UPDATE buvette.users SET  email = ? WHERE name = ?";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, name);
        stmt.setString(2, email );
        
        stmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.out.println("Error updating user information:");
        e.printStackTrace();
        return false;
    }
        
        
        
        
        
        
    
        
        
        
        
        
    }
    
    
    
    
    
    
    
    

    public static boolean deletePlat(String nomp) {
        String sql = "DELETE FROM buvette.plat WHERE nom = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomp);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting plat:");
            e.printStackTrace();
            return false;
        }
    }

    public static List<Plat> listPlats() {
        String sql = "SELECT * FROM buvette.plat";
        List<Plat> plats = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Plat plat = new Plat();
                plat.setNom(rs.getString("nom"));
                plat.setPrix(rs.getDouble("prix"));
                plat.setDescription(rs.getString("descrp"));
                plat.setCategorie(rs.getString("cat"));
                plat.setImagePath(rs.getString("image"));
                plats.add(plat);
            }
        } catch (SQLException e) {
            System.out.println("Error listing plats:");
            e.printStackTrace();
        }
        return plats;
    }

}
