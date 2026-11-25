# Compte rendu — TP3 : Service bancaire SOAP / JAX-WS

**Sujet :** [TP3.pdf](TP3.pdf) · **Environnement :** Maven, JDK 23, Jakarta JAX-WS/Metro · **Date de revue :** 25 novembre 2026

> **Traçabilité :** ce compte rendu distingue (a) les fichiers livrés, (b) le build et les tests attestés par [GitHub Actions](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044) du 25 novembre 2026, et (c) les manipulations à effectuer manuellement. Une sortie attendue n'est pas une sortie observée.

## 1. Objectifs
Créer une entité `Compte`, exposer les opérations SOAP d'une banque, publier un endpoint puis vérifier son WSDL et le consommer dans SoapUI.

## 2. Réalisation
| Élément demandé | Fichier de réponse | Explication |
| --- | --- | --- |
| Modèle `Compte` | [`Compte.java`](src/main/java/edu/iset/soa/tp3/entity/Compte.java) | `code`, `solde`, `dateCreation` et accesseurs pour le mapping XML. |
| Contrat du service | [`BanqueService.java`](src/main/java/edu/iset/soa/tp3/service/BanqueService.java) | `@WebService`, `@WebMethod`, `@WebParam`; trois opérations. |
| Implémentation | [`BanqueServiceImpl.java`](src/main/java/edu/iset/soa/tp3/service/BanqueServiceImpl.java) | Conversion fixe à 3,5 et comptes pédagogiques. |
| Publication | [`BanqueServer.java`](src/main/java/edu/iset/soa/tp3/BanqueServer.java) | `Endpoint.publish` sur `http://localhost:8080/BanqueWS`. |
| Tests unitaires | [`BanqueServiceImplTest.java`](src/test/java/edu/iset/soa/tp3/service/BanqueServiceImplTest.java) | Vérifie les méthodes métier. |

## 3. Analyse du contrat WSDL demandé
**À confirmer sur le WSDL réellement généré** à `http://localhost:8080/BanqueWS?wsdl` : `definitions/targetNamespace`, `types` (schéma de `Compte`), `message`, `portType` (opérations), `binding` (SOAP/HTTP), `service`, `port`, `soap:address`. Les signatures Java annoncent `conversionEuroToDinar(double)`, `getCompte(long)` et `getComptes()`; le détail XML généré doit être relevé **après lancement**, pas inventé à partir de la source.

## 4. Résultats attestés
**Preuve CI :** [exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), job `Maven TP/TP03-Banque-SOAP (Java 23)` : `BUILD SUCCESS`; 3 tests, 0 échec. Cela confirme compilation/test Maven, pas la disponibilité d'un endpoint en exécution réelle.


**Résultats fonctionnels attendus, non attestés en SOAP :** `conversionEuroToDinar(100) = 350` ; `getCompte(1)` retourne un compte ; `getComptes()` retourne trois éléments. Le taux de change est **une constante de cours**, pas le taux bancaire réel.

## 5. Procédure de vérification manuelle
Depuis le dossier du TP : `mvn clean verify` puis `mvn exec:java`. Ouvrir le WSDL avec le navigateur et l'importer dans SoapUI. Exécuter les trois opérations. Enregistrer l'URL, la date, une capture du WSDL et les paires requête/réponse XML réelles. **État : endpoint et SoapUI non exécutés dans cette preuve CI.**

## 6. Conclusion
Le contrat, l'implémentation et le serveur JAX-WS sont indépendants des autres dossiers. La preuve de compilation et des tests métier est disponible ; la démonstration réseau/WSDL doit encore être archivée.
