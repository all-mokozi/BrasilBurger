<?php

namespace App\Form;

use App\Entity\Utilisateur;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Validator\Constraints\Email;
use Symfony\Component\Validator\Constraints\Regex;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\CallbackTransformer;
use Symfony\Component\OptionsResolver\OptionsResolver;

class UtilisateurType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom', TextType::class, [
                'label' => 'Nom complet',
                'attr' => [
                    'placeholder' => 'Ex : Mamadou Diallo'
                ]
            ])
            ->add('telephone', TextType::class, [
                'label' => 'Numéro de téléphone',
                'required' => true,
                'constraints' => [
                    new Regex(
                        pattern: '/^(77|78)\d{7}$/',
                        message: 'Le numéro doit commencer par 77 ou 78 et contenir exactement 9 chiffres.'
                    )
                ],
                'attr' => [
                    'placeholder' => 'Ex : 771234567'
                ]
            ])
            ->add('adresse', TextType::class, [
                'label' => 'Adresse',
                'required' => false,
                'attr' => [
                    'placeholder' => 'Ex : Dakar, Plateau'
                ]
            ])

            ->add('email', TextType::class, [
                'label' => 'Email',
                'required' => true,
                'constraints' => [
                    new Email(
                        message: 'Veuillez entrer une adresse email valide.'
                    )
                ],
                'attr' => [
                    'placeholder' => 'Ex : email@example.com'
                ]

            ])
            
            ->add('password', PasswordType::class, [
                'label' => 'Mot de passe',
                'required' => true,
                'attr' => [
                    'placeholder' => 'Entrez un mot de passe'
                ]

            ])
            ->add('btnSaveEmploye', SubmitType::class, [
                'label' => 'Enregistrer',
                'attr' => ['class' => 'btn btn-primary mt-3 float-end'],
            ]);

        $builder->get('telephone')->addModelTransformer(new CallbackTransformer(
            function ($value) { return $value; },
            function ($value) { return preg_replace('/\s+/', '', $value); }
        ));
    }



    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Utilisateur::class,
        ]);
    }
}
