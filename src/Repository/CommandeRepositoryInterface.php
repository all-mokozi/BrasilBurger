<?php
namespace App\Repository;

use App\Entity\Commande;

interface CommandeRepositoryInterface
{
    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array;
    public function findAllWithClient();
    public function countAll(): int;
    public function countByCriteria(array $criteria = []): int;
    public function findById(int $id): ?Commande;
    public function save(Commande $commande): void;
    public function getRecetteDuJour(): float;
    public function countCommandesByEtat(string $etat): int;
    public function getProduitPlusVendu(): ?string;
    public function getProduitPlusVenduDuJour(): ?array;
}