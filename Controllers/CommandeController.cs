using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Models;
using Service;
using System.ComponentModel.DataAnnotations;
using System.Security.Claims;
[Authorize]
public class CommandeController : Controller
{
    private readonly IcommandeService _service;
    private readonly IProduitService _produitService;
    private readonly IZoneService _zoneService;

    public CommandeController(IcommandeService service, IProduitService produitService, IZoneService zoneService)
    {
        _service = service;
        _produitService = produitService;
        _zoneService = zoneService;
    }

    [HttpGet]
    public IActionResult Commande(int id)
    {
        var produit = _produitService.getProduitById(id);
        if (produit == null) return NotFound();

        ViewBag.Zones = _zoneService.GetAllZones() ?? new List<Zone>();

        return View(produit);
    }

    [HttpPost]
public IActionResult Confirmer(int ProduitId, int Quantite, ModeConso ModeConsommation, int? ZoneId, ModePaiement MoyenPaiement )
{
    var produit = _produitService.getProduitById(ProduitId);
    if (produit == null) return NotFound();

    bool estLivraison = ModeConsommation == ModeConso.LIVRAISON;

    if (Quantite <= 0 || (estLivraison && ZoneId == null))
    {
        if (Quantite <= 0)
            ModelState.AddModelError("Quantite", "La quantité doit être supérieure à 0.");

        if (estLivraison && ZoneId == null)
            ModelState.AddModelError("ZoneId", "La zone de livraison est obligatoire pour ce mode.");

        ViewBag.Zones = _zoneService.GetAllZones() ?? new List<Zone>();
        return View("Commande", produit);
    }

    var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
    if (userIdClaim == null) return Unauthorized();

    int clientId = int.Parse(userIdClaim);

    var commande = new Commande
    {
        ClientId = clientId, 
        DateCommande = DateTime.UtcNow,
        MontantTotal = Quantite * produit.Prix,
        ModeC = ModeConsommation,
        Etat = "VALIDE",
        ZoneId = estLivraison ? ZoneId : null,
        ProduitId = ProduitId,
        Quantite = Quantite,
    };

    var ligneCommande = new LigneCommande
    {
        ProduitId = ProduitId,
        Quantite = Quantite,
        PrixUnitaire = produit.Prix,
        Commande = commande
    };
  var paiement = new Paiement
{
    Montant = Quantite * produit.Prix,
    MethodePaiement = MoyenPaiement.ToString(), 
    DatePaiement = DateTime.UtcNow,
    Commande = commande ,
    ReferenceTransaction= "REF:"+ Guid.NewGuid().ToString("N")[..8].ToUpper(),


};

    try
    {
        _service.addCommande(commande, ligneCommande,paiement);
        return RedirectToAction("Index", "Produit");
    }
    catch (Exception ex)
    {
        Console.WriteLine($"[ERREUR CONFIRMATION]: {ex.Message}");
        if (ex.InnerException != null) Console.WriteLine($"[INNER]: {ex.InnerException.Message}");

        ViewBag.Zones = _zoneService.GetAllZones() ?? new List<Zone>();
        ModelState.AddModelError("", "Une erreur est survenue lors de l'enregistrement. Veuillez réessayer.");
        return View("Commande", produit);
    }
}
    public IActionResult Retirer(int commandeId)
    {
        try
        {
            
            var commande = _service.GetCommandeById(commandeId);

            if (commande != null)
            {
                _service.SupprimerCommande(commande);
                TempData["Success"] = "Produit retiré du panier.";
            }

         
            return RedirectToAction("Index", "Panier");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"[ERREUR SUPPRESSION]: {ex.Message}");
            TempData["Erreur"] = "Impossible de supprimer l'article.";
            return RedirectToAction("Index", "Panier");
        }
    }
public IActionResult MesCommandes()
{
    var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
    if (userIdClaim == null) return Unauthorized();

    int clientId = int.Parse(userIdClaim);

    var mesCommandes = _service.GetCommandeByClientId(clientId);

    return View(mesCommandes);
}

  
}