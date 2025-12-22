using Models;

namespace Service
{

    public interface IcommandeService
    {
        public void addCommande(Commande c, LigneCommande l,Paiement p);
        public Commande GetCommandeById(int id);

        public void SupprimerCommande(Commande c);
          public IEnumerable<Commande> GetCommandeByClientId(int clientId);
    }
}