<?php
namespace App\Repository;
interface CommandeRepositoryInterface
{
    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array;
    public function findAllWithClient();
    public function countAll(): int;
}