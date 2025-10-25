package com.example.bibliotheque.model;

import java.util.Date;

public class Emprunt {
    private int id;
    private int adherentId;
    private String adherentNom;
    private String livreIsbn;
    private String livreTitre;
    private Date dateEmprunt;
    private Date dateRetourPrevue;
    private Date dateRetourEffectif;
    private String statut;

    public Emprunt(int id, int adherentId, String adherentNom, String livreIsbn, String livreTitre,
                   Date dateEmprunt, Date dateRetourPrevue, Date dateRetourEffectif, String statut) {
        this.id = id;
        this.adherentId = adherentId;
        this.adherentNom = adherentNom;
        this.livreIsbn = livreIsbn;
        this.livreTitre = livreTitre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourEffectif = dateRetourEffectif;
        this.statut = statut;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAdherentId() { return adherentId; }
    public void setAdherentId(int adherentId) { this.adherentId = adherentId; }
    public String getAdherentNom() { return adherentNom; }
    public void setAdherentNom(String adherentNom) { this.adherentNom = adherentNom; }
    public String getLivreIsbn() { return livreIsbn; }
    public void setLivreIsbn(String livreIsbn) { this.livreIsbn = livreIsbn; }
    public String getLivreTitre() { return livreTitre; }
    public void setLivreTitre(String livreTitre) { this.livreTitre = livreTitre; }
    public Date getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(Date dateEmprunt) { this.dateEmprunt = dateEmprunt; }
    public Date getDateRetourPrevue() { return dateRetourPrevue; }
    public void setDateRetourPrevue(Date dateRetourPrevue) { this.dateRetourPrevue = dateRetourPrevue; }
    public Date getDateRetourEffectif() { return dateRetourEffectif; }
    public void setDateRetourEffectif(Date dateRetourEffectif) { this.dateRetourEffectif = dateRetourEffectif; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return "Emprunt{" +
                "id=" + id +
                ", adherentId=" + adherentId +
                ", adherentNom='" + adherentNom + '\'' +
                ", livreIsbn='" + livreIsbn + '\'' +
                ", livreTitre='" + livreTitre + '\'' +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourEffectif=" + dateRetourEffectif +
                ", statut='" + statut + '\'' +
                '}';
    }
}
