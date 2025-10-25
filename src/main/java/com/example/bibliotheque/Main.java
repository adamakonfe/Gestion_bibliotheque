package com.example.bibliotheque;

import com.example.bibliotheque.controller.LivreController;
import com.example.bibliotheque.controller.LoginController;
import com.example.bibliotheque.model.LivreDAO;
import com.example.bibliotheque.view.LoginDialog;
import com.example.bibliotheque.view.LivrePanel;
import com.example.bibliotheque.model.AdherentDAO;
import com.example.bibliotheque.view.AdherentPanel;
import com.example.bibliotheque.controller.AdherentController;
import com.example.bibliotheque.model.EmpruntDAO;
import com.example.bibliotheque.view.EmpruntPanel;
import com.example.bibliotheque.controller.EmpruntController;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Appliquer un look and feel plus moderne si disponible (Nimbus)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus n'est pas disponible, Swing utilisera le look and feel par défaut
            e.printStackTrace();
        }

        // Création de la fenêtre principale
        SwingUtilities.invokeLater(() -> {
            // Afficher la boîte de dialogue de connexion d'abord
            LoginDialog loginDialog = new LoginDialog(null); // null car pas de parent JFrame au début
            new LoginController(loginDialog);
            loginDialog.setVisible(true);

            // Si la connexion n'est pas réussie, quitter l'application
            if (!loginDialog.isSucceeded()) {
                System.exit(0);
            }

            // Si la connexion est réussie, continuer avec la fenêtre principale
            JFrame frame = new JFrame("Gestion de Bibliothèque");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null); // Centrer la fenêtre

            // Panneau de titre général
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setBackground(new Color(26, 108, 163)); // Bleu foncé
            JLabel mainTitleLabel = new JLabel("Système de Gestion de Bibliothèque", SwingConstants.CENTER);
            mainTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            mainTitleLabel.setForeground(Color.WHITE);
            mainTitleLabel.setBorder(BorderFactory.createEmptyBorder(10,0,5,0));
            titlePanel.add(mainTitleLabel, BorderLayout.CENTER);

            JLabel subTitleLabel = new JLabel("Gérez efficacement vos livres, adhérents et emprunts", SwingConstants.CENTER);
            subTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            subTitleLabel.setForeground(Color.WHITE);
            subTitleLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
            titlePanel.add(subTitleLabel, BorderLayout.SOUTH);
            
            frame.add(titlePanel, BorderLayout.NORTH);

            // Création du JTabbedPane principal
            JTabbedPane tabbedPane = new JTabbedPane();

            // Onglet Livres
            // ATTENTION : Il faut toujours instancier le contrôleur avec le même panel qui sera ajouté au tabbedPane !
            LivrePanel livrePanel = new LivrePanel();
            new LivreController(livrePanel, new LivreDAO());
            tabbedPane.addTab("Livres", null, livrePanel, "Gestion des livres");

            // Onglet Adhérents
            AdherentPanel adherentPanel = new AdherentPanel();
            new AdherentController(adherentPanel, new AdherentDAO());
            tabbedPane.addTab("Adhérents", null, adherentPanel, "Gestion des adhérents");

            // Onglet Emprunts
            EmpruntPanel empruntPanel = new EmpruntPanel();
            new EmpruntController(empruntPanel, new EmpruntDAO(), new AdherentDAO(), new LivreDAO());
            tabbedPane.addTab("Emprunts", null, empruntPanel, "Gestion des emprunts");

            frame.add(tabbedPane, BorderLayout.CENTER); // Ajoute le JTabbedPane à la fenêtre
            tabbedPane.setSelectedIndex(0); // Affiche toujours l'onglet Livres au démarrage

            // Menu Bar (comme sur l'image)
            JMenuBar menuBar = new JMenuBar();
            JMenu fichierMenu = new JMenu("Fichier");
            JMenuItem quitterItem = new JMenuItem("Quitter");
            quitterItem.addActionListener(e -> System.exit(0));
            fichierMenu.add(quitterItem);
            menuBar.add(fichierMenu);

            JMenu aideMenu = new JMenu("Aide");
            JMenuItem aproposItem = new JMenuItem("À propos");
            aproposItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, 
                "Application de Gestion de Bibliothèque\nVersion 1.0\nDéveloppée avec Java Swing et MVC", 
                "À propos", JOptionPane.INFORMATION_MESSAGE));
            aideMenu.add(aproposItem);
            menuBar.add(aideMenu);
            frame.setJMenuBar(menuBar);

            frame.setVisible(true);
        });
    }
}
