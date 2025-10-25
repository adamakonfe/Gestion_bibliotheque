package com.example.bibliotheque.controller;

import com.example.bibliotheque.model.Adherent;
import com.example.bibliotheque.model.AdherentDAO;
import com.example.bibliotheque.view.AdherentPanel;
import javax.swing.*;
import java.util.Date;
import java.util.List;

public class AdherentController {
    private AdherentPanel view;
    private AdherentDAO model;

    public AdherentController(AdherentPanel view, AdherentDAO model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        // Listeners pour les boutons
        view.addButton.addActionListener(e -> ajouterAdherent());
        view.updateButton.addActionListener(e -> modifierAdherent());
        view.deleteButton.addActionListener(e -> supprimerAdherent());
        view.clearButton.addActionListener(e -> view.clearFields());
        view.searchButton.addActionListener(e -> rechercherAdherents());

        // Listener pour la sélection dans la table
        view.adherentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedId = view.getSelectedAdherentId();
                if (selectedId != -1) {
                    Adherent adherent = model.getAdherentById(selectedId);
                    view.displayAdherent(adherent);
                }
            }
        });

        // Charger les adhérents au démarrage
        loadAdherents();
    }

    private void loadAdherents() {
        List<Adherent> adherents = model.listerAdherents();
        view.loadAdherents(adherents);
    }

    private void rechercherAdherents() {
        String termeRecherche = view.searchField.getText();
        if (termeRecherche.trim().isEmpty()) {
            loadAdherents();
        } else {
            List<Adherent> adherents = model.rechercherAdherents(termeRecherche);
            view.loadAdherents(adherents);
        }
    }

    private void ajouterAdherent() {
        try {
            String nom = view.nomField.getText();
            String prenom = view.prenomField.getText();
            String adresse = view.adresseField.getText();
            String email = view.emailField.getText();
            String telephone = view.telephoneField.getText();
            // La date d'inscription est gérée par la BDD ou au moment de l'ajout
            boolean actif = view.actifCheckBox.isSelected();

            if (nom.isEmpty() || prenom.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nom et Prénom sont obligatoires.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Adherent adherent = new Adherent(nom, prenom, adresse, email, telephone, new Date(), actif); // new Date() pour la date d'inscription actuelle
            
            if (model.ajouterAdherent(adherent)) {
                JOptionPane.showMessageDialog(view, "Adhérent ajouté avec succès ! ID: " + adherent.getIdAdherent(), "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadAdherents();
                view.clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur lors de l'ajout de l'adhérent.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Une erreur s'est produite: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void modifierAdherent() {
        try {
            int id = view.getSelectedAdherentId();
            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un adhérent à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nom = view.nomField.getText();
            String prenom = view.prenomField.getText();
            String adresse = view.adresseField.getText();
            String email = view.emailField.getText();
            String telephone = view.telephoneField.getText();
            boolean actif = view.actifCheckBox.isSelected();
            
            // Récupérer la date d'inscription existante pour ne pas la modifier ici
            // ou la laisser être mise à jour si la logique métier le permet.
            // Pour cet exemple, on ne la modifie pas directement via le formulaire.
            Adherent adherentExistant = model.getAdherentById(id);
            Date dateInscription = (adherentExistant != null) ? adherentExistant.getDateInscription() : new Date();

            if (nom.isEmpty() || prenom.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nom et Prénom sont obligatoires.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Adherent adherent = new Adherent( nom, prenom, adresse, email, telephone, dateInscription, actif);
            if (model.modifierAdherent(adherent)) {
                JOptionPane.showMessageDialog(view, "Adhérent modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadAdherents();
                view.clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur lors de la modification de l'adhérent.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "L'ID de l'adhérent est invalide.", "Erreur de Format", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Une erreur s'est produite: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void supprimerAdherent() {
        int selectedId = view.getSelectedAdherentId();
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un adhérent à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(view, 
            "Êtes-vous sûr de vouloir supprimer cet adhérent ?\nCela pourrait échouer s'il a des emprunts en cours.", 
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (model.supprimerAdherent(selectedId)) {
                JOptionPane.showMessageDialog(view, "Adhérent supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadAdherents();
                view.clearFields();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Erreur lors de la suppression de l'adhérent.\nIl se peut que cet adhérent ait des emprunts actifs.", 
                    "Erreur de suppression", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
