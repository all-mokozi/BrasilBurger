using Data;
using Impl;
using Repository;
using Service;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authentication.Cookies;
var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");

builder.Services.AddDbContext<BrasilBurgerDbContext>(options =>
    options.UseNpgsql(connectionString));
builder.Services.AddScoped<IProduitRepository, ProduitRepository>();
builder.Services.AddScoped<IProduitService, ProduitServiceImpl>();
builder.Services.AddScoped<IcommandeService, CommandeServiceImpl>();
builder.Services.AddScoped<ICommandeRepository, CommandeRepositoryImpl>();
builder.Services.AddScoped<IZoneRepository,ZoneRepositoryImpl>();
builder.Services.AddScoped<IZoneService,ZoneServiceImpl>();
builder.Services.AddScoped<IPanierRepository, PanierRepositoryImpl>();
builder.Services.AddScoped<IPanierService, PanierServiceImpl>();
builder.Services.AddScoped<IUtilisateurRepository, UtilisateurRepositoryImpl>();
builder.Services.AddScoped<IUtilisateurService, UtilisateurServiceImpl>();

builder.Services.AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme)
    .AddCookie(options =>
    {
        options.LoginPath = "/Auth/Login";
        options.AccessDeniedPath = "/Auth/Login";
        options.ExpireTimeSpan = TimeSpan.FromHours(2);
        options.SlidingExpiration = true;
    });

var app = builder.Build();


if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}


app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();
app.UseAuthentication();
app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Produit}/{action=Index}/{id?}");

app.Run();
