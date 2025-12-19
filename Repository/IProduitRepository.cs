using Models;

namespace Repository
{
    public interface  IProduitRepository
    {
        public IEnumerable<Produit> selectAllProduit();
         public IEnumerable<Produit> selectByType(String type);
         public Produit selectById(int id );
        
    }

}