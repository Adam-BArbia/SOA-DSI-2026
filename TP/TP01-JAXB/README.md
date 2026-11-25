# TP1 — JAXB, XML / XSD (JDK 23)

**Sujet :** [TP1.pdf](TP1.pdf). Projet Maven autonome.

1. Configurer **JDK 23** et Maven 3.9+ dans IntelliJ IDEA.
2. `mvn clean verify` compile, teste le mapping JAXB et **exécute les trois programmes** avec le test d'intégration `ProgrammesJaxbIntegrationTest` ; le document XML est validé contre le XSD produit.
3. Exécuter `edu.iset.soa.tp1.GenererMessageXml` pour générer `message.xml`.
4. Exécuter `edu.iset.soa.tp1.LireMessageXml` pour reconstituer et afficher l'objet Java.
5. Exécuter `edu.iset.soa.tp1.GenererSchema` pour produire `message.xsd` à partir de l'entité.
6. `mvn clean generate-sources` lance l'outil XJC sur le schéma inclus dans `src/main/xsd/message.xsd` et génère les classes dans `target/generated-sources/jaxb` (package séparé `edu.iset.soa.tp1.generated`).

**Indépendance :** ni TP2 ni un autre dossier ne sont nécessaires. Les exemples sont conservés dans `exemples/`. Les fichiers de sortie `message.xml` et `message.xsd` sont générés à la racine de ce TP lorsque les classes ou tests sont exécutés depuis ce répertoire. Ils sont ignorés par Git. Le workflow pratique conserve les sorties réelles en artifact GitHub Actions, sans télécharger de plugin Maven d'exécution supplémentaire.
