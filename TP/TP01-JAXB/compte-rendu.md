# Compte rendu — TP1 : JAXB, XML, XSD et XJC

**Sujet :** [TP1.pdf](TP1.pdf) · **Projet :** Maven/JDK 23 · **Date de revue :** 25 novembre 2026

> **Traçabilité :** ce compte rendu distingue (a) les fichiers livrés, (b) le build et les tests attestés par [GitHub Actions](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044) du 25 novembre 2026, et (c) les manipulations à effectuer manuellement. Une sortie attendue n'est pas une sortie observée.

## 1. Objectifs et démarche
Transformer un objet Java en XML (marshalling), faire l'opération inverse (unmarshalling), produire un schéma XSD à partir d'une classe annotée puis générer des classes Java à partir d'un XSD (XJC). Le projet est indépendant des autres TP.

## 2. Réalisation, dans l'ordre du sujet
| Travail | Réponse dans le dépôt | Observation vérifiable |
| --- | --- | --- |
| Définir la classe métier | [`Message.java`](src/main/java/edu/iset/soa/tp1/Message.java) | `@XmlRootElement`, `@XmlElement`, `@XmlAttribute`, `@XmlTransient` explicitent le mapping. |
| Générer le XML | [`GenererMessageXml.java`](src/main/java/edu/iset/soa/tp1/GenererMessageXml.java) | Marshalling vers `message.xml` ; [exemple versionné](exemples/message.xml). |
| Relire le XML | [`LireMessageXml.java`](src/main/java/edu/iset/soa/tp1/LireMessageXml.java) | Désérialisation dans un `Message`. La propriété `isNew` est ignorée par XML, donc sa valeur n'est pas restaurée. |
| Java vers XSD | [`GenererSchema.java`](src/main/java/edu/iset/soa/tp1/GenererSchema.java) | Génération de `message.xsd` par `JAXBContext.generateSchema`. |
| XSD vers Java | [`pom.xml`](pom.xml) et [schéma source](src/main/xsd/message.xsd) | XJC génère les classes dans `target/generated-sources/jaxb`. |
| Vérifier le mapping | [test JUnit](src/test/java/edu/iset/soa/tp1/MessageJaxbTest.java) | Test automatisé de round-trip (1 test vert dans CI). |

## 3. Résultats réellement attestés
**Preuve CI :** [exécution GitHub Actions #45587136044](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45587136044), job `Maven TP/TP01-JAXB (Java 23)` : `BUILD SUCCESS`; 1 test, 0 échec. Cela confirme compilation/test Maven, pas la disponibilité d'un endpoint en exécution réelle.


## 4. Reproduction locale et pièces à joindre
Dans ce dossier : `mvn clean verify` puis exécuter successivement `GenererMessageXml`, `LireMessageXml` et `GenererSchema` depuis IntelliJ. Contrôler le XML et le XSD produits, ainsi que les sources de `target/generated-sources/jaxb`. Pour le compte rendu définitif, joindre les **vraies** captures des consoles et des fichiers générés. **État :** exécution des classes `main` en environnement étudiant non attestée dans le dépôt.

## 5. Analyse et conclusion
JAXB rend explicite le contrat entre modèle Java et document XML. `@XmlTransient` montre qu'une propriété non sérialisée n'est pas conservée par l'échange. XJC illustre le sens inverse : du contrat XSD vers les classes. La compilation et le test sont établis ; les captures d'exécution sont à compléter.
