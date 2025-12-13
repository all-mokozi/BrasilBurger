package brasil.service;

import brasil.dto.ProduitDTO;
import brasil.dto.MenuDTO;

import java.io.File;
import java.util.List;

import brasil.dto.ComposantDTO;
import brasil.entity.Produit;
import brasil.enumeration.ProdEnum;
import brasil.entity.Menu;
import brasil.factory.ProduitFactory;
import brasil.repository.IProduitRepository;
import brasil.repository.IMenuComposantRepository; 

public class ProduitService {

    private final IProduitRepository produitRepository;
    private final IMenuComposantRepository menuComposantRepository;
    private final ImageService imageService;
    ProdEnum categorie;

    public ProduitService(
            IProduitRepository produitRepository,
            IMenuComposantRepository menuComposantRepository,
            ImageService imageService

    ) {
        this.produitRepository = produitRepository;
        this.menuComposantRepository = menuComposantRepository;
        this.imageService = imageService;

    }

    public void ajouterProduit(ProduitDTO p) {
         File imageFile = new File(p.getImage()); // chemin local
        String imageUrl = imageService.uploadImage(imageFile);
        p.setImage(imageUrl);

        if (p.getCategorie() == ProdEnum.MENU) {
            ajouterMenu((MenuDTO) p);
            return;
        }

        if (p.getPrix() <= 0) {
            throw new IllegalArgumentException("Le prix du produit doit être strictement positif.");
        }

        Produit produit = ProduitFactory.createProduit(p);
        produitRepository.insert(produit);

        System.out.println("Produit (" + produit.getCategorie() + ") " + produit.getNom() + " ajouté avec succès.");
    }

    private void ajouterMenu(MenuDTO dto) {
        double totalComposants = 0.0;
       
        for (ComposantDTO comp : dto.getComposants()) {
            Double prixComp = produitRepository.findPriceById(comp.getId_produit());

            if (prixComp == null) {
                throw new IllegalArgumentException(
                        "Le composant avec l'ID " + comp.getId_produit() + " est introuvable.");
            }

            totalComposants += prixComp * comp.getQte();
        }

        double prixFinalMenu = totalComposants * 0.95;

        dto.setPrix(prixFinalMenu);

        Menu menuEntite = (Menu) ProduitFactory.createProduit(dto);
        produitRepository.insert(menuEntite);

        int idMenuGenere = menuEntite.getId();

        for (ComposantDTO comp : dto.getComposants()) {
            menuComposantRepository.lierComposant(
                    idMenuGenere,
                    comp.getId_produit(),
                    comp.getQte());
        }

        System.out.printf("Menu '%s' ajouté. Prix : %.2f\n",
                menuEntite.getNom(), prixFinalMenu);
    }

    public List<Produit> getComposantsDisponibles() {
        return produitRepository.findComposantsDisponibles();
    }

}