using Data;
using Microsoft.AspNetCore.Mvc;
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
            return _context.Produits
                .OrderByDescending(p=>p.Id )
                .ToList();;

        }
        public IEnumerable<Produit> selectByType(string type)
        {
            if (string.IsNullOrEmpty(type) || type.ToLower() == "tout")
                return _context.Produits.ToList();

            Enum.TryParse<TypeProduit>(type, true, out var typeProduit);

            return _context.Produits
                .Where(p => p.TypeProduit == typeProduit)
                .OrderByDescending(p=>p.Id )
                .ToList();
        }
        public Produit? SelectById(int id)

        {
            
          return _context.Produits.Find(id);
          
                  
          
           
            
        }
       

    }

}