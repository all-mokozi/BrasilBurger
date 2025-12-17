using Microsoft.EntityFrameworkCore;
using Models;
namespace Data
{
    public class BrasilBurgerDbContext : DbContext
    {
        public BrasilBurgerDbContext(DbContextOptions<BrasilBurgerDbContext> options) : base(options)
        { }
        public DbSet<Client> clients { get; set; }
        public DbSet<Commande> Commandes{ get; set; }
        public DbSet<Quartier> Quartiers { get; set; }
        public DbSet<LigneCommande> LigneCommandes{ get; set; }
        public DbSet<Paiement> Paiements { get; set; }
        public DbSet<Produit> Produits{ get; set; }
        public DbSet<MenuComposant> MenuComposants { get; set; }
    
    }


}