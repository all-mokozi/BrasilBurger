# Utilise l'image PHP officielle avec Apache
FROM php:8.4-apache

# Installation des dépendances système et des extensions PHP
RUN apt-get update && apt-get install -y \
    libpq-dev \
    git \
    unzip \
    libicu-dev \
    && docker-php-ext-install pdo pdo_pgsql intl

# Activation de mod_rewrite pour Apache
RUN a2enmod rewrite

# Installation de Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Définition du répertoire de travail
WORKDIR /var/www/html

# Copie du projet
COPY . .

# Variables fictives pour le build (avant l'installation)
ENV DATABASE_URL="postgresql://db_user:db_pass@127.0.0.1:5432/db_name?serverVersion=16&charset=utf8"
ENV APP_SECRET=67d34c1ca291563f66810c9c45014878 

# Installation des dépendances PHP
RUN composer install --no-dev --optimize-autoloader

# Définition de l'environnement en production (APRÈS composer install)
ENV APP_ENV=prod

# Exécuter les scripts du cache après l'installation complète
RUN php bin/console cache:clear --env=prod --no-debug 2>&1 || true

# Configuration d'Apache pour pointer vers /public
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/*.conf
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf /etc/apache2/conf-available/*.conf

# Création des dossiers nécessaires et gestion des droits
RUN mkdir -p var/cache var/log var/sessions \
    && chown -R www-data:www-data /var/www/html/var \
    && chmod -R 777 /var/www/html/var

# Création du script de démarrage (Seulement le cache et Apache)
RUN echo '#!/bin/sh\n\
php bin/console cache:clear --env=prod\n\
exec apache2-foreground' > /usr/local/bin/docker-entrypoint.sh

RUN chmod +x /usr/local/bin/docker-entrypoint.sh

EXPOSE 80

# On utilise le script pour démarrer
ENTRYPOINT ["docker-entrypoint.sh"]