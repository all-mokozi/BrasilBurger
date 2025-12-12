package brasil.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import brasil.dto.BurgerDTO;
import brasil.enumeration.ProdEnum;

public class BurgeRView {

    // Le Scanner est déclaré au niveau de l'instance
    private final Scanner scanner = new Scanner(System.in);

    // --- Méthodes de lecture sécurisée (pour la robustesse) ---

    // Lecture String sécurisée (vérifie le vide)
    private String readString(String prompt) {
        String input;
        do {
            System.out.print(prompt + " : ");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("⚠️ La saisie ne peut pas être vide. Veuillez réessayer.");
            }
        } while (input.isEmpty());
        return input;
    }

    // Lecture double sécurisée (gère InputMismatchException et prix <= 0)
    private double readPositiveDouble(String prompt) {
        double value = 0;
        while (true) {
            System.out.print(prompt + " : ");
            try {
                value = scanner.nextDouble();
                if (value <= 0) {
                    System.out.println("⚠️ Le prix doit être strictement positif.");
                    continue;
                }
                break; // Sort de la boucle si la saisie est valide
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Saisie invalide. Veuillez entrer un nombre.");
            } finally {
                scanner.nextLine(); // TRÈS IMPORTANT: Consomme la nouvelle ligne
            }
        }
        return value;
    }

    // --- Saisie principale ---

    public BurgerDTO saisirProduit() {
        System.out.println("\n--- 🍔 Saisie des informations Communes du Produit ---");
        BurgerDTO dto = new BurgerDTO();

        // Utilisation de la méthode sécurisée
        dto.setNom(readString("Nom du produit"));

        // Utilisation de la méthode sécurisée
        dto.setPrix(readPositiveDouble("Prix du produit (doit être > 0)"));

        ProdEnum categorie = null;
        int choix;

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

        } while (choix < 1 || choix > 2);

        switch (choix) {
            case 1:
                categorie = ProdEnum.BURGER;
                break;
            case 2:
                categorie = ProdEnum.MENU;
                break;
            case 3:
                categorie = ProdEnum.COMPLEMENT;
                break;
        }

        dto.setCategorie(categorie);

        dto.setImage(readString("Image du produit (URL ou chemin)"));

        dto.setDescription(readString("Description du produit"));

        dto.setArchive(false);

        return dto;
    }

    // Dans brasil.view.ProduitView.java

public int readInt() {
    int value = 0;
    while (true) {
        try {
            // S'assurer que le scanner a un int
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= 1 && value <= 4) { // Validation simple pour le menu
                    break;
                } else {
                    System.out.println("⚠️ Veuillez choisir une option entre 1 et 4.");
                }
            } else {
                System.out.println("⚠️ Saisie invalide. Veuillez entrer un nombre entier.");
            }
        } catch (InputMismatchException e) {
            System.out.println("⚠️ Saisie invalide. Veuillez entrer un nombre entier.");
        } finally {
            scanner.nextLine(); // Consomme la nouvelle ligne ou l'entrée invalide
        }
    }
    return value;
}

    public void close() {
        if (scanner != null) {
            scanner.close();

        }
    }
}