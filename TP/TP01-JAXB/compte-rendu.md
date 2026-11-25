# Compte rendu — TP1 : JAXB, XML et XSD

## Objectif
Mettre en pratique la conversion entre objets Java et documents XML avec JAXB, puis la génération et l'exploitation d'un schéma XSD.

## Environnement
Java 23, Maven, Jakarta XML Binding. Le projet Maven est autonome ; le sujet original est [TP1.pdf](TP1.pdf).

## Réalisation
1. La classe `Message` définit les propriétés `from`, `to`, `text` et `isNew`. Les annotations JAXB représentent l'émetteur et le destinataire comme éléments XML, le texte comme attribut et excluent `isNew` de la sérialisation.
2. `GenererMessageXml` crée un objet puis écrit `message.xml` à l'aide d'un `Marshaller`.
3. `LireMessageXml` utilise un `Unmarshaller` pour reconstruire l'objet à partir du XML.
4. `GenererSchema` produit `message.xsd` avec `generateSchema`.
5. La phase Maven `generate-sources` applique XJC à `src/main/xsd/message.xsd`, dans un package distinct.

## Résultats et vérification
- Vérification reproductible : `mvn clean verify`.
- Résultat du workflow GitHub Actions du 25/11/2026 : compilation et test `MessageJaxbTest` réussis (1 test, 0 échec).
- Pour la démonstration sur poste local : lancer les trois classes dans l'ordre indiqué dans le [README](README.md), puis examiner les fichiers générés.
- Exemples de référence : [exemples/](exemples/). Les exemples ne remplacent pas des captures de la session.

## Observations
Le marshalling convertit un objet en XML ; l'unmarshalling réalise l'opération inverse. Une propriété `@XmlTransient` n'est pas reconstituée depuis le document XML. XSD formalise la structure échangeable.

## Conclusion
Les étapes principales du TP sont implémentées dans un projet indépendant. Le contrôle automatisé confirme la compilation et le test JAXB ; les sorties des programmes peuvent être présentées directement pendant la soutenance.
