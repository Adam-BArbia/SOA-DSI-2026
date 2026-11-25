# TP7 — GraphQL Inventory Service avec Spring Boot et MySQL

> **Compte rendu du TP :** [consulter le document de réalisation, les tests CI et les observations](compte-rendu.md). Les captures d'une manipulation réelle restent à fournir lorsqu'elles ne figurent pas dans le dépôt.

[Énoncé](TP7.pdf). **Projet autonome Java 17**, MySQL et Spring GraphQL. L'application expose **Query, Mutation** et les relations **Product ↔ Category**.

## Démarrer (XAMPP / MySQL local)
1. Démarrer **MySQL** dans XAMPP ; créer `ProduitDB` ou autoriser sa création par JDBC (la base de données est spécifique à ce TP).
2. Utiliser les valeurs `DB_URL`, `DB_USER`, `DB_PASSWORD` suivant votre installation. Par défaut : `jdbc:mysql://localhost:3306/ProduitDB?createDatabaseIfNotExist=true`, `root`, mot de passe vide **uniquement en environnement de cours local**.
3. Ouvrir ce dossier dans IntelliJ avec **JDK 17+**, ou exécuter :
   ```bash
   mvn clean verify
   mvn spring-boot:run
   ```
4. Ouvrir `http://localhost:8090/graphiql?path=/graphql`.

Le schéma est `src/main/resources/graphql/schema.graphqls`. Chaque exécution ajoute automatiquement **3 catégories** (`Computer`, `Printer`, `Smartphone`) et **10 produits par catégorie**, mais uniquement si la base ne contient pas déjà de catégories.

## Tests des 12 opérations de l'énoncé
`graphql/tests.graphql` contient les requêtes pour : (1) tous les produits, (2) catégories, (3) produit avec catégorie, (4) catégorie avec produits, (5) produit par ID, (6) catégorie par ID, (7–8) ajout/vérification, (9–10) modification/vérification, (11–12) suppression/vérification.

Remplacer les ID de démonstration par les valeurs renvoyées par les requêtes. `saveProduct` / `updateProduct` vérifient que la catégorie existe et que les valeurs sont positives.

## Précisions par rapport au PDF
- Le PDF mêle `String`, `Long`, `Float` et `@GeneratedValue` pour les ID. Ici, **identifiant Product : `String` UUID**, **identifiant Category : `Long` auto-généré** et **identifiants GraphQL : `ID`**, ce qui est cohérent avec le mapping Java et les valeurs montrées.
- Le PDF suggère `ddl-auto=create` qui efface les tables au redémarrage. Pour éviter une destruction accidentelle, la configuration adopte `ddl-auto=update` dans ce **TP pédagogique local**. Ne pas utiliser `update` comme stratégie de migration en production.
- Ne jamais versionner de vrais secrets : la configuration accepte des variables d'environnement.
