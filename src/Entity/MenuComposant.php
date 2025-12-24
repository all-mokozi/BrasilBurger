<?php

namespace App\Entity;

use App\Repository\MenuComposantRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: MenuComposantRepository::class)]
class MenuComposant
{
    
#[ORM\Id]
    #[ORM\ManyToOne(targetEntity: Produit::class, inversedBy: "composants")]
    #[ORM\JoinColumn(name: "id_menu", referencedColumnName: "id", onDelete: "CASCADE")]
    private ?Produit $menu = null;

    #[ORM\Id]
    #[ORM\ManyToOne(targetEntity: Produit::class)]
    #[ORM\JoinColumn(name: "id_produit_composant", referencedColumnName: "id", onDelete: "RESTRICT")]
    private ?Produit $produitComposant = null;

    #[ORM\Column(type: "integer")]
    private int $quantite = 1;

    public function getMenu(): ?Produit
    {
        return $this->menu;
    }

   

    public function getProduitComposant(): ?Produit
    {
        return $this->produitComposant;
    }

   

    public function getQuantite(): int
    {
        return $this->quantite;
    }

}