# Guide de démonstration locale — preuves pratiques

Ce guide rassemble les vérifications à réaliser sur un poste Windows avec IntelliJ, Maven, SoapUI et XAMPP. Les comptes rendus sont rangés **dans chaque TP**, sans dépendance entre les projets. Les commandes et les fichiers d'exemple facilitent la reproduction ; une capture n'est conservée comme preuve qu'après exécution effective.

## Ordre conseillé : moins de manipulations

| TP | Manipulation minimale | Preuve à conserver |
| --- | --- | --- |
| TP1 — JAXB | Lancer les classes de génération et lecture XML | Console + `message.xml` et `message.xsd` |
| TP2 — SoapUI | Importer les deux WSDL publics et envoyer Add / FullCountryInfo | Une réponse SOAP par service |
| TP3 — Banque SOAP | Démarrer `BanqueServer`, ouvrir `?wsdl`, lancer les trois opérations dans SoapUI | WSDL + réponse SOAP |
| TP4 — CXF | Démarrer `LocalBanqueFixture`, puis `ClientMain` | Console du serveur et du client |
| TP5 — REST | Démarrer séparément Jersey, Spring MVC et Spring Data REST ; envoyer les requêtes fournies | Réponses HTTP et statut de chaque variante |
| TP6 — Météo | Configurer la clé OpenWeatherMap, lancer le service et demander Tunis puis Sousse | Deux réponses JSON |
| TP7 — GraphQL | Démarrer MySQL dans XAMPP, lancer Spring Boot et exécuter les requêtes dans `graphql/tests.graphql` | GraphiQL + tables dans phpMyAdmin |

## Vérification préalable commune

Dans chaque sous-projet Maven, lancer `mvn clean verify`. Le dépôt dispose déjà d'un workflow GitHub Actions qui valide les compilations et les tests unitaires. Les services SOAP/REST, les bases MySQL et les API externes nécessitent une session d'intégration dédiée.

## MySQL / TP7 : procédure courte

1. Ouvrir **XAMPP Control Panel** et démarrer **MySQL**.
2. Ouvrir `http://localhost/phpmyadmin` et exécuter :

```sql
CREATE DATABASE IF NOT EXISTS ProduitDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

3. Dans `TP/TP07-Spring-GraphQL`, définir les paramètres de connexion dans l'environnement IntelliJ si la configuration XAMPP est différente : `DB_URL`, `DB_USER`, `DB_PASSWORD`. Pour une installation locale XAMPP par défaut, vérifier l'utilisateur `root` et son mot de passe réel ; ne jamais committer de secret.
4. Lancer l'application et ouvrir `http://localhost:8090/graphiql?path=/graphql`.
5. Copier les opérations du fichier `graphql/tests.graphql` et les exécuter dans l'ordre. Réutiliser l'identifiant créé pour les opérations update/delete.
6. Dans phpMyAdmin, ouvrir `ProduitDB` et vérifier les enregistrements et les relations.

**Preuves recommandées :** une capture de GraphiQL avec `productList`, une capture d'une mutation réussie, une capture phpMyAdmin montrant les tables.

## SoapUI : procédure courte

- **TP2** : File > New SOAP Project ; importer le WSDL Calculator puis celui de CountryInfoService ; reprendre les XML de `requetes/` et cliquer sur Submit. Exporter le projet via **Save Project As** au format XML dans le dossier TP2.
- **TP3** : lancer `BanqueServer` avant d'importer `http://localhost:8080/BanqueWS?wsdl`. Envoyer `conversionEuroToDinar`, `getCompte`, `getComptes`.
- En cas d'échec d'un service public, noter le code HTTP et le message réel ; le service distant peut être indisponible.

## Où ranger les preuves

Dans le dossier de chaque TP, créer au besoin `captures/` et y placer des PNG nommés `01-demarrage.png`, `02-requete.png`, `03-reponse.png`. Ne pas y ajouter de faux écrans ou de réponses générées pour les présenter comme des captures.

## Critère de fin

Une preuve est suffisante lorsqu'elle montre : l'application ou le client utilisé, l'opération appelée, les paramètres et le résultat observé. Il n'est pas nécessaire de faire une capture pour chaque ligne de code.
