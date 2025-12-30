<?php

namespace App\Entity;

use App\Repository\Impl\UtilisateurRepositoryImpl;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;


#[ORM\Entity(repositoryClass: UtilisateurRepositoryImpl::class)]
#[ORM\Table(name: "utilisateur")]
#[UniqueEntity(fields: ['email'], message: 'Cet email est déjà utilisé.')]
#[UniqueEntity(fields: ['telephone'], message: 'Ce numéro est déjà utilisé.')]
class Utilisateur
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: "integer")]
    private ?int $id = null;

    #[ORM\Column(length: 100)]
    private ?string $nom = null;

    #[Assert\Regex(
        pattern: '/^[A-Za-z0-9._%+\-]+@[A-Za-z0-9.\-]+\.[A-Za-z]{2,}$/',
        message: "Le format de l'email n'est pas autorisé."
    )]
    #[ORM\Column(name: "login", length: 100, unique: true)]
    #[Assert\NotBlank(message: "L'email est requis.")]
    #[Assert\Email(message: "Cette adresse email ('{{ value }}') n'est pas valide.",)]
    private ?string $email = null;


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

    public function setNom(?string $nom)
    {
        $this->nom = $nom;
    }



    public function getEmail(): ?string
    {
        return $this->email;
    }
    public function getNom(): ?string
    {
        return $this->nom;
    }


    public function setEmail(?string $email)
    {
        $this->email = $email;
    }
  



    public function getPassword(): ?string
    {
        return $this->password;
    }
    public function setPassword(?string $password)
    {
        $this->password = $password;
    }



    public function getTelephone(): ?string
    {
        return $this->telephone;
    }
    public function setTelephone(?string $telephone)
    {
        $this->telephone = $telephone;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(?string $adresse)
    {
        $this->adresse = $adresse;
    }

    public function isEtat(): ?bool
    {
        return $this->etat;
    }


    public function getRole(): ?string
    {
        return $this->role;
    }
    public function setRole(string $role)
    {
        $this->role = $role;
    }
}
