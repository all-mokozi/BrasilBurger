package brasil;

import java.sql.Connection;

import brasil.config.DataBaseSingleton;
import brasil.dto.MenuDTO;
import brasil.dto.ProduitDTO;
import brasil.entity.Produit;
import brasil.enumeration.ProdEnum;
import brasil.repository.IMenuComposantRepository;
import brasil.repository.IProduitRepository;
import brasil.repository.impl.MenuComposantRepositoryImpl;
import brasil.repository.impl.ProduitRepositoryimpl;
import brasil.service.ProduitService;
import brasil.view.ProduitView;

import java.util.InputMismatchException;
import java.util.List;

public class Main {
    
    private static final ProduitView view = new ProduitView();

    public static int afficherMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Afficher les produits");
        System.out.println("3. Modifier un produit");
        System.out.println("4. Quitter");
        
        int choix = 0;
        try {
          
            System.out.print("Choisissez une option (1-4): ");
            choix = view.readInt(); // Supposons que cette méthode existe et lit un entier
        } catch (InputMismatchException e) {
            System.out.println("Saisie invalide. Veuillez entrer un nombre.");
        }
        return choix;
    }

    public static void main(String[] args) {
        Connection conn = null;
        
        try {
            // Initialiser la connexion au début de l'application
            conn = DataBaseSingleton.getInstance().getConnection();
            IProduitRepository repo = new ProduitRepositoryimpl(conn); 
            IMenuComposantRepository menuComposantRepo = new MenuComposantRepositoryImpl(conn); // À implémenter si nécessaire
            ProduitService service = new ProduitService(repo, menuComposantRepo);



            int choix = 0;
            boolean running = true;
            
            // BOUCLE PRINCIPALE DE L'APPLICATION
            while (running) {
                choix = afficherMenu(); 

                switch (choix) {
                    case 1:
                        // 1. Saisir les données de base (Produit ou MenuDTO)
                        ProduitDTO dto = view.saisirProduit();
                        
                        // 2. LOGIQUE SPÉCIFIQUE AU MENU
                        if (dto.getCategorie() == ProdEnum.MENU) {
                            
                            // A. Récupérer la liste des composants disponibles (Burger, Complément)
                            List<Produit> composants = service.getComposantsDisponibles();
                            
                            // B. Appeler la méthode de la View avec les deux arguments
                            // Note: Le downcast (MenuDTO) dto est nécessaire car dto est de type ProduitDTO
                            view.saisirComposants((MenuDTO) dto, composants);
                        }
                        
                        // 3. Traiter le produit (Menu ou Produit simple)
                        service.ajouterProduit(dto);
                        break;
                    case 2:
                        System.out.println("Fonctionnalité 'Afficher' non implémentée.");
                      
                        break;
                    case 3:
                        System.out.println("Fonctionnalité 'Modifier' non implémentée.");
                        break;
                    case 4:
                        
                        System.out.println("Application fermée. Au revoir !");
                        break;
                    default:
                        System.out.println(" Veuillez choisir entre 1 et 4.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur critique de l'application : " + e.getMessage());
        } finally {
            
            DataBaseSingleton.getInstance().closeConnection();
        }
    }
}