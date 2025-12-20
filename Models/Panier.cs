using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("panier")]
    public class Panier
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        [Column("client_id")]
        public int ClientId { get; set; }

        public virtual ICollection<Commande> Commandes { get; set; } = new List<Commande>();

        [NotMapped]
        public decimal TotalPanier => Commandes.Sum(c => c.MontantTotal);
    }
}