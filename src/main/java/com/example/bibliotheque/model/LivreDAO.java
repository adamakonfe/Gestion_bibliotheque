package com.example.bibliotheque.model;

import com.example.bibliotheque.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {
    public static boolean ajouterLivre(Livre livre) {
        String sql = "INSERT INTO livre (isbn, titre, auteur, editeur, annee, categorie, disponible) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livre.getIsbn());
            stmt.setString(2, livre.getTitre());
            stmt.setString(3, livre.getAuteur());
            stmt.setString(4, livre.getEditeur());
            stmt.setInt(5, livre.getAnnee());
            stmt.setString(6, livre.getCategorie());
            stmt.setBoolean(7, livre.isDisponible());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modifierLivre(Livre livre) {
        String sql = "UPDATE livre SET titre=?, auteur=?, editeur=?, annee=?, categorie=?, disponible=? WHERE isbn=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getEditeur());
            stmt.setInt(4, livre.getAnnee());
            stmt.setString(5, livre.getCategorie());
            stmt.setBoolean(6, livre.isDisponible());
            stmt.setString(7, livre.getIsbn());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean supprimerLivre(String isbn) {
        String sql = "DELETE FROM livre WHERE isbn=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Livre> listerLivres() {
    return listerLivres(false);
}

public static List<Livre> listerLivresDisponibles() {
    return listerLivres(true);
}

private static List<Livre> listerLivres(boolean seulementDisponibles) {
    List<Livre> livres = new ArrayList<>();
    String sql = seulementDisponibles ? "SELECT * FROM livre WHERE disponible = 1" : "SELECT * FROM livre";
    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            Livre livre = new Livre(
                rs.getString("isbn"),
                rs.getString("titre"),
                rs.getString("auteur"),
                rs.getString("editeur"),
                rs.getInt("annee"),
                rs.getString("categorie"),
                rs.getBoolean("disponible")
            );
            livres.add(livre);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return livres;
}



    public static List<Livre> rechercherLivres(String champ, String valeur) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre WHERE " + champ + " LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + valeur + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Livre livre = new Livre(
                    rs.getString("isbn"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getString("editeur"),
                    rs.getInt("annee"),
                    rs.getString("categorie"),
                    rs.getBoolean("disponible")
                );
                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }
}
