package ApiBDD;

import java.net.Socket;

import java.sql.*;

public class db_connection {

    Connection connection;
    Statement statement;
    String SQL;

    String url;
    String username;
    String password;
    Socket client;
    int Port;
    String Host;

    public db_connection(String url, String username, String password, String Host, int Port) {

        this.url = url;
        this.username = username;
        this.password = password;
        this.Host = Host;

        this.Port = Port;
    }

    public Connection connexionDatabase() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");



            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.err.println(e);//
        }
        return connection;
    }

    public Connection closeconnexion() {

        try {
            connection.close();
        } catch (Exception e) {
            System.err.println(e);//
        }
        return connection;
    }

    public ResultSet exécutionQuery(String sql) {


    connexionDatabase();
    ResultSet resultSet = null;
    try {
        statement = connection.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );
        resultSet = statement.executeQuery(sql);
        System.out.println(sql);
    } catch (SQLException ex) {
        System.err.println(ex);
    }
    return resultSet;
}


    public String exécutionUpdate(String sql) {
        connexionDatabase();
        String result = "";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            result = sql;

        } catch (SQLException ex) {
            result = ex.toString();
        }
        return result;

    }

//afficher tous
    public ResultSet querySelectAll(String nomTable) {

        connexionDatabase();
        SQL = "SELECT * FROM " + nomTable;
        System.out.println(SQL);
        return this.exécutionQuery(SQL);

    }

//afficher tous avec paramètre
    public ResultSet querySelectAll(String nomTable, String état) {

        connexionDatabase();
        SQL = "SELECT * FROM " + nomTable + " WHERE " + état;
        return this.exécutionQuery(SQL);

    }

//
    public ResultSet querySelect(String[] nomColonne, String nomTable) {

        connexionDatabase();
        int i;
        SQL = "SELECT ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " FROM " + nomTable;
        return this.exécutionQuery(SQL);

    }
    
// query select bon d'entrée
    public ResultSet queryBeSelect() {

        connexionDatabase();
        SQL = "SELECT be.num_be, produit.deseignation, produit.stock, be.date_entree "
                + "FROM bon_entree be "
                + "INNER JOIN produit "
                + "ON produit.id = be.produit_id";
        return this.exécutionQuery(SQL);
    }
    
// query select bon de sortie
    public ResultSet queryBsSelect() {

        connexionDatabase();
        SQL = "SELECT bs.num_bs, produit.deseignation, vente.stock_sortie, bs.date_sortie "
                + "FROM bon_sortie bs "
                + "INNER JOIN vente ON vente.id = bs.vente_id "
                + "INNER JOIN produit ON produit.id = bs.produit_bsid";
        return this.exécutionQuery(SQL);
    }

//
    public ResultSet fcSelectCommand(String[] nomColonne, String nomTable, String état) {

        connexionDatabase();
        int i;
        SQL = "SELECT ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " FROM " + nomTable + " WHERE " + état;
        return this.exécutionQuery(SQL);

    }
    
    // misy doublons foana
    // efa nandramako ny DISTINCT fa tsy mety
    // select all, mouvement
        public ResultSet manySelect() {

        connexionDatabase();
        SQL = "SELECT DISTINCT be.num_be as NUM_BE,num_bs as NUM_BS, p.deseignation AS DESIGNATION, "
                + "p.stock AS ENTREE, v.stock_sortie AS SORTIE, be.date_entree as DATE_BE,date_sortie "
                + "as DATE_BS FROM bon_entree be, bon_sortie bs, produit p, vente v WHERE p.code_produit = v.code_produit";
        return this.exécutionQuery(SQL);
    }
    


    public String queryInsert(String nomTable, String[] contenuTableau) {

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + " VALUES(";

        for (i = 0; i <= contenuTableau.length - 1; i++) {
            SQL += "'" + contenuTableau[i] + "'";
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }

        SQL += ")";
        return this.exécutionUpdate(SQL);

    }

// ajout
    public String queryInsert(String nomTable, String[] nomColonne, String[] contenuTableau) {

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + "(";
        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }
        SQL += ") VALUES(";
        for (i = 0; i <= contenuTableau.length - 1; i++) {
            SQL += "'" + contenuTableau[i] + "'";
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }

        SQL += ")";
        return this.exécutionUpdate(SQL);

    }

//modifier
    public String queryUpdate(String nomTable, String[] nomColonne, String[] contenuTableau, String état) {

        connexionDatabase();
        int i;
        SQL = "UPDATE " + nomTable + " SET ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i] + "='" + contenuTableau[i] + "'";
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " WHERE " + état;
        return this.exécutionUpdate(SQL);

    }

//supprimer
    public String queryDelete(String nomtable) {

        connexionDatabase();
        SQL = "DELETE FROM " + nomtable;
        return this.exécutionUpdate(SQL);

    }

//supprimer avec paramètre
    public String queryDelete(String nomTable, String état) {

        connexionDatabase();
        SQL = "DELETE FROM " + nomTable + " WHERE " + état;
        return this.exécutionUpdate(SQL);

    }
    

    public int getRowCount(ResultSet rs) {
    int rowCount = 0;
    try {
        if (rs != null) {
            rs.last(); // aller à la dernière ligne
            rowCount = rs.getRow(); // obtenir l'indice de la dernière ligne
            rs.beforeFirst(); // revenir avant la première ligne
        }
    } catch (SQLException ex) {
        System.err.println("Erreur lors du comptage des lignes : " + ex.getMessage());
    }
    return rowCount;
}

    

    


}
