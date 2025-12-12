package brasil.repository.impl;

import brasil.repository.IMenuComposantRepository;
import brasil.dto.ComposantDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class MenuComposantRepositoryImpl implements IMenuComposantRepository {
    
    private final Connection connection;
        public MenuComposantRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void lierComposant(int idMenu, int idComposant, int quantite) {
        
        String sql = "INSERT INTO MENU_COMPOSANT (id_menu, id_produit_composant, quantite) VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, idMenu); 
            
            statement.setInt(2, idComposant); 
            
            statement.setInt(3, quantite);
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC lors de la liaison du composant (ID " + idComposant + ") au Menu (ID " + idMenu + ") : " + e.getMessage(), e);
        }
    }


    @Override
    public List<ComposantDTO> findComposantsByMenuId(int idMenu) {
        return new ArrayList<>(); 
    }

    @Override
    public void deleteComposantsByMenuId(int idMenu) {
    }
}