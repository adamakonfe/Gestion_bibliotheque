package com.example.bibliotheque.model;

import com.example.bibliotheque.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdherentDAO {

    public boolean ajouterAdherent(Adherent adherent) {
        String sql = "INSERT INTO adherent (nom, prenom, adresse, email, telephone, date_inscription, actif) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, adherent.getNom());
            stmt.setString(2, adherent.getPrenom());
            stmt.setString(3, adherent.getAdresse());
            stmt.setString(4, adherent.getEmail());
            stmt.setString(5, adherent.getTelephone());
            stmt.setDate(6, new java.sql.Date(adherent.getDateInscription() != null ? adherent.getDateInscription().getTime() : System.currentTimeMillis()));
            stmt.setBoolean(7, adherent.isActif());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        adherent.setIdAdherent(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifierAdherent(Adherent adherent) {
        String sql = "UPDATE adherent SET nom=?, prenom=?, adresse=?, email=?, telephone=?, date_inscription=?, actif=? WHERE id_adherent=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adherent.getNom());
            stmt.setString(2, adherent.getPrenom());
            stmt.setString(3, adherent.getAdresse());
            stmt.setString(4, adherent.getEmail());
            stmt.setString(5, adherent.getTelephone());
            stmt.setDate(6, new java.sql.Date(adherent.getDateInscription() != null ? adherent.getDateInscription().getTime() : System.currentTimeMillis()));
            stmt.setBoolean(7, adherent.isActif());
            stmt.setInt(8, adherent.getIdAdherent());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerAdherent(int idAdherent) {
        String sql = "DELETE FROM adherent WHERE id_adherent=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAdherent);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Gérer le cas où l'adhérent a des emprunts (contrainte de clé étrangère)
            // On pourrait afficher un message plus spécifique ou logger l'erreur différemment
            System.err.println("Erreur lors de la suppression de l'adhérent: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Adherent> listerAdherents() {
        List<Adherent> adherents = new ArrayList<>();
        String sql = "SELECT * FROM adherent ORDER BY nom, prenom";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Adherent adherent = new Adherent(
                    rs.getInt("id_adherent"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getDate("date_inscription"),
                    rs.getBoolean("actif")
                );
                adherents.add(adherent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherents;
    }

    public Adherent getAdherentById(int idAdherent) {
        String sql = "SELECT * FROM adherent WHERE id_adherent = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAdherent);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Adherent(
                    rs.getInt("id_adherent"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getDate("date_inscription"),
                    rs.getBoolean("actif")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Adherent> rechercherAdherents(String termeRecherche) {
        List<Adherent> adherents = new ArrayList<>();
        // Recherche sur nom, prenom ou email
        String sql = "SELECT * FROM adherent WHERE nom LIKE ? OR prenom LIKE ? OR email LIKE ? ORDER BY nom, prenom";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String recherche = "%" + termeRecherche + "%";
            stmt.setString(1, recherche);
            stmt.setString(2, recherche);
            stmt.setString(3, recherche);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Adherent adherent = new Adherent(
                    rs.getInt("id_adherent"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getDate("date_inscription"),
                    rs.getBoolean("actif")
                );
                adherents.add(adherent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherents;
    }
}
