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
        public DbSet<Zone> Zones {get;set;}
        public DbSet<Panier> Paniers {get;set;}

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

    modelBuilder.Entity<Produit>()
        .Property(p => p.Prix)
        .HasPrecision(18, 2);

    modelBuilder.Entity<Commande>()
        .HasOne(c => c.Panier)
        .WithMany(p => p.Commandes)
        .HasForeignKey(c => c.PanierId) // On utilise uniquement la propriété C#
        .OnDelete(DeleteBehavior.Cascade);

    modelBuilder.Entity<Commande>()
        .Property(c => c.ModeC)
        .HasConversion<string>();

    modelBuilder.Entity<Commande>()
        .Property(c => c.MontantTotal)
        .HasPrecision(18, 2);

    base.OnModelCreating(modelBuilder);
}
    }}