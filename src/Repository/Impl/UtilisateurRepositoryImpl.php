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
            ->select('u.id', 'u.nom', 'u.prenom')
            ->where('u.id = :id')
            ->setParameter('id', $id)
            ->getQuery()
            ->getResult();
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
