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
    
}
