using Models;

namespace Repository
{
    public interface IUtilisateurRepository
    {
        void Add(Utilisateur u);
        Utilisateur GetByEmail(string email);
        Utilisateur GetByTelephone(string telephone);
        Utilisateur GetById(int id);
    }
}