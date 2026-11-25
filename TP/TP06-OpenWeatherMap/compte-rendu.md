# Compte rendu — TP6 : Consommer une API météo externe

**Sujet :** [TP6.pdf](TP6.pdf) · **Environnement :** Maven/JDK 17, Spring Boot/RestClient · **Date de revue :** 25 novembre 2026

> **Traçabilité :** ce compte rendu distingue (a) les fichiers livrés, (b) le build et les tests attestés par [GitHub Actions](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044) du 25 novembre 2026, et (c) les manipulations à effectuer manuellement. Une sortie attendue n'est pas une sortie observée.

## 1. Objectif
Consommer l'API REST d'OpenWeatherMap depuis une application Spring Boot, désérialiser la réponse JSON et exposer une route locale indépendante. Les composants utiles sont réécrits dans ce dossier, sans appel au code de TP5.

## 2. Réalisation
| Étape | Code/fichier | Observation attendue |
| --- | --- | --- |
| Contrôleur local | [`WeatherController.java`](src/main/java/edu/iset/soa/tp6/controller/WeatherController.java) | `GET /api/meteo/{city}`. |
| Appel HTTP distant | [`WeatherService.java`](src/main/java/edu/iset/soa/tp6/service/WeatherService.java) | Utilise `RestClient` avec `q`, `appid`, `units=metric`, `lang=fr`. |
| Mapping JSON | [`model/`](src/main/java/edu/iset/soa/tp6/model/) | Records `WeatherResponse`, `Main`, `Weather`, `Wind`. |
| Configuration | [`application.properties`](src/main/resources/application.properties) | URL et clé via variables/configuration ; aucune clé réelle committée. |
| Validation isolée | [`WeatherServiceTest.java`](src/test/java/edu/iset/soa/tp6/service/WeatherServiceTest.java) | Simule une réponse JSON puis contrôle le mapping des champs. |

## 3. Résultats réellement attestés
**Preuve CI :** [exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), job `Maven TP/TP06-OpenWeatherMap (Java 17)` : `BUILD SUCCESS`; 1 test de mapping avec serveur mocké, 0 échec. Cela confirme compilation/test Maven, pas la disponibilité d'un endpoint en exécution réelle.


Le test emploie **une réponse artificielle**, pas le réseau OpenWeatherMap. Si aucune clé n'est configurée, le service prévoit un retour **503** ; ce comportement n'est pas une météo réelle.

## 4. Vérification manuelle à fournir
Configurer `OPENWEATHER_API_KEY` **localement**, jamais dans Git, puis `mvn spring-boot:run`. Appeler `http://localhost:8080/api/meteo/Tunis` et une deuxième ville. Archiver l'URL **sans clé**, le statut HTTP, la date et la réponse JSON réellement reçue. Tester séparément le cas sans clé. Ne jamais coller une URL distante contenant le paramètre `appid` dans les captures ou les logs publics.

**État :** aucun appel réel à OpenWeatherMap n'est attesté par le dépôt.

## 5. Analyse et conclusion
Le flux client local → contrôleur → RestClient → JSON distant → records Java est en place. La preuve d'intégration existe pour le mapping avec mock ; une validation réseau authentique reste nécessaire pour conclure le TP pratique.
