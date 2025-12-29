<?php

namespace App\Repository\Impl;

use App\Entity\Utilisateur;
use App\Repository\UtilisateurRepositoryInterface;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Utilisateur>
 */
class UtilisateurRepositoryImpl extends ServiceEntityRepository implements UtilisateurRepositoryInterface
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Utilisateur::class);
    }
    public function findUtilisateurById(int $id): ?Utilisateur
    {
         return $this->createQueryBuilder('u')
        ->where('u.id = :id')
        ->andWhere('u.role = :role')
        ->setParameter('id', $id)
        ->setParameter('role', 'LIVREUR')
        ->getQuery()
        ->getOneOrNullResult();
        
    }

    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array
    {
        $qb = $this->createQueryBuilder('u');

        foreach ($criteria as $field => $value) {
            $qb->andWhere("u.$field = :$field")
               ->setParameter($field, $value);
        }

        foreach ($orderBy as $field => $direction) {
            $qb->addOrderBy("u.$field", $direction);
        }

        if ($limit !== null) {
            $qb->setMaxResults($limit);
        }

        if ($offset !== null) {
            $qb->setFirstResult($offset);
        }

        return $qb->getQuery()->getResult();
    }
    public function countAll(): int
    {
        return (int) $this->createQueryBuilder('u')
            ->select('COUNT(u.id)')
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function countByRole(string $role): int
    {
        return (int) $this->createQueryBuilder('u')
            ->select('COUNT(u.id)')
            ->where('u.role = :role')
            ->setParameter('role', $role)
            ->getQuery()
            ->getSingleScalarResult();
    }

//    /**
//     * @return Utilisateur[] Returns an array of Utilisateur objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('u')
//            ->andWhere('u.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('u.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Utilisateur
//    {
//        return $this->createQueryBuilder('u')
//            ->andWhere('u.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
