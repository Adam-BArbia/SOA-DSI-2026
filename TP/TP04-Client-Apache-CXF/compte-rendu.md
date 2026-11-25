# Compte rendu — TP4 : Client SOAP Apache CXF

## Objectif et travail demandé
Générer des stubs Java depuis un WSDL avec Apache CXF, démarrer un serveur et consommer les opérations depuis un client.

## Réalisation et explication
1. Le [WSDL autonome](src/main/resources/wsdl/banque.wsdl) définit `getCompte`, `getComptes` et `conversionEuroToDinar`.
2. Le plugin `cxf-codegen-plugin` exécute `wsdl2java` à la phase `generate-sources`. Les stubs sont générés dans `target/generated-sources/cxf`.
3. `LocalBanqueFixture` publie son service pédagogique sur `http://localhost:8081/BanqueWS`.
4. `ClientMain` instancie le client généré et invoque les trois méthodes.

Le WSDL et le serveur de ce TP sont **indépendants du TP3** : leur provenance ne doit pas être confondue avec le WSDL généré par Metro au TP3.

## Résultats et vérification
GitHub Actions a exécuté le serveur et le client dans le même runner Linux. Le WSDL du serveur a été accessible avec HTTP 200 ; le client a réellement affiché :

```text
CXF fixture WSDL served: HTTP 200
Compte : code=1, solde=2000.0
Nombre de comptes : 3
Résultat de conversion = 350.0
```

Ces quatre lignes sont issues des [logs d'exécution réussie](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117) (voir [artifact TP4](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117/artifacts/15011003836)). L'invocation cliente a donc été vérifiée **de bout en bout**, au-delà de la simple compilation.

Un premier essai avait révélé l'absence de `cxf-rt-transports-http-jetty` ; la dépendance a été ajoutée et l'exécution suivante a réussi. Aucune session graphique IntelliJ n'est prétendue.

## Reproduction
Exécuter `mvn clean compile`, démarrer `LocalBanqueFixture` avec `mvn exec:java -Dexec.mainClass=edu.iset.soa.tp4.LocalBanqueFixture`, puis lancer `ClientMain` dans une seconde console avec la même commande adaptée. Voir [README.md](README.md).

## Conclusion
La génération des stubs, la publication SOAP et la consommation réelle par le client Apache CXF sont vérifiées.
