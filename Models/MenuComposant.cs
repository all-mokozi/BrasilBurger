using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("menu_composant")]
    public class MenuComposant
    {
        [Column("id_menu")]
        public int IdMenu { get; set; }

        [Column("id_produit_composant")]
        public int IdProduitComposant { get; set; }

        [Required]
        [Column("quantite")]
        [Range(1, int.MaxValue, ErrorMessage = "La quantité doit être supérieure à 0")]
        public int Quantite { get; set; }

        public Produit Menu { get; set; } = null!;

        public Produit ProduitComposant { get; set; } = null!;
    }
}
