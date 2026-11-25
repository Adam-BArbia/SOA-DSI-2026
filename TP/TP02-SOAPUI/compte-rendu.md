# Compte rendu — TP2 : Consommation SOAP avec SoapUI

## Objectif et travail demandé
Importer les WSDL des services Calculator et CountryInfoService, comprendre les contrats SOAP, préparer des requêtes XML puis interpréter leurs réponses avec SoapUI. Énoncé : [TP2.pdf](TP2.pdf).

## Réalisation — Calculator
WSDL du sujet : `http://www.dneonline.com/calculator.asmx?WSDL`. Les quatre opérations identifiées sont `Add`, `Divide`, `Multiply`, `Subtract`. Le contrat distingue `types` (schéma des données), `message` (messages), `portType` (opérations), `binding` (protocole) et `service` (point d'accès).

La requête [calculator-add.xml](requetes/calculator-add.xml) utilise SOAP 1.1 (`http://schemas.xmlsoap.org/soap/envelope/`) et le namespace métier `http://tempuri.org/`. Le `Body` contient `Add`, `intA=15`, `intB=35`. La valeur mathématique **attendue** de `AddResult` est **50**. Le fichier [calculator-add-attendue.xml](reponses/calculator-add-attendue.xml) est **illustratif**, pas une réponse capturée.

## Réalisation — CountryInfoService
WSDL du sujet : `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL` ; namespace `http://www.oorsprong.org/websamples.countryinfo`. Service `CountryInfoService` ; ports SOAP 1.1 (`CountryInfoServiceSoap`) et SOAP 1.2 (`CountryInfoServiceSoap12`).

### Inventaire des opérations
Une copie **historique** du contrat, conservée dans un export de projet SoapUI tiers, contient **21 opérations**. Cet inventaire de référence n'est pas la preuve que le serveur public a répondu le 25/11/2026.

| Groupe | Opérations du contrat de référence |
| --- | --- |
| Continents | `ListOfContinentsByName`, `ListOfContinentsByCode` |
| Devises | `ListOfCurrenciesByName`, `ListOfCurrenciesByCode`, `CurrencyName`, `CountryCurrency`, `CountriesUsingCurrency` |
| Pays | `ListOfCountryNamesByCode`, `ListOfCountryNamesByName`, `ListOfCountryNamesGroupedByContinent`, `CountryName`, `CountryISOCode`, `CapitalCity`, `CountryFlag`, `CountryIntPhoneCode`, `FullCountryInfo`, `FullCountryInfoAllCountries` |
| Langues | `ListOfLanguagesByName`, `ListOfLanguagesByCode`, `LanguageName`, `LanguageISOCode` |

Référence contractuelle historique (export SoapUI public, **source externe**, non généré ici) : [CountryInfoWebService-soapui-project.xml](https://github.com/pskreddy9999/SoapUi/blob/master/CountryInfoWebService-soapui-project.xml). La liste exacte est à comparer au WSDL fourni par le serveur lors d'un éventuel nouvel import.

La requête [country-full-info.xml](requetes/country-full-info.xml) soumet `FullCountryInfo` avec `sCountryISOCode=TN`. La structure attendue est `FullCountryInfoResponse/FullCountryInfoResult` de type `tCountryInfo`, comprenant `sISOCode`, `sName`, `sCapitalCity`, `sPhoneCode`, `sContinentCode`, `sCurrencyISOCode`, `sCountryFlag` et `Languages` (liste de `tLanguage`). Les valeurs réelles ne sont **pas** attestées par une réponse distante réussie.

## Vérification et résultats
Des requêtes SOAP **HTTP automatisées, hors SoapUI**, ont été tentées vers les deux services publics depuis GitHub Actions. Les deux connexions ont été réinitialisées (`Connection reset by peer`) : **indisponibilité / absence de preuve de réponse**, non un succès applicatif. Voir [l'exécution distante](https://github.com/Adam-BArbia/SOA-DSI-2026/actions/runs/45592227117) et les [observations](../../docs/resultats-verification-distante.md). Aucun export de projet SoapUI ni capture graphique réelle n'est encore établi.

Pour une démonstration dans SoapUI : **File → New SOAP Project**, importer chaque WSDL, remplacer les corps de requêtes par les fichiers fournis et cliquer sur **Submit** ; conserver le XML et le statut authentiques de chaque réponse (ou erreur). Ne pas présenter le résultat attendu ou une réponse illustrative comme une réponse fournisseur.

## Conclusion
Les contrats et enveloppes demandés sont analysés et les requêtes sont préparées. Les réponses des fournisseurs publics et la manipulation graphique SoapUI restent non vérifiées.
