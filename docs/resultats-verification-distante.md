# Preuves d'exécution distantes — SOA DSI 2026

**Date :** 25 novembre 2026 · **Méthode :** GitHub Actions, runners Linux éphémères · **Exécutions :** [workflow initial — run 45591780562](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562) et [vérification après correction CXF — run 45592227117](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117).

## Distinction avec les tests manuels locaux

Des tests manuels sur Windows ont ensuite été réalisés avec SoapUI, XAMPP/MariaDB, GraphiQL, H2 et l'API OpenWeatherMap réelle. Ils sont documentés dans le [guide de démonstration locale](guide-preuves-pratiques.md) et les comptes rendus individuels. Les observations historiques de ce document restent **strictement celles des runners GitHub Actions** ; les succès locaux ultérieurs ne changent pas les résultats CI d'origine.

## Objet
Ces preuves établissent des exécutions et échanges **réellement observés en CI**. Il s'agit de vérifications distantes automatisées, et non de captures provenant d'IntelliJ, XAMPP, SoapUI ou d'un poste étudiant. Elles n'impliquent aucun téléchargement ou installation sur le PC de l'étudiant.

## Synthèse des vérifications

| TP | Vérification observée | Source de preuve |
| --- | --- | --- |
| TP1 — JAXB | Compilation et tests Maven, exécution des programmes JAXB, génération et analyse syntaxique de `message.xml` et `message.xsd` | [Artifact TP1](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562/artifacts/15011501865) |
| TP2 — Services publics SOAP | Deux requêtes réellement tentées, mais connexions réinitialisées par les fournisseurs ; résultat **non concluant** sur la disponibilité des services | [Artifact TP2](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562/artifacts/15010812374) |
| TP3 — Banque SOAP | WSDL généré téléchargé depuis le serveur lancé en CI ; trois opérations SOAP testées avec réponses XML HTTP 200, conversion 100 → 350 | [Artifact TP3](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562/artifacts/15011252478) |
| TP4 — Apache CXF | Serveur CXF local publié avec WSDL HTTP 200, client Java connecté ; compte 1 : solde 2000.0, 3 comptes retournés, conversion 100 EUR → 350.0 TND. Le premier essai a révélé un transport HTTP Jetty absent ; la dépendance a été ajoutée au module avant ce résultat réussi. | [Artifact TP4 — essai corrigé](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117/artifacts/15011003836) |
| TP5 — Jersey | Échanges HTTP réels : liste, filtres, POST 201, lecture, PUT 200, DELETE 204 puis GET 404 | [Artifact Jersey](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562/artifacts/15011012872) |
| TP5 — Spring MVC | Les mêmes échanges HTTP et CRUD réussis sur le serveur Spring MVC autonome | [Artifact Spring MVC](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562/artifacts/15010478910) |
| TP5 — Spring Data REST | HTTP 200 sur racine HAL, listes d'activités et catégories, filtres status/category | [Artifact Spring Data REST](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562/artifacts/15011401540) |
| TP6 — Météo | Application Spring Boot démarrée, appels HTTP Tunis et Sousse et réponses JSON vérifiées ; serveur météo amont **simulé**, pas appel OpenWeatherMap réel | [Artifact TP6](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562/artifacts/15010943276) |
| TP7 — MySQL + GraphQL | MySQL 8 réel : tables `category` et `product`, 3 catégories et 30 produits ; **12 opérations GraphQL réussies** (queries, ajout, vérification, modification, vérification, suppression, vérification) | [Artifact TP7 MySQL](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45591780562/artifacts/15010732748) |

## Méthode et limites

- Le fichier de pipeline [practical-evidence.yml](../.github/workflows/practical-evidence.yml) démarre de vraies instances de service ; les sondes sont décrites dans [tools/practical_http.py](../tools/practical_http.py).
- Dans TP2, le service Calculator et CountryInfoService ont tous deux réinitialisé la connexion depuis le runner GitHub Actions. L'exécution du script est terminée correctement car l'indisponibilité d'un fournisseur tiers est classée **inconclusive**, pas **pass**.
- Dans TP6, le contrôleur et le client HTTP Spring Boot ont été exercés, mais l'amont était un simulateur HTTP. Aucune mesure météo actuelle n'est certifiée.
- Les journaux incluent les valeurs d'exemple uniquement lorsque l'application en a réellement retourné. Les secrets de production ne sont pas requis.
- Les fichiers `observations.json` et `observations.md`, les journaux Maven/serveur et les réponses XML permettent au lecteur de consulter les preuves sans relancer les opérations.

## Résultat de l'analyse SOAP (TP3)

Le WSDL réellement servi par Metro utilise le `targetNamespace` `http://service.tp3.soa.iset.edu/`, le service `BanqueService`, le port `BanqueServiceImplPort`, le portType `BanqueService` et le binding `BanqueServiceImplPortBinding` en SOAP/HTTP *document/literal*. Les opérations déclarées sont `conversionEuroToDinar`, `getCompte` et `getComptes`. La réponse SOAP `getComptes` observée contient trois éléments `return` avec les codes `1`, `2`, `3` et les soldes respectifs `2000.0`, `3500.0`, `8700.0`. Les timestamps dépendent de l'instant du test.

## Correction technique validée (TP4)

Le premier démarrage de `LocalBanqueFixture` échouait avec `Cannot find any registered HttpDestinationFactory from the Bus` : le module compilait, mais le transport de publication HTTP embarqué manquait. L'ajout de `org.apache.cxf:cxf-rt-transports-http-jetty:4.0.4` au `pom.xml` a permis de publier le WSDL et d'exécuter le client sans modifier les classes métier. Les journaux du second essai conservent les sorties effectives.
