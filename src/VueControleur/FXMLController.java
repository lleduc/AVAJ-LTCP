/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import ConnexionBase.Connexion;
import Hopital.*;
import Hopital.Malade;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author thomas
 */
public class FXMLController extends Main implements Initializable {

    //Variables de la fenêtre de recherche
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

    ObservableList<String> listeCBMedecin, listeCBType;
    ArrayList<String> liste = null;
    public String requete, typeRequete;

    //Variable d'ajout/modification
    public boolean ajout_modif = true;
    public String personneTemporaire; //numéro personne utilisé pour l'ajout et la modification
    public String lala;

    Malade malade = new Malade();
    Docteur docteur = new Docteur();
    Infirmier infirmier = new Infirmier();
    Hospitalisation hospitalisation = new Hospitalisation();

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
    private TextArea resultat;

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

    //Variables de la fenêtre Liste patients

    @FXML
    private TableView<Malade> patients;
    @FXML
    private TableColumn<Malade, Integer> cnum;
    @FXML
    private TableColumn<Malade, String> cprenom;
    @FXML
    private TableColumn<Malade, String> cnom;

    ObservableList<Malade> PatientData = FXCollections.observableArrayList();

    @FXML
    private TextField servicep;
    @FXML
    private TextField medecinp;
    @FXML
    private TextField adressep;
    @FXML
    private TextField telephonep;
    @FXML
    private TextField mutuellep;

    //Variables de la fenêtre de connection
    @FXML
    private TextField identifiant;
    @FXML
    private PasswordField MDP;
    @FXML
    private Label champconnect;

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
    private GridPane gridCode1;
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
    @FXML
    private TextField code1;
    @FXML
    private TextField telephone1;
    @FXML
    private TextField adresse1;
    @FXML
    private TextField salaire1;
    @FXML
    private DatePicker dateEntree1;
    @FXML
    private DatePicker dateSortie1;

    // Varibles du graphique de reporting
    int countCar;
    int countChg;
    int countRea;

    float countInfJour;
    float countInfNuit;
    final String car = "CAR";
    final String chg = "CHG";
    final String rea = "REA";
    float countInfCar;
    float countInfChg;
    float countInfRea;
    float countChambreCar;
    float countChambreChg;
    float countChambreRea;
    @FXML
    CategoryAxis xAxis = new CategoryAxis();
    // L'axe des ordonnées est constitué par le nombre de patients oscultés.
    @FXML
    NumberAxis yAxis = new NumberAxis();
    @FXML
    BarChart<String, Number> graph = new BarChart<>(xAxis, yAxis);
    @FXML
    private ComboBox<String> donneesDiag;
    ObservableList<String> listeCBdonneesDiag;

    /**
     * Création de la connection à la base de donnée lors de l'appuie sur le
     * bouton connection si les identifiants et mots de passe sont valides.
     * Initialisation des Combobox.
     */
    public void initConnection() {

        String code;

        initCBMedecin();
        initCBType(type, "docteur");
        initCBMutuelle(mutuelle);
        initCBNumeroChambre(numeroChambre);
        initCBSpecialite(specialite);
        initCBRotation(rotation);
        initCBCodeService(codeService);
        initCBdonneesDiag();

        listePatientsInit();
        if (identifiant.getText().length() > 0) {
            requete = "SELECT code FROM employe WHERE no_employe LIKE '" + identifiant.getText() + "'";
            try {
                liste = maconnexion.remplirChampsRequete(requete);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!liste.isEmpty()) {
                code = (liste.get(0).substring(0, liste.get(0).length() - 1));

                if (MDP.getText().equalsIgnoreCase(code)) {
                    for (int i = 1; i <= 3; i++) {
                        tabPane.getTabs().get(i).getContent().setDisable(false);
                    }
                    tabPane.getTabs().get(0).getContent().setDisable(true);
                    tabPane.getSelectionModel().select(1);
                    champconnect.setText("Accès autorisé");
                } else {
                    champconnect.setText("Mot de passe et/ou Identifiant erroné(s)");
                    identifiant.setText("");
                    MDP.setText("");
                }
            } else {
                champconnect.setText("Mot de passe et/ou Identifiant erroné(s)");
                identifiant.setText("");
                MDP.setText("");
            }
        } else {
            champconnect.setText("Mot de passe et/ou Identifiant erroné(s)");
            identifiant.setText("");
            MDP.setText("");
        }

    }

    /**
     * Initialisation de la ComboBox medecin.
     */
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

    /**
     * Initialisation de la ComboBox type(medecin, infirmier ou malade).
     */
    public void initCBType(ComboBox<String> type, String types) {
        listeCBType = FXCollections.observableArrayList("docteur", "infirmier", "malade");
        type.setItems(listeCBType);

        type.setValue(types);

    }

    /**
     * Initialisation de la ComboBox mutuelle. Elle est remplie avec une requete
     * qui recherche toutes les mutuelles présentes dans la base de données.
     */
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

    /**
     * Initialisation de la ComboBox numéro de chambre. Elle est remplie avec
     * une requete qui recherche tous les numéros de chambres présents dans la
     * base de données.
     */
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

    /**
     * Initialisation de la ComboBox spécialité. Elle est remplie avec une
     * requete qui recherche toutes les spécialités présentes dans la base de
     * données.
     */
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

