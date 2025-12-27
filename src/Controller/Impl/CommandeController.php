<?php

namespace App\Controller\Impl;

use App\Service\CommandeServiceInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class CommandeController extends AbstractController
{

    private readonly CommandeServiceInterface $commandeService;
    
    public function __construct(  CommandeServiceInterface $commandeService, private readonly EntityManagerInterface $manager)
    {
        $this->commandeService = $commandeService;
        
    }
  #[Route('/', name: 'app_commande', methods: ['GET', 'POST'])]
public function list(Request $request): Response
{
    $page = $request->query->getInt('page', 1);
    $limit = $this->getParameter('LIMIT_PER_PAGE');
    $offset = ($page - 1) * $limit;

    $totalCommandes = $this->commandeService->countCommandes(); 
    $totalPages = ceil($totalCommandes / $limit);

    $commandes = $this->commandeService->list([], ['dateCommande' => 'DESC'], $limit, $offset);

    return $this->render('commande/index.html.twig', [
        'commandes' => $commandes,
        'pageEnCours' => $page,
        'totalPages' => $totalPages,
    ]);
}


#[Route('/{id}', name: 'app_commande_show', methods: ['GET', 'POST'])]
public function show(int $id): Response
{
    $commande = $this->commandeService->findById($id);

    if (!$commande) {
        throw $this->createNotFoundException('Commande non trouvée');
    }

    return $this->render('commande/show.html.twig', [
        'commande' => $commande,
    ]);
}

}