using Microsoft.AspNetCore.Mvc;
using Service;
using Models;
using Microsoft.AspNetCore.Authorization;
using System.Security.Claims;

[Authorize]
public class PanierController : Controller
{
    private readonly IPanierService _panierService;

    public PanierController(IPanierService panierService)
    {
        _panierService = panierService;
    }

    private int GetClientId()
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        if (userIdClaim == null) throw new UnauthorizedAccessException("Utilisateur non connecté");
        return int.Parse(userIdClaim);
    }

    [HttpGet]
    public IActionResult Index()
    {
        int clientId = GetClientId();
        var panier = _panierService.ObtenirPanierClient(clientId);
        return View(panier);
    }

    [HttpPost]
    public IActionResult Ajouter(int ProduitId, int Quantite, ModeConso ModeConsommation)
    {
        int clientId = GetClientId();
        try
        {
            _panierService.AjouterAuPanier(clientId, ProduitId, Quantite, ModeConsommation);
            return RedirectToAction("Index");
        }
        catch (Exception ex)
        {
            TempData["Error"] = ex.Message;
            return RedirectToAction("Index");
        }
    }

    [HttpPost]
    [Route("Panier/Valider")]
    public IActionResult Valider()
    {
        int clientId = GetClientId();
        try
        {
            _panierService.ValiderLePanier(clientId);
            TempData["Success"] = "Votre commande a été validée avec succès !";
            return RedirectToAction(nameof(Index));
        }
        catch (Exception ex)
        {
            TempData["Error"] = "Erreur lors de la validation : " + ex.Message;
            return RedirectToAction(nameof(Index));
        }
    }
}
