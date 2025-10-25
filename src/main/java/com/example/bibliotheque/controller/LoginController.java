package com.example.bibliotheque.controller;

import com.example.bibliotheque.view.LoginDialog;
import javax.swing.*;

public class LoginController {
    private LoginDialog view;

    public LoginController(LoginDialog view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.loginButton.addActionListener(e -> authenticate());
        view.cancelButton.addActionListener(e -> cancelLogin());
    }

    private void authenticate() {
        // Logique d'authentification basique
        // TODO: Remplacer par une vraie authentification (ex: BDD)
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.equals("admin") && password.equals("admin")) { // Identifiants codés en dur
            JOptionPane.showMessageDialog(view, "Connexion réussie !", "Login", JOptionPane.INFORMATION_MESSAGE);
            view.setSucceeded(true);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Nom d'utilisateur ou mot de passe incorrect.", "Login Échoué", JOptionPane.ERROR_MESSAGE);
            view.setSucceeded(false);
            // Garder la fenêtre de login ouverte
        }
    }

    private void cancelLogin() {
        view.setSucceeded(false);
        view.dispose();
        System.exit(0); // Quitter l'application si l'utilisateur annule la connexion
    }
}
