package brasil.entity;

import brasil.enumeration.ProdEnum;

public class Burger extends Produit {
   
    
    public Burger( String nom, double prix, ProdEnum categorie, String image, String description,  boolean isArchive) {
        super( nom, prix, categorie, image, description, isArchive);
        
    }

    
    }
