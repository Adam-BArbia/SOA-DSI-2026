# SOA & Services Web — DSI3 (2026–2027)

**Enseignante :** Bènène Fradi Boumiza · **Établissement :** ISET · **Dépôt académique :** 7 travaux pratiques et 5 TD numérotés (dont 2 sujets distincts pour le TD3).

> Chaque sujet est traité **indépendamment** : ses fichiers, exemples, dépendances et instructions résident exclusivement dans son dossier. Il n'existe pas de module Java partagé. Les PDF originaux sont inclus, sans modification de leur contenu.

## Travaux dirigés

| Sujet | Thème | Support | Réponses |
| --- | --- | --- | --- |
| [TD1](TD/TD01-XML/) | XML, DTD, XSD | Sujet + correction officielle | [reponses.md](TD/TD01-XML/reponses.md) |
| [TD2](TD/TD02-Conception-SOA/) | Paradigmes et conception SOA | Sujet + correction officielle | [reponses.md](TD/TD02-Conception-SOA/reponses.md) |
| [TD3A](TD/TD03-SOAP/sujet-MeteoService/) | WSDL du service météo | Sujet | [reponses.md](TD/TD03-SOAP/sujet-MeteoService/reponses.md) |
| [TD3B](TD/TD03-SOAP/sujet-BookService-UDDI/) | BookService, SOAP et UDDI | Sujet **complémentaire**, non une correction | [reponses.md](TD/TD03-SOAP/sujet-BookService-UDDI/reponses.md) |
| [TD4](TD/TD04-REST/) | REST, HTTP, HAL | Sujet | [reponses.md](TD/TD04-REST/reponses.md) |
| [TD5](TD/TD05-GraphQL/) | Schéma et opérations GraphQL | Sujet | [reponses.md](TD/TD05-GraphQL/reponses.md) |

## Travaux pratiques

| TP | Thème | Commande ou outil principal |
| --- | --- | --- |
| [TP1](TP/TP01-JAXB/) | JAXB, XML, XSD, XJC | `mvn clean verify` — JDK 23 |
| [TP2](TP/TP02-SOAPUI/) | Consommation des services SOAP via SoapUI | Importer les projets SoapUI |
| [TP3](TP/TP03-Banque-SOAP/) | Publier un service SOAP JAX-WS | `mvn clean compile exec:java` — JDK 23 |
| [TP4](TP/TP04-Client-Apache-CXF/) | Client SOAP Apache CXF et génération de stubs | `mvn clean compile` — JDK 23 |
| [TP5](TP/TP05-Spring-REST/) | REST avec Jersey, Spring MVC et Spring Data REST | 3 projets Maven **indépendants** |
| [TP6](TP/TP06-OpenWeatherMap/) | Consommation d'une API externe avec `RestClient` | Clé API requise pour les appels en direct |
| [TP7](TP/TP07-Spring-GraphQL/) | GraphQL + GraphiQL + JPA/MySQL | MySQL requis ; port 8090 |


## Comptes rendus et preuves de validation

Chaque dossier TP dispose d'un **[compte rendu détaillé](TP/TP01-JAXB/compte-rendu.md)** : objectifs, correspondance avec le PDF, code réalisé, preuves de tests et manipulations à reproduire. Les comptes rendus individuels sont : [TP1](TP/TP01-JAXB/compte-rendu.md) · [TP2](TP/TP02-SOAPUI/compte-rendu.md) · [TP3](TP/TP03-Banque-SOAP/compte-rendu.md) · [TP4](TP/TP04-Client-Apache-CXF/compte-rendu.md) · [TP5](TP/TP05-Spring-REST/compte-rendu.md) · [TP6](TP/TP06-OpenWeatherMap/compte-rendu.md) · [TP7](TP/TP07-Spring-GraphQL/compte-rendu.md).

- [**Matrice de conformité des sujets**](docs/grille-conformite.md) : où trouver chaque réponse et quel niveau de preuve est disponible.
- [**Registre de validation et preuves**](docs/preuves-validation.md) : lien vers le job CI réel, nombres de tests, limites et vérifications interactives manquantes.

**Important :** le pipeline GitHub Actions a réussi sur le commit de référence, mais aucune capture de séance ni réponse d'un service tiers n'est inventée. Le dépôt distingue code livré, tests automatiques passés et expériences pratiques encore à enregistrer.

## Évaluation et reproductibilité

1. Ouvrir le dossier de l'exercice voulu ; consulter le **PDF fourni** et le README ou `reponses.md`.
2. Pour un TP Java, ouvrir **ce dossier lui-même** dans IntelliJ IDEA en tant que projet Maven et utiliser le JDK demandé dans le sujet.
3. Consulter les exemples fournis. Les services externes, clés ou MySQL sont explicitement signalés.
4. Aucune fonctionnalité ne requiert de référencer un autre dossier du dépôt.

**Note de validation :** le code est fourni comme solution reproductible, mais les tests Maven et les appels à des services tiers doivent être exécutés dans un environnement disposant de Maven, des JDK et de l'accès réseau appropriés. Ne pas confondre exemples préparés et preuves d'exécution réelle.

## Divergences ou ambiguïtés des énoncés

- `TD33.pdf` est un **deuxième énoncé** intitulé TD3, et non une correction de `TD3.pdf` : les deux sont traités dans des dossiers séparés.
- Dans le TD1, certaines déclarations de la correction officielle ne concordent pas avec l'énoncé (par exemple les `id` dupliqués, ordre des balises et valeur `Doctorat`). Elles sont signalées et justifiées dans la solution.
- TP7 contient des incohérences de types (`Float` pour les identifiants de catégorie, `String` et `@GeneratedValue` pour l'identifiant du produit). La solution conserve le comportement demandé et utilise `ID` pour les identifiants GraphQL, `Long` pour les catégories et `String` UUID pour les produits.

**Source des énoncés :** PDF remis dans le cadre du cours, conservés comme références pédagogiques. Aucune distribution commerciale.

## Vérifications rapides sans dépendances Java externes

```bash
python tools/verify_static.py
javac -d /tmp/soa-check tools/JavaSyntaxCheck.java
java -cp /tmp/soa-check JavaSyntaxCheck .
```

Les contrôles statiques vérifient la syntaxe des fichiers XML/JSON/Java et, si `lxml` est présent, les exemples DTD/XSD du TD1. **Ils ne remplacent pas les tests Maven.** Le workflow [GitHub Actions](.github/workflows/validate.yml) exécute `mvn clean verify` dans chaque projet Maven indépendamment, après publication des fichiers sur GitHub.