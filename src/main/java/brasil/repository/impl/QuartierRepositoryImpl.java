package brasil.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import brasil.entity.Quartier;
import brasil.entity.Zone;
import brasil.repository.IQuartierRepository;

public class QuartierRepositoryImpl implements IQuartierRepository {

    private final Connection conn;

    public QuartierRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Quartier b, Zone z) {

        String sql = "INSERT INTO quartier (nom, zone_id) VALUES (?, ?) ";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, b.getNom());
            ps.setInt(2, z.getId()); // 

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        b.setId(rs.getInt(1));
                        System.out.println("Quartier inséré avec succès. ID: " + b.getId()); 
                    }
                }
            } else {

                throw new SQLException("L'insertion du Quartier a échoué, aucune ligne affectée.");
            }

        } catch (SQLException e) {

            System.err.println("Erreur SQL lors de l'insertion du Quartier."); 
            e.printStackTrace();

            throw new RuntimeException("Impossible d'insérer le Quartier dans la base de données.", e); 
        }
    }

    @Override
    public List<Quartier> selectAllQuartierByZone(int zoneId) {

        List<Quartier> quartiers = new ArrayList<>();

      String sql = "SELECT q.id, q.nom, z.id AS zone_id, z.nom AS zone_nom, z.prix_livraison " + 
                 "FROM quartier q " +
                 "JOIN zone z ON q.zone_id = z.id " + 
                 "WHERE q.zone_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, zoneId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Zone zone = new Zone();
                    zone.setId(rs.getInt("zone_id"));
                    zone.setNom(rs.getString("zone_nom"));
                    zone.setPrixLivraison(rs.getDouble("prix_livraison")); 

                    Quartier quartier = new Quartier();
                    quartier.setId(rs.getInt("id"));
                    quartier.setNom(rs.getString("nom"));

                    quartier.setZone(zone);

                    quartiers.add(quartier);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des quartiers par zone : " + e.getMessage());
           
        }

        return quartiers;
    }

      public List<Quartier> selectAll() {
        List<Quartier>  quartiers = new ArrayList<>();
        String sql = "SELECT * FROM quartier";

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Quartier quartier = new Quartier();
                quartier.setId(rs.getInt("id"));
                quartier.setNom(rs.getString("nom"));
                

                quartiers.add(quartier);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des zones : " + e.getMessage());
        }

        return quartiers;
    }
}