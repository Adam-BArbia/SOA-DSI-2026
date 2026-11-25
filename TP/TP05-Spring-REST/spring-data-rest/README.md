# TP5 — Spring Data REST

Cette variante est un **projet autonome**, dont les classes métier et repositories ne dépendent d'aucun autre sous-projet.

```bash
mvn clean verify
mvn spring-boot:run
```

- JDK 17 ; port `8080` ; H2 en mémoire (`jdbc:h2:mem:activitydb`).
- API : `/data/activities et /data/categories (HAL)`.
- Données de démonstration : `Sport/Football` et `Culture/Théâtre`.
- Voir [`requetes.http`](requetes.http) (IntelliJ HTTP Client ou adapté à curl/Postman).

Les captures ou résultats HTTP ne sont pas inventés et doivent être vérifiés lors de l'exécution réelle.
