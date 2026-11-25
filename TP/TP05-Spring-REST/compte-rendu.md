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

## H2 et reproduction
Chaque `application.properties` déclare `jdbc:h2:mem:activitydb;DB_CLOSE_DELAY=-1` ; les tests JPA et les échanges HTTP ont fonctionné avec l'initialisation des données. L'accès graphique à la **console H2** n'est pas une preuve vérifiée : l'interface peut être ouverte à `http://localhost:8080/h2-console` avec l'URL JDBC `jdbc:h2:mem:activitydb`, utilisateur `sa` et mot de passe vide, après démarrage du sous-projet.

Les exemples `requetes.http` restent disponibles dans chaque sous-dossier. Les requêtes REST et les statuts ont été validés à distance, sans passer par un navigateur graphique.

## Conclusion
Les trois approches REST sont implémentées, indépendantes et validées par de vrais échanges HTTP. Le workflow de Spring Data REST vérifie la lecture, les recherches et HAL, tandis que les CRUD complets sont prouvés sur Jersey et Spring MVC.
