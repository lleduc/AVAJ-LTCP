/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnexionBase;

/*
 * 
 * Librairies importées
 */
import java.sql.*;
import java.util.ArrayList;

/*
 * 
 * Connexion a votre BDD via le tunnel SSH
 * 
 * @author segado
 */
public class Connexion {

    /**
     * Attributs prives : connexion JDBC, statement, ordre requete et resultat requete
     */
    private Connection conn;
    private Statement stmt;
    private ResultSet rset;
    private ResultSetMetaData rsetMeta;
    private SSHTunnel ssh;
    private Boolean estConnecte = false;
    /**
     * ArrayList public pour les requêtes de sélection
     */
    public ArrayList<String> requetes = new ArrayList<String>();
    /**
     * ArrayList public pour les requêtes de MAJ
     */
    public ArrayList<String> requetesMaj = new ArrayList<String>(); // liste des requêtes de MAJ

    /**
     * Constructeur avec 4 paramètres : username et password ECE, login et password de la BDD
     */
    public Connexion(String usernameECE, String passwordECE, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException {
        // chargement driver "com.mysql.jdbc.Driver"
        Class.forName("com.mysql.jdbc.Driver");

        // Connexion via le tunnel SSH avec le username et le password ECE
        ssh = new SSHTunnel(usernameECE, passwordECE);

        if (ssh.connect()) {
            System.out.println("Connexion reussie");
            estConnecte = true;
            // url de connexion "jdbc:mysql://localhost:3305/usernameECE"
            String urlDatabase = "jdbc:mysql://localhost:3305/" + usernameECE;

            //création d'une connexion JDBC à la base
            conn = DriverManager.getConnection(urlDatabase, loginDatabase, passwordDatabase);

            // création d'un ordre SQL (statement)
            stmt = conn.createStatement();

            
        }
    }
    
    public Connexion(String url, String login, String password) throws SQLException, ClassNotFoundException {
        // chargement driver "com.mysql.jdbc.Driver"
        Class.forName("com.mysql.jdbc.Driver");

        // url de connexion "jdbc:mysql://localhost/avaj"
        String urlDatabase = "jdbc:mysql://localhost/" + url;

        //création d'une connexion JDBC à la base
        conn = DriverManager.getConnection(urlDatabase, login, password);

        // création d'un ordre SQL (statement)
        stmt = conn.createStatement();

    }
    

    /**
     * Methode qui retourne l'ArrayList des champs de la requete en parametre
     */
    public ArrayList remplirChampsRequete(String requete) throws SQLException {
        // récupération de l'ordre de la requete
        rset = stmt.executeQuery(requete);

        // récupération du résultat de l'ordre
        rsetMeta = rset.getMetaData();

        // calcul du nombre de colonnes du resultat
        
        int nbColonne = rsetMeta.getColumnCount();

        // creation d'une ArrayList de String
        ArrayList<String> liste;
        liste = new ArrayList<String>();

        // tant qu'il reste une ligne 
        while (rset.next()) {
            String champs;
            champs = rset.getString(1); // ajouter premier champ

            // Concatener les champs de la ligne separes par ,
            for (int i = 1; i < nbColonne; i++) {
                champs = champs + "," + rset.getString(i+1);
            }

            // ajouter un "\n" à la ligne des champs
            champs = champs + "\n";

            // ajouter les champs de la ligne dans l'ArrayList
            liste.add(champs);
        }

        // Retourner l'ArrayList
        return liste;
    }

    public Connection getConn() {
        return conn;
    }

    public Statement getStmt() {
        return stmt;
    }

    public ResultSet getRset() {
        return rset;
    }

    public ResultSetMetaData getRsetMeta() {
        return rsetMeta;
    }

    public ArrayList<String> getRequetes() {
        return requetes;
    }

    public ArrayList<String> getRequetesMaj() {
        return requetesMaj;
    }

    public SSHTunnel getSsh() {
        return ssh;
    }

    public Boolean getEstConnecte() {
        return estConnecte;
    }
    
}
