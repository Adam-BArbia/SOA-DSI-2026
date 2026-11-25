# Registre des preuves et limites de validation

**Objet :** dossier de remise « compte rendu » du cours SOA, DSI3, année universitaire 2026–2027. **Date de référence :** 25 novembre 2026.

## Source primaire des résultats automatiques

- [Exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), commit [`15f15b0`](https://github.com/Adam-BArbia/SOA-DSI-2026/commit/15f15b0d468c01f31fd838b77ae6d62ef10d8df1), branche `main` : **success**.
- 9 jobs verts : 1 job de validation statique XML/DTD/XSD/WSDL/JSON/PDF et 8 jobs Maven isolés.
- **Ce que cela prouve :** les sources de ce commit sont compatibles avec les builds Maven configurés et passent les tests JUnit présents ; les fichiers structurés passent les contrôles statiques configurés.
- **Ce que cela ne prouve pas :** import SoapUI, service SOAP en écoute, requête HTTP aboutie, appel OpenWeatherMap réel, interface GraphiQL ouverte, persistance MySQL effective, captures d'écran d'une séance de TP.

## Résultats des jobs Maven du commit référencé

| TP | Job CI | Résultat du journal | Portée |
| --- | --- | --- | --- |
| TP1 | `Maven TP/TP01-JAXB (Java 23)` | BUILD SUCCESS ; 1 test, 0 échec | Mapping JAXB testé |
| TP2 | Pas de job Maven | Validation XML statique uniquement | Aucun échange SoapUI validé |
| TP3 | `Maven TP/TP03-Banque-SOAP (Java 23)` | BUILD SUCCESS ; 3 tests, 0 échec | Logique métier testée |
| TP4 | `Maven TP/TP04-Client-Apache-CXF (Java 23)` | BUILD SUCCESS ; `wsdl2java` exécuté ; **No tests to run** | Compilation/génération seulement |
| TP5 Jersey | `Maven TP/TP05-Spring-REST/jersey (Java 17)` | BUILD SUCCESS ; 1 test, 0 échec | Test repository |
| TP5 MVC | `Maven TP/TP05-Spring-REST/spring-controller (Java 17)` | BUILD SUCCESS ; 1 test, 0 échec | Test repository |
| TP5 Data REST | `Maven TP/TP05-Spring-REST/spring-data-rest (Java 17)` | BUILD SUCCESS ; 1 test, 0 échec | Test repository |
| TP6 | `Maven TP/TP06-OpenWeatherMap (Java 17)` | BUILD SUCCESS ; 1 test, 0 échec | API distante **simulée** |
| TP7 | `Maven TP/TP07-Spring-GraphQL (Java 17)` | BUILD SUCCESS ; 1 test, 0 échec | DTO ; pas de MySQL |

**Total :** 9 tests unitaires détectés, tous réussis (1 + 3 + 1 + 1 + 1 + 1 + 1). Compte estimé à partir des journaux des huit jobs ; ne pas le confondre avec 9 tests d'intégration.

## Exigences de preuve manuelle pour la remise définitive

| TP | Preuves à ajouter après manipulation réelle | État au 25 novembre 2026 |
| --- | --- | --- |
| TP1 | Console marshalling/unmarshalling, XML et XSD générés, classes XJC | Non archivées |
| TP2 | Projet SoapUI exporté, échange Calculator et CountryInfoService, réponses serveur | Non archivées |
| TP3 | WSDL effectivement exposé, import et trois requêtes/réponses SoapUI | Non archivées |
| TP4 | Deux consoles serveur/client + réponse SOAP réelle | Non archivées |
| TP5 | GET/POST/PUT/DELETE, filtres, HAL, base H2 avec codes HTTP | Non archivées |
| TP6 | Réponse OpenWeatherMap réelle, cas de clé absente, secret masqué | Non archivées |
| TP7 | GraphiQL 12 scénarios et tables/valeurs MySQL réelles | Non archivées |

## Format de preuve conseillé

Lorsqu'une manipulation est réalisée, créer dans le TP un sous-dossier `captures/` et déposer des fichiers nommés `01-demarrage.png`, `02-requete.png`, `03-reponse.png` selon le cas. Noter dans le compte rendu **la date, la commande, l'environnement, l'entrée, le résultat effectivement observé et une conclusion**. Une capture doit provenir de l'exécution réelle, jamais d'un résultat attendu ou fabriqué.

## Sécurité et intégrité

Ne jamais publier les mots de passe, les clés API ou la chaîne de connexion complète avec secrets. Les PDF d'énoncés/corrections proviennent du cours ; vérifier l'autorisation de leur diffusion publique avant une remise ou un partage externe. Les résultats CI sont publiés par GitHub et peuvent être vérifiés indépendamment.
