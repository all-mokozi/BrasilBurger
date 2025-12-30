<?php
namespace App\Service;
use App\Entity\Commande;
use App\Entity\Utilisateur;

interface UtilisateurServiceInterface
{
    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array;
    public function countUtilisateur(): int;
    public function countUtilisateurByRole(string $role): int;
    public function ajouterLivreur(Utilisateur $utilisateur): int;
    // public function findById(int $id);
    // public function changeEtat(int $id, string $newStatus): ?bool;
}