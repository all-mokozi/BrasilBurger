using Microsoft.AspNetCore.Mvc;
using Models;
using Repository;
using Service;

namespace Impl
{

    public class CommandeServiceImpl : IcommandeService
    {
        private readonly ICommandeRepository _repo;
        public CommandeServiceImpl(ICommandeRepository repo)
        {
            _repo = repo;

        }

        public void addCommande(Commande c, LigneCommande l)
        {
            _repo.Insert(c);
        }
        public void SupprimerProduitDuPanier(int commandeId)
        {
            var commande = _repo.GetById(commandeId);
            if (commande != null)
            {
                _repo.Delete(commande);
            }
        }
        public Commande GetCommandeById(int id)
        {
            return _repo.GetById(id);
        }

        public void SupprimerCommande(Commande c)
        {
            _repo.Delete(c);
        }
        public IEnumerable<Commande> GetCommandeByClientId(int clientId)
        {
           return _repo.GetCommandeByClientId(clientId);
        }

    }

}