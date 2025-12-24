<?php
namespace App\Controller;

use Symfony\Component\HttpFoundation\Response ;

interface CommandeControllerInterface
{

    public function list(): Response;
    
}