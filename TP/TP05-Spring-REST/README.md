# TP5 — Web Service REST avec Spring Boot

**Sujet original :** [TP5.pdf](TP5.pdf). Comparaison entre trois technologies : **Jersey/JAX-RS**, **Spring MVC REST Controller** et **Spring Data REST**.

Conformément au principe d'autonomie, **chaque sous-projet est un projet Maven complet** avec ses **propres** entités, repositories, données d'initialisation et tests. Aucune dépendance entre eux.

| Approche | Projet à ouvrir | Adresse principale |
| --- | --- | --- |
| Jersey/JAX-RS | [`jersey/`](jersey/) | `http://localhost:8080/api/activities` |
| Spring REST Controller | [`spring-controller/`](spring-controller/) | `http://localhost:8080/api/activities` |
| Spring Data REST (HAL) | [`spring-data-rest/`](spring-data-rest/) | `http://localhost:8080/data/activities` |

- JDK 17 et Maven 3.9+ recommandés.
- **Lancer un seul sous-projet à la fois** : chacun utilise le port local `8080` avec une base H2 **en mémoire indépendante**.
- Depuis le dossier de chaque approche : `mvn clean verify` puis `mvn spring-boot:run`.
- Console H2 : `http://localhost:8080/h2-console` ; URL JDBC `jdbc:h2:mem:activitydb`.
- Vérifier les GET, POST, PUT et DELETE ainsi que les filtres par statut, catégorie, lieu et nom (les endpoints exacts diffèrent selon l'approche).

Les fichiers `requetes.http` servent à la reproduction interactive. Des échanges **HTTP réels** sur Jersey, Spring MVC et Spring Data REST ont déjà été validés dans le [workflow pratique](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117). Les CRUD complets sont vérifiés pour Jersey et Spring MVC ; la validation Spring Data REST couvre GET, filtres et HAL (pas son CRUD complet). Voir le [compte rendu](compte-rendu.md).
