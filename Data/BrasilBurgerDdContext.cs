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

protected override void OnModelCreating(ModelBuilder modelBuilder)
{
    modelBuilder.Entity<MenuComposant>()
        .HasOne(mc => mc.Menu)
        .WithMany(p => p.ComposantsDuMenu)
        .HasForeignKey(mc => mc.MenuId)
        .OnDelete(DeleteBehavior.Restrict);

    modelBuilder.Entity<MenuComposant>()
        .HasOne(mc => mc.ProduitComposant)
        .WithMany(p => p.UtiliseDansMenus)
        .HasForeignKey(mc => mc.ProduitComposantId)
        .OnDelete(DeleteBehavior.Restrict);

    modelBuilder.Entity<Produit>()
        .Property(p => p.TypeProduit)
        .HasConversion<string>();
    modelBuilder.Entity<Commande>()
        .Property(c=>c.ModeC)
        .HasConversion<string>();



    base.OnModelCreating(modelBuilder);
}


    
    }


}