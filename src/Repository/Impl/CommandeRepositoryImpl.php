<?php

namespace App\Repository\Impl;

use App\Entity\Commande;
use App\Repository\CommandeRepositoryInterface;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Commande>
 */
class CommandeRepositoryImpl extends ServiceEntityRepository implements CommandeRepositoryInterface
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }
    public function list(array $criteria = [], array $orderBy = [], ?int $limit = null, ?int $offset = null): array
    {
        $qb = $this->createQueryBuilder('c');
        if (isset($criteria['hasLivreur'])) {
            if ($criteria['hasLivreur'] === true) {
                $qb->andWhere('c.livreur IS NOT NULL');
            } elseif ($criteria['hasLivreur'] === false) {
                $qb->andWhere('c.livreur IS NULL');
            }
            unset($criteria['hasLivreur']);
        }

        if (isset($criteria['clientNom'])) {
            $qb->join('c.client', 'client');
            $qb->andWhere('LOWER(client.nom) LIKE LOWER(:clientNom)')
                ->setParameter('clientNom', '%' . $criteria['clientNom'] . '%');
            unset($criteria['clientNom']);
        }

        if (isset($criteria['dateCommandeStart']) && isset($criteria['dateCommandeEnd'])) {
            $qb->andWhere('c.dateCommande BETWEEN :start AND :end')
                ->setParameter('start', $criteria['dateCommandeStart'])
                ->setParameter('end', $criteria['dateCommandeEnd']);
            unset($criteria['dateCommandeStart'], $criteria['dateCommandeEnd']);
        }

        foreach ($criteria as $field => $value) {
            $qb->andWhere("c.$field = :$field")
                ->setParameter($field, $value);
        }

        foreach ($orderBy as $field => $direction) {
            $qb->addOrderBy("c.$field", $direction);
        }

        if ($limit !== null) {
            $qb->setMaxResults($limit);
        }

        if ($offset !== null) {
            $qb->setFirstResult($offset);
        }

        return $qb->getQuery()->getResult();
    }
    public function findAllWithClient(): array
    {
        return $this->createQueryBuilder('c')
            ->select('c', 'u')
            ->join('c.client', 'u')
            ->getQuery()
            ->getResult();
    }

    public function countAll(): int
    {
        return (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function countByCriteria(array $criteria = []): int
    {
        $qb = $this->createQueryBuilder('c')
            ->select('COUNT(c.id)');

        if (!empty($criteria['hasLivreur']) && $criteria['hasLivreur'] === true) {
            $qb->andWhere('c.livreur IS NOT NULL');
            unset($criteria['hasLivreur']); // 🔥 TRÈS IMPORTANT
        }

        if (isset($criteria['clientNom'])) {
            $qb->join('c.client', 'client');
            $qb->andWhere('LOWER(client.nom) LIKE LOWER(:clientNom)')
                ->setParameter('clientNom', '%' . $criteria['clientNom'] . '%');
            unset($criteria['clientNom']);
        }

        if (isset($criteria['dateCommandeStart']) && isset($criteria['dateCommandeEnd'])) {
            $qb->andWhere('c.dateCommande BETWEEN :start AND :end')
                ->setParameter('start', $criteria['dateCommandeStart'])
                ->setParameter('end', $criteria['dateCommandeEnd']);
            unset($criteria['dateCommandeStart'], $criteria['dateCommandeEnd']);
        }

        foreach ($criteria as $field => $value) {
            $qb->andWhere("c.$field = :$field")
                ->setParameter($field, $value);
        }

        return (int) $qb->getQuery()->getSingleScalarResult();
    }



    public function findById(int $id): ?Commande
    {
        return $this->find($id);
    }
    public function save(Commande $commande): void
    {
        $this->getEntityManager()->persist($commande);


        $this->getEntityManager()->flush();
    }

    public function getRecetteDuJour(): float
    {
        $today = new \DateTime('today');
        $tomorrow = new \DateTime('tomorrow');

        return (float) $this->createQueryBuilder('c')
            ->select('SUM(c.montantTotal)')
            ->where('c.etat IN (:etats)')
            ->andWhere('c.dateCommande >= :today')
            ->andWhere('c.dateCommande < :tomorrow')
            ->setParameter('etats', ['VALIDE', 'TERMINE'])
            ->setParameter('today', $today)
            ->setParameter('tomorrow', $tomorrow)
            ->getQuery()
            ->getSingleScalarResult() ?? 0;
    }

    public function countCommandesByEtat(string $etat): int
    {
        return (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->where('c.etat = :etat')
            ->setParameter('etat', $etat)
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function getProduitPlusVendu(): ?string
    {
        $result = $this->createQueryBuilder('c')
            ->select('p.nom as produitNom, COUNT(c.id) as total')
            ->join('c.produit', 'p')
            ->where('c.etat IN (:etats)')
            ->setParameter('etats', ['VALIDE', 'TERMINE'])
            ->groupBy('p.id')
            ->orderBy('total', 'DESC')
            ->setMaxResults(1)
            ->getQuery()
            ->getOneOrNullResult();

        return $result ? $result['produitNom'] : null;
    }

    public function getProduitPlusVenduDuJour(): ?array
    {
        $today = new \DateTime('today');
        $tomorrow = new \DateTime('tomorrow');

        $result = $this->createQueryBuilder('c')
            ->select('p.nom as produitNom, COUNT(c.id) as total')
            ->join('c.produit', 'p')
            ->where('c.etat IN (:etats)')
            ->andWhere('c.dateCommande >= :today')
            ->andWhere('c.dateCommande < :tomorrow')
            ->setParameter('etats', ['VALIDE', 'TERMINE'])
            ->setParameter('today', $today)
            ->setParameter('tomorrow', $tomorrow)
            ->groupBy('p.id')
            ->orderBy('total', 'DESC')
            ->setMaxResults(1)
            ->getQuery()
            ->getOneOrNullResult();

        return $result ?: null;
    }





    //    /**
    //     * @return Commande[] Returns an array of Commande objects
    //     */
    //    public function findByExampleField($value): array
    //    {
    //        return $this->createQueryBuilder('c')
    //            ->andWhere('c.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->orderBy('c.id', 'ASC')
    //            ->setMaxResults(10)
    //            ->getQuery()
    //            ->getResult()
    //        ;
    //    }

    //    public function findOneBySomeField($value): ?Commande
    //    {
    //        return $this->createQueryBuilder('c')
    //            ->andWhere('c.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->getQuery()
    //            ->getOneOrNullResult()
    //        ;
    //    }
}
