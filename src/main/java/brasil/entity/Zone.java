package brasil.entity;

import java.util.ArrayList;

public class Zone {
    private String nom;
    private double prixLivraison;
    private int id;   
    private ArrayList<Quartier> quartiers;
    public Zone(String nom, double prixLivraison) {
        this.nom = nom;
        this.prixLivraison = prixLivraison;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public double getPrixLivraison() {
        return prixLivraison;
    }
    public void setPrixLivraison(double prixLivraison) {
        this.prixLivraison = prixLivraison;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ArrayList<Quartier> getAllQuartiers() {
        return quartiers;
    }
    public void addQuartier(Quartier quartier) {
        this.quartiers.add(quartier);
    }
    
}
