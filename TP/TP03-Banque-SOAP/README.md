# TP3 — Banque : service SOAP avec JAX-WS

> **Compte rendu du TP :** [consulter le document de réalisation, les tests CI et les observations](compte-rendu.md). Les captures d'une manipulation réelle restent à fournir lorsqu'elles ne figurent pas dans le dépôt.

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

## Validation demandée par le sujet
Importer le WSDL **généré réellement** dans SoapUI puis identifier : `targetNamespace`, `service`, `port`, `binding`, `portType`, opérations, paramètres, types XML de `Compte` et représentation de `List<Compte>`. Lancer `conversionEuroToDinar(100)`, `getCompte(1)` et `getComptes()`.

L'implémentation et le test sont indépendants du client du TP4. Le taux de change est un **exemple fixe de cours**, et ne représente pas un taux financier réel.
