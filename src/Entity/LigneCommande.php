<?php

namespace App\Entity;

use App\Repository\Impl\LigneCommandeRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: LigneCommandeRepository::class)]
#[ORM\Table(name: "ligne_commande")]
class LigneCommande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: "integer")]
    private ?int $id = null;

    #[ORM\Column(type: "integer")]
    private int $quantite;

    #[ORM\Column(name: "prix_unitaire", type: Types::DECIMAL,precision: 10, scale: 2 )]
    private string $prixUnitaire;

    #[ORM\Column(name: "commande_id", type: "integer")]
    private int $commandeId;

    #[ORM\Column(name: "produit_id", type: "integer")]
    private int $produitId;


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getQuantite(): int
    {
        return $this->quantite;
    }

    public function setQuantite(int $quantite): self
    {
        $this->quantite = $quantite;
        return $this;
    }

    public function getPrixUnitaire(): string
    {
        return $this->prixUnitaire;
    }

    public function setPrixUnitaire(string $prixUnitaire): self
    {
        $this->prixUnitaire = $prixUnitaire;
        return $this;
    }

    public function getCommandeId(): int
    {
        return $this->commandeId;
    }

    public function setCommandeId(int $commandeId): self
    {
        $this->commandeId = $commandeId;
        return $this;
    }

    public function getProduitId(): int
    {
        return $this->produitId;
    }

    public function setProduitId(int $produitId): self
    {
        $this->produitId = $produitId;
        return $this;
    }
}
