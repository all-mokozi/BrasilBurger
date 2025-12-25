<?php
namespace App\Service;
use App\Entity\Commande;
interface CommandeServiceInterface
{
    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array;
    public function countCommandes(): int;
}