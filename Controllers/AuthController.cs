using Microsoft.AspNetCore.Mvc;
using Models;
using Service;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using System.Security.Claims;

namespace Controllers
{
    public class AuthController : Controller
    {
        private readonly IUtilisateurService _userService;

        public AuthController(IUtilisateurService userService)
        {
            _userService = userService;
        }



        [HttpGet]
        public IActionResult Register() => View();

        [HttpPost]
        [HttpPost]
        public async Task<IActionResult> Register(RegisterViewModel model)
        {
            if (!ModelState.IsValid) return View(model);

            var user = new Utilisateur
            {
                Nom = model.Nom,
                Email = model.Email,
                Telephone = model.Telephone,
                Password = model.Password,
                Role = "CLIENT", 

            };

            try
            {
                _userService.Inscription(user);

                var claims = new List<Claim>
        {
            new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
            new Claim(ClaimTypes.Name, user.Nom),
            new Claim(ClaimTypes.Email, user.Email),
            new Claim(ClaimTypes.Role, user.Role),
            new Claim("Id", user.Id.ToString())
        };

                var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
                var principal = new ClaimsPrincipal(identity);

                await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, principal);

                return RedirectToAction("Index", "Produit");
            }
            catch (Exception ex)
            {
                ModelState.AddModelError("Email", ex.Message);
                return View(model);
            }
        }


        [HttpGet]
        public IActionResult Login() => View();

        [HttpPost]
        public async Task<IActionResult> Login(string email, string password)
        {
            var user = _userService.Connexion(email, password);

            if (user != null)
            {
                var claims = new List<Claim>
                {
                     new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()), // <-- IMPORTAN
                    new Claim(ClaimTypes.Name, user.Nom),
                    new Claim(ClaimTypes.Email, user.Email),
                    new Claim(ClaimTypes.Role, user.Role),
                    new Claim("Id", user.Id.ToString())
                };

                var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
                var principal = new ClaimsPrincipal(identity);

                await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, principal);

                return RedirectToAction("Index", "Produit");
            }

            ViewBag.Error = "Email ou mot de passe incorrect";
            return View();
        }

        public async Task<IActionResult> Logout()
        {
            await HttpContext.SignOutAsync();
            return RedirectToAction("Index", "Produit");
        }
    }
}