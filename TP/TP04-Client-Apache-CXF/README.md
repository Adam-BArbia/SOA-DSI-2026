# TP4 — Consommation SOAP avec Apache CXF

> **Compte rendu du TP :** [consulter le document de réalisation, les tests CI et les observations](compte-rendu.md). Les captures d'une manipulation réelle restent à fournir lorsqu'elles ne figurent pas dans le dépôt.

[Énoncé](TP4.pdf). **Projet Maven/JDK 23 indépendant.** Contrairement au scénario de classe qui présuppose le WSDL du TP3, **ce projet embarque son propre contrat WSDL pédagogique** dans `src/main/resources/wsdl/banque.wsdl`. Il n'importe aucun fichier/code du TP3.

## Générer les stubs et compiler
```bash
mvn clean compile
```
Le plugin `cxf-codegen-plugin` lit le WSDL local et génère les classes Java dans `target/generated-sources/cxf` (package `edu.iset.soa.tp4.generated`).

## Démonstration autonome
1. Démarrer un fournisseur de service SOAP **local inclus dans ce TP** :
   ```bash
   mvn exec:java -Dexec.mainClass=edu.iset.soa.tp4.LocalBanqueFixture
   ```
2. Dans une seconde console, lancer le client généré :
   ```bash
   mvn exec:java -Dexec.mainClass=edu.iset.soa.tp4.ClientMain
   ```
3. Le client invoque `getCompte(1)`, `getComptes()` et `conversionEuroToDinar(100)`. La sortie attendue du taux de **cours** est `350.0`.

## Contrat et provenance
`banque.wsdl` est un **contrat WSDL pédagogique autonome** cohérent avec les opérations demandées, pas une capture du WSDL réel du TP3. Le serveur embarqué est un fixture local de test ; ne pas le confondre avec le programme autonome du TP3. Les classes générées ne sont pas commitées : elles sont produites par Maven.
