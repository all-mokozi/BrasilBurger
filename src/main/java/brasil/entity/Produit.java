package brasil.entity;
import brasil.enumeration.ProdEnum;
public  class Produit {

    private int id;
    private String nom;
    private double prix;
    private ProdEnum categorie;
    private String image;
    private String description;
    private boolean isArchive;
    public Produit() {
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean isArchive) {
        this.isArchive = isArchive;
    }

    public Produit( String nom, double prix, ProdEnum categorie, String image, String description, boolean isArchive) {
       
        this.nom = nom;
        this.prix = prix;
        this.categorie = categorie;
        this.image = image;
        this.description = description;
        this.isArchive = false;
    }
   
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Produit  nom=" + nom + ", prix=" + prix + ", categorie=" + categorie + ", description=" + description + "";
    }
}