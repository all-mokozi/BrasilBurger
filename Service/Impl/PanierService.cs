using Data;
using Models;
using Repository;

namespace Service
{
    public class PanierServiceImpl : IPanierService
    {
        private readonly IPanierRepository _panierRepo;
        private readonly IProduitService _produitService;
        private readonly BrasilBurgerDbContext _context;

        public PanierServiceImpl(IPanierRepository panierRepo, IProduitService produitService, BrasilBurgerDbContext context)
        {
            _panierRepo = panierRepo;
            _produitService = produitService;
            _context = context;
        }

        public void AjouterAuPanier(int clientId, int produitId, int quantite, ModeConso mode)
        {
            var produit = _produitService.getProduitById(produitId);
            if (produit == null) throw new Exception("Produit introuvable");

            var nouvelleCommande = new Commande
            {
                ProduitId = produitId,
                Quantite = quantite,
                ModeC = mode,
                Etat = "EN_COURS",
                ClientId = clientId,
                DateCommande = DateTime.UtcNow,
                MontantTotal = produit.Prix * quantite
            };

            _panierRepo.InsertCommandeToPanier(clientId, nouvelleCommande);
        }
        public Panier ObtenirPanierClient(int clientId)
        {
            return _panierRepo.GetPanierByClientId(clientId);
        }
        public void ValiderLePanier(int clientId)
{
    // On pourrait ajouter ici une logique de vérification (stock, paiement...)
    _panierRepo.ValiderPanier(clientId);
}

        // public void RetirerDuPanier(int commandeId)
        // {
        //     // Logique de suppression via le repo
        // }

        // public void ViderPanier(int clientId)
        // {
        //     _panierRepo.ClearPanier(clientId);
        // }
    }
}