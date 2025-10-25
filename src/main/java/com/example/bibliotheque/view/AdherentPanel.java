package com.example.bibliotheque.view;

import com.example.bibliotheque.model.Adherent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class AdherentPanel extends JPanel {
    // Champs du formulaire

    public JTextField nomField;
    public JTextField prenomField;
    public JTextField adresseField;
    public JTextField emailField;
    public JTextField telephoneField;
    public JCheckBox actifCheckBox;
    public JTextField dateInscriptionField; // Sera affiché, non modifiable directement ici

    // Boutons d'action
    public JButton addButton;
    public JButton updateButton;
    public JButton deleteButton;
    public JButton clearButton;

    // Table et modèle
    public JTable adherentTable;
    public DefaultTableModel tableModel;

    // Panneau de recherche
    public JTextField searchField;
    public JButton searchButton;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public AdherentPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // Panneau de recherche en haut
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");
        searchPanel.add(new JLabel("Rechercher Adhérent:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Panneau central avec liste et détails
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 ligne, 2 colonnes (liste | détails)

        // Panneau de la liste des adhérents (à gauche)
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Liste des Adhérents"));
        tableModel = new DefaultTableModel(new Object[]{ "Nom", "Prénom", "Email", "Actif"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition directe dans la table
            }
        };
        adherentTable = new JTable(tableModel);
        listPanel.add(new JScrollPane(adherentTable), BorderLayout.CENTER);
        centerPanel.add(listPanel);

        // Panneau des détails de l'adhérent (à droite)
        JPanel detailsPanel = new JPanel(new BorderLayout(0, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Détails de l'Adhérent"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nom
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; nomField = new JTextField(15);
        formPanel.add(nomField, gbc);

        // Prénom
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; prenomField = new JTextField(15);
        formPanel.add(prenomField, gbc);

        // Adresse
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Adresse:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; adresseField = new JTextField(15);
        formPanel.add(adresseField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; emailField = new JTextField(15);
        formPanel.add(emailField, gbc);

        // Téléphone
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(new JLabel("Téléphone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; telephoneField = new JTextField(15);
        formPanel.add(telephoneField, gbc);
        
        // Date d'inscription (non modifiable)
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(new JLabel("Inscrit le:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; dateInscriptionField = new JTextField(15); dateInscriptionField.setEditable(false);
        formPanel.add(dateInscriptionField, gbc);

        // Statut Actif
        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(new JLabel("Actif:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; actifCheckBox = new JCheckBox(); actifCheckBox.setSelected(true);
        formPanel.add(actifCheckBox, gbc);
        
        // Pour pousser les champs vers le haut
        gbc.gridx = 0; gbc.gridy = 8; gbc.weighty = 1.0; gbc.gridwidth = 2;
        formPanel.add(new JPanel(), gbc); // Panel vide pour occuper l'espace

        detailsPanel.add(formPanel, BorderLayout.CENTER);

        // Panneau des boutons d'action
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(46, 204, 113));
        updateButton = new JButton("Modifier");
        updateButton.setBackground(new Color(52, 152, 219));
        deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(231, 76, 60)); 
        clearButton = new JButton("Effacer");
        clearButton.setBackground(new Color(243, 156, 18)); 
        actionButtonPanel.add(addButton);
        actionButtonPanel.add(updateButton);
        actionButtonPanel.add(deleteButton);
        actionButtonPanel.add(clearButton);
        detailsPanel.add(actionButtonPanel, BorderLayout.SOUTH);

        centerPanel.add(detailsPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void loadAdherents(List<Adherent> adherents) {
        tableModel.setRowCount(0); // Vider la table
        for (Adherent adherent : adherents) {
            tableModel.addRow(new Object[]{
                adherent.getIdAdherent(),
                adherent.getNom(),
                adherent.getPrenom(),
                adherent.getEmail(),
                adherent.isActif() ? "Oui" : "Non"
            });
        }
    }

    public void displayAdherent(Adherent adherent) {
        if (adherent != null) {
            nomField.setText(adherent.getNom());
            prenomField.setText(adherent.getPrenom());
            adresseField.setText(adherent.getAdresse());
            emailField.setText(adherent.getEmail());
            telephoneField.setText(adherent.getTelephone());
            if (adherent.getDateInscription() != null) {
                 dateInscriptionField.setText(dateFormat.format(adherent.getDateInscription()));
            } else {
                dateInscriptionField.setText("");
            }
            actifCheckBox.setSelected(adherent.isActif());
        } else {
            clearFields();
        }
    }

    public void clearFields() {
        nomField.setText("");
        prenomField.setText("");
        adresseField.setText("");
        emailField.setText("");
        telephoneField.setText("");
        dateInscriptionField.setText("");
        actifCheckBox.setSelected(true);
        adherentTable.clearSelection();
    }

    public int getSelectedAdherentId() {
        int selectedRow = adherentTable.getSelectedRow();
        if (selectedRow >= 0) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1; // Cette méthode reste utile pour la sélection, même si l'ID n'est plus affiché dans le formulaire

    }
}
