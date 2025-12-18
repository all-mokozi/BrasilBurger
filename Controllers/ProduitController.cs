using Microsoft.AspNetCore.Mvc;
using Models;
using Service;

namespace Controllers
{
    
    public class ProduitController : Controller
    {
        private readonly IProduitService _servive;
        public ProduitController(IProduitService service)
        {
            _servive=service;
        }
        public IActionResult Index()
        {
            var produits=_servive.afficherAllProduit();
            return View(produits);
            
        }
        public IActionResult FiltrerByType(String type)
        {
            var produits=_servive.afficherProduitByType(type);
            return View("Index",produits);
        }
        
    }
}