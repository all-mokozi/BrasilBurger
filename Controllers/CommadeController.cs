using Microsoft.AspNetCore.Mvc;
using Models;
using Service;

namespace Controllers
{
    
    public class CommandeController : Controller
    {
        private readonly IcommandeService _service;
        public CommandeController(IcommandeService service)
        {
            _service=service;
            
        }
        [HttpPost]
        public IActionResult Index(Commande c)
        {
            _service.addCommande(c);
            return View();
        }
        
    }
}