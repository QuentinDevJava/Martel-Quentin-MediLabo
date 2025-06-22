# Medilabo – Application d'aide a la détection du diabète

**Medilabo** est une application web distribuée, basée sur une architecture **microservices**, permettant de gérer les données des patients, les notes médicales, et d’évaluer les risques de diabète. L’application inclut également un système d’authentification sécurisé via **JWT**.

---

## Structure du projet

![Image](https://github.com/user-attachments/assets/aa823f3e-c6cf-49d7-9b85-a9242507b833)

---

## Services inclus

- **UI (Thymeleaf)** – Interface web utilisateur
- **Gateway API** – Reverse proxy & routage dynamique
- **Eureka Server** – Service de découverte (Service Discovery)
- **Config Server** – Centralisation de la configuration Spring Cloud
- **Actuator** – Endpoints de monitoring pour chaque microservice (`/actuator/health`)
- **Auth API** – Gestion des utilisateurs et tokens (MongoDB)
- **Patient API** – Gestion des patients (MySQL)
- **Note API** – Gestion des notes médicales (MongoDB)
- **Évaluation Risque API** – Analyse de risque de diabète (calculs & logique métier)
- **Swagger UI** – Documentation interactive de chaque API
- **Bases de données** :
  - **MySQL** : données patients
  - **MongoDB** : notes médicales
  - **PostgreSQL** : service d’authentification

---

## ⚙️ Prérequis

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Docker Desktop](https://docs.docker.com/desktop/)
- Java 21+
- Maven 4.0.0+

---

## 🗂️ Arborescence du projet
```
├──medilabo/
│ ├── bd
│ ├── service/
│ │ ├── eurekaserver/
│ │ ├── configserver/
│ │ ├── gatewayapi/
│ │ ├── authapi/
│ │ ├── patientapi/
│ │ ├── noteapi/
│ │ ├── evaluationrisqueapi/
│ │ └── ui/
│ ├── docker/
│ │ ├── docker-compose.yml
│ │ ├── .env

```
## Variables d’environnement `.env`

Un fichier `.env` est présent dans le dossier docker. Il contient les variables sensibles nécessaires aux bases de données.


```env
# MySQL
MYSQL_INITDB_DATABASE=medilabo_mysql
MYSQL_INITDB_USER=medilabo_admin
MYSQL_INITDB_PASSWORD="!eUFG8LWZ4nb2$@VBVuiLI69gre?Zb?j"
MYSQL_INITDB_ROOT_PASSWORD="!eUFG8LWZ4nb2$@VBVuiLI69gre?Zb?j"

# MongoDB
MONGO_INITDB_DATABASE=medilabo_mongodb
MONGO_INITDB_ROOT_USERNAME=mongo_admin
MONGO_INITDB_ROOT_PASSWORD="!eUFG8LWZ4nb2$@VBVuiLI69gre?Zb?j"

# PostgreSQL
POSTGRES_INITDB_DATABASE=medilabo_postgres
POSTGRES_INITDB_ROOT_USERNAME=postgres_admin
POSTGRES_INITDB_ROOT_PASSWORD="!eUFG8LWZ4nb2$@VBVuiLI69gre?Zb?j"

```
⚠️ Si vous modifiez ces identifiants, pensez à synchroniser les fichiers de configuration dans le configserver (dans chaque fichier .yml concerné).



## Démarrage de l'application

```bash
# 1. Cloner le dépôt
git clone https://github.com/QuentinDevJava/Martel-Quentin-MediLabo.git

# 2. Aller dans le dossier docker
cd Martel-Quentin-MediLabo/medilabo/docker

# 3. Construire tous les services Docker
docker compose build

# 4. Lancer les services en arrière-plan
docker compose up -d

# 5. Vérifier que tout fonctionne
docker ps

```

### Àrrêt de l’application

```bash
# Arrêter tous les conteneurs
docker compose down

# (Optionnel) Supprimer aussi les volumes persistants
docker compose down -v
```


## Accès aux services

    Composant                 URL

    Interface utilisateur :	  http://localhost:5001
    Gateway :     	          http://localhost:5005
    Eureka Server: 	          http://localhost:8761
    Config Server: 	          http://localhost:5555
    Auth API :	              http://localhost:5004/actuator	
    Note API :                http://localhost:5002/actuator	
    Patient API :	          http://localhost:5000/actuator	
    Évaluation Risque API :	  http://localhost:5003/actuator	

## Swagger (Documentation des APIs)

Chaque microservice expose sa documentation Swagger :

    Microservice	          Swagger UI 

    Patient API	              http://localhost:5000/swagger-ui.html
    Note API	              http://localhost:5002/swagger-ui.html
    Évaluation Risque API     http://localhost:5003/swagger-ui.html
    Auth API	              http://localhost:5004/swagger-ui.html

## Commandes Docker utiles

    Commande	            Description

    docker compose build	Build des images Docker
    docker compose up -d	Démarre les services en mode détaché
    docker compose down	    Stoppe les conteneurs
    docker compose down -v	Supprime les conteneurs et les volumes
    docker compose logs -f	Affiche les logs de tous les services
    docker ps	            Liste les conteneurs en cours d’exécution