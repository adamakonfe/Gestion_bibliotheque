package com.example.bibliotheque.model;

import com.example.bibliotheque.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpruntDAO {
    public boolean ajouterEmprunt(Emprunt emprunt) {
        String sql = "INSERT INTO emprunt (adherent_id, livre_isbn, date_emprunt, date_retour_prevue, statut) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprunt.getAdherentId());
            stmt.setString(2, emprunt.getLivreIsbn());
            stmt.setDate(3, new java.sql.Date(emprunt.getDateEmprunt().getTime()));
            stmt.setDate(4, new java.sql.Date(emprunt.getDateRetourPrevue().getTime()));
            stmt.setString(5, emprunt.getStatut());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rendreEmprunt(int empruntId, Date dateRetourReelle) {
        String sql = "UPDATE emprunt SET statut = 'Rendu', date_retour_effectif = ? WHERE id_emprunt = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(dateRetourReelle.getTime()));
            stmt.setInt(2, empruntId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerEmprunt(int empruntId) {
        String sql = "DELETE FROM emprunt WHERE id_emprunt = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empruntId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Emprunt> listerEmprunts() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.*, a.nom AS adherentNom, l.titre AS livreTitre FROM emprunt e " +
                     "JOIN adherent a ON e.adherent_id = a.id_adherent " +
                     "JOIN livre l ON e.livre_isbn = l.isbn";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Emprunt emprunt = new Emprunt(
                    rs.getInt("id_emprunt"),
                    rs.getInt("adherent_id"),
                    rs.getString("adherentNom"),
                    rs.getString("livre_isbn"),
                    rs.getString("livreTitre"),
                    rs.getDate("date_emprunt"),
                    rs.getDate("date_retour_prevue"),
                    rs.getDate("date_retour_effectif"),
                    rs.getString("statut")
                );
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    public List<Emprunt> rechercherEmprunts(String critere, String valeur) {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.*, a.nom AS adherentNom, l.titre AS livreTitre FROM emprunt e " +
                     "JOIN adherent a ON e.adherent_id = a.id_adherent " +
                     "JOIN livre l ON e.livre_isbn = l.isbn WHERE ";
        switch (critere) {
            case "Adhérent":
                sql += "a.nom LIKE ?";
                break;
            case "Livre":
                sql += "l.titre LIKE ?";
                break;
            case "Statut":
                sql += "e.statut LIKE ?";
                break;
            default:
                sql += "a.nom LIKE ?";
        }
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + valeur + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Emprunt emprunt = new Emprunt(
                        rs.getInt("id_emprunt"),
                        rs.getInt("adherent_id"),
                        rs.getString("adherentNom"),
                        rs.getString("livre_isbn"),
                        rs.getString("livreTitre"),
                        rs.getDate("date_emprunt"),
                        rs.getDate("date_retour_prevue"),
                        rs.getDate("date_retour_effectif"),
                        rs.getString("statut")
                    );
                    emprunts.add(emprunt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }
}
