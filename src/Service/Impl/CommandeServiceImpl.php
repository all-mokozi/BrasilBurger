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
    public function countCommandes(): int
    {
        return $this->commandeRepository->countAll();
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
   
    
}