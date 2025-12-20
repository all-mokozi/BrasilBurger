using Data;
using Models;
using Repository;

namespace Impl
{
    
    public class UtilisateurRepositoryImpl : IUtilisateurRepository
    {
        private readonly BrasilBurgerDbContext _context;

        public UtilisateurRepositoryImpl(BrasilBurgerDbContext context)
        {
            _context = context;
        }

        public void Add(Utilisateur u)
        {
            _context.Utilisateurs.Add(u);
            _context.SaveChanges();
        }

        public Utilisateur GetByEmail(string email)
        {
            return _context.Utilisateurs.FirstOrDefault(u => u.Email == email);
        }

        public Utilisateur GetByTelephone(string telephone)
        {
            return _context.Utilisateurs.FirstOrDefault(u => u.Telephone == telephone);
        }

        public Utilisateur GetById(int id)
        {
            return _context.Utilisateurs.Find(id);
        }
    }
}
    