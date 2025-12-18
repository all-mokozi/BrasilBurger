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
