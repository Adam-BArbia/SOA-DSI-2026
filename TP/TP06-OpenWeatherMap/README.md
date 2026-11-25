# TP6 — Consommation d'une API REST externe avec Spring Boot

[Énoncé](TP6.pdf). Application Spring Boot autonome : elle reproduit localement les éléments Spring Boot utiles du TP5 **sans dépendance sur TP5**, puis expose sa propre API météo.

## Prérequis
- JDK 17+ et Maven 3.9+.
- Compte OpenWeatherMap et **clé personnelle**. Ne jamais l'écrire dans le dépôt.

## Exécution
```bash
export OPENWEATHER_API_KEY="votre_cle"
mvn clean verify
mvn spring-boot:run
```
Sous PowerShell : `$env:OPENWEATHER_API_KEY="votre_cle"`.

Tester : `GET http://localhost:8080/api/meteo/Tunis` (ou une autre ville).

L'appel vers `https://api.openweathermap.org/data/2.5/weather` transmet `q`, `appid`, `units=metric` et `lang=fr`, puis mappe le JSON sur des **records** `WeatherResponse`, `Main`, `Weather`, `Wind`.

`mvn test` utilise un **serveur HTTP mocké** pour vérifier la sérialisation sans faire d'appel réel. Une requête réelle exige une clé valide et un accès internet. En l'absence de clé, l'API répond explicitement `503 Service Unavailable` plutôt que d'exposer une clé ou simuler des données.
