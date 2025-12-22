using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
namespace Models
{
   [Table("commande")]
public class Commande
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("date_commande")]
    public DateTime DateCommande { get; set; } = DateTime.UtcNow;

    [Column("etat")]
    public string Etat { get; set; } = "EN_COURS";

    [Column("montant_total")]
    public decimal MontantTotal { get; set; }

    [Column("adresse_livraison")]
    public string? AddLivraison { get; set; }

    [Column("mode_livraison")]
    public required ModeConso ModeC { get; set; }

    [Column("quantite")]
    public int Quantite { get; set; } = 1;

      [Column("client_id")]
    public int ClientId { get; set; }
    
    [ForeignKey("ClientId")]
    public Utilisateur Client { get; set; }

    // --- RELATION PRODUIT ---
    [Column("produit_id")]
    public int ProduitId { get; set; }

    [ForeignKey(nameof(ProduitId))]
    public virtual Produit? Produit { get; set; }

    // --- RELATION PANIER ---
    [Column("panier_id")]
    public int? PanierId { get; set; }

    [ForeignKey(nameof(PanierId))]
    public virtual Panier? Panier { get; set; }

    [Column("zone_id")]
    public int? ZoneId { get; set; }

    [ForeignKey(nameof(ZoneId))]
    public virtual Zone? Zone { get; set; }
public virtual ICollection<LigneCommande> LignesCommandes { get; set; } = new List<LigneCommande>();
    
} 
}

