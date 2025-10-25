package com.example.bibliotheque.model;

import java.util.Date;

public class Adherent {
    private int idAdherent;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Date dateInscription;
    private boolean actif;

    // Constructeur complet
    public Adherent(int idAdherent, String nom, String prenom, String adresse, String email, String telephone, Date dateInscription, boolean actif) {
        this.idAdherent = idAdherent;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.dateInscription = dateInscription;
        this.actif = actif;
    }

    // Constructeur sans idAdherent (pour les nouveaux adhérents avant insertion en BD)
    public Adherent(String nom, String prenom, String adresse, String email, String telephone, Date dateInscription, boolean actif) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.dateInscription = dateInscription;
        this.actif = actif;
    }
    
    // Constructeur vide (si nécessaire)
    public Adherent() {}

    // Getters
    public int getIdAdherent() {
        return idAdherent;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public boolean isActif() {
        return actif;
    }

    // Setters
    public void setIdAdherent(int idAdherent) {
        this.idAdherent = idAdherent;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " (ID: " + idAdherent + ")";
    }
}
