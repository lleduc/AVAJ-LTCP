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
public class Hospitalisation {
    private int no_malade;
    private String code_service;
    private int no_chambre = 0;
    private int lit;
    
    public Hospitalisation()
    {
        
    }

    public int getNo_malade() {
        return no_malade;
    }

    public String getCode_service() {
        return code_service;
    }

    public int getNo_chambre() {
        return no_chambre;
    }

    public int getLit() {
        return lit;
    }

    public void setNo_malade(int no_malade) {
        this.no_malade = no_malade;
    }

    public void setCode_service(String code_service) {
        this.code_service = code_service;
    }

    public void setNo_chambre(int no_chambre) {
        this.no_chambre = no_chambre;
    }

    public void setLit(int lit) {
        this.lit = lit;
    }
    
    
}
