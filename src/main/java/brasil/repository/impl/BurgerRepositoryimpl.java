package brasil.repository.impl;
import brasil.entity.Burger;
import brasil.repository.IProduitRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Nécessaire pour Statement.RETURN_GENERATED_KEYS

public class BurgerRepositoryimpl implements IProduitRepository {
    
    // La connexion est passée via le constructeur, c'est propre !
    private final Connection conn;

    public BurgerRepositoryimpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Burger b) {
        String sql = "INSERT INTO produit (nom, prix, image, type_produit, is_archived) VALUES (?, ?, ?, ?, ?) RETURNING id";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, b.getNom());
            ps.setDouble(2, b.getPrix());
            ps.setString(3, b.getImage());
          
            ps.setString(4, b.getCategorie().toString()); 
            ps.setBoolean(5, b.isArchive());
            
            int affectedRows = ps.executeUpdate(); 

            if (affectedRows > 0) {
                
                try (ResultSet rs = ps.getGeneratedKeys()) { 
                    if (rs.next()) {
                        b.setId(rs.getInt(1)); 
                        System.out.println("Produit inséré avec succès. ID: " + b.getId());
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
    
}