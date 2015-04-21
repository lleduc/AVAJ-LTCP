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
public class Employé {
    private int numéro;
    private String nom;
    private String prénom;
    private String adresse;
    private String tel;

    
    
    public Employé()
    {
        
    }
    
    
    public int getNuméro() {
        return numéro;
    }

    public String getNom() {
        return nom;
    }

    public String getPrénom() {
        return prénom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setNuméro(int numéro) {
        this.numéro = numéro;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrénom(String prénom) {
        this.prénom = prénom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
    
    
    
}
