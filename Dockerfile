# Utilise l'image PHP officielle avec Apache
FROM php:8.4-apache

# Installation des dépendances système et des extensions PHP pour PostgreSQL
RUN apt-get update && apt-get install -y \
    libpq-dev \
    git \
    unzip \
    && docker-php-ext-install pdo pdo_pgsql

# Activation de mod_rewrite pour Symfony
RUN a2enmod rewrite

# Installation de Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Définition du répertoire de travail
WORKDIR /var/www/html

# Copie des fichiers du projet
COPY . .

# Définition de l'environnement en production
ENV APP_ENV=prod

# Installation des dépendances Symfony (sans les scripts qui ont besoin de la DB)
RUN composer install --no-dev --optimize-autoloader --no-scripts

# --- CORRECTION CRUCIALE ---
# On crée de force le dossier var et ses sous-dossiers
RUN mkdir -p var/cache var/log var/sessions
# ---------------------------

# Changement de la racine d'Apache vers le dossier /public de Symfony
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/*.conf
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf /etc/apache2/conf-available/*.conf

# Maintenant, chown fonctionnera car var/ existe
RUN chown -R www-data:www-data var/

# Port exposé par Render
EXPOSE 80