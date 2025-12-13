package brasil.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import brasil.entity.Zone;
import brasil.repository.IZoneRepository;

public class ZoneRepositoryimpl implements IZoneRepository{
   
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
    }
    

