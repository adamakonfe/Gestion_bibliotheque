package com.example.bibliotheque.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LivrePanel extends JPanel {
    // ...
    private JPanel detailsPanel; // Pour contrôle dynamique du panneau de détails

    // Champs de recherche
    public JTextField searchField;
    public JComboBox<String> searchCriteriaCombo;
    public JButton searchButton, listAllButton, listAvailableButton;
    public JButton showDetailsButton, showAddButton; // Nouveaux boutons

    // Tableau pour afficher les livres
    public JTable livreTable;
    public DefaultTableModel tableModel;

    // Champs de détails du livre
    public JTextField isbnField, titreField, auteurField, editeurField, anneeField, categorieField;

    // Boutons d'action
    public JButton addButton, updateButton, deleteButton, clearButton;

    public LivrePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau de recherche
        JPanel searchPanelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanelContainer.setBorder(BorderFactory.createTitledBorder(null, "Recherche de livres", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLUE));
        
        JPanel searchFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;

        gbcSearch.gridx = 0; gbcSearch.gridy = 0;
        searchFieldsPanel.add(new JLabel("Rechercher :"), gbcSearch);
        searchField = new JTextField(20);
        gbcSearch.gridx = 1; gbcSearch.gridy = 0;
        searchFieldsPanel.add(searchField, gbcSearch);

        String[] criteria = {"Titre", "Auteur", "ISBN", "Catégorie"};
        searchCriteriaCombo = new JComboBox<>(criteria);
        gbcSearch.gridx = 2; gbcSearch.gridy = 0;
        searchFieldsPanel.add(searchCriteriaCombo, gbcSearch);

        searchButton = new JButton("Rechercher");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        gbcSearch.gridx = 3; gbcSearch.gridy = 0;
        searchFieldsPanel.add(searchButton, gbcSearch);

        listAllButton = new JButton("Tous les livres");
        listAllButton.setBackground(new Color(52, 152, 219));
        listAllButton.setForeground(Color.WHITE);
        gbcSearch.gridx = 4; gbcSearch.gridy = 0;
        searchFieldsPanel.add(listAllButton, gbcSearch);

        listAvailableButton = new JButton("Livres disponibles");
        listAvailableButton.setBackground(new Color(46, 204, 113));
        listAvailableButton.setForeground(Color.WHITE);
        gbcSearch.gridx = 5; gbcSearch.gridy = 0;
        searchFieldsPanel.add(listAvailableButton, gbcSearch);
        
        searchPanelContainer.add(searchFieldsPanel);



        add(searchPanelContainer, BorderLayout.NORTH); // Add search panel directly to LivrePanel

        // Panneau central (liste et détails)
        // --- Affichage principal (liste + détails) ---
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder(null, "Liste des livres", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLUE));
        String[] columnNames = {"ISBN", "Titre", "Auteur", "Éditeur", "Année", "Catégorie", "Disponible"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition directe dans la table
            }
        };
        livreTable = new JTable(tableModel);
        livreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(livreTable);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        // Ajout des boutons sous la table
        JPanel listBottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        showDetailsButton = new JButton("Afficher Détails");
        showAddButton = new JButton("Ajouter Livre");
        listBottomButtonPanel.add(showDetailsButton);
        listBottomButtonPanel.add(showAddButton);
        listPanel.add(listBottomButtonPanel, BorderLayout.SOUTH);
        add(listPanel, BorderLayout.CENTER); // Affiche la table au centre

        // Panneau Détails du livre
        detailsPanel = new JPanel(new BorderLayout(0, 10)); // Use BorderLayout for detailsPanel
        JPanel formFieldsPanel = new JPanel(new GridBagLayout()); // New panel for form fields
        detailsPanel.setBorder(BorderFactory.createTitledBorder(null, "Détails du livre", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLUE));
        // gbc for formFieldsPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"ISBN:", "Titre:", "Auteur:", "Éditeur:", "Année:", "Catégorie:"};
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.anchor = GridBagConstraints.EAST;
            formFieldsPanel.add(new JLabel(labels[i]), gbc); // Add to formFieldsPanel
            fields[i] = new JTextField(30);
            gbc.gridx = 1; gbc.gridy = i; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
            formFieldsPanel.add(fields[i], gbc); // Add to formFieldsPanel
        }
        isbnField = fields[0];
        titreField = fields[1];
        auteurField = fields[2];
        editeurField = fields[3];
        anneeField = fields[4];
        categorieField = fields[5];

        // Panneau des boutons d'action
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(46, 204, 113)); // Vert
        addButton.setForeground(Color.WHITE);
        updateButton = new JButton("Modifier");
        updateButton.setBackground(new Color(52, 152, 219)); // Bleu
        updateButton.setForeground(Color.WHITE);
        deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(231, 76, 60)); // Rouge
        deleteButton.setForeground(Color.WHITE);
        clearButton = new JButton("Effacer");
        clearButton.setBackground(new Color(243, 156, 18)); // Jaune/Orange
        clearButton.setForeground(Color.WHITE);

        actionButtonPanel.add(addButton);
        actionButtonPanel.add(updateButton);
        actionButtonPanel.add(deleteButton);
        actionButtonPanel.add(clearButton);

        detailsPanel.add(formFieldsPanel, BorderLayout.CENTER);
        detailsPanel.add(actionButtonPanel, BorderLayout.SOUTH);
        add(detailsPanel, BorderLayout.SOUTH); // Ajoute les détails en bas

    }

    // Permet d'afficher ou masquer le panneau de détails dynamiquement
    public void showDetailsPanel(boolean visible) {
        detailsPanel.setVisible(visible);
        revalidate();
        repaint();
    }
}
