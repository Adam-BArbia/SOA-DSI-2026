# TP3 — Banque : service SOAP avec JAX-WS

**Énoncé :** [TP3.pdf](TP3.pdf). Projet Maven autonome **JDK 23**, bibliothèque Metro/Jakarta JAX-WS 4.0.3.

## Démarrage
```bash
mvn clean verify
mvn exec:java
```
**Endpoint** : `http://localhost:8080/BanqueWS`
**WSDL généré** : `http://localhost:8080/BanqueWS?wsdl`

## Méthodes
- `conversionEuroToDinar(double montant)`: taux pédagogique **3,5** ; `100 EUR → 350 TND`.
- `getCompte(long code)`: renvoie un compte avec identifiant, solde et date.
- `getComptes()`: renvoie trois comptes exemples.

## Contrat et vérification pratique
Le WSDL a été téléchargé et les trois opérations SOAP ont été testées avec réponses HTTP 200 dans le [workflow d'intégration](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117). Les attributs du contrat, les structures JAXB et les résultats sont consignés dans le [compte rendu](compte-rendu.md). L'import dans SoapUI reste une démonstration graphique possible.

## Validation demandée par le sujet
Importer le WSDL **généré réellement** dans SoapUI puis identifier : `targetNamespace`, `service`, `port`, `binding`, `portType`, opérations, paramètres, types XML de `Compte` et représentation de `List<Compte>`. Lancer `conversionEuroToDinar(100)`, `getCompte(1)` et `getComptes()`.

L'implémentation et le test sont indépendants du client du TP4. Le taux de change est un **exemple fixe de cours**, et ne représente pas un taux financier réel.
