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
public class Malade extends Personne {
    
    private String mutuelle;
    
    public Malade()
    {
        
    }

    
    public String getMutuelle() {
        return mutuelle;
    }

       public void setMutuelle(String mutuelle) {
        this.mutuelle = mutuelle;
    }
    
    
}
