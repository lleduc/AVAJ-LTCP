/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author thomas
 */
public class FXMLMAJAjoutController extends FXMLRechercheController implements Initializable {
    @FXML
    private GridPane gridMedecin;
    @FXML
    private GridPane gridInfirmier;
    @FXML
    private GridPane gridMalade;
    @FXML
    private GridPane gridCommune;
   
     /* Initializes the controller class.
     */
    
    public void lala(){
         System.out.println(lala);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        //initCBType();
        //actionCBType();
        gridCommune.setVisible(true);
        gridMedecin.setVisible(false);
        gridMalade.setVisible(false);
        gridInfirmier.setVisible(false);
       
        if(ajout_modif==true)
        {
            lala();
            
        }
        
    }    
    
    
}
