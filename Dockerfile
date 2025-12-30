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

# Définition de l'environnement en production
ENV APP_ENV=prod

# --- CORRECTIF : Variables fictives pour le build ---
# Cela empêche Symfony de chercher une vraie base de données maintenant
ENV DATABASE_URL="postgresql://db_user:db_pass@127.0.0.1:5432/db_name?serverVersion=16&charset=utf8"
ENV APP_SECRET=67d34c1ca291563f66810c9c45014878 

# 1. Installation des dépendances PHP
RUN composer install --no-dev --optimize-autoloader --no-scripts

# 2. Préparation des dossiers et droits (Très important avant le cache:clear)
RUN mkdir -p var/cache var/log var/sessions \
    && chown -R www-data:www-data var/ \
    && chmod -R 777 var/

# 3. Changement de la racine d'Apache
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/*.conf
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf /etc/apache2/conf-available/*.conf

# 4. Vidage du cache (maintenant il ne devrait plus planter)
RUN php bin/console cache:clear --env=prod

# Nettoyage des droits final
RUN chown -R www-data:www-data /var/www/html/var

EXPOSE 80