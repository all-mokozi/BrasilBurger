# Stage 1 : Builder - installe toutes les dépendances
FROM php:8.4-apache as builder

# Installation des dépendances système et des extensions PHP
RUN apt-get update && apt-get install -y \
    libpq-dev \
    git \
    unzip \
    libicu-dev \
    && docker-php-ext-install pdo pdo_pgsql intl

# Installation de Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Définition du répertoire de travail
WORKDIR /var/www/html

# Copie du projet
COPY . .

# Variables fictives pour le build
ENV DATABASE_URL="postgresql://db_user:db_pass@127.0.0.1:5432/db_name?serverVersion=16&charset=utf8"
ENV APP_SECRET=67d34c1ca291563f66810c9c45014878

# Installation de TOUTES les dépendances (y compris dev) pour que DebugBundle soit disponible
RUN composer install --optimize-autoloader

# Définition de l'environnement en production
ENV APP_ENV=prod

# Exécuter les scripts du cache après l'installation complète (tolère les erreurs)
RUN php bin/console cache:clear --env=prod --no-debug || true

# Stage 2 : Production - copie seulement ce qu'il faut
FROM php:8.4-apache

# Installation des dépendances système et des extensions PHP
RUN apt-get update && apt-get install -y \
    libpq-dev \
    libicu-dev \
    && docker-php-ext-install pdo pdo_pgsql intl \
    && rm -rf /var/lib/apt/lists/*

# Activation de mod_rewrite pour Apache
RUN a2enmod rewrite

# Définition du répertoire de travail
WORKDIR /var/www/html

# Copie du projet depuis le builder
COPY --from=builder --chown=www-data:www-data /var/www/html .

# Supprimer le dossier var qui vient du builder et le recréer avec les bonnes permissions
RUN rm -rf /var/www/html/var && \
    mkdir -p \
    var/cache/prod/asset_mapper \
    var/cache/prod/pools \
    var/log \
    var/sessions && \
    chown -R www-data:www-data /var/www/html/var && \
    chmod -R 777 /var/www/html/var

# Assurer que .htaccess est présent et lisible
RUN ls -la /var/www/html/public/ && \
    chmod 644 /var/www/html/public/.htaccess || true

# Définition de l'environnement en production
ENV APP_ENV=prod

# Configuration d'Apache pour pointer vers /public
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/*.conf
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf /etc/apache2/conf-available/*.conf

# Création du script de démarrage (Seulement le cache et Apache)
RUN echo '#!/bin/sh\n\
php bin/console cache:clear --env=prod\n\
exec apache2-foreground' > /usr/local/bin/docker-entrypoint.sh

RUN chmod +x /usr/local/bin/docker-entrypoint.sh

EXPOSE 80

# On utilise le script pour démarrer
ENTRYPOINT ["docker-entrypoint.sh"]