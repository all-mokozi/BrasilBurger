package brasil.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import brasil.dto.ComposantDTO; // NOUVEL IMPORT
import brasil.dto.MenuDTO; // NOUVEL IMPORT
import brasil.dto.ProduitDTO;
import brasil.entity.Produit;
import brasil.enumeration.ProdEnum;

public class ProduitView {
    private  final Scanner scanner = new Scanner(System.in);


public int readInt() { 
    int value = 0;
    while (true) {
        System.out.print("Votre choix : ");
        try {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
               
                break; 
            } else {
                System.out.println(" Saisie invalide. Veuillez entrer un nombre entier.");
            }
        } catch (InputMismatchException e) {
            System.out.println(" Saisie invalide. Veuillez entrer un nombre entier.");
        } finally {
            scanner.nextLine(); 
        }
    }
    return value;
}


    public ProduitDTO saisirProduit() {
        System.out.println("\n--- 🍔 Saisie des informations Communes du Produit ---");
        
        ProduitDTO dto; 

        String nom = readString("Nom du produit");
       
        
        ProdEnum categorie = null;
        int choix;

        // --- Saisie de Catégorie ---
        do {
            System.out.println("\nCatégorie du produit:");
            System.out.println("1. Burger");
            System.out.println("2. Menu");
            System.out.println("3. Complément");
            System.out.print("Choisissez une catégorie (1-3): ");

            try {
                choix = scanner.nextInt();
            } catch (InputMismatchException e) {
                choix = 0;
            }
            scanner.nextLine();

        } while (choix < 1 || choix > 3); 

        if (choix == 2) {
            MenuDTO menuDto = new MenuDTO();
            menuDto.setNom(nom);
           
            menuDto.setCategorie(ProdEnum.MENU);
            
           
            dto = menuDto;
        } else {
             double prix = readPositiveDouble("Prix du produit (doit être > 0)");
            dto = new ProduitDTO();
            dto.setNom(nom);
            dto.setPrix(prix);
            
            switch (choix) {
                case 1: categorie = ProdEnum.BURGER; break;
                case 3: categorie = ProdEnum.COMPLEMENT; break;
            }
            dto.setCategorie(categorie);
        }

        dto.setImage(readString("Image du produit (URL ou chemin)"));
        dto.setDescription(readString("Description du produit"));
        dto.setArchive(false);

        return dto;
    }
    
    
public void saisirComposants(MenuDTO menuDto, List<Produit> composantsDisponibles) {
    System.out.println("\n--- Saisie des Composants du Menu ---");

    if (composantsDisponibles.isEmpty()) {
        System.out.println("AUCUN COMPOSANT DISPONIBLE. Veuillez d'abord ajouter des Burgers ou Compléments.");
        return;
    }

    System.out.println("\n----------------------------------------------------");
    System.out.printf("| %-4s | %-25s | %-8s |\n", "ID", "NOM", "PRIX");
    System.out.println("----------------------------------------------------");
    for (Produit p : composantsDisponibles) {
        System.out.printf("| %-4d | %-25s | %-8.2f |\n", p.getId(), p.getNom(), p.getPrix());
    }
    System.out.println("----------------------------------------------------");


    String reponse;
    do {
         
        boolean idValide = false;
        int idComp = 0;
        
        while(!idValide) {
               

            System.out.print("Entrez l'ID du composant à ajouter au menu : ");
             int id = readInt(); 
            
            if (composantsDisponibles.stream().anyMatch(p -> p.getId() == id)) {
                idComp = id;
                idValide = true;
            } else {
                System.out.println("ID de composant invalide ou non disponible. Réessayez.");
            }
        }
        
        System.out.println("Quantité pour ce composant");
        int quantite = readInt();
        
        ComposantDTO compDTO = new ComposantDTO(idComp, quantite);
        menuDto.addComposant(compDTO);

        reponse = readString("Ajouter un autre composant ? (o/n)");
        
    } while (reponse.equalsIgnoreCase("o"));
}


    public  String readString(String prompt) {
        String input;
        do {
            System.out.print(prompt + " : ");
            input =  scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("⚠️ La saisie ne peut pas être vide. Veuillez réessayer.");
            }
        } while (input.isEmpty());
        return input;
    }

    private double readPositiveDouble(String prompt) {
        double value = 0;
        while (true) {
            System.out.print(prompt + " : ");
            try {
                value = scanner.nextDouble();
                if (value <= 0) {
                    System.out.println(" Le prix doit être strictement positif.");
                    continue;
                }
                break; 
            } catch (InputMismatchException e) {
                System.out.println(" Saisie invalide. Veuillez entrer un nombre.");
            } finally {
                scanner.nextLine(); 
            }
        }
        return value;
    }
    
}