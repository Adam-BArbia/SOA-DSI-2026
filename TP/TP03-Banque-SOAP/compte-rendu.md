# Compte rendu — TP3 : Service Web SOAP bancaire

## Objectif
Publier un service JAX-WS, examiner son WSDL et appeler ses opérations avec un client SOAP.

## Environnement
Java 23, Maven, Jakarta JAX-WS/Metro et SoapUI. Sujet : [TP3.pdf](TP3.pdf).

## Implémentation
- `Compte` représente un compte par son code, son solde et sa date de création.
- `BanqueService` déclare trois opérations SOAP : `conversionEuroToDinar`, `getCompte` et `getComptes`.
- `BanqueServiceImpl` fournit les données pédagogiques et la conversion avec un taux fixe de 3,5.
- `BanqueServer` publie le service sur `http://localhost:8080/BanqueWS`.

## Analyse du WSDL (question 8)
Après démarrage, `http://localhost:8080/BanqueWS?wsdl` expose le contrat généré par JAX-WS. Les éléments à relever sont `definitions/targetNamespace`, `service`, `port`, `binding`, `portType`, les opérations et les types XML de `Compte`. `getComptes` transporte une collection de comptes : sa représentation exacte est déterminée par le WSDL effectivement généré.

## Tests (question 9)
Exécuter `mvn clean verify` puis `mvn exec:java` et importer le WSDL local dans SoapUI. Cas de démonstration :
- `conversionEuroToDinar(100)` : valeur mathématique attendue `350.0`.
- `getCompte(1)` : un compte de code `1` avec les données de l'implémentation.
- `getComptes()` : trois comptes exemples.

Le workflow GitHub Actions du 25/11/2026 confirme la compilation et les **3 tests unitaires réussis**. Ces tests de logique métier ne remplacent pas l'envoi d'enveloppes SOAP au service en fonctionnement.

## Conclusion
L'interface, l'implémentation et la publication SOAP sont présentes. L'analyse WSDL et les requêtes SoapUI peuvent être illustrées sur le serveur local.
