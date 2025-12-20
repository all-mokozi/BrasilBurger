using Models;

namespace Service
{
    public interface IPanierService
    {
        void AjouterAuPanier(int clientId, int produitId, int quantite, ModeConso mode);

        Panier ObtenirPanierClient(int clientId);
         public void ValiderLePanier(int clientId);

        // void RetirerDuPanier(int commandeId);

        // void ViderPanier(int clientId);
    }
}