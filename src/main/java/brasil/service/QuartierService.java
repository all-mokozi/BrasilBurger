package brasil.service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import brasil.dto.QuartierDTO;
import brasil.entity.Quartier;
import brasil.entity.Zone;
import brasil.factory.QuartierFactory;
import brasil.repository.IQuartierRepository;
import brasil.repository.IZoneRepository;

public class QuartierService {

    private final IZoneRepository zoneRepository;
    private final IQuartierRepository quartierRepository;
    Scanner scanner = new Scanner(System.in);

    public QuartierService(
            IZoneRepository zoneRepository,
            IQuartierRepository quartierRepository

    ) {
        this.zoneRepository = zoneRepository;
        this.quartierRepository = quartierRepository;

    }

    public void ajouterZoneQ(QuartierDTO q) {
        System.out.println("La liste des Zones de Couverture");
        zoneRepository.selectAll().forEach(z -> System.out.println("ID: " + z.getId() + ", Nom: " + z.getNom()));
        int zoneId = readInt("Veuillez Choisir l'ID du Quartier rattacher");
        Zone z = zoneRepository.selectZoneById(zoneId);

        Quartier quartier = QuartierFactory.createQuartier(q);
        quartierRepository.insert(quartier, z);
        System.out.println("Quartier " + quartier.getNom() + " ajoutée avec succès.");

    }

    public int readInt(String me) {
        int value = 0;
        System.out.println(me);
        while (true) {

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

    public void afficherQuartierByZone() {

        Zone zone = null;
        int zoneId = -1; // Initialisation à une valeur non significative

        zoneRepository.selectAll().forEach(z -> System.out.println("ID: " + z.getId() + ", Nom: " + z.getNom()));

        do {
            zoneId = readInt("Veuillez Choisir l'ID de la Zone rattachée (ou 0 pour annuler)");

            if (zoneId == 0) {
                System.out.println("Opération annulée.");
                return;
            }

            zone = zoneRepository.selectZoneById(zoneId);

            if (zone == null) {
                System.out.println(
                        "⚠️ Erreur : L'ID de Zone " + zoneId + " est invalide ou n'existe pas. Veuillez réessayer.");
            }

        } while (zone == null);
        System.out.println("\n--- Détails de la Zone ---");
        System.out.println("ID: " + zone.getId() +
                " | Nom Zone: " + zone.getNom() +
                " | Prix Livraison: " + zone.getPrixLivraison() +
                "\n-------------------");

        // 4. Charger et afficher les Quartiers
        List<Quartier> quartiers = quartierRepository.selectAllQuartierByZone(zoneId);

        if (quartiers.isEmpty()) {
            System.out.println("Aucun quartier n'est encore enregistré pour la Zone " + zone.getNom() + ".");
        } else {
            System.out.println("--- Quartiers rattachés ---");
            int i = 1;
            for (Quartier p : quartiers) {
                System.out.println(i + ". " + p);
                i++;
            }
        }
    }

    public void afficherAllQuartier() {
        List<Quartier> quartiers = quartierRepository.selectAll();
        int i = 1;
        for (Quartier p : quartiers) {

            System.out.println(i + ". " + p);
            i = i + 1;
        }
    }

}
