<?php

namespace App\Entity;

use App\Repository\Impl\UtilisateurRepository as ImplUtilisateurRepository;
use App\Repository\Impl\UtilisateurRepositoryImpl;
use App\Repository\UtilisateurRepository;
use Doctrine\ORM\Mapping as ORM;


#[ORM\Entity(repositoryClass: UtilisateurRepositoryImpl::class)]
#[ORM\Table(name: "utilisateur")]

class Utilisateur
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: "integer")]
    private ?int $id = null;

    #[ORM\Column(length: 100)]
    private ?string $nom = null;

    #[ORM\Column(length: 100, unique: true)]
    private ?string $login = null;

    #[ORM\Column(length: 255)]
    private ?string $password = null;

    #[ORM\Column(length: 20, nullable: true)]
    private ?string $telephone = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $adresse = null;

    #[ORM\Column(name: "etat", type: "boolean", options: ["default" => true])]
    private ?bool $etat = true;

    #[ORM\Column(length: 20)]
    private ?string $role = null;

   
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }
 

    public function getLogin(): ?string
    {
        return $this->login;
    }
  

    public function getPassword(): ?string
    {
        return $this->password;
    }
  

    public function getTelephone(): ?string
    {
        return $this->telephone;
    }
    public function setTelephone(?string $telephone): self
    {
        $this->telephone = $telephone;
        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }
 

    public function isEtat(): ?bool
    {
        return $this->etat;
    }


    public function getRole(): ?string
    {
        return $this->role;
    }
    public function setRole(string $role): self
    {
        $this->role = $role;
        return $this;
    }
}
