using Microsoft.AspNetCore.Mvc;
using Service;
using Models;

public class PanierController : Controller
{
    private readonly IPanierService _panierService;

    public PanierController(IPanierService panierService)
    {
        _panierService = panierService;
    }

    [HttpGet]
    public IActionResult Index()
    {
        int clientId = 1; 
        var panier = _panierService.ObtenirPanierClient(clientId);
        return View(panier);
    }

    [HttpPost]
    
public IActionResult Ajouter(int ProduitId, int Quantite, ModeConso ModeConsommation) 
{
    int clientId = 1;
    try 
    {
        // On passe ModeConsommation qui vient du formulaire
        _panierService.AjouterAuPanier(clientId, ProduitId, Quantite, ModeConsommation);
        return RedirectToAction("Index");
    }
    catch (Exception ex)
    {
        return BadRequest(ex.Message);
    }
}
[HttpPost]
[Route("Panier/Valider")] 
public IActionResult Valider()
{
    int clientId = 1;
    try 
    {
        _panierService.ValiderLePanier(clientId);
        return RedirectToAction(nameof(Index)); 
    }
    catch (Exception ex)
    {
        return RedirectToAction(nameof(Index));
    }
}
    // [HttpPost]
    // public IActionResult Supprimer(int commandeId)
    // {
    //     // On passe par le service plutôt que le context directement
    //     _panierService.RetirerDuPanier(commandeId);
    //     return RedirectToAction("Index");
    // }

    // [HttpPost]
//     public IActionResult Valider()
//     {
//         int clientId = 1;
//         try 
//         {
//             _panierService.ViderPanier(clientId); // Ou une méthode ConfirmerPanier
//             TempData["Success"] = "Votre commande Brasil Burger est en route !";
//             return RedirectToAction("Index", "Produit");
//         }
//         catch (Exception ex)
//         {
//             TempData["Error"] = "Erreur validation : " + ex.Message;
//             return RedirectToAction("Index");
//         }
//     }
}