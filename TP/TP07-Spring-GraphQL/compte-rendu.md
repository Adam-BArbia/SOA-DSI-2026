# Compte rendu — TP7 : API GraphQL avec Spring Boot et MySQL

**Sujet :** [TP7.pdf](TP7.pdf) · **Environnement :** Maven/JDK 17, Spring GraphQL, JPA, MySQL/XAMPP · **Date de revue :** 25 novembre 2026

> **Traçabilité :** ce compte rendu distingue (a) les fichiers livrés, (b) le build et les tests attestés par [GitHub Actions](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044) du 25 novembre 2026, et (c) les manipulations à effectuer manuellement. Une sortie attendue n'est pas une sortie observée.

## 1. Objectifs
Construire les entités `Product` / `Category`, définir un schéma GraphQL, implémenter les `Query` / `Mutation`, initialiser des données de test et effectuer les 12 opérations indiquées par l'énoncé dans GraphiQL.

## 2. Réalisation
| Besoin | Fichier | Réponse |
| --- | --- | --- |
| Entités + relation | [`Product.java`](src/main/java/edu/iset/soa/tp7/entities/Product.java), [`Category.java`](src/main/java/edu/iset/soa/tp7/entities/Category.java) | Relation JPA `ManyToOne` / `OneToMany`. |
| Contrat GraphQL | [`schema.graphqls`](src/main/resources/graphql/schema.graphqls) | `productList`, `productById`, `categories`, `categoryById`, `saveProduct`, `updateProduct`, `deleteProduct`. |
| Résolveurs | [`ProductGraphQLController.java`](src/main/java/edu/iset/soa/tp7/web/ProductGraphQLController.java) | Consultation, création, modification, suppression et résolution de `Category.products`. |
| Données de départ | [`InventoryApplication.java`](src/main/java/edu/iset/soa/tp7/InventoryApplication.java) | Sur base sans catégories, 3 catégories et 10 produits chacune. |
| Scénarios de l'énoncé | [`graphql/tests.graphql`](graphql/tests.graphql) | 12 actions décrites (certaines sont des répétitions/vérifications). |

## 3. Justification des écarts avec le PDF
Le PDF mélange plusieurs types pour certains identifiants. L'implémentation choisit `Product.id = String` UUID, `Category.id = Long` auto-généré et `ID` côté schéma GraphQL pour une correspondance cohérente. `ddl-auto=update` remplace le `create` destructeur montré dans l'énoncé. Ces choix pédagogiques sont **explicites**, pas des copies littérales du sujet.

## 4. Résultats réellement attestés
**Preuve CI :** [exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), job `Maven TP/TP07-Spring-GraphQL (Java 17)` : `BUILD SUCCESS`; 1 test DTO, 0 échec. Cela confirme compilation/test Maven, pas la disponibilité d'un endpoint en exécution réelle.


**Limite essentielle :** ce test ne lance pas MySQL, n'exécute pas les 12 requêtes GraphQL et ne prouve pas que les mutations fonctionnent en base réelle.

## 5. Protocole de test manuel (XAMPP)
1. Démarrer **MySQL** dans XAMPP. Préparer une base `ProduitDB`. Configurer au besoin `DB_URL`, `DB_USER`, `DB_PASSWORD` en environnement local, sans committer d'identifiants.
2. Depuis ce dossier : `mvn clean verify`, puis `mvn spring-boot:run` ; ouvrir `http://localhost:8090/graphiql?path=/graphql`.
3. Suivre [les 12 étapes](graphql/tests.graphql) : liste produits, liste catégories, relations dans les deux sens, consultation par ID, ajout et vérification, modification et vérification, suppression et vérification.
4. Remplacer `PRODUCT_ID` et les IDs de catégorie par ceux **réellement obtenus** ; noter les réponses `data` et `errors`, la date, les statuts et des captures du schéma/GraphiQL. Vérifier la quantité initiale de données dans MySQL.
5. En cas de redémarrage avec une base déjà remplie, noter que le seeding est conditionné par `categories.count() == 0`.

**État :** toutes les requêtes sont préparées, mais la session GraphiQL + MySQL réelle n'a pas été attestée dans le dépôt.

## 6. Conclusion
Les couches entités, repositories, résolveurs et schéma sont présentes. La compilation passe en CI ; la dernière preuve attendue pour le compte rendu est l'exécution documentaire des 12 scénarios sur MySQL local.
