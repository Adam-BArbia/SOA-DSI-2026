# Compte rendu — TP7 : GraphQL, Spring Boot et MySQL

## Objectif et travail demandé
Créer une API GraphQL pour les produits et catégories, utiliser MySQL et exécuter les requêtes et mutations prévues par l'énoncé. Énoncé : [TP7.pdf](TP7.pdf).

## Réalisation
- `Category` : ID Java `Long`, nom, relation `@OneToMany` vers `Product`.
- `Product` : ID Java `String` UUID, nom, prix, quantité et relation `@ManyToOne`.
- [schema.graphqls](src/main/resources/graphql/schema.graphqls) : scalaire `ID`, types `Product` et `Category`, entrée `ProductRequest`, quatre queries et trois mutations.
- `InventoryApplication` initialise trois catégories (`Computer`, `Printer`, `Smartphone`) et dix produits par catégorie si les catégories sont absentes.

Les choix `Long`, UUID et `ddl-auto=update` assurent des types cohérents et évitent de supprimer les données au redémarrage du serveur. Ils sont explicités dans le [README](README.md).

## Requêtes et mutations
```graphql
query { productList { id name price quantity category { name } } }
query { categories { id name products { id name } } }
mutation {
  saveProduct(product: {name:"PC Demo", price:1299.0, quantity:8, categoryId:"1"}) {
    id name price quantity
  }
}
```

La valeur `categoryId:"1"` ci-dessus est **illustrative** ; utiliser l'identifiant réellement renvoyé par `categories`. Après création, réutiliser l'ID UUID retourné pour `productById`, `updateProduct` et `deleteProduct`. Les [12 étapes](graphql/tests.graphql) détaillent cette séquence.

## Résultats réellement observés
Le [workflow d'intégration du 25/11/2026](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117) a démarré **MySQL 8 en conteneur** et l'application Spring Boot. Les observations enregistrent :

- Tables `category` et `product` présentes dans la base `ProduitDB`.
- **3 catégories et 30 produits** initiaux.
- HTTP 200 pour toutes les **12 opérations GraphQL**.
- Création, relecture, modification, nouvelle relecture, suppression et vérification du résultat `null` après suppression.
- Interface `/graphiql` accessible en HTTP 200 (vérification réseau automatique, **sans capture graphique**).

Extrait authentique de la trace CI :

```text
[PASS] 7 save: GraphQL HTTP 200
[PASS] 8 verify save: GraphQL HTTP 200
[PASS] 9 update: GraphQL HTTP 200
[PASS] 10 verify update: GraphQL HTTP 200
[PASS] 11 delete: GraphQL HTTP 200
[PASS] 12 verify delete: GraphQL HTTP 200
```

Les réponses JSON complètes sont dans l'[artifact du TP7](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117/artifacts/15011802048) ; le workflow ne prétend pas avoir utilisé la base XAMPP locale.

## Vérification manuelle locale — XAMPP, MariaDB et GraphiQL

Sur Windows, le serveur Spring Boot a été démarré sur le port **8090** avec XAMPP (MariaDB 10.4) et la base `ProduitDB`. Maven `verify` a réussi avec **un test unitaire**. phpMyAdmin a montré les tables `category` et `product` contenant **3 catégories** et **30 produits**.

L'endpoint `POST /graphql` a été testé avec PowerShell : `productList` a renvoyé 30 produits et leurs catégories. Le cycle de mutation a été exécuté avec un **produit de test temporaire** : `saveProduct`, `updateProduct`, vérification par `productById`, puis `deleteProduct`. La suppression a renvoyé `DELETED`, et `productById` a ensuite renvoyé `null`. Le jeu initial de 30 produits a été retrouvé.

L'interface graphique GraphiQL a également été ouverte dans le navigateur sur `http://localhost:8090/graphiql?path=/graphql`. L'exécution de `GetProducts` y a affiché les 30 produits avec `id`, `name`, `price`, `quantity` et `category` sans erreur GraphQL.

**Preuves locales :**
- [GraphiQL — exécution de productList](preuves/TP07-GraphiQL-ProductList-Success.png)
- [Réponse JSON complète](preuves/TP07-GraphQL-ProductList-Success.json)
- [Tables dans phpMyAdmin](preuves/TP07-MySQL-Tables-Success.png)
- [Catégories dans phpMyAdmin](preuves/TP07-MySQL-Categories-Success.png)
- [Produits dans phpMyAdmin](preuves/TP07-MySQL-Products-Success.png)

**Correction de l'interface :** la version `spring-graphql` `1.3.3` (gérée initialement par Spring Boot 3.4.2) provoquait une page GraphiQL bloquée sur `Loading...` à cause de ressources frontend distantes manquantes. La version `1.3.6` a permis son fonctionnement lors du test local. **Cette correction vérifie la démonstration locale, pas l'aptitude à une exposition publique** : le GraphiQL fourni par les anciennes versions dépend de ressources CDN et nécessite une revue de sécurité ou un hébergement d'interface maîtrisé avant déploiement sur Internet.

## Conclusion
L'API GraphQL a été validée automatiquement en CI sur MySQL 8 et manuellement sur XAMPP/MariaDB, y compris les opérations CRUD et l'interface GraphiQL. Les preuves sont distinctes selon l'environnement d'exécution.
