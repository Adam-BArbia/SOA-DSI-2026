# TP1 — JAXB, XML / XSD (JDK 23)

> **Compte rendu du TP :** [consulter le document de réalisation, les tests CI et les observations](compte-rendu.md). Les captures d'une manipulation réelle restent à fournir lorsqu'elles ne figurent pas dans le dépôt.

**Sujet :** [TP1.pdf](TP1.pdf). Projet Maven autonome.

1. Configurer **JDK 23** et Maven 3.9+ dans IntelliJ IDEA.
2. `mvn clean verify` compile et teste le mapping JAXB.
3. Exécuter `edu.iset.soa.tp1.GenererMessageXml` pour générer `message.xml`.
4. Exécuter `edu.iset.soa.tp1.LireMessageXml` pour reconstituer et afficher l'objet Java.
5. Exécuter `edu.iset.soa.tp1.GenererSchema` pour produire `message.xsd` à partir de l'entité.
6. `mvn clean generate-sources` lance l'outil XJC sur le schéma inclus dans `src/main/xsd/message.xsd` et génère les classes dans `target/generated-sources/jaxb` (package séparé `edu.iset.soa.tp1.generated`).

**Indépendance :** ni TP2 ni un autre dossier ne sont nécessaires. Les exemples sont conservés dans `exemples/`. Les fichiers de sortie `message.xml` et `message.xsd` sont générés à la racine de ce TP lorsque les classes sont exécutées depuis ce répertoire.
