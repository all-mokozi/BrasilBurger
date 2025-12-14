package brasil.view;
import java.util.Scanner;

import brasil.dto.QuartierDTO;

public class QuartierView {
    private final Scanner scanner = new Scanner(System.in);

    public QuartierDTO saisirQuatier() {
        System.out.println("\n---  Saisie des informations du Quartier ---");

        QuartierDTO dto = new QuartierDTO();

        String nom = readString("Nom du Quartier");
        dto.setNom(nom);
        

        return dto;
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
