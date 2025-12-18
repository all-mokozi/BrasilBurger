using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("menu_composant")]
    public class MenuComposant
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        // 🔑 FK vers le menu
        [Column("id_menu")]
        public int MenuId { get; set; }

        // 🔑 FK vers le produit composant
        [Column("id_produit_composant")]
        public int ProduitComposantId { get; set; }

        [Required]
        [Column("quantite")]
        [Range(1, int.MaxValue)]
        public int Quantite { get; set; }

        // 🔗 Navigations
        public Produit Menu { get; set; } = null!;
        public Produit ProduitComposant { get; set; } = null!;
    }
}
