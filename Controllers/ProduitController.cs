using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Models;
using Service;

namespace Controllers
{
    [AllowAnonymous]
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
            
    
    bool estConnecte = User.Identity.IsAuthenticated;
    string nomUtilisateur = User.Identity.Name;
    
    // Passer ces informations à la vue
    ViewData["EstConnecte"] = estConnecte;
    ViewData["NomUtilisateur"] = nomUtilisateur;
            return View(produits);
            
        }
        public IActionResult FiltrerByType(String type)
        {
            var produits=_servive.afficherProduitByType(type);
            return View("Index",produits);
        }
         [HttpGet]
        public IActionResult Details(int id)
        {
            var produit=_servive.getProduitById(id);
            return View("Details",produit);
        }
        
    }
}