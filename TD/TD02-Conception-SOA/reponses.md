# TD2 — Réponses détaillées

## Exercice 1 — Application de facturation
**1.** Il s'agit d'un programme **monolithique fortement couplé** : modification des remises qui casse les paiements, duplication pour le mobile, responsabilités mélangées et effets de bord difficiles à anticiper. Le manque de modules/contrats séparés gêne la maintenance et la réutilisation.

**2. Évolution progressive :**
- **Procédural** : répartir les traitements dans `saisirClient()`, `calculerRemise()`, `encaisserPaiement()` ; réduit la répétition et clarifie les fonctions, sans supprimer les dépendances sur les données.
- **Objet** : créer `Client`, `Facture`, `Paiement` et leurs services ; encapsule les états et les comportements.
- **Composant** : regrouper les classes en modules `Facturation`, `Paiement`, `Reporting` avec interfaces explicites ; rend les composants testables et remplaçables.
- **Service (SOA)** : exposer des opérations métiers autonomes par contrat (REST/SOAP), réutilisables par Web, mobile et partenaires indépendamment de leur langage.

**3.** Un service par instruction créerait un système trop fragmenté (appels réseau nombreux, coût, latence, coordination difficile). Regrouper des responsabilités **cohérentes** dans quelques services à granularité métier : `ServiceClients`, `ServiceFacturation`, `ServicePaiements`, `ServiceReporting` ; des services applicatifs peuvent les orchestrer.

**4.** Situation **A = B2C** (l'entreprise fournit directement aux particuliers consultation/paiement de factures). Situation **B = B2B** (échange automatisé avec entreprises partenaires).

## Exercice 2 — Agence de voyages et compagnie aérienne
**1.** Hétérogénéité de technologie/langage, de formats de dates (`JJ/MM/AAAA` vs `AAAA-MM-JJ`) et de modèle de données (`nom/prénom/numéroPasseport` vs `identitéClient/documentVoyage`). Chaque système fonctionne isolément mais ne partage pas de contrat compréhensible.

**2.** Modifier les applications internes des deux entreprises coûte cher et augmente leur couplage. Une **interface commune + adaptateurs** permet de transformer les formats et d'encapsuler les différences sans changer les règles métier internes.

**3.** Architecture : consommateur de réservation → façade d'intégration utilisant un contrat standard (REST/JSON ou SOAP/XML) → adaptateur de date et de données → API/logiciel de la compagnie. Un schéma/version de contrat documenté assure l'interopérabilité.

**4.** **Non** : si le contrat externe reste stable et indépendant du langage, le nouveau client Python consomme le même service.

**5a.** Un unique `GestionAgence` serait une interface trop grosse et peu maintenable ; un service par opération élémentaire ferait exploser le nombre d'appels. Regrouper les responsabilités cohérentes.

**5b. Classement :**

| Fonctionnalité | Type de service |
| --- | --- |
| Gestion des clients (CRUD) | **Service entité** |
| Vérification de disponibilité d'un vol | **Service fonctionnel** |
| Calcul du prix total | **Service fonctionnel** |
| Confirmation par e-mail | **Service transverse / infrastructure** |
| Coordination de la réservation entière | **Service applicatif** |

**5c.** Une API de `ServiceReservation` orchestre `ServiceClients`, `ServiceDisponibilite`, `ServiceTarification`, `ServiceConfirmation`. Le site Web et l'application mobile consomment **le même contrat**, sans recopier les règles métier.

## Exercice 3 — Contrats, orchestration et réutilisation
**1.** Les nouveaux arguments obligatoires (`typeClient`, `modeTransport`) et la nouvelle structure de réponse cassent les clients anciens : **changement de contrat non rétrocompatible**. Garder `v1` disponible ; proposer `v2`, ou des paramètres optionnels avec valeurs par défaut si la sémantique le permet. Documenter, tester et planifier une migration progressive.

**2. Enchaînement :**

```mermaid
flowchart LR
  C[ServiceCommande] --> S[ServiceStock]
  S --> P[ServicePaiement]
  P --> L[ServiceLivraison]
  L --> N[ServiceNotification]
```

**3.** Un **orchestrateur applicatif** contrôle l'ordre, les conditions et les erreurs. Chaque service connaît son propre contrat et l'orchestrateur plutôt que tous les autres ; en cas d'échec de paiement, il peut libérer la réservation de stock, et ne déclenche pas la livraison.

**4.** Conditions favorisant la réutilisation de `ServiceNotification` : **contrat standard indépendant du cas d'usage** (événement, destinataire, modèle, contenu) et **faible couplage** vis-à-vis des services qui l'appellent (pas d'accès direct aux classes internes de Commande/Livraison). Gérer aussi les erreurs d'envoi de façon prévisible.
