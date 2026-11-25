# Compte rendu — TP4 : Client SOAP Apache CXF

**Sujet :** [TP4.pdf](TP4.pdf) · **Projet :** Maven/JDK 23 · **Date de revue :** 25 novembre 2026

> **Traçabilité :** ce compte rendu distingue (a) les fichiers livrés, (b) le build et les tests attestés par [GitHub Actions](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044) du 25 novembre 2026, et (c) les manipulations à effectuer manuellement. Une sortie attendue n'est pas une sortie observée.

## 1. Objectif et contrainte d'autonomie
Le sujet présente le client Apache CXF en lien avec le service bancaire du TP3. Pour respecter la consigne de ce dépôt — chaque TP exécutable **sans importer les fichiers d'un autre TP** — cette réponse inclut un WSDL pédagogique et un mini-serveur SOAP indépendants. **C'est une adaptation documentée du scénario**, non une preuve que le WSDL est celui généré par TP3.

## 2. Réalisation
| Étape | Artefact | Résultat attendu |
| --- | --- | --- |
| Contrat WSDL local | [`src/main/resources/wsdl/banque.wsdl`](src/main/resources/wsdl/banque.wsdl) | Définit `getCompte`, `getComptes`, `conversionEuroToDinar`. |
| Génération du client | [`pom.xml`](pom.xml) | `cxf-codegen-plugin:wsdl2java` produit les classes dans `target/generated-sources/cxf`. |
| Fournisseur de test local | [`LocalBanqueFixture.java`](src/main/java/edu/iset/soa/tp4/LocalBanqueFixture.java) | Écoute sur `http://localhost:8081/BanqueWS`. |
| Client consommateur | [`ClientMain.java`](src/main/java/edu/iset/soa/tp4/ClientMain.java) | Exécute les trois opérations et affiche comptes/conversion. |

## 3. Preuve disponible
**Preuve CI :** [exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), job `Maven TP/TP04-Client-Apache-CXF (Java 23)` : `BUILD SUCCESS`; aucun test JUnit exécuté (`No tests to run`). Cela confirme compilation/test Maven, pas la disponibilité d'un endpoint en exécution réelle.


Le journal du même job confirme l'exécution du plugin `cxf-codegen:wsdl2java`. **Attention :** aucun test automatisé ne démarre le serveur SOAP ni n'invoque le client.

## 4. Reproduction de la manipulation
1. Terminal A, dans ce dossier : `mvn clean compile`, puis `mvn exec:java -Dexec.mainClass=edu.iset.soa.tp4.LocalBanqueFixture`.
2. Terminal B, dans le même dossier : `mvn exec:java -Dexec.mainClass=edu.iset.soa.tp4.ClientMain`.
3. Ouvrir `http://localhost:8081/BanqueWS?wsdl` et conserver les captures/console **réelles**. La conversion de 100 euros doit retourner **350.0** selon la constante pédagogique ; trois comptes sont attendus.
4. Pour suivre à la lettre le scénario dépendant de TP3 du sujet, comparer aussi, lors d'une séance séparée, le contrat généré par TP3 ; préciser cette comparaison sans l'utiliser comme dépendance de construction du TP4.

**État :** exécution de l'échange de bout en bout **non attestée** à ce jour.

## 5. Conclusion
Le dépôt démontre la génération de proxies SOAP via CXF à partir d'un WSDL versionné. La compilation est confirmée ; une vraie trace du dialogue fournisseur/client est le principal complément attendu pour un compte rendu définitif.
