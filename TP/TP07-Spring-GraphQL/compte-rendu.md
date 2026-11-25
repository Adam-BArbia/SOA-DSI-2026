# Compte rendu — TP7 : GraphQL, Spring Boot et MySQL

## Objectif
Développer une API GraphQL de gestion de produits et de catégories, reliée à une base MySQL et interrogée avec GraphiQL.

## Environnement
Java 17, Spring Boot, Spring GraphQL, Spring Data JPA, MySQL via XAMPP, GraphiQL. Sujet : [TP7.pdf](TP7.pdf).

## Modèle de données
- `Category` : identifiant `Long`, nom et relation `@OneToMany` vers les produits.
- `Product` : identifiant `String` UUID, nom, prix, quantité et relation `@ManyToOne` vers une catégorie.
- Le schéma [schema.graphqls](src/main/resources/graphql/schema.graphqls) déclare `Product`, `Category`, `ProductRequest`, `Query` et `Mutation`.

Les identifiants GraphQL utilisent le scalaire `ID`. Le code conserve des types Java cohérents là où le sujet mélange `Float`, `Long` et `String`.

## Requêtes
`productList`, `productById`, `categories` et `categoryById` exposent les données et les relations entre catégories et produits.

## Mutations
`saveProduct` ajoute un produit, `updateProduct` modifie ses propriétés et `deleteProduct` le supprime. Les contrôles vérifient notamment l'existence de la catégorie et les valeurs non négatives.

## Base MySQL et jeu de données
La configuration utilise `ProduitDB` sur `localhost:3306` par défaut, avec paramètres modifiables via `DB_URL`, `DB_USER` et `DB_PASSWORD`. L'initialisation ajoute trois catégories (`Computer`, `Printer`, `Smartphone`) et dix produits par catégorie lorsque la table des catégories est vide. `ddl-auto=update` évite de supprimer automatiquement les données au redémarrage.

## Exercices et vérification
Le fichier [graphql/tests.graphql](graphql/tests.graphql) regroupe les **12 étapes** du sujet : listes, relations, recherches par ID, création, contrôle, modification, contrôle, suppression et contrôle.

Pour la démonstration :
1. Démarrer MySQL dans XAMPP et créer ou autoriser la création de `ProduitDB`.
2. Exécuter `mvn clean verify` et `mvn spring-boot:run`.
3. Ouvrir `http://localhost:8090/graphiql?path=/graphql`.
4. Exécuter les opérations dans l'ordre, en remplaçant `PRODUCT_ID` par l'identifiant effectivement retourné.
5. Vérifier dans phpMyAdmin les tables `category` et `product` et leur relation.

Le workflow GitHub Actions du 25/11/2026 confirme la compilation et **un test unitaire réussi** du DTO. Le workflow ne lance pas MySQL et ne constitue donc pas une vérification de l'API contre cette base.

## Conclusion
Le service couvre le modèle, les queries, les mutations et les exemples de tests demandés. La démonstration MySQL/GraphiQL permet d'illustrer les échanges réels et les modifications persistées.
