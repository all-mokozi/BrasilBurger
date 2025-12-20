using Microsoft.AspNetCore.Mvc;
using Models;
using Service;
using System.ComponentModel.DataAnnotations;

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
    public IActionResult Confirmer(int ProduitId, int Quantite, ModeConso ModeConsommation, int? ZoneId)
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

        var commande = new Commande
        {
            ClientId = 1, // À remplacer par l'ID de l'utilisateur connecté plus tard
            DateCommande = DateTime.UtcNow,
            MontantTotal = Quantite * produit.Prix,
            ModeC = ModeConsommation,
            Etat = "VALIDE",
            ZoneId = estLivraison ? ZoneId : null,
            ProduitId = ProduitId,

        };

        var ligneCommande = new LigneCommande
        {
            ProduitId = ProduitId,
            Quantite = Quantite,
            PrixUnitaire = produit.Prix,
            Commande = commande
        };

        try
        {
            _service.addCommande(commande, ligneCommande);

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
    [HttpPost]
    public IActionResult Retirer(int commandeId)
    {
        try
        {
            // On récupère la commande via le service
            var commande = _service.GetCommandeById(commandeId);

            if (commande != null)
            {
                _service.SupprimerCommande(commande);
                TempData["Success"] = "Produit retiré du panier.";
            }

            // On redirige vers l'index du Panier pour voir la mise à jour
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
    int clientId = 1; 
    var mesCommandes = _service.GetCommandeByClientId(clientId);

    return View(mesCommandes);
}
  
}