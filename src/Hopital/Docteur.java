/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hopital;

/**
 *
 * @author Fort
 */
public class Docteur extends Employe{
    
    private String specialite;
 
    
    public Docteur()
    {
        
    }
    
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String spécialite) {
        this.specialite = spécialite;
    }
    
   
}
