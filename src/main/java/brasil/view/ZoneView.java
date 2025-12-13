package brasil.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import brasil.dto.ZoneDTO;

public class ZoneView {
    private final Scanner scanner = new Scanner(System.in);

    public ZoneDTO saisirZone() {
        System.out.println("\n---  Saisie des informations de la Zone ---");

        ZoneDTO dto = new ZoneDTO();

        String nom = readString("Nom de la zone");
        dto.setNom(nom);
        double prixLivraison = readPositiveDouble("Prix de livraison");
        dto.setPrixLivraison(prixLivraison);

        return dto;
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

    public String readString(String prompt) {
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

}
