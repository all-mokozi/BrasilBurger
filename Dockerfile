# Utilise l'image PHP officielle avec Apache
FROM php:8.4-apache

# Installation des dépendances système et des extensions PHP pour PostgreSQL
RUN apt-get update && apt-get install -y \
    libpq-dev \
    git \
    unzip \
    && docker-php-ext-install pdo pdo_pgsql

# Activation de mod_rewrite pour Apache (nécessaire pour les routes Symfony)
RUN a2enmod rewrite

# Installation de Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Définition du répertoire de travail
WORKDIR /var/www/html

# Copie de l'intégralité du projet dans le conteneur
COPY . .

# Définition de l'environnement en production
ENV APP_ENV=prod

# 1. Installation des dépendances PHP (sans scripts pour éviter les erreurs de DB au build)
RUN composer install --no-dev --optimize-autoloader --no-scripts

# 2. Installation des dépendances JS (AssetMapper) pour éviter l'erreur Stimulus
# RUN php bin/console importmap:install

# 3. Création forcée des dossiers de cache et logs
RUN mkdir -p var/cache var/log var/sessions

# Changement de la racine d'Apache vers le dossier /public de Symfony
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/*.conf
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf /etc/apache2/conf-available/*.conf

# Attribution des droits à l'utilisateur Apache (www-data)
RUN chown -R www-data:www-data var/

# Vidage final du cache pour s'assurer que les routes sont bien enregistrées
RUN php bin/console cache:clear --env=prod

# Port exposé par Render
EXPOSE 80