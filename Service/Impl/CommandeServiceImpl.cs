using Microsoft.AspNetCore.Mvc;
using Models;
using Repository;
using Service;

namespace Impl
{
    
    public class CommandeServiceImpl : IcommandeService
    {   private readonly ICommandeRepository _repo;
    public CommandeServiceImpl(ICommandeRepository repo)
        {
            _repo=repo;
            
        }
        
        public void addCommande(Commande c)
        {
         _repo.Insert(c)   ;
        }
    }
}