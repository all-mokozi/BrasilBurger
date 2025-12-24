<?php

namespace App\Controller\Impl;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class MenuComposantController extends AbstractController
{
    #[Route('/menu/composant', name: 'app_menu_composant')]
    public function index(): Response
    {
        return $this->render('menu_composant/index.html.twig', [
            'controller_name' => 'MenuComposantController',
        ]);
    }
}
