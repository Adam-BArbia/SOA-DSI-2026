# Compte rendu — TP2 : Consommer des services SOAP avec SoapUI

**Sujet :** [TP2.pdf](TP2.pdf) · **Outil :** SoapUI · **Date de revue :** 25 novembre 2026

> **Traçabilité :** ce compte rendu distingue (a) les fichiers livrés, (b) le build et les tests attestés par [GitHub Actions](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044) du 25 novembre 2026, et (c) les manipulations à effectuer manuellement. Une sortie attendue n'est pas une sortie observée.

## 1. Objectifs
Importer les contrats WSDL de deux services SOAP, découvrir les opérations et construire des requêtes. Ce TP demande surtout une **manipulation graphique et des réponses réelles de services distants**, pas la compilation Java.

## 2. Partie I — Calculator
**WSDL du sujet :** `http://www.dneonline.com/calculator.asmx?WSDL`. Dans SoapUI : créer un projet SOAP, importer le WSDL, relever `Add`, `Subtract`, `Multiply`, `Divide`, et identifier dans le contrat `types`, `message`, `portType`, `binding`, `service`. La requête prête à coller est [`requetes/calculator-add.xml`](requetes/calculator-add.xml) : `intA=15`, `intB=35`. Le résultat **arithmétique attendu** est 50 ; [ce fichier de réponse](reponses/calculator-add-attendue.xml) est une **illustration**, pas une réponse capturée du réseau.

**À observer lors de l'exécution :** requête `Envelope / Body / Add`, réponse `AddResponse / AddResult`, statut HTTP et horodatage. Capture de l'écran SoapUI : **non disponible dans le dépôt**.

## 3. Partie II — CountryInfoService
**WSDL du sujet :** `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL`. Importer un second projet, examiner ses opérations, puis exécuter [`requetes/country-full-info.xml`](requetes/country-full-info.xml) avec le code `TN`. Comparer les champs de la requête aux valeurs effectivement rendues par le service. Ne pas recopier de réponse supposée si le service est indisponible.

## 4. Résultat et niveau de preuve
| Vérification | Statut | Support |
| --- | --- | --- |
| Sujet original fourni | Fourni | [TP2.pdf](TP2.pdf) |
| Requêtes SOAP préparées | Fourni | [`requetes/`](requetes/) |
| Résultat 15 + 35 | **Attendu**, non observé | [XML illustratif](reponses/calculator-add-attendue.xml) |
| Import WSDL dans SoapUI | **À vérifier manuellement** | Pas d'export `*-soapui-project.xml` versionné |
| Réponse du Calculator et CountryInfoService | **À vérifier manuellement** | Pas de réponses/captures réelles versionnées |

Le job statique [GitHub Actions](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044) valide la structure XML ; **aucun appel distant ni manipulation SoapUI n'est inclus dans CI**.

## 5. Comment compléter les preuves authentiques
1. Installer et ouvrir SoapUI, créer les deux projets et enregistrer les exports de projet dans ce dossier (sans identifiants/secrets).
2. Envoyer les requêtes préparées, sauvegarder les réponses **effectivement observées** et une capture de chaque échange dans un sous-dossier `captures/` à créer lors de l'expérience.
3. Noter pour chaque cas : date, endpoint WSDL/URL, opération, paramètres, statut HTTP, résultat reçu, erreur éventuelle.
4. En cas d'indisponibilité réseau, conserver l'erreur telle quelle et expliquer la limite plutôt que présenter une simulation comme une réussite.

## 6. Conclusion
Les messages SOAP, les espaces de noms et les étapes WSDL sont expliqués et les requêtes sont disponibles. Le critère central d'un TP SoapUI — la preuve d'échange réel — reste à documenter après manipulation.
