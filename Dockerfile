# Étape 1 : Build (Compilation)
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /app

# Copier le fichier projet et restaurer les dépendances NuGet
COPY *.csproj ./
RUN dotnet restore

# Copier tout le reste et compiler
COPY . ./
RUN dotnet publish -c Release -o out

# Étape 2 : Runtime (Exécution)
FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app

# Copier les fichiers compilés depuis l'étape de build
COPY --from=build /app/out .

# Render utilise la variable d'environnement PORT, on force l'écoute dessus
ENV ASPNETCORE_URLS=http://+:8080
EXPOSE 8080

# Lancer l'application (Remplacez BrasilBurger.dll par le nom exact de votre DLL)
ENTRYPOINT ["dotnet", "BrasilBurger.dll"]