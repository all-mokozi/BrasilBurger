using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models
{
    [Table("quartier")]
    public class Quartier
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        [Required]
        [Column("nom")]
        [StringLength(100)]
        public string Nom { get; set; } = null!;

        // Foreign Key vers Zone
        [Required]
        [Column("zone_id")]
        public int ZoneId { get; set; }

        public Zone Zone { get; set; } = null!;
    }
}
