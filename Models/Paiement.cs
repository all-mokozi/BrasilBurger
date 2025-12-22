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

    [Column("date_paiement")]
    public DateTime DatePaiement { get; set; } = DateTime.Now;

    [Column("montant")]
    public decimal Montant { get; set; }

    [Column("methode_paiement")] // Doit être 'WAVE', 'OM' ou 'ESPECES'
    public string MethodePaiement { get; set; }

    [Column("reference_transaction")]
    public string? ReferenceTransaction { get; set; }

    [Column("commande_id")]
    public int CommandeId { get; set; }

    [ForeignKey("CommandeId")]
    public Commande Commande { get; set; } = null!;
}}