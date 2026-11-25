# Suivi des livrables et vérification

**Documents complémentaires :** [grille de conformité](grille-conformite.md) · [registre des preuves CI](preuves-validation.md). Les sept rapports pratiques sont dans chaque TP sous `compte-rendu.md`.

Cette matrice sépare **livré dans le dépôt** et **testé en exécution réelle**. Une capture, un log ou un rapport ne doit jamais être fabriqué.

| Sujet | Énoncé PDF | Réponses/code | Vérification automatisée (CI) | Validation manuelle requise |
| --- | --- | --- | --- | --- |
| TD1–TD5 (6 sujets) | Oui | Oui | XML/graphèmes et vérifications statiques ciblées | Lecture de toutes les réponses |
| TP1 | Oui | Oui | Contrôle structurel | Maven + JAXB + XJC sur JDK 23 |
| TP2 | Oui | Oui | Fichiers XML bien formés | SoapUI + services distants |
| TP3 | Oui | Oui | Contrôle structurel | Maven + SoapUI sur JDK 23 |
| TP4 | Oui | Oui | Contrôle WSDL/XML | Maven + génération CXF + service local |
| TP5 | Oui | Oui | Contrôle structurel | Maven + H2 et requêtes HTTP des 3 variantes |
| TP6 | Oui | Oui | Contrôle structurel | Maven + API key OpenWeatherMap pour le direct |
| TP7 | Oui | Oui | Schéma GraphQL/XML statique | Maven + MySQL + GraphiQL |

## Checklist de remise
- [x] Builds Maven et tests existants exécutés en CI sur les 8 projets (voir [preuve du run](preuves-validation.md)) ; [ ] les reproduire au besoin sur le poste local
- [ ] Suivre les README de chaque TP et enregistrer les captures réelles nécessaires
- [ ] Vérifier les résultats SOAPUI contre les WSDL actuels
- [ ] Vérifier GraphiQL sur `localhost:8090/graphiql?path=/graphql`
- [ ] Vérifier les paramètres MySQL local et la clé OpenWeatherMap (jamais commiter un secret)
- [ ] Contrôler la conformité et l'autorisation de redistribution des PDF sur le dépôt public
