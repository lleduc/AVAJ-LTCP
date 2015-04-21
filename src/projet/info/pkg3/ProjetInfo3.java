/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.info.pkg3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import jdbc2014.Connexion;

/**
 *
 * @author thomas
 */
public class ProjetInfo3 extends Application {
    
    private Stage primaryStage;
    protected AnchorPane menu;
    ///////////////////////////////////////
    public Connexion maconnexion;
    ////////////////////////////////////////
    
    @Override
    public void start(Stage stage) throws Exception {
              
        this.primaryStage = stage;
        this.primaryStage.setTitle("AVAJ LTCP");
        
        initMenu();
        
    }

    public void initMenu() {
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ProjetInfo3.class.getResource("FXMLRecherchebis.fxml"));
            menu = (AnchorPane) loader.load();

            
            Scene scene = new Scene(menu);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //initConnection();
        launch(args);
    }
    
}
