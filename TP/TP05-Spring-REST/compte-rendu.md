# Compte rendu — TP5 : Services REST avec Jersey, Spring MVC et Spring Data REST

## Objectif et travail demandé
Publier et comparer des ressources d'activités et de catégories avec trois approches REST indépendantes : Jersey/JAX-RS, contrôleurs Spring MVC et Spring Data REST. Énoncé : [TP5.pdf](TP5.pdf).

## Réalisation et explication
| Approche | Implémentation | Exposition |
| --- | --- | --- |
| [Jersey](jersey/) | `@Path`, `@GET`, `@POST`, `@PUT`, `@DELETE` ; `Response` | `/api/activities` |
| [Spring MVC](spring-controller/) | `@RestController`, annotations de méthodes HTTP ; `ResponseEntity` | `/api/activities` |
| [Spring Data REST](spring-data-rest/) | Repositories JPA publiés automatiquement, réponses HAL | `/data/activities` |

Chaque variante dispose de ses propres entités, données d'initialisation, base **H2 en mémoire** et projet Maven. Le port local 8080 implique de démarrer **une variante à la fois**.

## Résultats HTTP observés en CI
Sur les serveurs réellement démarrés en GitHub Actions :

| Scénario | Jersey | Spring MVC | Spring Data REST |
| --- | --- | --- | --- |
| Liste activités/catégories | HTTP 200 | HTTP 200 | HTTP 200 + HAL |
| Filtre par statut/catégorie | HTTP 200 | HTTP 200 | HTTP 200 |
| Filtre par nom | HTTP 200 | HTTP 200 | Non exécuté dans ce workflow |
| Création d'une activité | HTTP 201 | HTTP 201 | Non exécuté dans ce workflow |
| Lecture après création | HTTP 200 | HTTP 200 | Non exécuté dans ce workflow |
| Modification | HTTP 200 | HTTP 200 | Non exécuté dans ce workflow |
| Suppression, puis lecture | HTTP 204 puis HTTP 404 | HTTP 204 puis HTTP 404 | Non exécuté dans ce workflow |

Extrait authentique des observations Jersey dans les logs CI :

```text
[PASS] create activity: HTTP 201, id=3
[PASS] read activity: HTTP 200, persisted name correct
[PASS] update activity: HTTP 200, modified name correct
[PASS] delete activity: HTTP 204 followed by HTTP 404
```

Les réponses HAL de Spring Data REST contiennent `_links`, `_embedded` et `page` lorsque ces éléments sont applicables. Voir [workflow réussi](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117) et artifacts des jobs Jersey, Spring MVC et Spring Data REST.

## H2 et vérification locale

Chaque variante utilise sa propre base H2 en mémoire (`jdbc:h2:mem:activitydb;DB_CLOSE_DELAY=-1`) et s'exécute séparément sur le port 8080.

La variante **Spring MVC** (`spring-controller`) a été démarrée sur Windows ; sa compilation Maven a réussi avec un test unitaire. L'interface `http://localhost:8080/h2-console` a été ouverte avec l'URL JDBC `jdbc:h2:mem:activitydb`, l'utilisateur `sa` et un mot de passe vide.

Les requêtes `SELECT * FROM CATEGORY` et `SELECT * FROM ACTIVITY` ont montré deux catégories (Sport, Culture) et deux activités (Football à Tunis, Théâtre à Sousse).

**Preuve manuelle :** [capture de la console H2](preuves/TP05-H2-Console-Success.png).

Les exemples `requetes.http` restent disponibles dans chaque sous-dossier. Les tests REST/CRUD sur Jersey et Spring MVC ainsi que les lectures HAL sur Spring Data REST décrits plus haut proviennent des **workflows CI**, et non de cette session H2 locale.

## Conclusion
Les trois implémentations REST sont indépendantes et ont été vérifiées par échanges HTTP automatisés. Une vérification graphique supplémentaire de la base H2 de la variante Spring MVC a été effectuée localement.
