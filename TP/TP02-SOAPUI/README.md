# TP2 — Consommation d'un service Web avec SoapUI

**Énoncé :** [TP2.pdf](TP2.pdf). Ce TP est une **manipulation de SoapUI**, et non un projet Java. Les requêtes enregistrées sont directement copiables dans l'éditeur XML de SoapUI. Elles ne constituent pas une preuve de communication à distance.

## Partie 1 — Service calculatrice
1. Dans SoapUI, créer **File → New SOAP Project**, nommer `TP02-Calculator`, saisir le WSDL `http://www.dneonline.com/calculator.asmx?WSDL` et cocher la génération des requêtes exemples.
2. Repérer `Add`, `Divide`, `Multiply`, `Subtract` dans l'interface obtenue ; analyser `<types>`, `<message>`, `<portType>`, `<binding>`, `<service>` : structures XML, messages, opérations, protocole et adresse du service respectivement.
3. Dans l'opération `Add`, remplacer le corps par [`requetes/calculator-add.xml`](requetes/calculator-add.xml), dont `intA=15`, `intB=35` ; le résultat **mathématique attendu** est `50`.
4. `soapenv:Envelope` encadre la requête ; `soapenv:Header` (vide ici) est optionnel ; `soapenv:Body` contient `tem:Add`, son espace de noms métier et les arguments `tem:intA`, `tem:intB`.
5. `soapenv` correspond à `http://schemas.xmlsoap.org/soap/envelope/` et `tem` à `http://tempuri.org/` dans le WSDL de l'exemple.
6. Envoyer avec le bouton vert **Submit** ; analyser le XML de la réponse, notamment `AddResult`. [`reponses/calculator-add-attendue.xml`](reponses/calculator-add-attendue.xml) est une réponse **illustrative**, à confronter au résultat réel.

## Partie 2 — CountryInfoService
1. Créer un autre projet SoapUI, WSDL : `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL`.
2. Inspecter les opérations générées ; le [compte rendu](compte-rendu.md) présente les 21 opérations d'un contrat historique de référence (sans affirmer que le serveur a répondu lors du test du 25/11/2026).
3. Tester une requête de détail de pays avec `TN` via [`requetes/country-full-info.xml`](requetes/country-full-info.xml).
4. Comparer les éléments de `<Body>` dans la demande et la réponse et interpréter le nom, la capitale et le code ISO retournés.

## Preuves à fournir après exécution réelle
Si l'enseignante exige une démonstration spécifique dans SoapUI, insérer dans `captures/` les captures **réellement réalisées** : projet importé et réponse ou erreur authentique de chaque service. Un export XML du projet est facultatif tant qu'il n'est pas explicitement demandé. Aucune capture n'est fabriquée dans ce dépôt.

> Les services distants cités par le cours peuvent être inaccessibles ou changer. En cas d'erreur réseau, l'analyser comme un problème d'environnement distant et conserver les fichiers XML et le WSDL réellement consulté. Ne pas inventer une réponse d'un serveur hors ligne.
