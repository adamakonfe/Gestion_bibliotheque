package com.example.bibliotheque.view;

import javax.swing.*;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.util.Properties;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmpruntPanel extends JPanel {
    public JCheckBox onlyOngoingCheckBox;
    // Champs du formulaire
    public JComboBox<String> adherentCombo;
    public JComboBox<String> livreCombo;
    public JDatePickerImpl dateEmpruntPicker;
    public JDatePickerImpl dateRetourPrevuePicker;

    public JComboBox<String> statutCombo;

    // Champs de recherche
    public JTextField searchField;
    public JComboBox<String> searchCriteriaCombo;
    public JButton searchButton, listAllButton;

    // Tableau pour afficher les emprunts
    public JTable empruntTable;
    public DefaultTableModel tableModel;

    // Boutons d'action
    public JButton addButton, returnButton, deleteButton, clearButton;

    public EmpruntPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau de recherche
        JPanel searchPanelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanelContainer.setBorder(BorderFactory.createTitledBorder(null, "Recherche d'emprunts", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLUE));

        JPanel searchFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;

        gbcSearch.gridx = 0; gbcSearch.gridy = 0;
        searchFieldsPanel.add(new JLabel("Rechercher :"), gbcSearch);
        searchField = new JTextField(20);
        gbcSearch.gridx = 1; gbcSearch.gridy = 0;
        searchFieldsPanel.add(searchField, gbcSearch);

        String[] criteria = {"Adhérent", "Livre", "Statut"};
        searchCriteriaCombo = new JComboBox<>(criteria);
        gbcSearch.gridx = 2; gbcSearch.gridy = 0;
        searchFieldsPanel.add(searchCriteriaCombo, gbcSearch);

        searchButton = new JButton("Rechercher");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        gbcSearch.gridx = 3; gbcSearch.gridy = 0;
        searchFieldsPanel.add(searchButton, gbcSearch);

        listAllButton = new JButton("Tous les emprunts");
        listAllButton.setBackground(new Color(52, 152, 219));
        listAllButton.setForeground(Color.WHITE);
        gbcSearch.gridx = 4; gbcSearch.gridy = 0;
        searchFieldsPanel.add(listAllButton, gbcSearch);

        // Ajout de la case à cocher pour filtrer les emprunts en cours
        JCheckBox onlyOngoingCheckBox = new JCheckBox("Afficher seulement les emprunts en cours");
        onlyOngoingCheckBox.setSelected(false);
        gbcSearch.gridx = 5; gbcSearch.gridy = 0;
        searchFieldsPanel.add(onlyOngoingCheckBox, gbcSearch);
        this.onlyOngoingCheckBox = onlyOngoingCheckBox;

        searchPanelContainer.add(searchFieldsPanel);
        add(searchPanelContainer, BorderLayout.NORTH);

        // Panneau central (liste et détails)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Panneau Liste des emprunts
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder(null, "Liste des emprunts", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLUE));
        String[] columnNames = {"ID", "Adhérent", "Livre", "Date Emprunt", "Retour Prévu", "Retour Effectif", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        empruntTable = new JTable(tableModel);
        empruntTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(empruntTable);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(listPanel);

        // Panneau Détails de l'emprunt
        JPanel detailsPanel = new JPanel(new BorderLayout(0, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder(null, "Détails de l'emprunt", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLUE));
        JPanel formFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; formFieldsPanel.add(new JLabel("Adhérent :"), gbc);
        adherentCombo = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 0; formFieldsPanel.add(adherentCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formFieldsPanel.add(new JLabel("Livre :"), gbc);
        livreCombo = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 1; formFieldsPanel.add(livreCombo, gbc);

        // Initialisation du JDatePicker pour Date Emprunt
        gbc.gridx = 0; gbc.gridy = 2; formFieldsPanel.add(new JLabel("Date Emprunt :"), gbc);
        UtilDateModel modelEmprunt = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Aujourd'hui");
        p.put("text.month", "Mois");
        p.put("text.year", "Année");
        JDatePanelImpl panelEmprunt = new JDatePanelImpl(modelEmprunt, p);
        dateEmpruntPicker = new JDatePickerImpl(panelEmprunt, new org.jdatepicker.impl.DateComponentFormatter());
        gbc.gridx = 1; gbc.gridy = 2; formFieldsPanel.add(dateEmpruntPicker, gbc);

        // Initialisation du JDatePicker pour Retour Prévu
        gbc.gridx = 0; gbc.gridy = 3; formFieldsPanel.add(new JLabel("Retour Prévu :"), gbc);
        UtilDateModel modelRetour = new UtilDateModel();
        JDatePanelImpl panelRetour = new JDatePanelImpl(modelRetour, p);
        dateRetourPrevuePicker = new JDatePickerImpl(panelRetour, new org.jdatepicker.impl.DateComponentFormatter());
        gbc.gridx = 1; gbc.gridy = 3; formFieldsPanel.add(dateRetourPrevuePicker, gbc);

        gbc.gridx = 0; gbc.gridy = 5; formFieldsPanel.add(new JLabel("Statut :"), gbc);
        statutCombo = new JComboBox<>(new String[]{"En cours", "Rendu"});
        gbc.gridx = 1; gbc.gridy = 5; formFieldsPanel.add(statutCombo, gbc);

        // Panneau des boutons d'action
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Nouvel emprunt");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        returnButton = new JButton("Rendre");
        returnButton.setBackground(new Color(241, 196, 15));
        returnButton.setForeground(Color.WHITE);
        deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        clearButton = new JButton("Effacer");
        clearButton.setBackground(new Color(243, 156, 18));
        clearButton.setForeground(Color.WHITE);

        actionButtonPanel.add(addButton);
        actionButtonPanel.add(returnButton);
        actionButtonPanel.add(deleteButton);
        actionButtonPanel.add(clearButton);

        detailsPanel.add(formFieldsPanel, BorderLayout.CENTER);
        detailsPanel.add(actionButtonPanel, BorderLayout.SOUTH);
        centerPanel.add(detailsPanel);

        add(centerPanel, BorderLayout.CENTER);
    }
}
