<?php

namespace App\Controller\Impl;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class LigneCommandeController extends AbstractController
{
    #[Route('/ligne/commande', name: 'app_ligne_commande')]
    public function index(): Response
    {
        return $this->render('ligne_commande/index.html.twig', [
            'controller_name' => 'LigneCommandeController',
        ]);
    }
}
