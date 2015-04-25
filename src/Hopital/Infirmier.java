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
public class Infirmier extends Employe {
    
   private String code_service;
   private String rotation;
  
    
    public Infirmier()
    {
        
    }

    public String getCode_service() {
        return code_service;
    }

    public String getRotation() {
        return rotation;
    }

    public void setCode_service(String code_service) {
        this.code_service = code_service;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    
    
}
