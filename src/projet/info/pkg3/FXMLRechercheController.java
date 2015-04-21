/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.info.pkg3;

import jdbc2014.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.util.Base64;
import java.io.File;

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
    private void SearchBouton(ActionEvent event) {
        //initConnection();
        System.out.println("Rechercher");
        String result = searchBar.getText();
        System.out.println(result);
        ////////////////////////////////////////////////////////////
        try {
            ArrayList<String> liste = null;

            // recuperer la liste des lignes de la requete selectionnee
            result = "SELECT nom, prenom FROM employe  WHERE nom LIKE '"+result+"'";
            liste = maconnexion.remplirChampsRequete(result);

            // afficher les lignes de la requete selectionnee a partir de la liste
            for (int i = 0; i < liste.size(); i++) {
                System.out.println(liste.get(i));
            }
        } catch (SQLException e) {
            System.out.println("Echec SQL");
            e.printStackTrace();
        }
        //////////////////////////////////////////////////////////////
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    ///////////////////////////////////////////////////////////////////
    public void initConnection() {
        if (identifiant.getText().equals("Thomas") && MDP.getText().equals("c'est gagné!")) {
            try {              
                
                maconnexion = new Connexion(dechiffreur("72656E79"), dechiffreur("4C5A6F613235373424"),
                        dechiffreur("72656E792D7277"), dechiffreur("486A4237564B3952"));
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ProjetInfo3.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Essaye encore");
            identifiant.setText("");
            MDP.setText("");
        }

    }
    
    private String dechiffreur(String code)
{
    StringBuilder codeClair = new StringBuilder("");
    for (int i = 0; i < code.length(); i += 2)
    {
        String str = code.substring(i, i + 2);
        codeClair.append((char) Integer.parseInt(str, 16));
    }
    return codeClair.toString();
}
////////////////////////////////////////////////////////////////////////

}
