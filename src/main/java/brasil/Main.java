package brasil;

import java.sql.Connection;

import brasil.config.DataBaseSingleton;
import brasil.dto.MenuDTO;
import brasil.dto.ProduitDTO;
import brasil.dto.ZoneDTO;
import brasil.entity.Produit;
import brasil.enumeration.ProdEnum;
import brasil.repository.IMenuComposantRepository;
import brasil.repository.IProduitRepository;
import brasil.repository.IZoneRepository;
import brasil.repository.impl.MenuComposantRepositoryImpl;
import brasil.repository.impl.ProduitRepositoryimpl;
import brasil.repository.impl.ZoneRepositoryimpl;
import brasil.service.ImageService;
import brasil.service.ProduitService;
import brasil.service.ZoneService;
import brasil.view.ProduitView;
import brasil.view.ZoneView;

import java.util.InputMismatchException;
import java.util.List;

public class Main {

    private static final ProduitView view = new ProduitView();
    private static final ZoneView zoneView = new ZoneView();

    public static int afficherMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Ajouter une zone");
        System.out.println("3. Afficher tous les produits");
        System.out.println("4.Afficher  les produits par categorie");
        System.out.println("5.Quitter");

        int choix = 0;
        try {

            System.out.print("Choisissez une option (1-5): ");
            choix = view.readInt(); 
        } catch (InputMismatchException e) {
            System.out.println("Saisie invalide. Veuillez entrer un nombre.");
        }
        return choix;
    }

    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DataBaseSingleton.getInstance().getConnection();
            IProduitRepository repo = new ProduitRepositoryimpl(conn);
            IMenuComposantRepository menuComposantRepo = new MenuComposantRepositoryImpl(conn);
            ImageService imageService = new ImageService();
            ProduitService service = new ProduitService(repo, menuComposantRepo, imageService);
            IZoneRepository zoneRepo = new ZoneRepositoryimpl(conn); 
                ZoneService zoneService = new ZoneService(zoneRepo);



            int choix = 0;
            boolean running = true;

            while (running) {
                choix = afficherMenu();

                switch (choix) {
                    case 1:
                        ProduitDTO dto = view.saisirProduit();
                        if (dto.getCategorie() == ProdEnum.MENU) {

                            List<Produit> composants = service.getComposantsDisponibles();

                            view.saisirComposants((MenuDTO) dto, composants);
                        }

                        service.ajouterProduit(dto);
                        break;
                    case 2:
                        ZoneDTO zoneDTO = zoneView.saisirZone();
                        zoneService.ajouterZone(zoneDTO);

                        break;
                    case 3:
                        
                        service.afficherProduits();
                        break;
                    case 4:

                       ProdEnum cat =view.retourCat();
                        service.afficherPrduitsBycategorie(cat);
                        break;
                    case 5:

                      System.out.println("Merci et a la prochaine");
                       running = false;
                        break;
                    default:
                        System.out.println(" Veuillez choisir entre 1 et 5");
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur critique de l'application : " + e.getMessage());
        } finally {

            DataBaseSingleton.getInstance().closeConnection();
        }
    }
}