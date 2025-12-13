package brasil.dto;
public class ZoneDTO {
    public ZoneDTO() {
    }

    private int id_zone;
    private String nom;
    private double prixLivraison;

    public double getPrixLivraison() {
        return prixLivraison;
    }

    public void setPrixLivraison(double prixLivraison) {
        this.prixLivraison = prixLivraison;
    }

    public ZoneDTO(int id_zone, String nom, double prixLivraison) {
        this.id_zone = id_zone;
        this.nom = nom;
        this.prixLivraison = prixLivraison;
    }

    public int getId_zone() {
        return id_zone;
    }

    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "ZoneDTO [id_zone=" + id_zone + ", nom=" + nom + ", prixLivraison=" + prixLivraison + "]";
    }
    
    
}