<?php

namespace App\Controller\Impl;

use App\Service\CommandeServiceInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[IsGranted('ROLE_GESTIONNAIRE')]
final class CommandeController extends AbstractController
{

    private readonly CommandeServiceInterface $commandeService;

    public function __construct(CommandeServiceInterface $commandeService, private readonly EntityManagerInterface $manager)
    {
        $this->commandeService = $commandeService;
    }
    #[Route('/', name: 'app_commande', methods: ['GET', 'POST'])]
    public function list(Request $request): Response
    {
        $page = $request->query->getInt('page', 1);
        $etat = $request->query->get('etat');
        $date = $request->query->get('date');
        $client = $request->query->get('client');

        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;

        $criteria = [];
        if ($etat) {
            $criteria['etat'] = $etat;
        }
        if ($date) {
            $startDate = new \DateTime($date . ' 00:00:00');
            $endDate = new \DateTime($date . ' 23:59:59');
            $criteria['dateCommandeStart'] = $startDate;
            $criteria['dateCommandeEnd'] = $endDate;
        }
        if ($client) {
            $criteria['clientNom'] = $client;
        }

        $totalCommandes = $this->commandeService->countCommandes($criteria);
        $totalPages = ceil($totalCommandes / $limit);
        $commandes = $this->commandeService->list($criteria, ['dateCommande' => 'DESC'], $limit, $offset);

        // Statistiques
        $recetteDuJour = $this->commandeService->getRecetteDuJour();
        $commandesTerminees = $this->commandeService->countCommandesByEtat('TERMINE');
        $commandesValidees = $this->commandeService->countCommandesByEtat('VALIDE');
        $produitPlusVenduDuJour = $this->commandeService->getProduitPlusVenduDuJour();

        return $this->render('commande/index.html.twig', [
            'commandes' => $commandes,
            'pageEnCours' => $page,
            'totalPages' => $totalPages,
            'filtres' => [
                'etat' => $etat,
                'date' => $date,
                'client' => $client
            ],
            'recetteDuJour' => $recetteDuJour,
            'commandesTerminees' => $commandesTerminees,
            'commandesValidees' => $commandesValidees,
            'produitPlusVenduDuJour' => $produitPlusVenduDuJour
        ]);
    }
    #[Route('/livraison', name: 'app_livraison_list', methods: ['GET', 'POST'])]

    public function listerLivraison(Request $request): Response
    {
        $filtre = [
            'modeLivraison' => 'LIVRAISON',
            'etat' => 'TERMINE',
            'hasLivreur' => true

        ];
        $page = $request->query->getInt('page', 1);
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;

        $totalCommandes = $this->commandeService->countCommandes($filtre);
        $totalPages = ceil($totalCommandes / $limit);

        $commandes = $this->commandeService->list($filtre, ['dateCommande' => 'DESC'], $limit, $offset);

        return $this->render('commande/livraison.html.twig', [
            'commandes' => $commandes,
            'pageEnCours' => $page,
            'totalPages' => $totalPages,
        ]);
    }


    #[Route('/commandes/{id}', name: 'app_commande_show', methods: ['GET'])]
    public function show(int $id, Request $request): Response
    {
        $commande = $this->commandeService->findById($id);

        if (!$commande) {
            throw $this->createNotFoundException('Commande non trouvée');
        }

        $page = $request->query->getInt('page', 1);

        return $this->render('commande/show.html.twig', [
            'commande' => $commande,
            'page' => $page,
        ]);
    }
    #[Route('/commandes/{id}/etat', name: 'app_commande_change_etat', methods: ['POST'])]
    public function changeEtat(Request $request, int $id): Response
    {
        $newStatus = $request->request->get('etat');

        $success = $this->commandeService->changeEtat($id, $newStatus);

        if (!$success) {
            throw $this->createNotFoundException('Impossible de modifier l’état');
        }

        return $this->redirectToRoute('app_commande_show', [
            'id' => $id
        ]);
    }

    #[Route('/livreur/{id}/livraisons', name: 'app_livreur_livraisons', methods: ['GET'])]
    public function livreurLivraisons(int $id, Request $request): Response
    {
        $page = $request->query->getInt('page', 1);
        $etat = $request->query->get('etat');
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;

        $criteria = [];
        if ($etat) {
            $criteria['etat'] = $etat;
        }

        $livraisons = $this->commandeService->getLivraisonsByLivreur($id, $criteria, ['dateCommande' => 'DESC'], $limit, $offset);
        $totalLivraisons = count($this->commandeService->getLivraisonsByLivreur($id, $criteria));
        $totalPages = ceil($totalLivraisons / $limit);

        $commandesDisponibles = $this->commandeService->list(['etat' => 'TERMINE', 'hasLivreur' => false, 'modeLivraison' => 'LIVRAISON'], ['dateCommande' => 'DESC'], 10);

        return $this->render('commande/livreur_livraisons.html.twig', [
            'livraisons' => $livraisons,
            'livreurId' => $id,
            'pageEnCours' => $page,
            'totalPages' => $totalPages,
            'filtres' => ['etat' => $etat],
            'commandesDisponibles' => $commandesDisponibles
        ]);
    }

    #[Route('/assigner-livraison/{commandeId}/livreur/{livreurId}', name: 'app_assigner_livraison_livreur', methods: ['POST'])]
    public function assignerLivraisonALivreur(int $commandeId, int $livreurId): Response
    {
        $success = $this->commandeService->assignerLivraisonALivreur($commandeId, $livreurId);

        if ($success) {
            $this->addFlash('success', 'Livraison assignée avec succès au livreur.');
        } else {
            $this->addFlash('error', 'Impossible d\'assigner cette livraison.');
        }

        return $this->redirectToRoute('app_livreur_livraisons', ['id' => $livreurId]);
    }
}
