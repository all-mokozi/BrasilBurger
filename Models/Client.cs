  using System.ComponentModel.DataAnnotations;

namespace Models

{
    public class Client
    {
        public int Id { get; set; }

        [Required(ErrorMessage = "Le nom est requis")]
        [StringLength(20, MinimumLength = 4, ErrorMessage = "Le nom doit avoir entre {2} et {1} caractères")]
        public required string Nom { get; set; }

        [Required(ErrorMessage = "Le prénom est requis")]
        [StringLength(20, MinimumLength = 4, ErrorMessage = "Le prénom doit avoir entre {2} et {1} caractères")]
        public required string Prenom { get; set; }

        [Required(ErrorMessage = "L'email est requis")]
        [EmailAddress(ErrorMessage = "Ce champ doit être un email valide")]
        public required string Login { get; set; }

        [Required(ErrorMessage = "Le téléphone est requis")]
        [RegularExpression(@"^(77|78)[0-9]{7}$", ErrorMessage = "Le numéro doit contenir 9 chiffres et commencer par 77 ou 78")]
        public required string Telephone { get; set; }

        [Required(ErrorMessage = "Le mot de passe est requis")]
        public required string Password { get; set; }

        public string Role { get; } = "CLIENT";

        public bool IsActive { get; set; } = true;

        public IEnumerable<Commande> Commandes { get; set; } = new List<Commande>();

        public override string ToString()
        {
            return $"ID: {Id}, Nom: {Nom}, Prenom: {Prenom}";
        }
    }
}




