using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("produit")]
    public class Produit
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        [Required]
        [Column("nom")]
        [StringLength(25)]
        public string Nom { get; set; } = null!;

        [Required]
        [Column("prix", TypeName = "numeric(10,2)")]
        [Range(0, double.MaxValue, ErrorMessage = "Le prix doit être >= 0")]
        public decimal Prix { get; set; }

        [Column("image")]
        [StringLength(512)]
        public string? Image { get; set; }

        [Column("description")]
        [StringLength(500)]
        public string? Description { get; set; }

        [Required]
        [Column("type_produit")]
        public TypeProduit TypeProduit { get; set; }

        [Column("is_archived")]
        public bool IsArchived { get; set; } = false;

        
          public ICollection<MenuComposant> ComposantsDuMenu { get; set; }
            = new List<MenuComposant>();

        
        public ICollection<MenuComposant> UtiliseDansMenus { get; set; }
            = new List<MenuComposant>();
    }
    }
