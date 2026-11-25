# Compte rendu — TP6 : Consommation d'une API REST externe

## Objectif
Consommer une API météo externe depuis Spring Boot et exposer les données sous forme d'un endpoint REST local.

## Environnement
Java 17, Maven, Spring Boot, `RestClient` et OpenWeatherMap. Sujet : [TP6.pdf](TP6.pdf).

## Réalisation
- `WeatherController` expose `GET /api/meteo/{city}`.
- `WeatherService` appelle `https://api.openweathermap.org/data/2.5/weather` avec les paramètres `q`, `appid`, `units=metric` et `lang=fr`.
- Les records Java `WeatherResponse`, `Main`, `Weather` et `Wind` représentent les données JSON.
- La clé est fournie via `OPENWEATHER_API_KEY` ; elle ne figure pas dans le code versionné.

## Vérification
Exécuter `mvn clean verify` puis `mvn spring-boot:run`. Avec une clé valide, demander `http://localhost:8080/api/meteo/Tunis`, puis une deuxième ville, et examiner la température, l'humidité, la description et le vent.

Le workflow GitHub Actions du 25/11/2026 confirme la compilation et **un test réussi** de désérialisation avec un serveur HTTP simulé. Les valeurs météorologiques du test sont fictives ; une réponse réelle dépend de la clé et de l'état du service distant.

## Observations
`RestClient` encapsule l'appel HTTP et mappe la réponse JSON sur des objets typés. En l'absence de clé, le service renvoie un statut 503 explicite.

## Conclusion
L'API locale et l'intégration du client HTTP sont implémentées. Une requête avec clé personnelle permet de montrer le fonctionnement avec des données météo courantes.
