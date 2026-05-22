# Interns Management (Spring Boot)

Application web de gestion des stages et stagiaires, migrée depuis un projet JEE/Eclipse (2021) vers **Spring Boot 3**.

## Prérequis

- Java 17+
- Maven 3.8+
- MySQL 8+ (ou MariaDB 10.4+)

## Base de données

### Installation en une commande

```bash
mysql -u root -p < src/main/resources/db/setup.sql
```

Ce script crée la base `l3_pfe`, toutes les tables et les données de démonstration.

Scripts séparés (optionnel) :

- `src/main/resources/db/schema.sql` — structure uniquement
- `src/main/resources/db/data.sql` — données de seed (à exécuter après le schéma)

### Configuration Spring

Éditez `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/l3_pfe
spring.datasource.username=root
spring.datasource.password=
```

## Comptes de connexion

| Utilisateur | Mot de passe | Rôle | Remarque |
|-------------|--------------|------|----------|
| `admin` | `admin` | Super-admin | Géré dans le code (accès global, menu administration) |
| `kbenali` | `password123` | Admin | Catégorie **Informatique**, Direction Technique |
| `smansouri` | `password123` | Admin | Catégorie **Réseaux**, Direction Technique |
| `mdupont` | `password123` | Admin | Catégorie **Gestion**, Direction RH |
| — | — | Employé | Lien « Accès employé » sur la page de connexion (sans mot de passe) |

Les mots de passe en base sont en clair : **réservé au développement local**.

## Lancer l’application

```bash
mvn spring-boot:run
```

Puis ouvrez [http://localhost:8080](http://localhost:8080)

## Structure du projet

```
src/main/java/com/interns/management/
  config/          # Configuration Spring MVC
  controller/      # Contrôleurs
  model/           # Entités métier
  service/         # Accès base de données
  web/             # Intercepteur d’authentification

src/main/resources/
  db/              # Scripts SQL (schema, data, setup)
  static/          # CSS, images

src/main/webapp/WEB-INF/views/   # Vues JSP
```

## Tables principales

- `categorie`, `direction`, `niveaustage` — référentiels
- `admin` — comptes administrateurs (login)
- `encadreur` — encadreurs
- `stagiare` — stagiaires
- `stage` — thèmes / stages (avec fichier livrable `travail_livrer`)
