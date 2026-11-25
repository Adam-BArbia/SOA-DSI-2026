# TD1 — Réponses question par question

## Exercice 1 — Structure d'un étudiant
1. **Racine :** `<etudiant>`.
2. **Éléments :** `etudiant`, `nom`, `prenom`, `age`, `email`, `formation`, `specialite`, `annee`. **Attribut :** `niveau="Ingenieur"` porté par `formation`.
3. Le document possède **un seul élément `<formation>`**.
4. L'attribut `niveau` appartient à **`formation`**, et non à `etudiant`.
5. **Oui, bien formé** : racine unique, balises correctement imbriquées et fermées, attribut cité entre guillemets. Cela n'implique pas automatiquement la validité contre une DTD/XSD.
6. Arbre :

   ```text
   etudiant
   ├── nom
   ├── prenom
   ├── age
   ├── email
   └── formation [@niveau="Ingenieur"]
       ├── specialite
       └── annee
   ```

## Exercice 2 — Correction du XML mal formé
1. **Non**, ce document n'est pas bien formé.
2. Erreurs : `<telephone>` n'est pas fermé ; `niveau=Ingenieur` omet les guillemets ; `<specialite>` est fermé par `</formation>` ; cette fermeture prématurée entraîne une fermeture de `formation` en trop et une imbrication incorrecte.
3. Après correction : racine `etudiant`, enfants directs `nom`, `prenom`, `contact`, `formation`, `email`. L'unique attribut est `formation/@niveau`. Voir [`exemples/exercice2-corrige.xml`](exemples/exercice2-corrige.xml), dont le contenu reste proche du sujet.

## Exercice 3 — DTD universitaire
1. **Oui, le document XML est bien formé** : les balises sont correctement imbriquées.
2. **Non, il n'est pas valide contre la DTD fournie.** Trois violations apparaissent dans le PDF :
   - L'étudiant `E003` met `formation` **avant** `email`, contrairement à `(nom, prenom, email, formation)`.
   - La valeur de l'attribut `id="E002"` est **dupliquée** (DTD : `ID`, donc unique).
   - Le niveau `Doctorat` du dernier étudiant est **interdit** par `(Licence|Ingenieur|Master)`.
   La version corrigée est dans `exemples/universite-corrige.xml`.
3. **Bien formé** = respecte la syntaxe XML ; **valide** = bien formé et respecte les contraintes de la DTD/XSD, dont types, ordre, cardinalité et attributs.
4. `niveau="Doctorat"` est bien formé, mais **non valide** pour la DTD actuelle.
5. Inverser `nom` et `prenom` conserve la bonne formation syntaxique mais **viole l'ordre** imposé par la DTD.
6. Supprimer `email` produit également un document bien formé, mais **non valide** car cet élément est obligatoire.
7. Modifier la déclaration en `<!ELEMENT etudiant (nom, prenom, email?, formation)>` : `?` autorise zéro ou une occurrence.

> **Précision sur le document « TD1 Correction » :** le PDF de correction affirme que l'exercice 3 est valide. Cette conclusion ne correspond pas au document **effectivement présent** dans l'énoncé fourni, qui contient les trois violations ci-dessus. Nous ne modifions pas la correction originale.

## Exercice 4 — Ordre des éléments et déclaration XML
1. **Tel que transcrit dans le PDF d'énoncé**, la déclaration `<? xml ... ?>` est incorrecte : un espace est interdit entre `<?` et `xml`. Il faut `<?xml ... ?>`. Avec cette correction, le reste est bien formé.
2. Même une fois la déclaration corrigée, le document **n'est pas valide** : `formation` contient `annee` avant `specialite`, et `email` vient après `formation` alors que la DTD impose `email` avant `formation`.
3. Voir `exemples/exercice4-corrige.xml` : il rétablit la déclaration XML, place `email` au bon endroit et inverse les enfants de `formation`.

## Exercice 5 — Schéma XML d'un étudiant
Voir [`exemples/etudiant.xsd`](exemples/etudiant.xsd). Il impose l'ordre `id, nom, prenom, age, email, formation, annee`, l'âge **18–60** et l'année **1–5** ; `formation/@niveau` est obligatoire et limité à `Licence`, `Ingenieur`, `Master`. Un exemple de validation se trouve dans `exemples/etudiant-valide.xml`.

## Exercice 6 — Schéma d'évènement
1. Racine : `<evenement>`.
2. **Cinq** éléments obligatoires : `id`, `titre`, `date`, `lieu`, `participants`.
3. `date` a le type **`xs:date`**, par exemple `2026-10-15`.
4. `participants` a le type **`xs:integer`**.
5. Voir [`exemples/evenement.xml`](exemples/evenement.xml) ; la date suit le format ISO requis.

## Exercice 7 — Échange entre applications
1. Voir [`exemples/etudiant-echange.xml`](exemples/etudiant-echange.xml) contenant `id`, `nom`, `prenom`, `email`, puis `formation` avec `code` et `libelle`.
2. Une structure commune donne la même signification et organisation aux champs des deux systèmes.
3. Un XSD définit un contrat validable (types, présence, ordre, valeurs autorisées) et détecte les messages incompatibles.
4. Si `id` doit être entier mais vaut `ABC12`, la validation/conversion échoue et le consommateur doit rejeter ou signaler le message.
5. Non. XML sert aussi à **échanger**, **structurer** et parfois transformer des données (par exemple avec XSLT).
6. XML est textuel, standardisé, extensible, auto-descriptif et indépendant du langage et de la plateforme.
7. **SOAP** utilise XML comme format central de son enveloppe et de ses messages.
