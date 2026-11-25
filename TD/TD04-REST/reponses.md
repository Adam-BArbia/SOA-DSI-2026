# TD4 — Réponses aux cinq exercices

## Exercice 1 — URI et verbes HTTP
**1.** Le développeur propose des URI fondées sur des **verbes** (`get`, `add`, `update`, `delete`) plutôt que des noms de ressources. Surtout, employer **GET pour ajouter/supprimer** viole la sémantique de GET (lecture sans effet métier demandé). `POST /updateActivity/5` manque de cohérence avec la modification d'une ressource identifiée.

**2–3. Proposition cohérente :**

| Besoin | Méthode et URI |
| --- | --- |
| Lister les activités | `GET /api/activities` |
| Consulter l'activité 5 | `GET /api/activities/5` |
| Créer une activité | `POST /api/activities` |
| Remplacer l'activité 5 | `PUT /api/activities/5` (ou `PATCH` pour modification partielle) |
| Supprimer l'activité 5 | `DELETE /api/activities/5` |
| Activités de catégorie 2 | `GET /api/categories/2/activities` |

Une variante documentée pour filtrer peut être `GET /api/activities?categoryId=2` ; utiliser une convention unique dans une même API.

## Exercice 2 — Résultats filtrés et codes HTTP
1. **Non.** `GET /api/categories/1/activities` devrait renvoyer seulement des activités de catégorie `1`. La réponse donnée contient « Théâtre » rattachée à la catégorie `2`.
2. Exposer une **projection/DTO** contenant seulement `name` et `price` ; éventuellement permettre une sélection de champs (`?fields=name,price`) si elle fait partie du contrat.
3. Une catégorie existante sans activité : **`200 OK`** avec `[]` est une représentation claire de la liste vide (un `404` concernerait plutôt la catégorie inexistante).
4. `200 OK` avec « Activity not found » est incohérent : renvoyer **`404 Not Found`** et un corps d'erreur JSON facultatif (`{"error":"Activity not found","id":15}`).
5. **4xx** = requête incorrecte ou impossible du point de vue du client (validation, authentification, ressource absente) ; **5xx** = échec du serveur à traiter une requête qui pourrait être correcte (exception interne, base indisponible).

## Exercice 3 — Jersey vs Spring MVC
1. En **Jersey/JAX-RS**, `@GET` traite GET, `@Path("/{id}")` déclare le chemin et `@PathParam("id")` injecte l'identifiant ; `@Produces(APPLICATION_JSON)` déclare le type de sortie. En **Spring**, `@GetMapping("/{id}")` associe la méthode et le chemin, `@PathVariable` extrait l'ID ; la classe englobante est typiquement annotée `@RestController` et `@RequestMapping`.
2. `jakarta.ws.rs.core.Response` est la représentation d'une réponse JAX-RS ; `org.springframework.http.ResponseEntity<T>` joue un rôle comparable dans Spring MVC, avec un corps typé `T`.
3. Les deux peuvent produire le **même JSON métier**, mais ce n'est pas automatique : sérialisation/configuration/contenu et en-têtes peuvent différer selon leur runtime.
4. Dans les deux extraits, une activité absente produit un **HTTP 404 sans corps** : `Response.status(404).build()` ou `ResponseEntity.notFound().build()`.

## Exercice 4 — Spring Data REST et HAL
1. Spring Data REST expose automatiquement les ressources et opérations CRUD des interfaces repository Spring Data, selon sa configuration, sans contrôleur personnalisé.
2. `_links` contient des liens hypermédias vers les ressources et relations ; `self` identifie l'URI canonique de la ressource actuelle.
3. Suivre l'URI donnée par `_links.activities.href`, ici `http://localhost:8080/api/categories/1/activities`, au lieu de construire le chemin arbitrairement.
4. HAL fournit la **découverte des ressources liées** et facilite la navigation des clients via des relations nommées.
5. Un JSON classique fournit souvent uniquement les propriétés métier ; HAL ajoute notamment `_links` et parfois `_embedded` pour décrire les ressources navigables et diminuer le couplage à des URI codées en dur.
6. Si l'on consulte `/api/categories/1`, le lien `activities.href` ne doit pas pointer vers **`/api/categories/2/activities`**. Corriger vers `/api/categories/1/activities`.

## Exercice 5 — Réservation et statuts
1. Retourner `200` à chaque opération masquerait les échecs pour les clients, outils de surveillance, caches et appels automatisés ; les codes HTTP doivent refléter le résultat.
2. **A : `200 OK`** (lecture réussie). **B : `201 Created`** avec ressource créée et idéalement `Location`. **C : `404 Not Found`** (activité absente). **D : `500 Internal Server Error`** (incident serveur, erreur de traitement interne). Une indisponibilité temporaire avérée pourrait justifier `503 Service Unavailable` selon le cas.
3. Exemples des réponses B et D : [`exemples/creation.json`](exemples/creation.json) et [`exemples/erreur-interne.json`](exemples/erreur-interne.json). Pour la réservation, `status != OPEN` ou capacité atteinte pourraient déclencher `409 Conflict` ; la validation est indépendante du `200` universel proposé.
