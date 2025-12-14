package brasil;

import java.sql.Connection;

import brasil.config.DataBaseSingleton;
import brasil.dto.MenuDTO;
import brasil.dto.ProduitDTO;
import brasil.dto.QuartierDTO;
import brasil.dto.ZoneDTO;
import brasil.entity.Produit;
import brasil.enumeration.ProdEnum;
import brasil.repository.IMenuComposantRepository;
import brasil.repository.IProduitRepository;
import brasil.repository.IQuartierRepository;
import brasil.repository.IZoneRepository;
import brasil.repository.impl.MenuComposantRepositoryImpl;
import brasil.repository.impl.ProduitRepositoryimpl;
import brasil.repository.impl.QuartierRepositoryImpl;
import brasil.repository.impl.ZoneRepositoryimpl;
import brasil.service.ImageService;
import brasil.service.ProduitService;
import brasil.service.QuartierService;
import brasil.service.ZoneService;
import brasil.view.ProduitView;
import brasil.view.QuartierView;
import brasil.view.ZoneView;

import java.util.InputMismatchException;
import java.util.List;

public class Main {

    private static final ProduitView view = new ProduitView();
    private static final ZoneView zoneView = new ZoneView();
    private static final QuartierView quartierView = new QuartierView();

    public static int afficherMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Ajouter une zone");
        System.out.println("3. Afficher les zone");

        System.out.println("4. Afficher tous les produits");
        System.out.println("5.Afficher  les produits par categorie");
        System.out.println("6.Ajouter  Quartier");
        System.out.println("7.Afficher tous les Quartiers  ");
        System.out.println("8.Afficher Quartier par Zone");
        System.out.println("9.QUITTER");

        int choix = 0;
        try {

            System.out.print("Choisissez une option : ");
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
            IQuartierRepository quartierRepository = new QuartierRepositoryImpl(conn);
            QuartierService quartierService = new QuartierService(zoneRepo, quartierRepository);

            int choix = 0;
            boolean running = true;

            while (running) {
                choix = afficherMenu();
               
                switch (choix) {
                    case 1:
                        ProduitDTO dto = view.saisirProduit();
                        if (dto == null) {
                            System.out.println("Vous quittez a la prochaine.");
                            return; 
                        }
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

                        zoneService.afficherAllZone();
                        ;
                        break;
                    case 4:

                        service.afficherProduits();
                        break;
                    case 5:
                        ProdEnum cat = view.retourCat();
                        service.afficherPrduitsBycategorie(cat);
                        break;
                    case 6:
                        QuartierDTO quartierDTO = quartierView.saisirQuatier();
                        quartierService.ajouterZoneQ(quartierDTO);
                    case 7:
                        quartierService.afficherAllQuartier();
                        break;
                    case 8:
                        quartierService.afficherQuartierByZone();

                        break;
                    case 9:
                        System.out.println("Merci et a la prochaine");
                        running =false;

                        break;
                    default:
                        System.out.println(" Veuillez choisir entre 1 et 9");
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur critique de l'application : " + e.getMessage());
        } finally {

            DataBaseSingleton.getInstance().closeConnection();
        }
    }
}