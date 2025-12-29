<?php
namespace App\Repository;

use App\Entity\Commande;

interface CommandeRepositoryInterface
{
    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array;
    public function findAllWithClient();
    public function countAll(): int;
    public function findById(int $id): ?Commande;
    public function save(Commande $commande): void;
}