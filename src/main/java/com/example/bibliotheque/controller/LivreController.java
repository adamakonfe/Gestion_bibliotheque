package com.example.bibliotheque.controller;

import com.example.bibliotheque.model.Livre;
import com.example.bibliotheque.model.LivreDAO;
import com.example.bibliotheque.view.LivrePanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LivreController {
    private LivrePanel view;
    private LivreDAO model;

    public LivreController(LivrePanel view, LivreDAO model) {
        this.view = view;
        this.model = model;
        initController();
        loadLivres();
    }

    private void initController() {
        view.addButton.addActionListener(e -> ajouterLivre());
        view.updateButton.addActionListener(e -> modifierLivre());
        view.deleteButton.addActionListener(e -> supprimerLivre());
        view.clearButton.addActionListener(e -> clearFields());
        view.searchButton.addActionListener(e -> rechercherLivre());
        view.listAllButton.addActionListener(e -> {
            loadLivres();
            view.showDetailsPanel(false);
        });

        // Bouton "Afficher Détails" pour réafficher le panneau de détails
        view.showDetailsButton.addActionListener(e -> view.showDetailsPanel(true));

        // Bouton "Ajouter Livre" pour afficher le panneau de détails vidé
        view.showAddButton.addActionListener(e -> {
            view.showDetailsPanel(true);
            clearFields();
        });
        view.listAvailableButton.addActionListener(e -> listerLivresDisponibles());

        view.livreTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.livreTable.getSelectedRow() != -1) {
                fillFieldsFromTable();
                view.showDetailsPanel(true);
            }
        });
    }

    private void loadLivres() {
        System.out.println("[DEBUG] Bouton Tous les livres cliqué");
        List<Livre> livres = model.listerLivres();
        System.out.println("[DEBUG] Nombre de livres récupérés : " + livres.size());
        for (Livre l : livres) {
            System.out.println("[DEBUG] Livre : " + l.getTitre() + " (" + l.getIsbn() + ")");
        }

        view.tableModel.setRowCount(0); // Clear existing data
        for (Livre livre : livres) {
            view.tableModel.addRow(new Object[]{
                livre.getIsbn(),
                livre.getTitre(),
                livre.getAuteur(),
                livre.getEditeur(),
                livre.getAnnee(),
                livre.getCategorie(),
                livre.isDisponible() ? "Oui" : "Non"
            });
        }
    }

    private void listerLivresDisponibles() {
    System.out.println("[DEBUG] Bouton Livres disponibles cliqué");
    List<Livre> livres = model.listerLivresDisponibles();
    view.tableModel.setRowCount(0); 
    for (Livre livre : livres) {
        view.tableModel.addRow(new Object[]{
            livre.getIsbn(), livre.getTitre(), livre.getAuteur(),
            livre.getEditeur(), livre.getAnnee(), livre.getCategorie(),
            "Oui"
        });
    }
}

    private void ajouterLivre() {
        try {
            String isbn = view.isbnField.getText();
            String titre = view.titreField.getText();
            String auteur = view.auteurField.getText();
            String editeur = view.editeurField.getText();
            int annee = Integer.parseInt(view.anneeField.getText());
            String categorie = view.categorieField.getText();
            // Par défaut, un nouveau livre est disponible

            if (isbn.isEmpty() || titre.isEmpty()) {
                JOptionPane.showMessageDialog(view, "ISBN et Titre sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Livre livre = new Livre(isbn, titre, auteur, editeur, annee, categorie, true);
            if (model.ajouterLivre(livre)) {
                JOptionPane.showMessageDialog(view, "Livre ajouté avec succès !");
                loadLivres();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur lors de l'ajout du livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "L'année doit être un nombre valide.", "Erreur de Format", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Une erreur s'est produite: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierLivre() {
        int selectedRow = view.livreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Sélectionnez un livre à modifier.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            String isbn = view.isbnField.getText(); // ISBN ne devrait pas être modifiable ici, mais on le récupère pour l'update
            String titre = view.titreField.getText();
            String auteur = view.auteurField.getText();
            String editeur = view.editeurField.getText();
            int annee = Integer.parseInt(view.anneeField.getText());
            String categorie = view.categorieField.getText();
            // La disponibilité peut être gérée différemment (ex: via emprunts)
            // Pour cet exemple, on suppose qu'on peut la modifier manuellement si besoin.
            // On récupère la dispo actuelle du livre sélectionné pour la modification.
            boolean disponible = model.listerLivres().stream().filter(l -> l.getIsbn().equals(isbn)).findFirst().map(Livre::isDisponible).orElse(true);

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Le Titre est obligatoire.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Livre livre = new Livre(isbn, titre, auteur, editeur, annee, categorie, disponible);
            if (model.modifierLivre(livre)) {
                JOptionPane.showMessageDialog(view, "Livre modifié avec succès !");
                loadLivres();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur lors de la modification du livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "L'année doit être un nombre valide.", "Erreur de Format", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Une erreur s'est produite: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerLivre() {
        int selectedRow = view.livreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Sélectionnez un livre à supprimer.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String isbn = (String) view.livreTable.getValueAt(selectedRow, 0);
        int confirmation = JOptionPane.showConfirmDialog(view, "Êtes-vous sûr de vouloir supprimer ce livre ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            if (model.supprimerLivre(isbn)) {
                JOptionPane.showMessageDialog(view, "Livre supprimé avec succès !");
                loadLivres();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur lors de la suppression du livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void rechercherLivre() {
    System.out.println("[DEBUG] Bouton Rechercher cliqué");
        String champ = view.searchCriteriaCombo.getSelectedItem().toString().toLowerCase();
        // Mapper le nom d'affichage du JComboBox aux noms de colonnes de la DB
        switch (champ) {
            case "titre": champ = "titre"; break;
            case "auteur": champ = "auteur"; break;
            case "isbn": champ = "isbn"; break;
            case "catégorie": champ = "categorie"; break;
            default: champ = "titre"; // Recherche par titre par défaut
        }
        String valeur = view.searchField.getText();
        List<Livre> livres = model.rechercherLivres(champ, valeur);
        view.tableModel.setRowCount(0); // Clear existing data
        for (Livre livre : livres) {
            view.tableModel.addRow(new Object[]{
                livre.getIsbn(), livre.getTitre(), livre.getAuteur(),
                livre.getEditeur(), livre.getAnnee(), livre.getCategorie(),
                livre.isDisponible() ? "Oui" : "Non"
            });
        }
    }

    private void clearFields() {
        view.isbnField.setText("");
        view.titreField.setText("");
        view.auteurField.setText("");
        view.editeurField.setText("");
        view.anneeField.setText("");
        view.categorieField.setText("");
        view.livreTable.clearSelection();
        view.isbnField.setEditable(true); // Rendre l'ISBN éditable pour un nouveau livre
    }

    private void fillFieldsFromTable() {
        int selectedRow = view.livreTable.getSelectedRow();
        if (selectedRow != -1) {
            view.isbnField.setText(String.valueOf(view.livreTable.getValueAt(selectedRow, 0)));
            view.titreField.setText(String.valueOf(view.livreTable.getValueAt(selectedRow, 1)));
            view.auteurField.setText(String.valueOf(view.livreTable.getValueAt(selectedRow, 2)));
            view.editeurField.setText(String.valueOf(view.livreTable.getValueAt(selectedRow, 3)));
            view.anneeField.setText(String.valueOf(view.livreTable.getValueAt(selectedRow, 4)));
            view.categorieField.setText(String.valueOf(view.livreTable.getValueAt(selectedRow, 5)));
            // Optionnel : afficher la disponibilité si tu veux
            view.isbnField.setEditable(false); // Empêcher la modification de l'ISBN
        }
    }
}
