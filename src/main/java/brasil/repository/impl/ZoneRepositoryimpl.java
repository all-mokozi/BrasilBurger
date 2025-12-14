package brasil.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import brasil.entity.Zone;
import brasil.repository.IZoneRepository;

public class ZoneRepositoryimpl implements IZoneRepository {

    private final Connection conn;

    public ZoneRepositoryimpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Zone b) {

        String sql = "INSERT INTO zone (nom, prix_livraison) VALUES (?, ?) ";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, b.getNom());
            ps.setDouble(2, b.getPrixLivraison());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        b.setId(rs.getInt(1));
                        System.out.println("Zone insérée avec succès. ID: " + b.getId());
                    }
                }
            } else {

                throw new SQLException("L'insertion du Zone a échoué, aucune ligne affectée.");
            }

        } catch (SQLException e) {

            System.err.println("Erreur SQL lors de l'insertion du Zone.");
            e.printStackTrace();

            throw new RuntimeException("Impossible d'insérer le Zone dans la base de données.", e);
        }
    }

    public List<Zone> selectAll() {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT * FROM zone";

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Zone zone = new Zone();
                zone.setId(rs.getInt("id"));
                zone.setNom(rs.getString("nom"));
                zone.setPrixLivraison(rs.getDouble("prix_livraison"));

                zones.add(zone);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des zones : " + e.getMessage());
        }

        return zones;
    }
public Zone selectZoneById(int id) {
    String sql = "SELECT * FROM zone WHERE id = ?";
    Zone zone = null;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                zone = new Zone();
                zone.setId(rs.getInt("id"));
                zone.setNom(rs.getString("nom"));
                zone.setPrixLivraison(rs.getDouble("prix_livraison"));
            }
        }

    } catch (SQLException e) {
        System.err.println("Erreur lors de la récupération de la zone : " + e.getMessage());
    }

    return zone;
}


}
