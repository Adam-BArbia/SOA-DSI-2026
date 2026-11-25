# Guide de démonstration locale — preuves pratiques

Ce guide distingue les manipulations **effectivement réalisées sur Windows** des contrôles automatisés GitHub Actions. Chaque TP reste autonome ; les preuves locales sont classées dans son dossier `preuves/` et les comptes rendus dans `compte-rendu.md`.

## État des démonstrations

| TP | Vérification manuelle réalisée | Preuves locales |
| --- | --- | --- |
| TP1 — JAXB | Pas de vérification manuelle attestée dans cette session ; exécution CI disponible | — |
| TP2 — SoapUI | Import WSDL publics et réponses réelles `Add(15,35)=50`, `FullCountryInfo(TN)` | 2 captures et 2 projets SoapUI : [preuves](../TP/TP02-SOAPUI/preuves/) |
| TP3 — Banque SOAP | Serveur local, WSDL et trois opérations exécutées dans SoapUI | 3 captures et 1 projet SoapUI : [preuves](../TP/TP03-Banque-SOAP/preuves/) |
| TP4 — CXF | Pas de vérification manuelle attestée dans cette session ; échange client/serveur validé en CI | — |
| TP5 — REST | Variante Spring MVC : Maven `verify` et consultation de la console H2 (2 catégories, 2 activités) | [Capture H2](../TP/TP05-Spring-REST/preuves/TP05-H2-Console-Success.png) |
| TP6 — Météo | Maven `verify` et appels réels OpenWeatherMap pour Tunis et Sousse | [Tunis](../TP/TP06-OpenWeatherMap/preuves/TP06-OpenWeatherMap-Tunis-Success.png), [Sousse](../TP/TP06-OpenWeatherMap/preuves/TP06-OpenWeatherMap-Sousse-Success.png) |
| TP7 — GraphQL | Maven `verify`, XAMPP/MariaDB, phpMyAdmin, GraphQL CRUD via PowerShell et `productList` dans GraphiQL | 3 captures SQL, 1 capture GraphiQL, 1 réponse JSON : [preuves](../TP/TP07-Spring-GraphQL/preuves/) |

Les scénarios HTTP/CRUD des trois variantes TP5 et les douze étapes GraphQL TP7 sont également vérifiés **en CI** ; la preuve d'une exécution CI ne constitue pas automatiquement une preuve d'exécution manuelle.

## Vérifications distantes et limites

Les workflows ont testé les services en runners Linux : [résultats distants](resultats-verification-distante.md). En CI, les services SOAP publics du TP2 avaient réinitialisé les connexions, tandis que l'amont météo du TP6 était simulé. Les tests manuels ultérieurs ont, séparément, obtenu des réponses réelles des deux fournisseurs SOAP et d'OpenWeatherMap.

## Reproduire TP7 avec XAMPP

1. Démarrer MySQL dans XAMPP et ouvrir `http://localhost/phpmyadmin`.
2. Utiliser la base `ProduitDB` (la configuration JDBC actuelle permet de la créer si nécessaire). Le serveur initialise trois catégories et trente produits lorsque la base est vide.
3. Fournir si nécessaire `DB_URL`, `DB_USER`, `DB_PASSWORD` dans la configuration d'exécution locale ; **ne pas publier de mots de passe**.
4. Démarrer `InventoryApplication` (port 8090), puis ouvrir `http://localhost:8090/graphiql?path=/graphql`.
5. Exécuter `productList`; les mutations et la vérification de suppression sont décrites dans [graphql/tests.graphql](../TP/TP07-Spring-GraphQL/graphql/tests.graphql) et le [compte rendu](../TP/TP07-Spring-GraphQL/compte-rendu.md).
6. Contrôler les tables `category` et `product` dans phpMyAdmin.

Le navigateur GraphiQL a été débloqué localement avec `spring-graphql.version=1.3.6`. Cette configuration à dépendances frontend CDN ne doit **pas** être considérée comme évaluée pour une exposition publique. Ne pas exposer GraphiQL sur Internet sans mise à niveau ou solution locale maîtrisée et contrôle de sécurité.

## Reproduire TP2 et TP3 dans SoapUI

- TP2 : importer les WSDL Calculator et CountryInfoService, puis exécuter `Add` et `FullCountryInfo` avec les valeurs des comptes rendus. Les fichiers `*-soapui-project.xml` des dossiers `preuves/` peuvent être réimportés.
- TP3 : démarrer `BanqueServer`, importer `http://localhost:8080/BanqueWS?wsdl`, puis exécuter `conversionEuroToDinar`, `getCompte` et `getComptes`. Employer le paramètre `montant` pour la conversion.

## Critère et classement des preuves

Conserver les PNG réels, réponses JSON et exports SoapUI dans `TP/TPxx-*/preuves/` avec leurs noms `TPxx-...` existants. Ne pas inclure les identifiants temporaires de tests, secrets, captures fabriquées ou réponses illustratives présentées comme observations. Les anciennes observations CI restent attribuées à leur environnement.
