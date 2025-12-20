using Models;
using Repository;
using Service;

namespace Impl
{
    public class ProduitServiceImpl : IProduitService
    {
        
        private readonly IProduitRepository _repo;
        public  ProduitServiceImpl(IProduitRepository repo)
        {
            _repo=repo;
        }
        public IEnumerable<Produit> afficherAllProduit()
        {
          return  _repo.selectAllProduit();   
    }
    public IEnumerable<Produit> afficherProduitByType(String type)
        {
            return _repo.selectByType( type);
        }
        
        public Produit? getProduitById(int id)
        {
            return _repo.SelectById(id);
        }
}
}