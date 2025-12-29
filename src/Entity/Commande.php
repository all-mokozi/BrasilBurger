<?php

namespace App\Entity;


use App\Repository\Impl\CommandeRepositoryImpl as ImplCommandeRepositoryImpl;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ImplCommandeRepositoryImpl::class)]
#[ORM\Table(name: "commande")]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: Types::INTEGER)]
    private ?int $id = null;

    #[ORM\Column(name: "date_commande", type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $dateCommande = null;

    #[ORM\Column(length: 20)]
    private ?string $etat = 'EN_COURS';

    #[ORM\Column(name: "montant_total", type: Types::DECIMAL, precision: 10, scale: 2)]
    private ?string $montantTotal = null;

    #[ORM\Column(name: "mode_livraison", length: 20, nullable: true)]
    private ?string $modeLivraison = null;

    // #[ORM\Column(name: "adresse_livraison", length: 255, nullable: true)]
    // private ?string $adresseLivraison = null;

    #[ORM\Column(type: Types::INTEGER)]
    private ?int $quantite = 1;

    #[ORM\ManyToOne(targetEntity: Zone::class)]
    #[ORM\JoinColumn(name: "Zone_id", referencedColumnName: "id", nullable: true)]
    private ?Zone $zone = null;
    #[ORM\ManyToOne(targetEntity: Utilisateur::class)]
    #[ORM\JoinColumn(name: "client_id", referencedColumnName: "id", nullable: true)]
    private ?Utilisateur $client = null;

    public function getClient(): ?Utilisateur
    {
        return $this->client;
    }

    public function setClient(?Utilisateur $client): self
    {
        $this->client = $client;
        return $this;
    }
    public function getZone(): ?Zone
    {
        return $this->zone;
    }

    public function setZone(?Zone $zone): self
    {
        $this->zone = $zone;
        return $this;
    }


    #[ORM\Column(name: "livreur_id", type: Types::INTEGER, nullable: true)]
    private ?int $livreurId = null;

    #[ORM\Column(name: "zone_id", type: Types::INTEGER, nullable: true)]
    private ?int $zoneId = null;

    #[ORM\Column(name: "panier_id", type: Types::INTEGER, nullable: true)]
    private ?int $panierId = null;

    #[ORM\ManyToOne(targetEntity: Produit::class)]
    #[ORM\JoinColumn(name: "produit_id", referencedColumnName: "id", nullable: false)]
    private ?Produit $produit = null;

    public function getProduit(): ?Produit
    {
        return $this->produit;
    }

    public function setProduit(?Produit $produit): self
    {
        $this->produit = $produit;
        return $this;
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateCommande(): ?\DateTimeInterface
    {
        return $this->dateCommande;
    }
    public function setDateCommande(\DateTimeInterface $dateCommande): self
    {
        $this->dateCommande = $dateCommande;
        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }
    public function setEtat(string $etat): self
    {
        $this->etat = $etat;
        return $this;
    }

    public function getMontantTotal(): ?string
    {
        return $this->montantTotal;
    }
    public function setMontantTotal(string $montantTotal): self
    {
        $this->montantTotal = $montantTotal;
        return $this;
    }

    public function getModeLivraison(): ?string
    {
        return $this->modeLivraison;
    }
    public function setModeLivraison(?string $modeLivraison): self
    {
        $this->modeLivraison = $modeLivraison;
        return $this;
    }

    // public function getAdresseLivraison(): ?string
    // {
    //     return $this->adresseLivraison;
    // }
    // public function setAdresseLivraison(?string $adresseLivraison): self
    // {
    //     $this->adresseLivraison = $adresseLivraison;
    //     return $this;
    // }

    public function getQuantite(): ?int
    {
        return $this->quantite;
    }
    public function setQuantite(int $quantite): self
    {
        $this->quantite = $quantite;
        return $this;
    }



    public function getLivreurId(): ?int
    {
        return $this->livreurId;
    }
    public function setLivreurId(?int $livreurId): self
    {
        $this->livreurId = $livreurId;
        return $this;
    }

    public function getZoneId(): ?int
    {
        return $this->zoneId;
    }
    public function setZoneId(?int $zoneId): self
    {
        $this->zoneId = $zoneId;
        return $this;
    }

    public function getPanierId(): ?int
    {
        return $this->panierId;
    }
    public function setPanierId(?int $panierId): self
    {
        $this->panierId = $panierId;
        return $this;
    }
}
