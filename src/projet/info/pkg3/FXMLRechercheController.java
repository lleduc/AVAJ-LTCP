/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.info.pkg3;

import java.awt.Label;
import java.awt.event.MouseEvent;
import jdbc2014.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author thomas
 */
public class FXMLRechercheController extends ProjetInfo3 implements Initializable {

    @FXML
    private TextField searchBar;
    @FXML
    private TextField identifiant;
    @FXML
    private TextField MDP;
    @FXML
    private ComboBox<String> medecin, type;
    @FXML
    Label nom, prenom;
    ObservableList<String> listeCBMedecin, listeCBType;
    ArrayList<String> liste = null;
    public String requete;

    @FXML
    private void SearchBouton(ActionEvent event) {

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

    /*@FXML
    private void CBMedecin(MouseEvent event) {
    try {
    requete = "SELECT m.nom\n"
    + "FROM malade m, docteur d, soigne s\n"
    + "WHERE d.nom LIKE '" + medecin.getValue() + "' AND d.numero = s.no_docteur AND s.no_malade = m.numero\n"
    + "ORDER BY nom\n";
    liste = maconnexion.remplirChampsRequete(requete);
    // afficher les lignes de la requete selectionnee a partir de la liste
    for (int i = 0; i < liste.size(); i++) {
    System.out.println(liste.get(i));
    }
    } catch (SQLException ex) {
    Logger.getLogger(FXMLRechercheController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }*/

    public void initConnection() {
        if (identifiant.getText().equals("") && MDP.getText().equals("")) {
            try {

                maconnexion = new Connexion(dechiffreur("72656E79"), dechiffreur("4C5A6F613235373424"),
                        dechiffreur("72656E792D7277"), dechiffreur("486A4237564B3952"));
                initCBMedecin();
                initCBType();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ProjetInfo3.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Essaye encore");
            identifiant.setText("");
            MDP.setText("");
        }

    }

    public void initCBMedecin() {

        try {
            requete = "SELECT e.nom\n"
                    + "FROM employe e, docteur d\n"
                    + "WHERE e.numero = d.numero\n"
                    + "ORDER BY nom\n";
            liste = maconnexion.remplirChampsRequete(requete);
            listeCBMedecin = FXCollections.observableArrayList(liste);
            medecin.setItems(listeCBMedecin);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRechercheController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void initCBType() {
            listeCBType = FXCollections.observableArrayList("docteur", "infirmier", "malade");
            type.setItems(listeCBType);
    }

    private String dechiffreur(String code) {
        StringBuilder codeClair = new StringBuilder("");
        for (int i = 0; i < code.length(); i += 2) {
            String str = code.substring(i, i + 2);
            codeClair.append((char) Integer.parseInt(str, 16));
        }
        return codeClair.toString();
    }
////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        medecin.setOnAction((event) -> {
            requete = medecin.getValue();
            System.out.println("ComboBox Action (selected: " + medecin.getValue().substring(0, medecin.getValue().length()-1)+")");
            
            try {
                
             requete = "SELECT DISTINCT m.nom\n"
             + "FROM malade m, docteur d, soigne s, employe e\n"
             + "WHERE e.nom LIKE '" + medecin.getValue().substring(0, medecin.getValue().length()-1) + "' AND e.numero = s.no_docteur AND s.no_malade = m.numero\n"
             + "ORDER BY nom\n";
             liste = maconnexion.remplirChampsRequete(requete);
             // afficher les lignes de la requete selectionnee a partir de la liste
             for (int i = 0; i < liste.size(); i++) {
             System.out.println(liste.get(i));
             }
             } catch (SQLException ex) {
             Logger.getLogger(FXMLRechercheController.class.getName()).log(Level.SEVERE, null, ex);
             }
            
        });
        
        //////////////////////////////////////////////////////////////
        
        /////////////////////////////////////////////////////////////
    }
}
