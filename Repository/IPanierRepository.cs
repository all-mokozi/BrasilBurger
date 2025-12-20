using Models;

namespace Repository
{
    public interface IPanierRepository
    {
       public Panier GetPanierByClientId(int clientId);

        public void InsertCommandeToPanier(int clientId, Commande c);
        public void ValiderPanier(int clientId);
        public void DeleteProduitduPaierById(int ProduitId);
        

        // void RemoveFromPanier(int panierId, int commandeId);

        // void ClearPanier(int clientId);
        
        // void Save();
    
}
}