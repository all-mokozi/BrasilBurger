using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;




namespace Models
{
    [Table("commande")]

    public class Commande
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        [Column("date_commande")]
        public DateTime DateCommande { get; set; } = DateTime.Now;

        [Column("etat")]
        public string Etat { get; set; } = "EN_COURS";

        [Column("montant_total")]
        public decimal MontantTotal { get; set; }

        [Column("adresse_livraison")]
        public string? AddLivraison { get; set; }

        [Column("mode_livraison")]
        public required ModeConso ModeC { get; set; }

        [Column("client_id")]
        public int ClientId { get; set; }

        [Column("livreur_id")]
        public int? IdLivreur { get; set; }

        [Column("zone_id")]
        public int? IdZone { get; set; }
        public Zone? Zone { get; set; }

        public Paiement? Paiement { get; set; }
    }



}