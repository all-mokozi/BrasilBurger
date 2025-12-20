  using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Models

{
    [Table("utilisateur")] 
    public class Utilisateur
    {
            [Column("id")] 
            public int Id { get; set; }

        [Required(ErrorMessage = "Le nom est requis")]
        [StringLength(20, MinimumLength = 4, ErrorMessage = "Le nom doit avoir entre {2} et {1} caractères")]
        [Column("nom")] 
        public required string Nom { get; set; }


        [Required(ErrorMessage = "L'email est requis")]
        [EmailAddress(ErrorMessage = "Ce champ doit être un email valide")]
        [Column("login")] 
        public required string Email { get; set; }

        [Required(ErrorMessage = "Le téléphone est requis")]
        [RegularExpression(@"^(77|78)[0-9]{7}$", ErrorMessage = "Le numéro doit contenir 9 chiffres et commencer par 77 ou 78")]
        [Column("telephone")] 
        public required string Telephone { get; set; }

        [Required(ErrorMessage = "Le mot de passe est requis")]
        [Column("password")] 
        
        public required string Password { get; set; }
         [Column("role")] 

        public string Role { get; set;} = "CLIENT";
        [Column("etat")] 

        public bool IsActive { get; set; } = true;

        public IEnumerable<Commande> Commandes { get; set; } = new List<Commande>();

        public override string ToString()
        {
            return $"ID: {Id}, Nom: {Nom}";
        }
    }
}




