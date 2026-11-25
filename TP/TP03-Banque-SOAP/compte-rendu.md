# Compte rendu — TP3 : Service Web SOAP bancaire

## Objectif et travail demandé
Implémenter et publier un service SOAP JAX-WS, examiner le WSDL généré et tester la conversion et les opérations de compte. Énoncé : [TP3.pdf](TP3.pdf).

## Réalisation
- `Compte` : `code` (`long`), `solde` (`double`) et `dateCreation` (`java.util.Date`), avec constructeur sans argument et accesseurs JAXB.
- `BanqueService` : `conversionEuroToDinar(double montant)`, `getCompte(long code)`, `getComptes()`.
- `BanqueServiceImpl` : taux pédagogique fixe **3,5**, compte construit à partir du code et liste de trois comptes exemples.
- `BanqueServer` : publication sur `http://localhost:8080/BanqueWS` ; WSDL à `?wsdl`.

## Analyse du WSDL généré (question 8)
Le serveur Metro/JAX-WS lancé par GitHub Actions a **réellement servi** son WSDL en HTTP 200 ; le workflow a sauvegardé `wsdl-generated.xml`. Les caractéristiques observées sont :

| Élément | Valeur |
| --- | --- |
| `definitions/@targetNamespace` | `http://service.tp3.soa.iset.edu/` |
| `service` | `BanqueService` |
| `port` | `BanqueServiceImplPort` |
| `portType` | `BanqueService` |
| `binding` | `BanqueServiceImplPortBinding` |
| Style et transport | SOAP/HTTP, **document/literal** |
| Opérations | `conversionEuroToDinar`, `getCompte`, `getComptes` |

Les méthodes de l'interface Java déterminent les opérations du `portType` ; les `@WebParam` définissent les champs `montant` (`double`) et `code` (`long`) dans les requêtes. `getComptes` n'a pas de paramètre. Les réponses sont des structures JAXB : `conversionEuroToDinarResponse` contient un `return` numérique ; `getCompteResponse` contient le compte ; `getComptesResponse` contient des éléments `return` répétés (un par compte). Pour les types XSD exacts et leurs imports, consulter les fichiers XML générés dans [l'artifact de TP3](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117/artifacts/15010509898). Le code `Compte` correspond aux champs `code`, `solde` et `dateCreation` sérialisés en XML. Une extraction automatique détaillée des schémas est également prévue par le [script de vérification](../../tools/practical_http.py) afin de documenter les namespaces et les occurrences depuis le WSDL réel.

## Résultats observés (question 9)
Les trois opérations ont reçu de **véritables réponses SOAP sur HTTP 200** depuis un serveur démarré dans GitHub Actions :

| Requête | Résultat observé |
| --- | --- |
| `conversionEuroToDinar(100)` | `return = 350.0` |
| `getCompte(1)` | Un compte, solde calculé à partir du code (1100.0 dans l'implémentation) |
| `getComptes()` | 3 éléments `return` : codes 1, 2, 3 ; soldes 2000.0, 3500.0, 8700.0 |

Exemple de **structure de requête** (non capture de SoapUI) :

```xml
<soapenv:Body xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
              xmlns:tns="http://service.tp3.soa.iset.edu/">
  <tns:conversionEuroToDinar><montant>100</montant></tns:conversionEuroToDinar>
</soapenv:Body>
```

Preuve : [workflow d'intégration réussi](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117), fichiers `wsdl-generated.xml` et `soap-*.xml` dans l'artifact TP3. Environnement **runner Linux distant**, pas SoapUI ni serveur du poste étudiant.

## Vérification graphique facultative
Démarrer `mvn exec:java`, ouvrir le WSDL local dans SoapUI, puis exécuter les trois opérations pour produire une capture de l'interface. Cette étape n'a pas été observée automatiquement.

## Conclusion
La publication du WSDL et les trois échanges SOAP de bout en bout sont confirmés par CI. Seule la démonstration spécifique dans SoapUI reste à réaliser si exigée par l'enseignante.
