package brasil.factory;

import brasil.dto.ProduitDTO;
import brasil.entity.Produit;
import brasil.entity.Burger;
import brasil.entity.Menu;
import brasil.entity.Complement;

public class ProduitFactory {

    public static Produit createProduit(ProduitDTO dto) {
        
        String nom = dto.getNom();
        double prix = dto.getPrix();
        String image = dto.getImage();
        String description = dto.getDescription();
        boolean isArchive = dto.isArchive();
        
        switch (dto.getCategorie()) {
            case BURGER:
                return new Burger(nom, prix, dto.getCategorie(), image, description, isArchive);
                
            case MENU:
               
                return new Menu(nom, prix, dto.getCategorie(), image, description, isArchive);
                
            case COMPLEMENT:
                return new Complement(nom, prix, dto.getCategorie(), image, description, isArchive);
                
            default:
                throw new IllegalArgumentException("Type de produit '" + dto.getCategorie() + "' non supporté.");
        }
    }
}