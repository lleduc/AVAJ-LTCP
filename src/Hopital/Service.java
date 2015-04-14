/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hopital;

/**
 *
 * @author thomas
 */
public class Service {
    private String code;
    private String nom;
    private char batiment;
    private int directeur;

    public Service() 
    {
        
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public char getBatiment() {
        return batiment;
    }

    public int getDirecteur() {
        return directeur;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setBatiment(char batiment) {
        this.batiment = batiment;
    }

    public void setDirecteur(int directeur) {
        this.directeur = directeur;
    }
    
    
    
}
