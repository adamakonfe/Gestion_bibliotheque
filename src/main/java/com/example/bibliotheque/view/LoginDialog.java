package com.example.bibliotheque.view;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton loginButton;
    public JButton cancelButton;
    private boolean succeeded;

    public LoginDialog(Frame parent) {
        super(parent, "Connexion", true);

        // Panneau principal avec rembourrage
        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau des champs avec bordure titrée
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBorder(BorderFactory.createTitledBorder(null, "Informations de connexion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLUE));
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(10, 10, 10, 10);

        JLabel lbUsername = new JLabel("Nom d'utilisateur:");
        cs.gridx = 0; cs.gridy = 0; cs.gridwidth = 1;
        fieldsPanel.add(lbUsername, cs);

        usernameField = new JTextField(20);
        cs.gridx = 1; cs.gridy = 0; cs.gridwidth = 2;
        fieldsPanel.add(usernameField, cs);

        JLabel lbPassword = new JLabel("Mot de passe:");
        cs.gridx = 0; cs.gridy = 1; cs.gridwidth = 1;
        fieldsPanel.add(lbPassword, cs);

        passwordField = new JPasswordField(20);
        cs.gridx = 1; cs.gridy = 1; cs.gridwidth = 2;
        fieldsPanel.add(passwordField, cs);

        // Boutons stylisés
        loginButton = new JButton("Se Connecter");
        loginButton.setBackground(new Color(46, 204, 113)); // Vert
        loginButton.setForeground(Color.WHITE);

        cancelButton = new JButton("Annuler");
        cancelButton.setBackground(new Color(231, 76, 60)); // Rouge
        cancelButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }
}