    /**
     * Initialisation de la ComboBox code service. Elle est remplie avec une
     * requete qui recherche tous les services présents dans la base de données.
     */
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

    /**
     * Initialisation de la ComboBox Rotation. Elle est remplie avec une requete
     * qui recherche toutes les rotations présentes dans la base de données.
     */
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

    /**
     * Initialisation de la ComboBox de données du diagramme.
     */
    public void initCBdonneesDiag() {
        listeCBdonneesDiag = FXCollections.observableArrayList("Moyenne des salaires infirmiers par rotation", "Moyenne des salaires infirmiers par service", "Patient par service", "nombre de chambre par service");
        donneesDiag.setItems(listeCBdonneesDiag);
    }

    /**
     * Initialisation de l'action lors d'un évènement sur la ComboBox medecin.
     * Elle recherche tous les patients du médecin selectionné.
     */
    public void actionCBMedecin() {
        medecin.setOnAction((event) -> {
            requete = medecin.getValue();
            try {

                requete = "SELECT DISTINCT m.nom\n"
                        + "FROM malade m, soigne s, employe e\n"
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

            int nbMalade = 0;
            ArrayList<String> liste2 = null;
            ArrayList<String> liste3 = null;

            try {
                requete = "SELECT COUNT(DISTINCT malade.no_malade) FROM malade malade, soigne soigne, employe employe WHERE employe.nom LIKE '" + medecin.getValue().substring(0, medecin.getValue().length() - 1) + "' AND employe.no_employe = soigne.no_docteur AND soigne.no_malade = malade.no_malade";
                liste = maconnexion.remplirChampsRequete(requete);
                nbMalade = Integer.parseInt(liste.get(0).substring(0, liste.get(0).length() - 1));
                System.out.println(nbMalade);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                requete = "SELECT malade.no_malade, malade.nom FROM malade malade, soigne soigne, employe employe WHERE employe.nom LIKE '" + medecin.getValue().substring(0, medecin.getValue().length() - 1) + "' AND employe.no_employe = soigne.no_docteur AND soigne.no_malade = malade.no_malade";
                liste = maconnexion.remplirChampsRequete(requete);
                requete = "SELECT malade.prenom FROM malade malade , soigne soigne, employe employe WHERE employe.nom LIKE '" + medecin.getValue().substring(0, medecin.getValue().length() - 1) + "' AND employe.no_employe = soigne.no_docteur AND soigne.no_malade = malade.no_malade";
                liste2 = maconnexion.remplirChampsRequete(requete);
                requete = "SELECT malade.nom FROM malade malade, soigne soigne, employe employe WHERE employe.nom LIKE '" + medecin.getValue().substring(0, medecin.getValue().length() - 1) + "' AND employe.no_employe = soigne.no_docteur AND soigne.no_malade = malade.no_malade";
                liste3 = maconnexion.remplirChampsRequete(requete);    
            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            listePatients(nbMalade, liste2, liste3);

        });
    }

    /**
     * Initialisation de l'action lors d'un évènement sur la ComboBox
     * spécialité. Elle envoie une requête avec comme critère la spécialité
     * selectionnée.
     */
    public void actionCBSpecialite() {
        specialite.setOnAction((event) -> {
            if (!specialite.getValue().equalsIgnoreCase("")) {
                docteur.setSpecialite(specialite.getValue().substring(0, specialite.getValue().length() - 1));
                requete(docteur);
            }
        });
    }

    /**
     * Initialisation de l'action lors d'un évènement sur la ComboBox service.
     * Elle envoie une requête avec comme critère le service selectionné.
     */
    public void actionCBService() {
        codeService.setOnAction((event) -> {
            if (!codeService.getValue().equalsIgnoreCase("")) {
                infirmier.setCode_service(codeService.getValue().substring(0, codeService.getValue().length() - 1));
                requete(infirmier);
            }
        });
    }

    /**
     * Initialisation de l'action lors d'un évènement sur la ComboBox rotation.
     * Elle envoie une requête avec comme critère la rotation selectionnée.
     */
    public void actionCBRotation() {
        rotation.setOnAction((event) -> {
            if (!rotation.getValue().equalsIgnoreCase("")) {
                infirmier.setRotation(rotation.getValue().substring(0, rotation.getValue().length() - 1));
                requete(infirmier);
            }
        });
    }

    /**
     * Initialisation de l'action lors d'un évènement sur la ComboBox mutuelle.
     * Elle envoie une requête avec comme critère la mutuelle selectionnée.
     */
    public void actionCBMutuelle() {
        mutuelle.setOnAction((event) -> {
            if (!mutuelle.getValue().equalsIgnoreCase("")) {
                malade.setMutuelle(mutuelle.getValue().substring(0, mutuelle.getValue().length() - 1));
                requete(malade);
            }
        });
    }

    /**
     * Initialisation de l'action lors d'un évènement sur la ComboBox chambre.
     * Elle envoie une requête avec comme critère le numéro de chambre
     * selectionné.
     */
    public void actionCBChambre() {
        numeroChambre.setOnAction((event) -> {
            if (!numeroChambre.getValue().equalsIgnoreCase("")) {
                hospitalisation.setNo_chambre(numeroChambre.getValue().substring(0, numeroChambre.getValue().length() - 1));
                requete(malade);
            }
        });
    }

    /**
     * Initialisation de l'action lors d'un évènement sur la ComboBox type. Elle
     * modifie l'affichage en fonction du type de personne selectionnée dans la
     * Combobox.
     *
     * @param type
     * @param gridCommune
     * @param gridMedecin
     * @param gridMalade
     * @param gridInfirmier
     * @param dateEntree
     * @param dateSortie
     */
    public void actionCBType(ComboBox<String> type, GridPane gridCommune, GridPane gridMedecin, GridPane gridMalade, GridPane gridInfirmier, DatePicker dateEntree, DatePicker dateSortie) {

        type.setOnAction((event) -> {
            requete = type.getValue();
            gridCommune.setVisible(false);
            gridMedecin.setVisible(false);
            gridMalade.setVisible(false);
            gridInfirmier.setVisible(false);
            gridCode1.setVisible(false);

            if ("docteur".equals(requete)) {
                gridCommune.setVisible(true);
                gridMedecin.setVisible(true);
                actionCBSpecialite();
                gridCode1.setVisible(true);
            }
            if ("infirmier".equals(requete)) {
                gridCommune.setVisible(true);
                gridInfirmier.setVisible(true);
                gridCode1.setVisible(true);
                actionCBService();
                actionCBRotation();
            }
            if ("malade".equals(requete)) {
                gridCommune.setVisible(true);
                gridMalade.setVisible(true);
                actionCBMutuelle();
                actionCBChambre();
                actionDatePicker(dateEntree, "entree");
                actionDatePicker(dateSortie, "sortie");
            }
        });

    }

    /**
     * Initialisation de l'action lors d'un évènement sur la ComboBox
     * donneesDiag. Elle affiche les diagrammes de reporting en fonction du
     * choix de la Combobox.
     */
    public void actionCBdonneesDiag() {
        donneesDiag.setOnAction((event) -> {
            if (donneesDiag.getValue().equalsIgnoreCase("Moyenne des salaires infirmiers par rotation")) {
                initGraph1();
                moySalInf();
            }
            if (donneesDiag.getValue().equalsIgnoreCase("Moyenne des salaires infirmiers par service")) {
                initGraph2();
                moySalInfServ();
            }
            if (donneesDiag.getValue().equalsIgnoreCase("Patient par service")) {
                initGraph3();
                patParServ();
            }
            if (donneesDiag.getValue().equalsIgnoreCase("nombre de chambre par service")) {
                initGraph4();
                nbChambreParServ();
            }
        });
    }

    /**
     * Initialisation de l'action lors d'un évènement sur un datepicker. Il
     * retourne la date selectionné sous forme de string.
     *
     * @param date
     * @param type
     */
    public void actionDatePicker(DatePicker date, String type) {
        String pattern = "yyyy-MM-dd";

        date.setPromptText(pattern.toLowerCase());
        date.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    if (type.equalsIgnoreCase("entree")) {
                        hospitalisation.setDateE(dateFormatter.format(date));
                        System.out.println("dateE : " + hospitalisation.getDateE());
                        requete(malade);
                    }
                    if (type.equalsIgnoreCase("sortie")) {
                        hospitalisation.setDateS(dateFormatter.format(date));
                        System.out.println("dateS : " + hospitalisation.getDateS());
                        requete(malade);
                    }
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * Méthode récupérant tous les patients dans la base de données.
     */
    @FXML
    public void listePatientsInit() {

        int nbMalade = 0;
        ArrayList<String> liste2 = null;
        ArrayList<String> liste3 = null;

        try {
            requete = "SELECT COUNT(DISTINCT no_malade) FROM malade";
            liste = maconnexion.remplirChampsRequete(requete);
            nbMalade = Integer.parseInt(liste.get(0).substring(0, liste.get(0).length() - 1));
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            requete = "SELECT no_malade, nom FROM malade";
            liste = maconnexion.remplirChampsRequete(requete);
            
            requete = "SELECT prenom FROM malade";
            liste2 = maconnexion.remplirChampsRequete(requete);
            requete = "SELECT nom FROM malade";
            liste3 = maconnexion.remplirChampsRequete(requete);
                
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listePatients(nbMalade, liste2, liste3);
    }

    /**
     * Méthode permettant d'afficher tous les patients dans l'onglet liste
     * patient avec les valeurs récupérer par listePatientInit().
     *
     * @param nbMalade
     * @param liste2
     * @param liste3
     */
    public void listePatients(int nbMalade, ArrayList<String> liste2, ArrayList<String> liste3) {

        String prenom, nom;
        int numMalade = 0;

        int pos = 0;
        PatientData = FXCollections.observableArrayList();
        
        for (int i = 0; i < nbMalade; i++) {

            for (int j = 0; j < 4; j++) {
                char a = liste.get(i).charAt(j);
                if (a == ',') {
                    pos = j;
                }
            }
            numMalade = Integer.parseInt(liste.get(i).substring(0, pos));

            prenom = liste2.get(i);
            nom = liste3.get(i);
            PatientData.add(new Malade(numMalade, prenom, nom));

        }
        cnum.setCellValueFactory(cellData -> cellData.getValue().getNumProperty().asObject());
        cprenom.setCellValueFactory(cellData -> cellData.getValue().getSurnameProperty());
        cnom.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        patients.setItems(PatientData);
       
    }

    /**
     * Méthode permettant remplir les champs d'un patient lorsque l'on clique
     * sur un patient dans liste patient.
     */
    @FXML
    public void selectPatient() {
        int num = 0;
        Malade mal = patients.getSelectionModel().selectedItemProperty().get();
        num = mal.getNum();
        try {
            requete = (maconnexion.remplirChampsRequete("SELECT DISTINCT code_service FROM hospitalisation WHERE " + num + " = no_malade")).toString();
            requete = requete.substring(1, requete.length() - 1);
            servicep.setText(requete);
            requete = (maconnexion.remplirChampsRequete("SELECT DISTINCT employe.nom FROM soigne soigne, employe employe WHERE " + num + " = soigne.no_malade AND employe.no_employe = soigne.no_docteur")).toString();
            requete = requete.substring(1, requete.length() - 1);
            medecinp.setText(requete);
            requete = (maconnexion.remplirChampsRequete("SELECT DISTINCT adresse FROM malade WHERE " + num + " = no_malade")).toString();
            requete = requete.substring(1, requete.length() - 1);
            adressep.setText(requete);
            requete = (maconnexion.remplirChampsRequete("SELECT DISTINCT tel FROM malade WHERE " + num + " = no_malade")).toString();
            requete = requete.substring(1, requete.length() - 1);
            telephonep.setText(requete);
            requete = (maconnexion.remplirChampsRequete("SELECT DISTINCT mutuelle FROM malade WHERE " + num + " = no_malade")).toString();
            requete = requete.substring(1, requete.length() - 1);
            mutuellep.setText(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Méthode utilisée pour avertir l'utilisateur que sa saisie est érronée.
     *
     * @param ressource
     * @param titre
     */
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
            popup.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode d'ajout d'une personne à la base de données.
     *
     * @param event
     */
    public void Ajouter(ActionEvent event) {
        ajout_modif = true;
        String str;
        int a = 0;
        int b = 0;
        String codemutuelle;

        numero1.setEditable(false);
        tabPane.getTabs().get(4).getContent().setDisable(false);
        tabPane.getSelectionModel().select(4);
        actionCBType(type1, gridCommune1, gridMedecin1, gridMalade1, gridInfirmier1, dateEntree1, dateSortie1);
        initCBType(type1, "docteur");
        initCBMutuelle(mutuelle1);
        initCBNumeroChambre(numeroChambre1);
        initCBSpecialite(specialite1);
        initCBRotation(rotation1);
        initCBCodeService(codeService1);
        String typeSelect = type1.getValue();
        if (typeSelect.equalsIgnoreCase("docteur") || typeSelect.equalsIgnoreCase("infirmier")) {
            typeRequete = "employe";
            codemutuelle = code1.getText();
        } else {
            typeRequete = "malade";
            codemutuelle = mutuelle1.getValue().substring(0, mutuelle1.getValue().length() - 1);
        }
        requete = "SELECT employe.no_employe FROM employe employe";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        str = liste.get(0);
        str = str.substring(0, str.length() - 1);
        a = Integer.parseInt(str);

        for (int i = 0; i < liste.size(); i++) {
            b = Integer.parseInt(liste.get(i).substring(0, liste.get(i).length() - 1));

            if (b > a) {
                a = b;
            }
        }
        
        requete = "SELECT malade.no_malade FROM malade malade";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < liste.size(); i++) {
            b = Integer.parseInt(liste.get(i).substring(0, liste.get(i).length() - 1));
            //System.out.println("VAL" + liste.get(i));

            if (b > a) {
                a = b;
            }
        }

        
        a = a + 1;
        numero1.setText(Integer.toString(a));

    }

    /**
     * Méthode permettant d'envoyer les requêtes d'ajout/modification à la base
     * de données. En fonction du type d'action à réaliser, Valider n'agit pas
     * de la même manière.
     *
     * @param event
     */
    public void Valider(ActionEvent event) {

        String typeSelect = type1.getValue();
        String codemutuelle;

        if (typeSelect.equalsIgnoreCase("docteur") || typeSelect.equalsIgnoreCase("infirmier")) {
            typeRequete = "employe";
            codemutuelle = code1.getText();
        } else {
            typeRequete = "malade";
            codemutuelle = mutuelle1.getValue().substring(0, mutuelle1.getValue().length() - 1);
        }
        // lorsque l'on veut ajouter une nouvelle ligne 
        if (ajout_modif == true) {

            requete = "INSERT INTO " + typeRequete + " VALUES ('" + numero1.getText() + "', '" + nom1.getText() + "', '" + prenom1.getText() + "', '" + adresse1.getText() + "', '" + telephone1.getText() + "', '" + codemutuelle + "')";
            System.out.println(requete);
            insertion(requete);

            if (typeSelect.equalsIgnoreCase("docteur")) {
                requete = "INSERT INTO  docteur  VALUES ('" + numero1.getText() + "', '" + specialite1.getValue().substring(0, specialite1.getValue().length() - 1) + "')";
                System.out.println(requete);
                insertion(requete);
            }
            if (typeSelect.equalsIgnoreCase("infirmier")) {
                requete = "INSERT INTO  infirmier  VALUES ('" + numero1.getText() + "', '" + codeService1.getValue().substring(0, codeService1.getValue().length() - 1) + "', '" + rotation1.getValue().substring(0, rotation1.getValue().length() - 1) + "', '" + salaire1.getText() + "')";
                System.out.println(requete);
                insertion(requete);
            }
            if (typeSelect.equalsIgnoreCase("malade")) {
                requete = "INSERT INTO  hospitalisation  VALUES ('" + numero1.getText() + "', '1', '" + numeroChambre1.getValue().substring(0, numeroChambre1.getValue().length() - 1) + "', '1', '" + hospitalisation.getDateE() + "', '" + hospitalisation.getDateS() + "')";
                System.out.println(requete);
                insertion(requete);
            }
        }
        //lorsque l'on veut modifier une ligne de la BDD
        if (ajout_modif == false) {

            requete = " UPDATE " + typeRequete + " SET no_" + typeRequete + " = '" + lala + "' , nom = '" + nom1.getText() + "' ,prenom = '" + prenom1.getText() + "' ,adresse = '" + adresse1.getText() + "' ,tel = '" + telephone1.getText() + "'  WHERE no_" + typeRequete + " =" + lala;
            System.out.println(requete);
            insertion(requete);

            if (typeSelect.equalsIgnoreCase("malade")) {
                requete = " UPDATE " + typeRequete + " SET mutuelle = '" + mutuelle1.getValue().substring(0, mutuelle1.getValue().length() - 1) + "'  WHERE no_" + typeRequete + " =" + lala;
                insertion(requete);
                System.out.println(requete);
                requete = " UPDATE hospitalisation SET no_chambre = '" + numeroChambre1.getValue().substring(0, numeroChambre1.getValue().length() - 1) + "', date_entree = '" + hospitalisation.getDateE() + "', date_sortie = '" + hospitalisation.getDateS() + "'  WHERE no_" + typeRequete + " =" + lala;
                System.out.println(requete);
                insertion(requete);
            }

            if (typeRequete.equalsIgnoreCase("employe")) {
                requete = " UPDATE " + typeRequete + " SET code = '" + code1.getText() + "'  WHERE no_" + typeRequete + " =" + lala;
                System.out.println(requete);
                insertion(requete);
            }

            if (typeSelect.equalsIgnoreCase("docteur")) {
                requete = " UPDATE " + typeSelect + " SET specialite = '" + specialite1.getValue().substring(0, specialite1.getValue().length() - 1) + "'  WHERE no_" + typeSelect + " =" + lala;
                System.out.println(requete);
                insertion(requete);

            }

            if (typeSelect.equalsIgnoreCase("infirmier")) {
                requete = " UPDATE " + typeSelect + " SET code_service = '" + codeService1.getValue().substring(0, codeService1.getValue().length() - 1) + "' , rotation = '" + rotation1.getValue().substring(0, rotation1.getValue().length() - 1) + "' , salaire = '" + salaire1.getText() + "' WHERE no_" + typeSelect + " =" + lala;
                System.out.println(requete);
                insertion(requete);

            }

        }
        mise_a_zero();
        tabPane.getTabs().get(4).getContent().setDisable(true); //Si on valide, on interdit l'accès à l'onglet directement
        tabPane.getSelectionModel().select(2);
        listePatientsInit();
    }

    /**
     * Méthode permettant de retourner dans l'onglet de recherche sans envoyer
     * la requête de modification/ajout.
     *
     * @param event
     */
    public void Annuler(ActionEvent event) {
        tabPane.getTabs().get(4).getContent().setDisable(true); //Si on annule, on interdit l'accès à l'onglet directement
        tabPane.getSelectionModel().select(2);
        mise_a_zero();

    }

    /**
     * Méthode de remise à zéro des champs de recherche et ComboBox et modification
     * lorsque l'on change de page.
     */
    @FXML
    public void mise_a_zero() {
        //Remise à 0 des combobox et textField
        specialite1.setValue(null);
        type1.setValue(null);
        mutuelle1.setValue(null);
        rotation1.setValue(null);
        codeService1.setValue(null);
        numeroChambre1.setValue(null);
        nom1.setText(null);
        prenom1.setText(null);
        numero1.setText(null);
        code1.setText(null);
        telephone1.setText(null);
        adresse1.setText(null);
        salaire1.setText(null);
        dateEntree1.setValue(null);
        dateSortie1.setValue(null);
        
        specialite.setValue("");
        mutuelle.setValue("");
        rotation.setValue("");
        numeroChambre.setValue("");
        nom.setText(null);
        prenom.setText(null);
        numero.setText(null);
        codeService.setValue("");
        dateEntree.setValue(null);
        dateSortie.setValue(null);

        docteur.setSpecialite(null);
        infirmier.setRotation(null);
        infirmier.setCode_service(null);
        malade.setMutuelle(null);
        hospitalisation.setNo_chambre(null);
        hospitalisation.setDateE(null);
        hospitalisation.setDateS(null);
    }

    /**
     * Méthode d'envoie des requetes d'ajout, de modification et de suppression
     * à la base de données.
     *
     * @param requete
     */
    public void insertion(String requete) {
        try {
            maconnexion.getStmt().executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Récupère la première ligne de l'arryliste de requête.
     *
     * @param requete
     * @return
     */
    public String list_get_zero(String requete) {
        try {
            liste = null;
            liste = maconnexion.remplirChampsRequete(requete);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (liste.isEmpty()) {
            System.out.println("liste vide");
            return " ";
        } else {
            return liste.get(0);
        }
    }

    /**
     * Méthode de modification de personne dans la base de données.
     *
     * @param event
     */
    public void Modifier(ActionEvent event) {
        ajout_modif = false;
        int j = 0;
        char a;
        numero1.setEditable(false);
        String typeSelect = type.getValue();
        for (int i = 0; i < 4; i++) {
            
            a = personneTemporaire.charAt(i);
            if (a == ',') {
                j = i;
            }
        }
        lala = personneTemporaire.substring(0, j);
        //System.out.println(lala);
        tabPane.getTabs().get(4).getContent().setDisable(false);
        tabPane.getSelectionModel().select(4);
        actionCBType(type1, gridCommune1, gridMedecin1, gridMalade1, gridInfirmier1, dateEntree1, dateSortie1);
        initCBType(type1, type.getValue());
        initCBMutuelle(mutuelle1);
        initCBNumeroChambre(numeroChambre1);
        initCBSpecialite(specialite1);
        initCBRotation(rotation1);
        initCBCodeService(codeService1);

        if (typeSelect.equalsIgnoreCase("docteur") || typeSelect.equalsIgnoreCase("infirmier")) {
            typeRequete = "employe";
        } else {
            typeRequete = "malade";
        }
        numero1.setText(lala);
        nom1.setText(list_get_zero("SELECT DISTINCT " + typeRequete + ".nom FROM employe employe, malade malade WHERE " + typeRequete + ".no_" + typeRequete + " = '" + lala + "'\n"));
        prenom1.setText(list_get_zero("SELECT DISTINCT " + typeRequete + ".prenom FROM employe employe, malade malade WHERE " + typeRequete + ".no_" + typeRequete + " = '" + lala + "'\n"));
        adresse1.setText(list_get_zero("SELECT DISTINCT " + typeRequete + ".adresse FROM employe employe, malade malade WHERE " + typeRequete + ".no_" + typeRequete + " = '" + lala + "'\n"));
        telephone1.setText(list_get_zero("SELECT DISTINCT " + typeRequete + ".tel FROM employe employe, malade malade WHERE " + typeRequete + ".no_" + typeRequete + " = '" + lala + "'\n"));

        if (typeSelect.equalsIgnoreCase("docteur")) {

            specialite1.setValue(list_get_zero("SELECT DISTINCT " + typeSelect + ".specialite FROM docteur docteur WHERE " + typeSelect + ".no_" + typeSelect + " = '" + lala + "'\n"));
            code1.setText(list_get_zero("SELECT DISTINCT " + typeRequete + ".code FROM employe employe, malade malade WHERE " + typeRequete + ".no_" + typeRequete + " = '" + lala + "'\n"));
        }

        if (typeSelect.equalsIgnoreCase("malade")) {
            mutuelle1.setValue(list_get_zero("SELECT DISTINCT " + typeSelect + ".mutuelle FROM malade malade WHERE " + typeSelect + ".no_" + typeSelect + " = '" + lala + "'\n"));
            numeroChambre1.setValue(list_get_zero("SELECT DISTINCT hospitalisation.no_chambre FROM hospitalisation hospitalisation WHERE hospitalisation.no_" + typeSelect + " = '" + lala + "'\n"));
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            if (!numeroChambre1.getValue().equalsIgnoreCase(" ")) {
                dateEntree1.setValue(LocalDate.parse(list_get_zero("SELECT DISTINCT hospitalisation.date_entree FROM hospitalisation hospitalisation WHERE hospitalisation.no_" + typeSelect + " = '" + lala + "'\n").substring(0, list_get_zero("SELECT DISTINCT hospitalisation.date_entree FROM hospitalisation hospitalisation WHERE hospitalisation.no_" + typeSelect + " = '" + lala + "'\n").length() - 1), dateFormatter));
                String result = list_get_zero("SELECT DISTINCT hospitalisation.date_sortie FROM hospitalisation hospitalisation WHERE hospitalisation.no_" + typeSelect + " = '" + lala + "'\n").substring(0, list_get_zero("SELECT DISTINCT hospitalisation.date_sortie FROM hospitalisation hospitalisation WHERE hospitalisation.no_" + typeSelect + " = '" + lala + "'\n").length() - 1);
                if (!result.equalsIgnoreCase("null")) {
                    dateSortie1.setValue(LocalDate.parse(result, dateFormatter));
                }
            }
        }
        if (typeSelect.equalsIgnoreCase("infirmier")) {
            rotation1.setValue(list_get_zero("SELECT DISTINCT infirmier.rotation FROM infirmier infirmier WHERE infirmier.no_" + typeSelect + " = '" + lala + "'\n"));
            codeService1.setValue(list_get_zero("SELECT DISTINCT infirmier.code_service FROM infirmier infirmier WHERE infirmier.no_" + typeSelect + " = '" + lala + "'\n"));
            code1.setText(list_get_zero("SELECT DISTINCT " + typeRequete + ".code FROM employe employe, malade malade WHERE " + typeRequete + ".no_" + typeRequete + " = '" + lala + "'\n"));
            salaire1.setText(list_get_zero("SELECT DISTINCT infirmier.salaire FROM infirmier infirmier WHERE infirmier.no_" + typeSelect + " = '" + lala + "'\n"));
        }

    }

    /**
     * Méthode de suppression d'une personne dans la base.
     *
     * @param event
     */
    public void Supprimer(ActionEvent event) {
        String typeSelect = type.getValue();
        int j = 0;
        char a;
        if (typeSelect.equalsIgnoreCase("docteur") || typeSelect.equalsIgnoreCase("infirmier")) {
            typeRequete = "employe";

        } else {
            typeRequete = "malade";

        }
        for (int i = 0; i < 4; i++) {
            System.out.println(i);
            a = personneTemporaire.charAt(i);
            if (a == ',') {
                j = i;
            }
        }
        lala = personneTemporaire.substring(0, j);

        requete = "DELETE FROM `" + typeRequete + "` WHERE `no_" + typeRequete + "`=" + lala;
        System.out.println(requete);
        insertion(requete);

        if (typeSelect.equalsIgnoreCase("docteur")) {
            requete = "DELETE FROM `docteur` WHERE `no_docteur`=" + lala;
            System.out.println(requete);
            insertion(requete);
        }
        if (typeSelect.equalsIgnoreCase("infirmier")) {
            requete = "DELETE FROM `infirmier` WHERE `no_infirmier`=" + lala;
            System.out.println(requete);
            insertion(requete);
        }
        if (typeSelect.equalsIgnoreCase("malade")) {
            requete = "DELETE FROM `hospitalisation` WHERE `no_malade`=" + lala;
            System.out.println(requete);
            insertion(requete);
        }

        listePatientsInit();
    }

    /**
     * Méthode de récupération d'une saisie clavier pour mettre la valeur dans
     * la classe correspondante.
     *
     * @param type
     * @param personne
     */
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

    /**
     * Méthode appelant actionTF pour un numéro.
     *
     * @param event
     */
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

    /**
     * Méthode appelant actionTF pour un nom.
     *
     * @param event
     */
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

    /**
     * Méthode appelant actionTF pour un prénom.
     *
     * @param event
     */
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
                    requete += docteur.getNumero() + "'\n";
                    break;
                case "infirmier":
                    requete += infirmier.getNumero() + "'\n";
                    break;
                case "malade":
                    requete += malade.getNumero() + "'\n";
                    break;
            }
        }
        if (nom.getLength() > 0) {
            requete += " AND " + typeRequete + ".nom LIKE '";
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
        }
        if (prenom.getLength() > 0) {
            requete += " AND " + typeRequete + ".prenom LIKE '";
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
        if (typeSelect.equalsIgnoreCase("malade") && hospitalisation.getDateE() != null) {
            requete += "AND malade.no_malade = hospitalisation.no_malade\n"
                    + "AND hospitalisation.date_entree ='" + hospitalisation.getDateE() + "'\n";
        }
        if (typeSelect.equalsIgnoreCase("malade") && hospitalisation.getDateS() != null) {
            requete += "AND malade.no_malade = hospitalisation.no_malade\n"
                    + "AND hospitalisation.date_sortie ='" + hospitalisation.getDateS() + "'\n";
        }

        System.out.println(requete);
        try {
            liste = maconnexion.remplirChampsRequete(requete);

            // afficher les lignes de la requete selectionnee a partir de la liste
            for (int i = 0; i < liste.size(); i++) {
                System.out.println(liste.get(i));
                personneTemporaire = liste.get(0);
            }
            resultat.setText("");
            for (String res : liste) {
                resultat.appendText(res + "\n");
            }
            //System.out.println(personneTemporaire);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initialise le graphique de moyenne des salaires par rotation.
     */
    public void initGraph1() {
        requete = "SELECT AVG(salaire) FROM infirmier WHERE rotation LIKE 'JOUR'";

        try {
            liste = maconnexion.remplirChampsRequete(requete);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        countInfJour = Float.parseFloat(liste.get(0).substring(0, liste.get(0).length() - 1));
        requete = "SELECT AVG(salaire) FROM infirmier WHERE rotation LIKE 'NUIT'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countInfNuit = Float.parseFloat(liste.get(0).substring(0, liste.get(0).length() - 1));
    }

    /**
     * Initialise le graphique de moyenne des salaires par service.
     */
    public void initGraph2() {
        requete = "SELECT AVG(salaire) FROM infirmier WHERE code_service LIKE 'REA'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countInfRea = Float.parseFloat(liste.get(0).substring(0, liste.get(0).length() - 1));
        requete = "SELECT AVG(salaire) FROM infirmier WHERE code_service LIKE 'CHG'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countInfChg = Float.parseFloat(liste.get(0).substring(0, liste.get(0).length() - 1));
        requete = "SELECT AVG(salaire) FROM infirmier WHERE code_service LIKE 'CAR'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countInfCar = Float.parseFloat(liste.get(0).substring(0, liste.get(0).length() - 1));
    }

    /**
     * Initialise le graphique de nombre de patients par service.
     */
    public void initGraph3() {
        requete = "SELECT COUNT(DISTINCT no_malade) FROM hospitalisation WHERE code_service LIKE 'CAR'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countCar = Integer.parseInt(liste.get(0).substring(0, liste.get(0).length() - 1));
        requete = "SELECT COUNT(DISTINCT no_malade) FROM hospitalisation WHERE code_service LIKE 'CHG'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countChg = Integer.parseInt(liste.get(0).substring(0, liste.get(0).length() - 1));
        requete = "SELECT COUNT(DISTINCT no_malade) FROM hospitalisation WHERE code_service LIKE 'REA'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countRea = Integer.parseInt(liste.get(0).substring(0, liste.get(0).length() - 1));
    }

    /**
     * Initialise le graphique de nombre de chambres par service.
     */
    public void initGraph4() {
        requete = "SELECT COUNT(no_chambre) FROM hospitalisation WHERE code_service LIKE 'CAR'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countChambreCar = Integer.parseInt(liste.get(0).substring(0, liste.get(0).length() - 1));
        requete = "SELECT COUNT(no_chambre) FROM hospitalisation WHERE code_service LIKE 'CHG'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countChambreChg = Integer.parseInt(liste.get(0).substring(0, liste.get(0).length() - 1));
        requete = "SELECT COUNT(no_chambre) FROM hospitalisation WHERE code_service LIKE 'REA'";
        try {
            liste = maconnexion.remplirChampsRequete(requete);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        countChambreRea = Integer.parseInt(liste.get(0).substring(0, liste.get(0).length() - 1));
    }

    /**
     * Méthode permettant de connaître le salaire moyen des infirmiers.
     */
    private Chart moySalInf() {
        graph.getData().clear();
        System.out.println(countInfJour);
        System.out.println(countInfNuit);
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Moyenne salaire infirmier par rotation");
        serie.getData().add(new XYChart.Data<String, Number>("Jour", countInfJour));
        serie.getData().add(new XYChart.Data<String, Number>("Nuit", countInfNuit));
        graph.getData().add(serie);
        return graph;
    }

    /**
     * Méthode permettant de connaître le salaire moyen des infirmier par
     * service (utilisé pour le reporting).
     *
     * @return
     */
    private Chart moySalInfServ() {
        graph.getData().clear();
        System.out.println(countInfJour);
        System.out.println(countInfNuit);
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Moyenne salaire infirmier par service");
        serie.getData().add(new XYChart.Data<String, Number>("cardiologie", countInfCar));
        serie.getData().add(new XYChart.Data<String, Number>("chirurgie", countInfChg));
        serie.getData().add(new XYChart.Data<String, Number>("reanimation", countInfRea));
        graph.getData().add(serie);
        return graph;
    }

    /**
     * Méthode permettant de connaître le nombre de patients par service.
     *
     * @return
     */
    private Chart patParServ() {

        graph.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Nombre de patients par service");
        serie.getData().add(new XYChart.Data<String, Number>("cardiologie", countCar));
        serie.getData().add(new XYChart.Data<String, Number>("chirurgie", countChg));
        serie.getData().add(new XYChart.Data<String, Number>("reanimation", countRea));
        graph.getData().add(serie);
        return graph;
    }

    /**
     * Méthode qui permet de connaître le nombre de chambre par service
     */
    private Chart nbChambreParServ() {
        System.out.println(countCar);
        graph.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Nombre de chambres par services");
        serie.getData().add(new XYChart.Data<String, Number>("cardiologie", countChambreCar));
        serie.getData().add(new XYChart.Data<String, Number>("chirurgie", countChambreChg));
        serie.getData().add(new XYChart.Data<String, Number>("reanimation", countChambreRea));
        graph.getData().add(serie);
        return graph;
    }

    /**
     * Provient de stackoverflow.com Sous programme permettant de ne pas avoir 
     * ses identifiants Campus et base de donnée en clair dans le code.
     * @param code
     * @return 
     */
    private String dechiffreur(String code) {
        StringBuilder codeClair = new StringBuilder("");
        for (int i = 0; i < code.length(); i += 2) {
            String str = code.substring(i, i + 2);
            codeClair.append((char) Integer.parseInt(str, 16));
        }
        return codeClair.toString();
    }
    
    /**
     * Méthode se lancant automatiquement au lancement du programme
     * Vérifie la connexion internet pour se connecter et se connecte en local le cas échéant.
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            maconnexion = new Connexion(dechiffreur("72656E79"), dechiffreur("4C5A6F613235373424"),
                    dechiffreur("72656E792D7277"), dechiffreur("486A4237564B3952"));
            if (maconnexion.getEstConnecte() == false) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("bonjour");
                maconnexion = new Connexion("avaj", "root", "root");
                System.out.println("connexion offline ok");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        gridCommune.setVisible(true);
        gridMedecin.setVisible(false);
        gridMalade.setVisible(false);
        gridInfirmier.setVisible(false);
        gridCommune1.setVisible(true);
        gridMedecin1.setVisible(false);
        gridMalade1.setVisible(false);
        gridInfirmier1.setVisible(false);
        
        actionCBType(type, gridCommune, gridMedecin, gridMalade, gridInfirmier, dateEntree, dateSortie);

        for (int i = 1; i <= 4; i++) {
            tabPane.getTabs().get(i).getContent().setDisable(true);
        }
    }

}
