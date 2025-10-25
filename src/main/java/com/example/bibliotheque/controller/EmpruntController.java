package com.example.bibliotheque.controller;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.example.bibliotheque.model.Adherent;
import com.example.bibliotheque.model.AdherentDAO;
import com.example.bibliotheque.model.Emprunt;
import com.example.bibliotheque.model.EmpruntDAO;
import com.example.bibliotheque.model.Livre;
import com.example.bibliotheque.model.LivreDAO;
import com.example.bibliotheque.view.EmpruntPanel;

public class EmpruntController {
    private EmpruntPanel view;
    private EmpruntDAO dao;
    private AdherentDAO adherentDAO;
    private LivreDAO livreDAO;

    public EmpruntController(EmpruntPanel view, EmpruntDAO dao, AdherentDAO adherentDAO, LivreDAO livreDAO) {
        this.view = view;
        this.dao = dao;
        this.adherentDAO = adherentDAO;
        this.livreDAO = livreDAO;
        initController();
        loadCombos();
        loadEmprunts();
    }

    private void initController() {
        view.addButton.addActionListener(e -> ajouterEmprunt());
        view.returnButton.addActionListener(e -> rendreEmprunt());
        view.deleteButton.addActionListener(e -> supprimerEmprunt());
        view.clearButton.addActionListener(e -> clearFields());
        view.searchButton.addActionListener(e -> rechercherEmprunts());
        view.listAllButton.addActionListener(e -> loadEmprunts());
        view.onlyOngoingCheckBox.addActionListener(e -> loadEmprunts());
    }

    private void loadCombos() {
        view.adherentCombo.removeAllItems();
        List<Adherent> adherents = adherentDAO.listerAdherents();
        for (Adherent a : adherents) {
            view.adherentCombo.addItem(a.getIdAdherent() + " - " + a.getNom() + " " + a.getPrenom());
        }
        view.livreCombo.removeAllItems();
        List<Livre> livres = livreDAO.listerLivresDisponibles();
        for (Livre l : livres) {
            view.livreCombo.addItem(l.getIsbn() + " - " + l.getTitre());
        }
    }

    private void loadEmprunts() {
        List<Emprunt> emprunts = dao.listerEmprunts();
        view.tableModel.setRowCount(0);
        boolean onlyOngoing = view.onlyOngoingCheckBox.isSelected();

        for (Emprunt e : emprunts) {
            if (!onlyOngoing || "En cours".equalsIgnoreCase(e.getStatut())) {
                view.tableModel.addRow(new Object[]{
                    e.getId(),
                    e.getAdherentNom(),
                    e.getLivreTitre(),
                    e.getDateEmprunt(),
                    e.getDateRetourPrevue(),
                    e.getDateRetourEffectif(),
                    e.getStatut()
                });
            }
        }
    }

    private void ajouterEmprunt() {
        try {
            String adherentStr = (String) view.adherentCombo.getSelectedItem();
            String livreStr = (String) view.livreCombo.getSelectedItem();
            if (adherentStr == null || livreStr == null) {
                JOptionPane.showMessageDialog(view, "Sélectionnez un adhérent et un livre.");
                return;
            }
            int adherentId = Integer.parseInt(adherentStr.split(" - ")[0]);
            String livreIsbn = livreStr.split(" - ")[0];
            java.util.Date utilDateEmprunt = (java.util.Date) view.dateEmpruntPicker.getModel().getValue();
            java.util.Date utilDateRetourPrevue = (java.util.Date) view.dateRetourPrevuePicker.getModel().getValue();
            if (utilDateEmprunt == null || utilDateRetourPrevue == null) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner les deux dates.");
                return;
            }
            Date dateEmprunt = new java.sql.Date(utilDateEmprunt.getTime());
            Date dateRetourPrevue = new java.sql.Date(utilDateRetourPrevue.getTime());
            String statut = (String) view.statutCombo.getSelectedItem();

            Emprunt emprunt = new Emprunt(0, adherentId, "", livreIsbn, "", dateEmprunt, dateRetourPrevue, null, statut);
            if (dao.ajouterEmprunt(emprunt)) {
                JOptionPane.showMessageDialog(view, "Emprunt ajouté !");
                loadEmprunts();
                loadCombos();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur lors de l'ajout.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Une erreur est survenue lors de l'ajout de l'emprunt : " + ex.getClass().getSimpleName() + (ex.getMessage() != null ? (" - " + ex.getMessage()) : ""));
        }
    }

    private void rendreEmprunt() {
        int selectedRow = view.empruntTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Sélectionnez un emprunt à rendre.");
            return;
        }
        int empruntId = (int) view.tableModel.getValueAt(selectedRow, 0);
        try {
            // La date de retour réelle n'est plus saisie par l'utilisateur.
            // Utiliser la date du jour comme date de retour réelle :
            Date dateRetour = new java.sql.Date(System.currentTimeMillis());
            if (dao.rendreEmprunt(empruntId, dateRetour)) {
                JOptionPane.showMessageDialog(view, "Livre rendu !");
                loadEmprunts();
                loadCombos();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur lors du retour.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    private void supprimerEmprunt() {
        int selectedRow = view.empruntTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Sélectionnez un emprunt à supprimer.");
            return;
        }
        int empruntId = (int) view.tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(view, "Confirmer la suppression ?", "Suppression", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.supprimerEmprunt(empruntId)) {
                JOptionPane.showMessageDialog(view, "Emprunt supprimé !");
                loadEmprunts();
                loadCombos();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur lors de la suppression.");
            }
        }
    }

    private void rechercherEmprunts() {
        String critere = (String) view.searchCriteriaCombo.getSelectedItem();
        String valeur = view.searchField.getText();
        List<Emprunt> emprunts = dao.rechercherEmprunts(critere, valeur);
        view.tableModel.setRowCount(0);
        for (Emprunt e : emprunts) {
            view.tableModel.addRow(new Object[]{
                e.getId(),
                e.getAdherentNom(),
                e.getLivreTitre(),
                e.getDateEmprunt(),
                e.getDateRetourPrevue(),
                e.getDateRetourEffectif(),
                e.getStatut()
            });
        }
    }

    private void clearFields() {
        view.adherentCombo.setSelectedIndex(-1);
        view.livreCombo.setSelectedIndex(-1);
        view.dateEmpruntPicker.getModel().setValue(null);
        view.dateRetourPrevuePicker.getModel().setValue(null);
        view.statutCombo.setSelectedIndex(0);
        view.empruntTable.clearSelection();
    }
}
