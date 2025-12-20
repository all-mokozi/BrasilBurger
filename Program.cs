using Data;
using Impl;
using Repository;
using Service;
using Microsoft.EntityFrameworkCore;
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
var app = builder.Build();


if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}


app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Produit}/{action=Index}/{id?}");

app.Run();
