# Compte rendu — TP4 : Client SOAP Apache CXF

## Objectif
Générer des classes clientes depuis un WSDL et consommer un service SOAP à partir d'un programme Java.

## Environnement
Java 23, Maven, Apache CXF. Sujet : [TP4.pdf](TP4.pdf).

## Réalisation
1. Le contrat [banque.wsdl](src/main/resources/wsdl/banque.wsdl) décrit les opérations `getCompte`, `getComptes` et `conversionEuroToDinar`.
2. Le plugin `cxf-codegen-plugin` exécute `wsdl2java` pendant `generate-sources` et place les stubs dans `target/generated-sources/cxf`.
3. `LocalBanqueFixture` publie un service local de démonstration sur `http://localhost:8081/BanqueWS`.
4. `ClientMain` crée le proxy généré, configure son endpoint et invoque les trois méthodes.

## Autonomie du projet
Le sujet de cours s'appuie sur le service bancaire du TP3. Pour permettre une exécution totalement indépendante, ce dossier contient son **propre WSDL et son propre fournisseur local**. Ce contrat pédagogique ne doit pas être présenté comme une extraction du WSDL du TP3.

## Vérification
Le workflow GitHub Actions du 25/11/2026 confirme la génération CXF et la compilation Maven. Aucun test JUnit n'est défini pour ce module.

Démonstration locale : exécuter `mvn clean compile`, puis lancer `LocalBanqueFixture` dans une console et `ClientMain` dans une autre (commandes détaillées dans [README.md](README.md)). Le client affiche le compte, la taille de la collection et la conversion (résultat attendu pour 100 EUR : 350.0).

## Conclusion
Le projet montre la génération de stubs et leur utilisation dans un client Java. Une sortie console du client connecté au serveur local constitue la preuve pratique la plus simple.
