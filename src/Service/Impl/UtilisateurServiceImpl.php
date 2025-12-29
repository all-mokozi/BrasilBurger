<?php
namespace App\Service\Impl;
use App\Repository\UtilisateurRepositoryInterface;
use App\Service\UtilisateurServiceInterface;

class UtilisateurServiceImpl implements UtilisateurServiceInterface
{
    public function __construct(private UtilisateurRepositoryInterface $utilisateurRepository)
    {
    }

    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array
    {
        return $this->utilisateurRepository->list($criteria, $orderBy, $limit, $offset) ;
    }

        public function countUtilisateur(): int{
        return $this->utilisateurRepository->countAll();
        }

    public function countUtilisateurByRole(string $role): int{
        return $this->utilisateurRepository->countByRole($role);
        }

    // public function countCommandes(): int
    // {
    //     return $this->utilisateurRepository->countAll();
    // }
    // public function findById(int $id)
    // {
    //     return $this->utilisateurRepository->findById($id);
        
    // }
   
   
    
}