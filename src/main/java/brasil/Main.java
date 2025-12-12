package brasil;

import java.sql.Connection;

import brasil.config.DataBaseSingleton;
import brasil.dto.BurgerDTO;
import brasil.repository.IProduitRepository;
import brasil.repository.impl.BurgerRepositoryimpl;

import brasil.service.BurgerService;

import brasil.view.BurgeRView;

import java.util.InputMismatchException;

public class Main {
    
    private static final BurgeRView view = new BurgeRView();

    public static int afficherMenu() {
        System.out.println("\n--- 🍔 Menu Principal ---");
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
            IProduitRepository repo = new BurgerRepositoryimpl(conn); 
            BurgerService service = new BurgerService(repo);

            int choix = 0;
            boolean running = true;
            
            // BOUCLE PRINCIPALE DE L'APPLICATION
            while (running) {
                choix = afficherMenu(); 

                switch (choix) {
                    case 1:
                        
                        BurgerDTO dto = view.saisirProduit();
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
            view.close();
        }
    }
}