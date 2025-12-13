package brasil.repository.impl;

import brasil.entity.Produit;
import brasil.enumeration.ProdEnum;
import brasil.repository.IProduitRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProduitRepositoryimpl implements IProduitRepository {

    private final Connection conn;

    public ProduitRepositoryimpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Produit p) {

        String sql = "INSERT INTO produit (nom, prix, image, description, type_produit, is_archived) VALUES (?, ?, ?, ?, ?, ?) ";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.setString(3, p.getImage());

            ps.setString(4, p.getDescription());

            ps.setString(5, p.getCategorie().toString()); // Décalé à l'index 5
            ps.setBoolean(6, p.isArchive());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setId(rs.getInt(1));
                        System.out.println("Produit inséré avec succès. ID: " + p.getId());
                    }
                }
            } else {

                throw new SQLException("L'insertion du Produit a échoué, aucune ligne affectée.");
            }

        } catch (SQLException e) {

            System.err.println("Erreur SQL lors de l'insertion du Produit.");

            throw new RuntimeException("Impossible d'insérer le Produit dans la base de données.", e);
        }
    }


    @Override
    public Double findPriceById(int idProduit) {
        String sql = "SELECT prix FROM produit WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProduit);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("prix");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche du prix par ID: " + e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération du prix du composant.", e);
        }
        return null;
    }

    @Override
    public List<Produit> findComposantsDisponibles() {
        List<Produit> composants = new ArrayList<>();

        String sql = "SELECT id, nom, prix, type_produit FROM produit WHERE type_produit IN (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ProdEnum.BURGER.toString());
            ps.setString(2, ProdEnum.COMPLEMENT.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Produit p = new Produit();
                    p.setId(rs.getInt("id"));
                    p.setNom(rs.getString("nom"));
                    p.setPrix(rs.getDouble("prix"));

                    composants.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la récupération des composants disponibles : " + e.getMessage());
            throw new RuntimeException("Impossible de charger la liste des composants.", e);
        }
        return composants;
    }

}