/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connexion;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author loulou
 */
public class Main {
         public static void main(String[] args) /*throws SQLException, ClassNotFoundException */{
             
       
             try {
                 Connexion conn = new Connexion("lleduc","LL-30elise","lleduc-rw","T2buw8os");
             } catch (SQLException ex) {
                 Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    
}
