package brasil.entity;

public class Quartier {
    private int id;
    private String nom;
    private Zone zone;
    public Quartier(String nom, Zone zone) {
        this.nom = nom;
        this.zone = zone;
    }
    public Quartier() {
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
    public Zone getZone() {
        return zone;
    }
    public void setZone(Zone zone) {
        this.zone = zone;
    }
    @Override
    public String toString() {
        return "Quartier id:" + id + " nom:" + nom+ " ";
    }
    
}
