using Data;
using Models;
using Repository;

namespace Impl
{
    
    public class CommandeRepositoryImpl : ICommandeRepository
    {
        private readonly BrasilBurgerDbContext _context;
        public CommandeRepositoryImpl(BrasilBurgerDbContext context)
        {
            _context=context;
        }
        public void  Insert(Commande c)
        {
            _context.Add(c);
            _context.SaveChanges();

           
        }
    }
}