# Compte rendu — TP5 : API REST avec Jersey, Spring MVC et Spring Data REST

**Sujet :** [TP5.pdf](TP5.pdf) · **Environnement :** Maven/JDK 17, Spring Boot, JPA/H2 · **Date de revue :** 25 novembre 2026

> **Traçabilité :** ce compte rendu distingue (a) les fichiers livrés, (b) le build et les tests attestés par [GitHub Actions](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044) du 25 novembre 2026, et (c) les manipulations à effectuer manuellement. Une sortie attendue n'est pas une sortie observée.

## 1. Objectifs
Concevoir une API d'activités et catégories avec les opérations CRUD et des filtres, puis comparer trois styles d'implémentation : annotations JAX-RS/Jersey, contrôleurs Spring MVC et exposition automatique de repositories par Spring Data REST.

## 2. Réalisation indépendante des trois approches
| Approche | Sous-projet complet | Ressource principale | Implémentation |
| --- | --- | --- | --- |
| Jersey (JAX-RS) | [`jersey/`](jersey/) | `/api/activities` | [`ActivityRestApi.java`](jersey/src/main/java/edu/iset/soa/tp5/jersey/web/ActivityRestApi.java) |
| Spring MVC | [`spring-controller/`](spring-controller/) | `/api/activities` | [`ActivityRestController.java`](spring-controller/src/main/java/edu/iset/soa/tp5/mvc/web/ActivityRestController.java) |
| Spring Data REST (HAL) | [`spring-data-rest/`](spring-data-rest/) | `/data/activities` | [`ActivityRepository.java`](spring-data-rest/src/main/java/edu/iset/soa/tp5/data/repository/ActivityRepository.java) |

Chaque sous-projet possède **ses propres** entités `Activity` et `Category`, repositories, données de démonstration, `pom.xml`, test et `requetes.http`. On peut donc ouvrir chacun séparément. Les fonctionnalités explicites de Jersey/MVC comprennent lister/consulter/créer/modifier/supprimer les activités, puis filtrer par statut, catégorie, lieu et fragment de nom. Data REST expose les ressources/recherches selon les conventions Spring Data REST ; son format HAL ajoute des liens.

## 3. Preuves réellement établies
**Preuve CI :** [exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), job `Maven TP/TP05-Spring-REST/jersey (Java 17)` : `BUILD SUCCESS`; 1 test, 0 échec. Cela confirme compilation/test Maven, pas la disponibilité d'un endpoint en exécution réelle.

**Preuve CI :** [exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), job `Maven TP/TP05-Spring-REST/spring-controller (Java 17)` : `BUILD SUCCESS`; 1 test, 0 échec. Cela confirme compilation/test Maven, pas la disponibilité d'un endpoint en exécution réelle.

**Preuve CI :** [exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), job `Maven TP/TP05-Spring-REST/spring-data-rest (Java 17)` : `BUILD SUCCESS`; 1 test, 0 échec. Cela confirme compilation/test Maven, pas la disponibilité d'un endpoint en exécution réelle.


Ces trois tests valident principalement la couche repository/données. **Ils ne remplacent pas des tests HTTP du CRUD, des statuts ou des liens HAL**.

## 4. Manipulations HTTP à documenter
Pour **chaque variante**, ouvrir son dossier, lancer `mvn clean verify` puis `mvn spring-boot:run`, et exécuter son [fichier `requetes.http` Jersey](jersey/requetes.http), [MVC](spring-controller/requetes.http) ou [Data REST](spring-data-rest/requetes.http). Lancer **une seule variante à la fois** : toutes utilisent le port 8080 et leur propre base H2 éphémère. Vérifier la console H2 sur `/h2-console` avec `jdbc:h2:mem:activitydb`.

Collecter au minimum la réponse de liste, un POST réussi avec code **201**, une modification PUT, une suppression DELETE, un **404** sur ressource absente, les filtres et, dans Data REST, une réponse contenant `_links` / `_embedded` selon l'endpoint. Conserver les payloads et statuts effectivement observés ; aucun n'est présenté ici comme déjà exécuté.

## 5. Comparaison pédagogique
- **Jersey :** implémentation des routes avec `@Path`, `@GET`, `@POST`, `@PathParam` et `Response`.
- **Spring MVC :** `@RestController`, `@GetMapping`, `@PostMapping`, `@PathVariable` et `ResponseEntity`.
- **Spring Data REST :** publication des repositories et navigation hypermédia HAL ; peu de contrôleurs à écrire mais format et routes propres à l'outil.

## 6. Conclusion
La comparaison des trois styles REST est implémentée sans partage de code entre sous-projets. Les builds et les tests de persistance sont verts ; les traces HTTP et H2 restent à ajouter pour satisfaire pleinement la partie expérimentale du compte rendu.
