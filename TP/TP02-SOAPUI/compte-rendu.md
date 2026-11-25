# Compte rendu — TP2 : Consommation SOAP avec SoapUI

## Objectif
Découvrir un contrat WSDL, générer des opérations SOAP et interpréter les enveloppes XML et les réponses des services.

## Environnement
SoapUI ; services pédagogiques Calculator et CountryInfoService. Sujet : [TP2.pdf](TP2.pdf).

## Partie 1 — Calculator
Le WSDL `http://www.dneonline.com/calculator.asmx?WSDL` décrit les opérations `Add`, `Divide`, `Multiply` et `Subtract`. Les éléments `types`, `message`, `portType`, `binding` et `service` décrivent respectivement les types, les messages, les opérations, leur transport et l'adresse.

La requête [calculator-add.xml](requetes/calculator-add.xml) transmet `intA=15` et `intB=35`. Le résultat **arithmétique attendu** est `50`. La réponse XML dans [reponses/](reponses/) est un exemple de structure, et non un enregistrement de serveur.

## Partie 2 — CountryInfoService
Le WSDL `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL` présente des informations de pays. La requête [country-full-info.xml](requetes/country-full-info.xml) demande les informations de la Tunisie via `TN`. Le corps de la réponse doit être interprété selon le contrat obtenu lors de l'import.

## Analyse
Une enveloppe SOAP contient un `Body` et éventuellement un `Header`. Le `Body` porte l'opération et ses arguments ; les namespaces distinguent les éléments du protocole et ceux du service. Le transport HTTP et le format SOAP ont des responsabilités distinctes.

## Vérification pratique minimale
Importer les deux WSDL dans SoapUI, soumettre les deux requêtes, puis enregistrer une capture de chaque réponse dans `captures/`. Les services publics pouvant évoluer, relever le statut et le message d'erreur en cas d'indisponibilité.

## Conclusion
Les requêtes et leur analyse sont préparées. L'observation du serveur dans SoapUI permet de compléter les preuves pratiques sans modifier les fichiers du projet.
