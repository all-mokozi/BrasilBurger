using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("zone")]
    public class Zone
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        [Required]
        [Column("nom")]
        [StringLength(100)]
        public string Nom { get; set; } = null!;

        [Required]
        [Column("prix_livraison", TypeName = "numeric(10,2)")]
        [Range(0, double.MaxValue, ErrorMessage = "Le prix de livraison doit être >= 0")]
        public decimal PrixLivraison { get; set; }

        // Navigation vers commandes
        public List<Commande>? Commandes { get; set; }

        // Navigation vers quartiers
        public List<Quartier>? Quartiers { get; set; }
    }
}
