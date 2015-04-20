/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package premier.essai.graphique;

import Connexion.*;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author thomas
 */
public class PremierEssaiGraphique extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui-test.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("AVAJ LTCP");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        private String usernameECE="lleduc";
        private String passwordECE="LL-30elise";
        private String loginDatabase = "lleduc-rw";
        private String passwordDatabase= "T2buw8os";
        Connexion conn = new Connexion(usernameECE,passwordECE,loginDatabase,passwordDatabase);
        launch(args);
    }
    
}
