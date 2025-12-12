package brasil.entity;

public class Menu extends Produit {
    private Burger burger;
    private Complement complement;
    
    public Menu( String nom, double prix, brasil.enumeration.ProdEnum categorie, String image, String description, boolean isArchive, Burger burger, Complement complement) {
        super( nom, prix, categorie, image, description, isArchive);
        this.burger = burger;
        this.complement = complement;
    }

    public Burger getBurger() {
        return burger;
    }

    public void setBurger(Burger burger) {
        this.burger = burger;
    }

    public Complement getComplement() {
        return complement;
    }

    public void setComplement(Complement complement) {
        this.complement = complement;
    }
    
}
