<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use stdClass;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: ProduitRepository::class)]
class Produit
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

     #[ORM\Column(length: 25)]
    private ?string $nom = null;

    #[ORM\Column(type: "decimal", precision: 10, scale: 2)]
    private ?string $prix = null;

    #[ORM\Column(length: 512, nullable: true)]
    private ?string $image = null;

    #[ORM\Column(length: 500, nullable: true)]
    private ?string $description = null;

    #[ORM\Column(length: 50)]
    #[Assert\Choice(['BURGER', 'COMPLEMENT', 'MENU'])]
    private ?string $typeProduit = null; 

    #[ORM\Column(type: "boolean", options: ["default" => false])]
    private bool $isArchived = false;

    #[ORM\OneToMany(targetEntity: MenuComposant::class, mappedBy: "menu")]
    private Collection $composants;

    public function __construct() {
        $this->composants = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

   
    public function getNom()
    {
        return $this->nom;
    }

    

    public function getComposants()
    {
        return $this->composants;
    }

   
    public function setComposants($composants)
    {
        $this->composants = $composants;

        return $this;
    }

 
    public function getIsArchived()
    {
        return $this->isArchived;
    }

    public function setIsArchived($isArchived)
    {
        $this->isArchived = $isArchived;

        return $this;
    }

    public function getTypeProduit()
    {
        return $this->typeProduit;
    }

  
   

   
    public function getDescription()
    {
        return $this->description;
    }

  
   

 
    public function getImage()
    {
        return $this->image;
    }

 
    public function getPrix()
    {
        return $this->prix;
    }
}
