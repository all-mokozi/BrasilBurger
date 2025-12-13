
     package brasil.repository;

import brasil.dto.ComposantDTO;
import java.util.List;


public interface IMenuComposantRepository {

    void lierComposant(int idMenu, int idComposant, int quantite);
    List<ComposantDTO> findComposantsByMenuId(int idMenu);
    void deleteComposantsByMenuId(int idMenu);
}