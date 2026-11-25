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

## Vérification manuelle locale avec OpenWeatherMap réel

Sous Windows, l'application Spring Boot a été lancée avec une clé `OPENWEATHER_API_KEY` définie **uniquement dans la configuration locale d'exécution**. Maven `verify` a réussi. Les appels à `http://localhost:8080/api/meteo/Tunis` et `/api/meteo/Sousse` ont donné des réponses du fournisseur réel :

| Chemin demandé | Nom retourné | Température | Humidité | Vent | Description |
| --- | --- | ---: | ---: | ---: | --- |
| `/api/meteo/Tunis` | `Tunisie` | 22,47 °C | 47 % | 4,46 m/s | couvert |
| `/api/meteo/Sousse` | `Sousse` | 24,88 °C | 44 % | 4,12 m/s | peu nuageux |

Les valeurs sont des **observations ponctuelles** de la session de test, et non des prévisions ou des mesures permanentes. Le nom `Tunisie` a été renvoyé pour la requête Tunis : ce libellé est conservé tel qu'observé, sans le corriger artificiellement.

**Preuves locales :** [Tunis](preuves/TP06-OpenWeatherMap-Tunis-Success.png), [Sousse](preuves/TP06-OpenWeatherMap-Sousse-Success.png).

Le workflow GitHub Actions, décrit précédemment, utilisait quant à lui un **amont simulé**. Les vérifications locale et CI démontrent donc deux aspects distincts de l'intégration. Aucun secret n'est stocké dans le dépôt.

## Conclusion
L'application a été validée contre un simulateur en CI, puis contre l'API publique OpenWeatherMap lors d'une exécution locale avec clé personnelle.
