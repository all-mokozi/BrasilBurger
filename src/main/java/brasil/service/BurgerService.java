package brasil.service;

import brasil.dto.BurgerDTO;
import brasil.entity.Burger;
import brasil.repository.IProduitRepository;

public class BurgerService {
    private final IProduitRepository produitRepository;
    public BurgerService( IProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }
    public void ajouterProduit(BurgerDTO b) {

        Burger burger = new Burger(
            b.getNom(),
            b.getPrix(),
            b.getCategorie(),
            b.getImage(),
            b.getDescription(),
            b.isArchive()
        );
        produitRepository.insert(burger);;
       
    }
    
}
