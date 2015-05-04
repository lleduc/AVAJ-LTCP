/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hopital;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author thomas
 */
public class Personne {
    
    private int numero = 0;
    private String nom = "";
    private String prenom = "";
    private String adresse = "";
    private String tel = "";
    
    //////////////////////////////
    
   
    private  SimpleIntegerProperty num;
    private  SimpleStringProperty name;
    private  SimpleStringProperty surname;

    public Personne(int num, String name, String surname) {
        this.num = new SimpleIntegerProperty(num);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
    }
    
    ///////////////////////////////////////

    public Personne() {
        
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
     /////////////////////////////////////////////////////
    
    
    public SimpleIntegerProperty getNumProperty() {
        return num;
    }
    public void setNum(SimpleIntegerProperty num) {
        this.num = num;
    }
    public int getNum() {
        return num.get();
    }
    public void setNum(int num) {
        this.num.set(num);
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }
    public void setName(SimpleStringProperty name) {
        this.name = name;
    }
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    
    

    public SimpleStringProperty getSurnameProperty() {
        return surname;
    }
    public void setSurnameProperty(SimpleStringProperty surname) {
        this.surname = surname;
    }
    public String getSurname() {
        return surname.get();
    }
    public void setSurname(String surname){
        this.surname.set(surname);
    }

    ////////////////////////////////////////////////////
    
}
