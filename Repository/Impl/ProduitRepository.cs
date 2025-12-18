using Data;
using Models;
using Repository;

namespace Impl
{
    public class ProduitRepository : IProduitRepository
    {
        private readonly BrasilBurgerDbContext _context;
        public ProduitRepository(BrasilBurgerDbContext context)
        {
            _context = context;
        }
        public IEnumerable<Produit> selectAllProduit()
        {
            return _context.Produits.ToList();

        }
        public IEnumerable<Produit> selectByType(string type)
        {
            if (string.IsNullOrEmpty(type) || type.ToLower() == "tout")
                return _context.Produits.ToList();

            Enum.TryParse<TypeProduit>(type, true, out var typeProduit);

            return _context.Produits
                .Where(p => p.TypeProduit == typeProduit)
                .ToList();
        }
       

    }

}