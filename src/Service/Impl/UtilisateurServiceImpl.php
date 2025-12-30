<?php

namespace App\Service\Impl;

use App\Entity\Utilisateur;
use App\Repository\UtilisateurRepositoryInterface;
use App\Service\UtilisateurServiceInterface;

class UtilisateurServiceImpl implements UtilisateurServiceInterface
{
    public function __construct(private UtilisateurRepositoryInterface $utilisateurRepository) {}

    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array
    {
        return $this->utilisateurRepository->list($criteria, $orderBy, $limit, $offset);
    }

    public function countUtilisateur(): int
    {
        return $this->utilisateurRepository->countAll();
    }
    public function countUtilisateurByRole(string $role): int
    {
        return $this->utilisateurRepository->countByRole($role);
    }
    public function ajouterLivreur(Utilisateur $utilisateur): int
    {
        foreach ($this->utilisateurRepository->list(['email' => $utilisateur->getEmail()]) as $existingUser) {
            if ($existingUser->getEmail() === $utilisateur->getEmail()) {
                throw new \Exception("Un utilisateur avec cet email existe déjà.");
            }
        }
        $this->utilisateurRepository->save($utilisateur, true);

        return $utilisateur->getId();
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
