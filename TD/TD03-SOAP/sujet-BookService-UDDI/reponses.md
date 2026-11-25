# TD3 — Énoncé complémentaire : réponses

## Exercice 1 — Échange BookService
**1.** Le **client** est l'application qui calcule le prix (par exemple en Java) ; le **fournisseur** est `BookService`, qui expose `calculatePrice(id, quantity)` ; **SOAP** définit le message et **HTTP** transporte la requête/réponse dans cet exercice.
**2.** HTTP assure la connexion et le transport des messages, avec statuts et en-têtes HTTP ; SOAP représente la requête métier `calculatePrice` et sa réponse en XML normalisé.
**3.** Oui. Les systèmes Java et PHP communiquent si leurs clients/serveurs sont compatibles avec les mêmes protocoles, espaces de noms, contrat WSDL et schémas XML.
**4.** Le client construit le message avec `id=15`, `quantity=3`, l'envoie en HTTP au service ; le serveur SOAP désérialise les paramètres, appelle la méthode métier, sérialise un résultat de type `double` dans un message SOAP, puis le client interprète la réponse. Le **prix total numérique** est indéterminable sans connaître le prix unitaire du livre 15.

## Exercice 2 — Analyse des messages SOAP
**Partie A :**
1. `Envelope` délimite le message ; `Body` porte la requête (pas de `Header` ici).
2. `getBook` est l'opération, `bookId=25` son argument. Les tags du PDF sont des extraits **simplifiés**, dépourvus de déclarations de namespace : un message SOAP réel doit définir un namespace pour son enveloppe.
3. Dans la réponse, `Body` contient `getBookResponse`, puis `title=Programmation Java` et `price=60.000`, représentant l'objet retourné.
4. Le service n'exige aucune authentification ni métadonnée d'en-tête ; `Header` est **optionnel**.

**Partie B :**
1. Voir `exemples/getBook-avec-authentification.xml` : l'élément `authentification` contenant `login` et `password` est dans le `Header`, et `getBook/bookId=87` dans le `Body`.
2. `Envelope` encadre le message, `Header` porte les données de traitement/authentification, et `Body` demande l'opération. Les préfixes XML délimitent les namespaces.
3. Un texte **non convertible en entier** échoue lors de la validation ou désérialisation du type attendu (`xs:int`) ; le service peut renvoyer une faute SOAP (souvent erreur client). Un texte numérique tel que `"87"` est, en revanche, la représentation XML **normale** d'un entier valide.

> Note : le mot de passe de l'exercice est fictif et ne constitue **pas** une recommandation de transmettre des mots de passe en clair dans SOAP. Pour une vraie application, utiliser HTTPS et un mécanisme d'authentification conçu pour la production.

## Exercice 3 — Contrat WSDL BookService
1. Nom : `BookService`.
2. Une opération est décrite dans **l'extrait WSDL fourni**.
3. Opération `getBook`, paramètre entier `id : xs:int` (à ne pas confondre avec `bookId` dans l'exercice 2).
4. Messages `BookInput` et `BookOutput`, associés à `tns:getBook` et `tns:getBookResponse`.
5. `types` définit les schémas d'entrée (`getBook/id`) et de sortie (`getBookResponse/title` et `price`).
6. L'élément `binding` avec `soap:binding transport="http://schemas.xmlsoap.org/soap/http"` précise SOAP sur HTTP.
7. Adresse de l'exemple : `http://example.com/books` (adresse pédagogique, pas un service nécessairement disponible).
8. Non : les opérations abstraites du `portType` et les messages peuvent rester identiques. Il faut définir un **nouveau binding** de transport/protocole et éventuellement un nouveau port/adresse.

## Exercice 4 — Annuaire UDDI TravelPlus
1. Entreprise : **TravelPlus**.
2. Service : **TravelService**, recherche et réservation d'hôtels / consultation de disponibilité.
3. Point d'accès déclaré : `http://travelplus.tn/TravelService`.
4. `businessKey=B001` identifie l'entreprise ; `serviceKey=S001` identifie le service ; `bindingKey=BT001` identifie un accès ou une liaison technique à ce service.
5. `accessPoint` donne l'adresse technique à laquelle les clients adressent leurs requêtes.
6. Le `tModel` (`T001`, `TravelServiceSpecification`) référence une description technique réutilisable du service, notamment `TravelService.wsdl` et la technologie SOAP/HTTP.
7. **Pages blanches (white pages)** = identité et coordonnées ; **pages jaunes (yellow pages)** = catégories et domaines d'activité ; **pages vertes (green pages)** = informations d'invocation technique et bindings.
8. Consulter **`TravelService.wsdl`** : opérations, paramètres, types XML, messages, binding et emplacement du service.
9. Rechercher TravelPlus ou TravelService dans UDDI → consulter `businessService` et `bindingTemplate` → trouver `accessPoint` et `tModel` → récupérer/analyser le WSDL → générer ou configurer un client SOAP → construire l'appel → envoyer la requête et lire réponse/fautes.
10. Mettre à jour ou vérifier `bindingTemplate/accessPoint` dans UDDI et l'élément `soap:address` correspondant dans le WSDL déployé ; le contrat abstrait (`portType`, messages, types) ne change pas si seules les URL changent. Les clients configurés avec l'ancienne URL doivent aussi être informés.
