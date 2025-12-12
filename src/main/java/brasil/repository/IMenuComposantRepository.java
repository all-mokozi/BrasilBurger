
     package brasil.repository;

import brasil.dto.ComposantDTO;
import java.util.List;

/**
 * Interface définissant les opérations d'accès aux données 
 * pour la table de jointure des composants de Menu (MENU_COMPOSANT).
 */
public interface IMenuComposantRepository {

    void lierComposant(int idMenu, int idComposant, int quantite);
    List<ComposantDTO> findComposantsByMenuId(int idMenu);
    void deleteComposantsByMenuId(int idMenu);
}