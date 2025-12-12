package brasil.entity;

import brasil.enumeration.ProdEnum;

public class Complement extends Produit {
    
    public Complement( String nom, double prix, ProdEnum categorie, String image, String description, boolean isArchive) {
        super(nom, prix, categorie, image, description, isArchive);
        
    }
   
    
}
