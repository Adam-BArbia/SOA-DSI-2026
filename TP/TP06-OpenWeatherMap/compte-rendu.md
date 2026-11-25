# Compte rendu — TP6 : Consommation d'une API REST externe

## Objectif et travail demandé
Consommer OpenWeatherMap depuis Spring Boot et exposer un endpoint REST local. Énoncé : [TP6.pdf](TP6.pdf).

## Réalisation et explication
- `WeatherController` expose `GET /api/meteo/{city}`.
- `WeatherService` utilise `RestClient`, configuré par `weather.api.url`, et transmet `q`, `appid`, `units=metric` et `lang=fr` à l'endpoint `/data/2.5/weather`.
- `WeatherResponse` contient `name`, `main` (température, humidité), `weather` (description) et `wind` (vitesse). Ces champs sont compatibles avec la structure JSON attendue de l'API.
- La clé doit être fournie via `OPENWEATHER_API_KEY` ; aucune clé privée n'est stockée dans le dépôt.

## Résultats et vérification
Dans GitHub Actions, l'application a démarré et les appels HTTP `/api/meteo/Tunis` et `/api/meteo/Sousse` ont renvoyé HTTP 200 avec des objets typés. **Le fournisseur amont était un serveur HTTP simulé**, et non OpenWeatherMap.

Exemple de **données du simulateur** (fictives, et non un relevé météo) :

```json
{"name":"Tunis","main":{"temp":22.5,"humidity":58},"weather":[{"description":"ciel clair"}],"wind":{"speed":3.2}}
```

Le test confirme le chemin HTTP et la désérialisation, **pas** l'accès à une API météo publique. Voir [workflow d'intégration](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117). En absence de clé, le service signale l'indisponibilité (HTTP 503).

## Vérification facultative du fournisseur réel
Avec une clé personnelle autorisée dans `OPENWEATHER_API_KEY`, démarrer `mvn spring-boot:run` et demander `http://localhost:8080/api/meteo/Tunis` puis `http://localhost:8080/api/meteo/Sousse`. Les erreurs du fournisseur (clé invalide, quota, ville inconnue) doivent être rapportées comme telles, sans fabriquer de valeurs. Aucun appel réel avec une clé valide n'est confirmé à ce stade.

## Conclusion
L'intégration REST et le mapping JSON fonctionnent contre un fournisseur de test contrôlé. La communication réelle avec OpenWeatherMap dépend de la disponibilité d'une clé valide.
