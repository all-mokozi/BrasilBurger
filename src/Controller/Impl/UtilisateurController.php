<?php

namespace App\Controller\Impl;

use App\Service\UtilisateurServiceInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class UtilisateurController extends AbstractController
{

    public function __construct( private readonly UtilisateurServiceInterface $utilisateurService)
    {
    }
    #[Route('/utilisateur', name: 'app_utilisateur_list', methods: ['GET' , 'POST'])]
    public function index(Request $request): Response
    {
        $page = $request->query->getInt('page', 1);
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;
        $totalUtilisateurs = $this->utilisateurService->countUtilisateur(); 
        $totalPages = ceil($totalUtilisateurs / $limit);

        $utilisateurs = $this->utilisateurService->list([], ['id' => 'DESC'], $limit, $offset);

        return $this->render('utilisateur/index.html.twig', [
            'controller_name' => 'UtilisateurController',
            'utilisateurs' => $utilisateurs,
            'pageEnCours' => $page,
            'totalPages' => $totalPages,
        ]);
    }
    // #[Route('/utilisateur/{id}', name: 'app_utilisateur_profile')]

    #[Route('/livreurs', name: 'app_livreurs_list', methods: ['GET'])]
    public function livreurs(Request $request): Response
    {
        $page = $request->query->getInt('page', 1);
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;
        $totalLivreurs = $this->utilisateurService->countUtilisateurByRole('LIVREUR');
        $totalPages = ceil($totalLivreurs / $limit);

        $livreurs = $this->utilisateurService->list(['role' => 'LIVREUR'], ['id' => 'DESC'], $limit, $offset);

        return $this->render('utilisateur/index.html.twig', [
            'controller_name' => 'LivreursController',
            'utilisateurs' => $livreurs,
            'pageEnCours' => $page,
            'totalPages' => $totalPages,
            'pathname' => 'app_livreurs_list'
        ]);
    }
}
