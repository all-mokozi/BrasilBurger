using Models;
using Repository;
using Microsoft.EntityFrameworkCore;
using Data;

namespace Impl
{
    public class PanierRepositoryImpl : IPanierRepository
    {
        private readonly BrasilBurgerDbContext _context;

        public PanierRepositoryImpl(BrasilBurgerDbContext context)
        {
            _context = context;
        }

        public void InsertCommandeToPanier(int clientId, Commande c)
        {
            var panier = _context.Paniers.FirstOrDefault(p => p.ClientId == clientId);

            if (panier == null)
            {
                panier = new Panier { ClientId = clientId };
                _context.Paniers.Add(panier);
                _context.SaveChanges(); // On génère l'ID du panier
            }

            c.PanierId = panier.Id;
            c.DateCommande = DateTime.UtcNow; // Sécurité supplémentaire pour Postgres

            _context.Commandes.Add(c);
            _context.SaveChanges();
        }

        public Panier GetPanierByClientId(int clientId)
        {
            var panier = _context.Paniers
                .AsNoTracking() // Évite d'utiliser le cache mémoire corrompu
                .Include(p => p.Commandes)
                    .ThenInclude(c => c.Produit)
                .FirstOrDefault(p => p.ClientId == clientId);

            // Sécurité supplémentaire : On ne garde que les commandes qui ont bien un produit chargé
            if (panier != null && panier.Commandes != null)
            {
                panier.Commandes = panier.Commandes.Where(c => c.Produit != null).ToList();
            }

            return panier;
        }

        public void ValiderPanier(int clientId)
        {
            // 1. On récupère les articles du panier (ceux qui sont EN_COURS)
            var commandesAValider = _context.Commandes
                .Where(c => c.ClientId == clientId && c.Etat == "EN_COURS")
                .ToList();

            if (commandesAValider.Any())
            {
                foreach (var cmd in commandesAValider)
                {
                    // 2. On change l'état pour que la base de données accepte la modif
                    cmd.Etat = "VALIDE";

                    // 3. ON VIDE LE PANIER : On met le PanierId à null
                    // Ainsi, ils n'apparaîtront plus dans la vue "Panier"
                    cmd.PanierId = null;

                    _context.Entry(cmd).Property(x => x.Etat).IsModified = true;
                    _context.Entry(cmd).Property(x => x.PanierId).IsModified = true;
                }

                _context.SaveChanges();
            }
        }
        public void DeleteProduitduPaierById(int ProduitId)
        {
            _context.Commandes.Where(c=>c.ProduitId==ProduitId);
        }

}
    }
