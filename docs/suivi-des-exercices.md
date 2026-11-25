# Suivi des livrables et vérification

Ce tableau distingue **contenu livré**, **CI** et **preuves manuelles locales**. « Non attesté » signifie qu'aucune preuve manuelle de cette session n'a été enregistrée, et non que le projet ne fonctionne pas.

| Sujet | Énoncé PDF | Réponses/code | Validation CI ou contrôle automatisé | Vérification manuelle locale |
| --- | --- | --- | --- | --- |
| TD1–TD5 (6 sujets) | Oui | Oui | Contrôles XML/statiques ciblés | Relecture des réponses à poursuivre |
| TP1 — JAXB | Oui | Oui | Compilation et exécutions JAXB en CI | Non attestée dans cette session |
| TP2 — SoapUI | Oui | Oui | Requêtes publiques non concluantes depuis GitHub Actions | **Réalisée** : Calculator et CountryInfoService dans SoapUI ; 4 fichiers |
| TP3 — Banque SOAP | Oui | Oui | WSDL et trois opérations SOAP en CI | **Réalisée** : Maven, WSDL et 3 opérations SoapUI ; 4 fichiers |
| TP4 — Apache CXF | Oui | Oui | Publication HTTP et client CXF en CI | Non attestée dans cette session |
| TP5 — REST | Oui | Oui | Jersey, Spring MVC et Spring Data REST en CI | **Partielle** : Spring MVC + console H2 ; 1 capture |
| TP6 — OpenWeatherMap | Oui | Oui | Service local testé en CI avec fournisseur simulé | **Réalisée** : OpenWeatherMap réel Tunis/Sousse ; 2 captures |
| TP7 — GraphQL | Oui | Oui | MySQL 8 et 12 opérations GraphQL en CI | **Réalisée** : XAMPP/MariaDB, CRUD, GraphiQL ; 5 fichiers |

Les **16 fichiers de preuve locale** sont classés dans les sous-dossiers `preuves/` de TP2, TP3, TP5, TP6 et TP7 ; les autres fichiers déjà présents dans le dépôt peuvent provenir des vérifications automatisées et restent documentés séparément.

## Points de contrôle pour la remise

- [x] Conserver les réponses SoapUI locales et les exports des deux WSDL publics.
- [x] Conserver les réponses Banque SOAP observées dans SoapUI.
- [x] Documenter la console H2 de la variante Spring MVC du TP5.
- [x] Documenter les appels OpenWeatherMap réels, sans inclure la clé.
- [x] Documenter le CRUD TP7, MariaDB et GraphiQL avec les captures et JSON.
- [ ] Vérifier après publication que tous les liens vers `preuves/` fonctionnent.
- [ ] Vérifier le statut du workflow CI sur le commit final.
- [ ] Contrôler la conformité et l'autorisation de redistribution des PDF dans ce dépôt public.
- [ ] Pour un déploiement public éventuel, revoir la sécurité de la version GraphiQL et la configuration des accès.

Voir [guide des preuves](guide-preuves-pratiques.md) et [résultats CI](resultats-verification-distante.md).
