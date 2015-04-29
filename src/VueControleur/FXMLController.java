/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import ConnexionBase.Connexion;
import Hopital.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thomas
 */
public class FXMLController extends Main implements Initializable {

    
    //Variables de la fenêtre de recherche
    @FXML
    public Stage popup;
    @FXML
    public Label messagePopup;
    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox<String> medecin, type;
    @FXML
    private GridPane gridCommune;
    @FXML
    private TextField numero;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextArea resultat;

    ObservableList<String> listeCBMedecin, listeCBType;
    ArrayList<String> liste = null;
    public String requete, typeRequete;
    //Variable d'ajout/modification
    public boolean ajout_modif = true;
    public String personneTemporaire; //numéro personne utilisé pour l'ajout et la modification
    public String lala;
/////////////////////////////////////////////////////////////////////
    //String nomTF = null;
    //Employe  employe= new Employe();
    Malade malade = new Malade();
    Docteur docteur = new Docteur();
    Infirmier infirmier = new Infirmier();
    Hospitalisation hospitalisation = new Hospitalisation();
    ///////////////////////////////////////////////////////////////////////

    //Variables de malade dans recherche
    @FXML
    private ComboBox<String> mutuelle;
    @FXML
    private ComboBox<String> numeroChambre;
    ObservableList<String> listeCBMutuelle;
    ObservableList<String> listeCBNumeroChambre;
    @FXML
    private DatePicker dateEntree;
    @FXML
    private DatePicker dateSortie;

    @FXML
    private GridPane gridMalade;

    //Variables de medecin dans recherche
    @FXML
    private ComboBox<String> specialite;
    ObservableList<String> listeCBSpecialite;
    @FXML
    private GridPane gridMedecin;

    //Variables de infirmier dans recherche
    @FXML
    private ComboBox<String> codeService;
    @FXML
    private ComboBox<String> rotation;
    ObservableList<String> listeCBCodeService, listeCBRotation;

    @FXML
    private GridPane gridInfirmier;

    //Variables de la fenêtre de recherche
    @FXML
    private TableView patients;

    //Variables de la fenêtre de connection
    @FXML
    private TextField identifiant, MDP;

    //Variable de la fenêtre modification
    @FXML
    private ComboBox<String> type1;
    @FXML
    private TabPane tabPane;
    @FXML
    private GridPane gridMedecin1;
    @FXML
    private GridPane gridInfirmier1;
    @FXML
    private GridPane gridMalade1;
    @FXML
    private GridPane gridCommune1;
    @FXML
    private ComboBox<String> mutuelle1;
    @FXML
    private ComboBox<String> numeroChambre1;
    @FXML
    private ComboBox<String> specialite1;
    @FXML
    private ComboBox<String> rotation1;
    @FXML
    private ComboBox<String> codeService1;
    @FXML
    private TextField nom1;
    @FXML
    private TextField prenom1;
    @FXML
    private TextField numero1;

    //initialisationd la visibilité graphique
    // Sous programmes répondant aux actions sur l'interface graphique
    @FXML
    public void SearchBouton(ActionEvent event) {

        System.out.println("Rechercher");
        String result = searchBar.getText();
        System.out.println(result);

        try {

            // recuperer la liste des lignes de la requete selectionnee
            result = "SELECT nom, prenom FROM employe  WHERE nom LIKE '" + result + "%'";
            liste = maconnexion.remplirChampsRequete(result);

            // afficher les lignes de la requete selectionnee a partir de la liste
            for (int i = 0; i < liste.size(); i++) {
                System.out.println(liste.get(i));
            }
        } catch (SQLException e) {
            System.out.println("Echec SQL");
            e.printStackTrace();
        }
    }

