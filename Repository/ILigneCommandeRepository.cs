using Models;

namespace Repository
{
    public interface ILigneCommandeRepository
    {
        public void  Insert(LigneCommande c ); 

        //  public void Delete(Commande c);
       

        // public Commande GetById(int id);
        //   public IEnumerable<Commande> GetCommandeByClientId(int clientId);
    }
}