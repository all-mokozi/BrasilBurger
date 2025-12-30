<?php
namespace App\Service\Impl;
use App\Repository\CommandeRepositoryInterface;

use App\Service\CommandeServiceInterface;

class CommandeServiceImpl implements CommandeServiceInterface
{
    public function __construct(private CommandeRepositoryInterface $commandeRepository)
    {
    }

    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array
    {
        return $this->commandeRepository->list($criteria, $orderBy, $limit, $offset);
    }
    public function countCommandes(array $criteria = []): int
    {
        return $this->commandeRepository->countByCriteria($criteria);
    }
    public function findById(int $id)
    {
        return $this->commandeRepository->findById($id);
        
    }
    public function changeEtat(int $id, string $newStatus): ?bool
    {
        $commande = $this->commandeRepository->findById($id);
        if (!$commande) {
            return null; 
        }

        $commande->setEtat($newStatus);
        $this->commandeRepository->save($commande);

        return true; 
    }
    public function listerLivraison(): void
    {
        $this->commandeRepository->list(['modeLivraison' => 'LIVRAISON'], ['date_commande' => 'DESC'], null, null);
    }

    public function getRecetteDuJour(): float
    {
        return $this->commandeRepository->getRecetteDuJour();
    }

    public function countCommandesByEtat(string $etat): int
    {
        return $this->commandeRepository->countCommandesByEtat($etat);
    }

    public function getProduitPlusVendu(): ?string
    {
        return $this->commandeRepository->getProduitPlusVendu();
    }

    public function getProduitPlusVenduDuJour(): ?array
    {
        return $this->commandeRepository->getProduitPlusVenduDuJour();
    }

    public function getLivraisonsByLivreur(int $livreurId, array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array
    {
        return $this->commandeRepository->getLivraisonsByLivreur($livreurId, $criteria, $orderBy, $limit, $offset);
    }

    public function assignerLivraisonALivreur(int $commandeId, int $livreurId): bool
    {
        return $this->commandeRepository->assignerLivraisonALivreur($commandeId, $livreurId);
    }
    
}