    // Création de la connection à la base de donnée 
    // lors de l'appuie sur le bouton connection si
    // les identifiants et mots de passe sont valides
    public void initConnection() {
        if (identifiant.getText().equals("") && MDP.getText().equals("")) {
            try {

                maconnexion = new Connexion(dechiffreur("72656E79"), dechiffreur("4C5A6F613235373424"),
                        dechiffreur("72656E792D7277"), dechiffreur("486A4237564B3952"));

                
                
                
                initCBMedecin();
                initCBType(type, "docteur");
                initCBMutuelle(mutuelle);
                initCBNumeroChambre(numeroChambre);
                initCBSpecialite(specialite);
                initCBRotation(rotation);
                initCBCodeService(codeService);

                listePatients();
                ///////////////////////////////////////////////////////
                for (int i = 1; i <= 4; i++) {
                    tabPane.getTabs().get(i).getContent().setDisable(false);
                }
                //////////////////////////////////////////////////////

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Essaye encore");
            identifiant.setText("");
            MDP.setText("");
        }

    }

    // Initialisation des combobox
    public void initCBMedecin() {

        try {
            requete = "SELECT e.nom\n"
                    + "FROM employe e, docteur d\n"
                    + "WHERE e.no_employe = d.no_docteur\n"
                    + "ORDER BY nom\n";
            liste = maconnexion.remplirChampsRequete(requete);
            listeCBMedecin = FXCollections.observableArrayList(liste);
            medecin.setItems(listeCBMedecin);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void initCBType(ComboBox<String> type, String types ) {
        listeCBType = FXCollections.observableArrayList("docteur", "infirmier", "malade");
        type.setItems(listeCBType);
        ///////////////////////////////
        //ajout thomas
        type.setValue(types);
        /////////////////////////////////
    }

    public void initCBMutuelle(ComboBox<String> mutuelle) {
        try {
            requete = "SELECT DISTINCT mutuelle\n"
                    + "FROM malade\n"
                    + "ORDER BY mutuelle\n";
            liste = maconnexion.remplirChampsRequete(requete);
            listeCBMutuelle = FXCollections.observableArrayList(liste);
            mutuelle.setItems(listeCBMutuelle);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCBNumeroChambre(ComboBox<String> numeroChambre) {
        try {
            requete = "SELECT DISTINCT no_chambre\n"
                    + "FROM hospitalisation\n"
                    + "ORDER BY no_chambre\n";
            liste = maconnexion.remplirChampsRequete(requete);
            listeCBNumeroChambre = FXCollections.observableArrayList(liste);
            numeroChambre.setItems(listeCBNumeroChambre);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCBSpecialite(ComboBox<String> specialite) {
        try {
            requete = "SELECT DISTINCT specialite\n"
                    + "FROM docteur\n"
                    + "ORDER BY specialite\n";
            liste = maconnexion.remplirChampsRequete(requete);
            listeCBSpecialite = FXCollections.observableArrayList(liste);
            specialite.setItems(listeCBSpecialite);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCBCodeService(ComboBox<String> codeService) {
        try {
            requete = "SELECT DISTINCT code\n"
                    + "FROM service\n"
                    + "ORDER BY code\n";
            liste = maconnexion.remplirChampsRequete(requete);
            listeCBCodeService = FXCollections.observableArrayList(liste);
            codeService.setItems(listeCBCodeService);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCBRotation(ComboBox<String> rotation) {
        try {
            requete = "SELECT DISTINCT rotation\n"
                    + "FROM infirmier\n"
                    + "ORDER BY rotation\n";
            liste = maconnexion.remplirChampsRequete(requete);
            listeCBRotation = FXCollections.observableArrayList(liste);
            rotation.setItems(listeCBRotation);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Action des combobox lorsqu'un choix est selectionné
    public void actionCBMedecin() {
        medecin.setOnAction((event) -> {
            requete = medecin.getValue();
            //System.out.println("ComboBox Action (selected: " + medecin.getValue().substring(0, medecin.getValue().length()-1)+")");
            try {

                requete = "SELECT DISTINCT m.nom\n"
                        + "FROM malade m, docteur d, soigne s, employe e\n"
                        + "WHERE e.nom LIKE '" + medecin.getValue().substring(0, medecin.getValue().length() - 1) + "' AND e.no_employe = s.no_docteur AND s.no_malade = m.no_malade\n"
                        + "ORDER BY nom\n";
                liste = maconnexion.remplirChampsRequete(requete);

                // afficher les lignes de la requete selectionnee a partir de la liste
                for (int i = 0; i < liste.size(); i++) {
                    System.out.println(liste.get(i));
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void actionCBSpecialite() {
        specialite.setOnAction((event) -> {
            docteur.setSpecialite(specialite.getValue().substring(0, specialite.getValue().length() - 1));
            requete(docteur);
        });
    }

    public void actionCBService() {
        codeService.setOnAction((event) -> {
            infirmier.setCode_service(codeService.getValue().substring(0, codeService.getValue().length() - 1));
            requete(infirmier);
        });
    }

    public void actionCBRotation() {
        rotation.setOnAction((event) -> {
            infirmier.setRotation(rotation.getValue().substring(0, rotation.getValue().length() - 1));
            requete(infirmier);
        });
    }

    public void actionCBMutuelle() {
        mutuelle.setOnAction((event) -> {
            malade.setMutuelle(mutuelle.getValue().substring(0, mutuelle.getValue().length() - 1));
            requete(malade);
        });
    }

    public void actionCBChambre() {
        numeroChambre.setOnAction((event) -> {
            hospitalisation.setNo_chambre(numeroChambre.getValue().substring(0, numeroChambre.getValue().length() - 1));
            requete(malade);
        });
    }

    public void actionCBType(ComboBox<String> type, GridPane gridCommune, GridPane gridMedecin, GridPane gridMalade, GridPane gridInfirmier) {
        //////////////////////////////////////////////////////////////
        type.setOnAction((event) -> {
            requete = type.getValue();
            gridCommune.setVisible(false);
            gridMedecin.setVisible(false);
            gridMalade.setVisible(false);
            gridInfirmier.setVisible(false);

            if ("docteur".equals(requete)) {
                gridCommune.setVisible(true);
                gridMedecin.setVisible(true);

                actionCBSpecialite();

            }
            if ("infirmier".equals(requete)) {
                gridCommune.setVisible(true);
                gridInfirmier.setVisible(true);
                actionCBService();
                actionCBRotation();
            }
            if ("malade".equals(requete)) {
                gridCommune.setVisible(true);
                gridMalade.setVisible(true);
                actionCBMutuelle();
                actionCBChambre();
            }
        });
        /////////////////////////////////////////////////////////////

        // A toi de remplir Louis
    }

    // Rempli l'onglet liste patients avec les informations provenant de la barre
    public void listePatients() {
        // A toi de remplir Philippine
        // l'objet de gauche dans l'onglet est un TableView il est défini en haut, il s'appelle patients
        // c'est juste le point de départ, tu n'as pas à te limiter à un seul sous programme
        // ce sous programme est appelé dans initConnection, après la connection à la bdd
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Sous programmes permettant d'additionner les différents critères de recherche afin de créer une requête
    /*public void actionTFnom(){
     nom.setOnAction((event) -> {
     String nomrecup=null;
     if(nom.getLength() > 0 ){
     nomrecup = nom.getText();
     System.out.println(nomrecup);
     }else System.out.println("pas de nom");
     });
     }*/
    @FXML
    public void actionTF(TextField type, Personne personne) {
        if (type.equals(nom)) {
            personne.setNom(type.getText());
            System.out.println(personne.getNom());
        } else if (type.equals(prenom)) {
            personne.setPrenom(type.getText());
            System.out.println(personne.getPrenom());
        } else if (type.equals(numero)) {
            personne.setNumero(Integer.parseInt(type.getText()));
            System.out.println(personne.getNumero());
        }
        requete(personne);
    }

    public void actionTFNumero(KeyEvent event) {

        String ressource = "FXMLPopup.fxml";
        String titre = "Message d'erreur";
        if (numero.getLength() > 0) {
            if (numero.getText().matches("\\d+")) {
                if (type.getValue().equalsIgnoreCase("docteur")) {
                    actionTF(numero, docteur);
                } else if (type.getValue().equalsIgnoreCase("infirmier")) {
                    actionTF(numero, infirmier);
                } else if (type.getValue().equalsIgnoreCase("malade")) {
                    actionTF(numero, malade);
                }
            } else {
                popup(ressource, titre);
                numero.setText("");
            }
        } else {
            System.out.println("pas de numero");
            docteur.setNumero(0);
            infirmier.setNumero(0);
            malade.setNumero(0);

        }
    }

    public void popup(String ressource, String titre) {
        try {
            popup = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(ressource));
            AnchorPane page = (AnchorPane) loader.load();

            popup.setTitle(titre);
            popup.initModality(Modality.WINDOW_MODAL);
            popup.initOwner(primaryStage);
            Scene scene = new Scene(page);
            popup.setScene(scene);

            //PopupsaisienumeroController controleur = loader.getController();
            //controller.setDialogStage(popup);
            //controller.setPerson(person);
            // Show the dialog and wait until the user closes it
            popup.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Ajouter(ActionEvent event) {
       
        tabPane.getTabs().get(5).getContent().setDisable(false);
        tabPane.getSelectionModel().select(5);
        actionCBType(type1, gridCommune1, gridMedecin1, gridMalade1, gridInfirmier1);
        initCBType(type1, "");
        initCBMutuelle(mutuelle1);
        initCBNumeroChambre(numeroChambre1);
        initCBSpecialite(specialite1);
        initCBRotation(rotation1);
        initCBCodeService(codeService1);
        
        
    }
    
    public void Valider(ActionEvent event){
        requete = "INSERT INTO employe VALUES ('" + numero1.getText() + "', '" + nom1.getText() + "', '" + prenom1.getText() + "', '8 rue Ampère', '07 77 30 69 24', '1234')";
        System.out.println(requete);
        try {
            maconnexion.getStmt().executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String list_get_zero(String requete) {
        try {
            liste = maconnexion.remplirChampsRequete(requete);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste.get(0);
    }

    public void Modifier(ActionEvent event) {
        
        int j = 0;
        char a;
        String typeSelect = type.getValue();
        for (int i = 0; i < 4; i++) {
            System.out.println(i);
            a = personneTemporaire.charAt(i);
            if (a == ',') {
                j = i;
            } 
        }
        lala = personneTemporaire.substring(0, j);
        System.out.println(lala);
        tabPane.getTabs().get(5).getContent().setDisable(false);
        tabPane.getSelectionModel().select(5);
        actionCBType(type1, gridCommune1, gridMedecin1, gridMalade1, gridInfirmier1);
        initCBType(type1, type.getValue());
        initCBMutuelle(mutuelle1);
        initCBNumeroChambre(numeroChambre1);
        initCBSpecialite(specialite1);
        initCBRotation(rotation1);
        initCBCodeService(codeService1);
        //tabPane.getSelectionModel().select(5).setVisible(true);
        if (typeSelect.equalsIgnoreCase("docteur") || typeSelect.equalsIgnoreCase("infirmier")) {
            typeRequete = "employe";
        } else {
            typeRequete = "malade";
        }
        numero1.setText(lala);
        nom1.setText(list_get_zero("SELECT DISTINCT " + typeRequete + ".nom FROM employe employe, malade malade WHERE " + typeRequete + ".no_" + typeRequete + " = '" + lala + "'\n"));
        prenom1.setText(list_get_zero("SELECT DISTINCT " + typeRequete + ".prenom FROM employe employe, malade malade WHERE " + typeRequete + ".no_" + typeRequete + " = '" + lala + "'\n"));
        if (typeSelect.equalsIgnoreCase("docteur")) {
            specialite1.setValue(list_get_zero("SELECT DISTINCT " + typeSelect + ".specialite FROM docteur docteur WHERE " + typeSelect + ".no_" + typeSelect + " = '" + lala + "'\n"));
        }
        if (typeSelect.equalsIgnoreCase("malade")) {
            //dateEntree1.(list_get_zero("SELECT DISTINCT hospitalisation.no_chambre FROM hospitalisation hospitalisation WHERE hospitalisation.no_" + typeSelect + " = '"+lala+"'\n" )));
            numeroChambre1.setValue(list_get_zero("SELECT DISTINCT hospitalisation.no_chambre FROM hospitalisation hospitalisation WHERE hospitalisation.no_" + typeSelect + " = '" + lala + "'\n"));
            mutuelle1.setValue(list_get_zero("SELECT DISTINCT " + typeSelect + ".mutuelle FROM malade malade WHERE " + typeSelect + ".no_" + typeSelect + " = '" + lala + "'\n"));
        }
        if (typeSelect.equalsIgnoreCase("infirmier")) {
            rotation1.setValue(list_get_zero("SELECT DISTINCT infirmier.rotation FROM infirmier infirmier WHERE infirmier.no_" + typeSelect + " = '" + lala + "'\n"));
            codeService1.setValue(list_get_zero("SELECT DISTINCT infirmier.code_service FROM infirmier infirmier WHERE infirmier.no_" + typeSelect + " = '" + lala + "'\n"));
        }

    }

   
    public void actionTFnom(KeyEvent event) {
        if (nom.getLength() > 0) {
            if (type.getValue().equalsIgnoreCase("docteur")) {
                actionTF(nom, docteur);
            } else if (type.getValue().equalsIgnoreCase("infirmier")) {
                actionTF(nom, infirmier);
            } else if (type.getValue().equalsIgnoreCase("malade")) {
                actionTF(nom, malade);
            }
        } else {
            System.out.println("pas de nom");
            docteur.setNom("");
            infirmier.setNom("");
            malade.setNom("");
        }
    }

    public void actionTFPrenom(KeyEvent event) {
        if (prenom.getLength() > 0) {
            if (type.getValue().equalsIgnoreCase("docteur")) {
                actionTF(prenom, docteur);
            } else if (type.getValue().equalsIgnoreCase("infirmier")) {
                actionTF(prenom, infirmier);
            } else if (type.getValue().equalsIgnoreCase("malade")) {
                actionTF(prenom, malade);
            }
        } else {
            System.out.println("pas de prenom");
            docteur.setPrenom("");
            infirmier.setPrenom("");
            malade.setPrenom("");
        }
    }

    public void requete(Personne personne) {
        //System.out.println("requete");

        String typeSelect = personne.getClass().getName().substring(8).toLowerCase();
        System.out.println(typeSelect);

        if (typeSelect.equalsIgnoreCase("docteur") || typeSelect.equalsIgnoreCase("infirmier")) {
            typeRequete = "employe";
        } else {
            typeRequete = "malade";
        }

        requete = "SELECT DISTINCT " + typeRequete + ".no_" + typeRequete + "," + typeRequete + ".nom, " + typeRequete + ".prenom\n"
                + "FROM employe employe, docteur docteur, infirmier infirmier, malade malade, hospitalisation hospitalisation \n"
                + "WHERE " + typeRequete + ".no_" + typeRequete + " = " + typeSelect + ".no_" + typeSelect + "\n";

        if (numero.getLength() > 0) {
            requete += " AND " + typeRequete + ".no_" + typeRequete + " = '";
            switch (typeSelect) {
                case "docteur":
                    requete += docteur.getNumero() + "%'\n";
                    break;
                case "infirmier":
                    requete += infirmier.getNumero() + "%'\n";
                    break;
                case "malade":
                    requete += malade.getNumero() + "%'\n";
                    break;
            }
            //System.out.println(requete);
        }
        if (nom.getLength() > 0) {
            requete += "AND " + typeRequete + ".nom LIKE '";
            switch (typeSelect) {
                case "docteur":
                    requete += docteur.getNom() + "%'\n";
                    break;
                case "infirmier":
                    requete += infirmier.getNom() + "%'\n";
                    break;
                case "malade":
                    requete += malade.getNom() + "%'\n";
                    break;
            }
            //System.out.println(requete);
        }
        if (prenom.getLength() > 0) {
            requete += "AND " + typeRequete + ".prenom LIKE '";
            switch (typeSelect) {
                case "docteur":
                    requete += docteur.getPrenom() + "%'\n";
                    break;
                case "infirmier":
                    requete += infirmier.getPrenom() + "%'\n";
                    break;
                case "malade":
                    requete += malade.getPrenom() + "%'\n";
                    break;
            }
            //System.out.println(requete);
        }
        if (typeSelect.equalsIgnoreCase("docteur") && docteur.getSpecialite() != null) {
            requete += "AND docteur.specialite ='" + docteur.getSpecialite() + "'\n";
        }
        if (typeSelect.equalsIgnoreCase("infirmier") && infirmier.getCode_service() != null) {
            requete += "AND infirmier.code_service ='" + infirmier.getCode_service() + "'\n";
        }
        if (typeSelect.equalsIgnoreCase("infirmier") && infirmier.getRotation() != null) {
            requete += "AND infirmier.rotation ='" + infirmier.getRotation() + "'\n";
        }
        if (typeSelect.equalsIgnoreCase("malade") && malade.getMutuelle() != null) {
            requete += "AND malade.mutuelle ='" + malade.getMutuelle() + "'\n";
        }
        if (typeSelect.equalsIgnoreCase("malade") && hospitalisation.getNo_chambre() != null) {
            requete += "AND malade.no_malade = hospitalisation.no_malade\n"
                    + "AND hospitalisation.no_chambre ='" + hospitalisation.getNo_chambre() + "'\n";
        }

        System.out.println(requete);
        try {
            liste = maconnexion.remplirChampsRequete(requete);

            // afficher les lignes de la requete selectionnee a partir de la liste
            for (int i = 0; i < liste.size(); i++) {
                System.out.println(liste.get(i));
                //resultat.insertText(i, liste.get(i));
                personneTemporaire = liste.get(0);
            }
            //System.out.println(personneTemporaire);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Sous programme permettant de ne pas avoir ses identifiants Campus et base de donnée en clair dans le code
    private String dechiffreur(String code) {
        StringBuilder codeClair = new StringBuilder("");
        for (int i = 0; i < code.length(); i += 2) {
            String str = code.substring(i, i + 2);
            codeClair.append((char) Integer.parseInt(str, 16));
        }
        return codeClair.toString();
    }

    // Initialisation des actions des combobox
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        gridCommune.setVisible(true);
        gridMedecin.setVisible(false);
        gridMalade.setVisible(false);
        gridInfirmier.setVisible(false);
        gridCommune1.setVisible(true);
        gridMedecin1.setVisible(false);
        gridMalade1.setVisible(false);
        gridInfirmier1.setVisible(false);
        //actionCBMedecin();
        actionCBType(type, gridCommune, gridMedecin, gridMalade, gridInfirmier);
        //actionTFnom();

        ////////////////////////////////////////
        for (int i = 1; i <= 5; i++) {
            tabPane.getTabs().get(i).getContent().setDisable(true);
        }

        //////////////////////////////////////
    }

}
