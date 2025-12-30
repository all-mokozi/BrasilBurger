<?php
namespace App\Service;
use App\Entity\Commande;
interface CommandeServiceInterface
{
    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array;
    public function countCommandes(array $criteria = []): int;
    public function findById(int $id);
    public function changeEtat(int $id, string $newStatus): ?bool;
    public function listerLivraison(): void;
    public function getRecetteDuJour(): float;
    public function countCommandesByEtat(string $etat): int;
    public function getProduitPlusVendu(): ?string;
    public function getProduitPlusVenduDuJour(): ?array;
}