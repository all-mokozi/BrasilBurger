using Models;

namespace Service
{
    public interface IUtilisateurService
    {
        void Inscription(Utilisateur u);
        Utilisateur Connexion(string email, string password);
        bool EmailExisteDeja(string email);
    }
}