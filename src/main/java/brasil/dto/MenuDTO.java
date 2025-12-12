package brasil.dto;

import brasil.entity.Complement;
import brasil.enumeration.ProdEnum;

public class MenuDTO {

    

    private String nom;
    private double prix;

    private ProdEnum categorie;
    private String image;
    private String description;
    private boolean isArchive;
    private Complement complement;

    public Complement getComplement() {
        return complement;
    }

    public void setComplement(Complement complement) {
        this.complement = complement;
    }

    public MenuDTO() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public ProdEnum getCategorie() {
        return categorie;
    }

    public void setCategorie(ProdEnum categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean isArchive) {
        this.isArchive = isArchive;
    }

    @Override
    public String toString() {
        return "Burger [nom=" + nom + ", prix=" + prix + ", categorie=" + categorie + ", image=" + image
                + ", description=" + description + ", isArchive=" + isArchive + "]";
    }

}