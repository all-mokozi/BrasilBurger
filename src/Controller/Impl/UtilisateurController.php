<?php

namespace App\Controller\Impl;

use App\Entity\Utilisateur;
use App\Form\UtilisateurType;
use App\Service\UtilisateurServiceInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

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
#[Route('/utilisateur/creation', name: 'app_utilisateur_create', methods: ['GET', 'POST'])]
public function createUtilisateurReq(Request $request): Response
{
    $livreur = new Utilisateur();
    $livreur->setRole('LIVREUR');

    $form = $this->createForm(UtilisateurType::class, $livreur);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
       
        $this->utilisateurService->ajouterLivreur($livreur);
        $this->addFlash('success', 'Livreur ajouté avec succès.');
     
        return $this->redirectToRoute('app_livreurs_list');
    }
    return $this->render('utilisateur/form.html.twig', [
        'formLivreur' => $form->createView(),
    ]);
}

#[Route('/admin/login', name: 'admin_login')]
public function login(AuthenticationUtils $authenticationUtils): Response
{

    if ($this->getUser()) {
            return $this->redirectToRoute('app_commande');
        }
    // Dans UtilisateurController::login
$error = $authenticationUtils->getLastAuthenticationError();
if ($error) {
    // Cela affichera l'erreur réelle dans vos logs Symfony (et non juste "Authenticator failed")
    $this->addFlash('error', $error->getMessageKey()); 
}
    return $this->render('utilisateur/login.html.twig', [
        'last_username' => $authenticationUtils->getLastUsername(),
        'error' => $authenticationUtils->getLastAuthenticationError(),
    ]);
}
#[Route('/admin/logout', name: 'admin_logout')]
public function logout(): void
{
    // Symfony intercepte cette route automatiquement
}

}