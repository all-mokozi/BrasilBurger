using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("ligne_commande")]
    public class LigneCommande
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        [Required]
        [Column("quantite")]
        [Range(1, int.MaxValue, ErrorMessage = "La quantité doit être supérieure à 0")]
        public int Quantite { get; set; }

        [Required]
        [Column("prix_unitaire", TypeName = "numeric(10,2)")]
        [Range(0, double.MaxValue, ErrorMessage = "Le prix unitaire doit être >= 0")]
        public decimal PrixUnitaire { get; set; }

        [Required]
        [Column("commande_id")]
        public int CommandeId { get; set; }
        [ForeignKey("CommandeId")]
        public Commande? Commande { get; set; }
        [Required]
        [Column("produit_id")]
        public int ProduitId { get; set; }

        public Produit Produit { get; set; } = null!;
    }
}
