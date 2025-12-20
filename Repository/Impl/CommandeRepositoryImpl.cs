using Data;
using Models;
using Repository;
using Microsoft.EntityFrameworkCore;

namespace Impl
{
    public class CommandeRepositoryImpl : ICommandeRepository
    {
        private readonly BrasilBurgerDbContext _context;

        public CommandeRepositoryImpl(BrasilBurgerDbContext context)
        {
            _context = context;
        }

        public void Insert(Commande c)
        {
            _context.Commandes.Add(c); // Utilise le nom exact de ton DbSet
            _context.SaveChanges();
        }

        public void Delete(Commande c)
        {
            // On s'assure que l'entité est suivie par le contexte avant de supprimer
            if (_context.Entry(c).State == EntityState.Detached)
            {
                _context.Commandes.Attach(c);
            }
            _context.Commandes.Remove(c);
            _context.SaveChanges();
        }
        public IEnumerable<Commande> GetCommandeByClientId(int clientId)
        {
           return  _context.Commandes.Where(c=>c.ClientId==clientId)
           .Include(c=>c.Produit)
           .OrderByDescending(c=>c.DateCommande).ToList();
        }

        public Commande GetById(int id)
        {
            // .Find(id) est parfait ici
            return _context.Commandes.Find(id);
        }
    }
}