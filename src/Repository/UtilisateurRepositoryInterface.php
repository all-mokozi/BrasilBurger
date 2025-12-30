<?php
namespace App\Repository;

use App\Entity\Commande;
use App\Entity\Utilisateur;
use Monolog\Handler\Curl\Util;

interface UtilisateurRepositoryInterface
{
     public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array;
  
    public function countAll(): int;
    public function countByRole(string $role): int;
    public function findUtilisateurById(int $id):?Utilisateur;
    public function save(Utilisateur $entity, bool $flush = false): void;

    // public function save(Utilisateur $utilisateur): void;
}