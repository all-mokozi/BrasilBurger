using Models;
using Repository;

namespace Service
{
    public class UtilisateurServiceImpl : IUtilisateurService
    {
        private readonly IUtilisateurRepository _repo;

        public UtilisateurServiceImpl(IUtilisateurRepository repo)
        {
            _repo = repo;
        }

        public void Inscription(Utilisateur u)
        {
            if (EmailExisteDeja(u.Email))
                throw new Exception("Cet email est déjà utilisé.");
            
            
            _repo.Add(u);
        }

        public Utilisateur Connexion(string email, string password)
        {
            var user = _repo.GetByEmail(email);
            
            // On vérifie si l'utilisateur existe et si le mot de passe correspond
            if (user != null && user.Password == password)
            {
                return user;
            }
            return null; // Identifiants invalides
        }

        public bool EmailExisteDeja(string email)
        {
            return _repo.GetByEmail(email) != null;
        }
    }
}