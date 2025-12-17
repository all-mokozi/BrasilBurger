using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("paiement")]
public class Paiement
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("montant")]
    public decimal MontantP { get; set; }

    [Column("mode")]
    public ModePaiement MoyenP { get; set; }

    [Column("commande_id")]
    public int CommandeId { get; set; }

    public Commande Commande { get; set; } = null!;
}

}