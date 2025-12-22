using Microsoft.AspNetCore.Mvc;
using Models;
using Repository;
using Service;

namespace Impl
{

    public class CommandeServiceImpl : IcommandeService
    {
        private readonly ICommandeRepository _repo;
        private readonly ILigneCommandeRepository _ligneCrepo;
        private readonly IPaiementRepository _paiementRepo;
        public CommandeServiceImpl(ICommandeRepository repo,ILigneCommandeRepository ligneCrepo,IPaiementRepository paiementRepo)
        {
            _repo = repo;
             _ligneCrepo=ligneCrepo;
             _paiementRepo=paiementRepo;

        }

        public void addCommande(Commande c, LigneCommande l,Paiement p)
        {
            _repo.Insert(c);
            _ligneCrepo.Insert(l);
            _paiementRepo.Insert(p);
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