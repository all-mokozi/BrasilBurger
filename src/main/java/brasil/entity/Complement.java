package brasil.entity;

import brasil.enumeration.ProdEnum;
import brasil.enumeration.typeComplement;

public class Complement extends Produit {
    
    public Complement( String nom, double prix, ProdEnum categorie, String image, String description, boolean isArchive, typeComplement type) {
        super(nom, prix, categorie, image, description, isArchive);
        
    }
   
    
}
