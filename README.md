# Medilabo – Plateforme d’Évaluation de Risque Médical

Medilabo est une application web basée sur une architecture **microservices**, permettant de gérer les données patients, les notes médicales, et d’évaluer les risques médicaux. L’application inclut également un système d’authentification sécurisé via JWT.

Ce projet peut être lancé :
- **mode développement local**
- **mode production via Docker Compose**
trouver titre pour mise en route 
---

## Structure du projet

TODO Insertion du schemas a faire 



## Mode production – Docker Compose

### Prérequis

- Docker
- Docker Compose
- Fichier `.env` (voir ci-dessous)

### Lancement de l’application

```bash
cd docker
docker-compose build 
docker-compose up -d
```
verifcation des docker qui tourne docker ps

### Àrrêt de l’application
docker-compose down

### Accès aux services

    Composant - URL
    - Interface utilisateur :	http://localhost:5001
    - PI Gateway :  	        http://localhost:5005
    - Eureka Server: 	        http://localhost:8761
    - Config Server: 	        http://localhost:5555



liste des commandes pratique : desktop

