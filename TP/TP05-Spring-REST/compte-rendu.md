# Compte rendu — TP5 : Services REST avec Jersey, Spring MVC et Spring Data REST

## Objectif
Comparer trois façons de publier des ressources REST en Java : JAX-RS/Jersey, contrôleurs Spring MVC et exposition automatique par Spring Data REST.

## Environnement
Java 17, Maven, Spring Boot, JPA et H2 en mémoire. Sujet : [TP5.pdf](TP5.pdf). Les trois projets Maven sont autonomes et utilisent chacun le port 8080 : les lancer **séparément**.

## Partie 1 — Jersey/JAX-RS
Le sous-projet [jersey/](jersey/) utilise `@Path`, `@GET`, `@POST`, `@PUT`, `@DELETE` et `@PathParam` pour exposer les activités et catégories. Les réponses sont construites avec `jakarta.ws.rs.core.Response`.

## Partie 2 — Spring MVC
Le sous-projet [spring-controller/](spring-controller/) utilise `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping` et `@DeleteMapping`. `ResponseEntity` permet de préciser le statut HTTP.

## Partie 3 — Spring Data REST
Le sous-projet [spring-data-rest/](spring-data-rest/) expose les repositories JPA sous `/data`. Les représentations HAL ajoutent des liens hypermédias `_links` pour la navigation entre ressources.

## Comparaison
| Critère | Jersey | Spring MVC | Spring Data REST |
| --- | --- | --- | --- |
| Définition HTTP | Annotations JAX-RS | Annotations Spring | Repository exposé |
| Contrôle des réponses | `Response` | `ResponseEntity` | Convention du framework |
| Exposition CRUD | Méthodes explicites | Méthodes explicites | Automatique |
| Hypermedia HAL | Non imposé | Non imposé | Fourni par défaut |

## Vérification
Pour chaque dossier : `mvn clean verify`, puis `mvn spring-boot:run`. Exécuter les exemples de `requetes.http` pour vérifier lecture, création, modification, suppression et filtres ; examiner les codes 200/201/204/404 selon le scénario. La console H2 se trouve à `/h2-console` avec `jdbc:h2:mem:activitydb`.

Le workflow GitHub Actions du 25/11/2026 a compilé les trois projets et exécuté **un test de repository réussi par variante**. Les échanges HTTP doivent être présentés à partir de la session locale.

## Conclusion
Les trois implémentations montrent les mêmes concepts REST avec différents degrés de contrôle et d'automatisation.
