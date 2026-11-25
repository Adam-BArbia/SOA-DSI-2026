# TP5 — Web Service REST avec Spring Boot

> **Compte rendu du TP :** [consulter le document de réalisation, les tests CI et les observations](compte-rendu.md). Les captures d'une manipulation réelle restent à fournir lorsqu'elles ne figurent pas dans le dépôt.

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

⚠️ Les requêtes d'exemple sous `requetes.http` ne sont pas exécutées automatiquement : leur réussite doit être vérifiée sur la machine d'évaluation.
