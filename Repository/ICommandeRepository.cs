using Models;

namespace Repository
{
    public interface ICommandeRepository
    {
        public void  Insert(Commande c ); 

         public void Delete(Commande c);
       

        public Commande GetById(int id);
          public IEnumerable<Commande> GetCommandeByClientId(int clientId);
    }
}