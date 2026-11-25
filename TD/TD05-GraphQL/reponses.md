# TD5 — Réponses détaillées

## Exercice 1 — Principes GraphQL
1. **Over-fetching** (le serveur retourne trop de champs) et **under-fetching** (plusieurs appels REST nécessaires pour réunir les données liées) sont deux problèmes auxquels une sélection GraphQL de champs répond. REST peut aussi les limiter via projections/expansions.
2. Le client interroge généralement **un point d'entrée unique** (`/graphql`) avec une requête indiquant l'opération et les champs voulus ; ce n'est pas un endpoint séparé par ressource.
3. Le **schéma** expose types, champs, arguments, relations, nullabilité et opérations valides ; il forme le **contrat typé** auquel les clients et les resolvers doivent se conformer.

## Exercice 2 — Schéma Author/Book
1. Types d'objet : `Author`, `Book`. Racines d'opérations : `Query`, `Mutation`, `Subscription`. Types scalaires présents : `ID`, `String`.
2. `Query` lit (`books`, `book`), `Mutation` modifie (`addBook`) et `Subscription` suit des évènements (`bookAdded`), avec un transport compatible (souvent WebSocket), si le serveur l'implémente.
3. `!` indique **non-null**. `[Book!]!` est une **liste non nulle** dont les **éléments sont eux aussi non nuls** ; elle peut être vide.
4. `Book.author` accède à l'auteur d'un livre ; `Author.books` accède à sa collection de livres. Les resolvers assurent l'accès réel aux données.

## Exercice 3 — Requêtes et mutations
1. Voir `exemples/livres-auteurs.graphql` : `books { title author { name } }`.
2. Voir `exemples/livres-auteurs-id.graphql` : ajout de `id` dans la sélection.
3. Voir `exemples/ajouter-livre.graphql` : `addBook(title: "Programmation avec Spring", authorId: "1") { id title }`.
4. L'opération `subscription { bookAdded { id title } }` s'abonne aux nouvelles insertions **si le serveur dispose du support des subscriptions** ; une simple définition de schéma ne suffit pas à activer la diffusion.

## Exercice 4 — Cycle d'une requête
1. Le client envoie la requête → le serveur GraphQL analyse sa syntaxe → valide les champs, types et arguments contre le schéma → exécute les resolvers → assemble la réponse structurée (`data`, et éventuellement `errors`) → la retourne au client.
2. Le **GraphQL Engine** gère parsing, validation et exécution selon le schéma ; les **resolvers** récupèrent ou calculent les valeurs depuis la source (base de données, service externe, etc.).
3. Un champ inconnu provoque une **erreur de validation** : l'opération n'est pas exécutée normalement (la réponse comporte des `errors`). Le statut HTTP dépend du serveur et du type d'erreur.
4. Avantage : demander précisément les champs nécessaires et des objets liés en une opération. REST reste adapté à une API CRUD simple, à des ressources cacheables par URL ou aux opérations HTTP classiques.
