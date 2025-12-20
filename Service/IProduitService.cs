using Models;

namespace Service
{
    
    public interface IProduitService
    {
        public IEnumerable<Produit> afficherAllProduit();
        public IEnumerable<Produit> afficherProduitByType(String type);
        public Produit? getProduitById(int id );
    }
}