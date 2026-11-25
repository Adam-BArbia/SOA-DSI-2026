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

## Reproduction sur XAMPP
Démarrer MySQL dans XAMPP, créer `ProduitDB` si nécessaire, paramétrer `DB_URL`, `DB_USER`, `DB_PASSWORD` suivant l'installation, puis lancer `mvn spring-boot:run` dans ce dossier. Ouvrir `http://localhost:8090/graphiql?path=/graphql`. L'interface phpMyAdmin peut confirmer visuellement les tables et les modifications, mais **aucune capture de celle-ci** n'est présentée comme une preuve CI.

## Conclusion
La base MySQL réelle, le schéma GraphQL, les requêtes et les mutations sont validés de bout en bout dans GitHub Actions. La manipulation graphique de GraphiQL/phpMyAdmin reste facultative, sauf demande spécifique de l'enseignante.
