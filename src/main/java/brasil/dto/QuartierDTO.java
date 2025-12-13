package brasil.dto;

import brasil.entity.Zone;

;

public class QuartierDTO {
    private int id;
    private String nom;
    private Zone zone;
    public QuartierDTO() {
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
    
}
