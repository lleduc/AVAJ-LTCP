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
public class Infirmier extends Employé {
    
   private String code_service;
   private String rotation;
   private float salaire;
    
    public Infirmier()
    {
        
    }

    public String getCode_service() {
        return code_service;
    }

    public String getRotation() {
        return rotation;
    }

    public float getSalaire() {
        return salaire;
    }

    
    
}
