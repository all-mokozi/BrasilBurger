package brasil.dto;

public class ComposantDTO {
    private int id_produit;
    private int qte;
    public ComposantDTO(int id_produit, int qte) {
        this.id_produit = id_produit;
        this.qte = qte;
    }
    public int getId_produit() {
        return id_produit;
    }
    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }
    public int getQte() {
        return qte;
    }
    public void setQte(int qte) {
        this.qte = qte;
    }
    
    
}
