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

La requête [country-full-info.xml](requetes/country-full-info.xml) soumet `FullCountryInfo` avec `sCountryISOCode=TN`. La structure attendue est `FullCountryInfoResponse/FullCountryInfoResult` de type `tCountryInfo`, comprenant `sISOCode`, `sName`, `sCapitalCity`, `sPhoneCode`, `sContinentCode`, `sCurrencyISOCode`, `sCountryFlag` et `Languages` (liste de `tLanguage`). Les valeurs ci-dessous ont ensuite été confirmées lors de la session manuelle locale SoapUI.

## Vérification et résultats

### Exécution automatisée distante (GitHub Actions)
Les requêtes vers les deux fournisseurs publics ont été tentées en CI, mais les connexions ont été réinitialisées (`Connection reset by peer`). Ce résultat est **non concluant** pour les fournisseurs dans l'environnement du runner, et ne constitue pas une réponse SOAP réussie. Voir les [résultats distants](../../docs/resultats-verification-distante.md).

### Vérification manuelle locale (SoapUI 5.10.1, Windows)
Les WSDL ont été importés et les deux opérations suivantes ont été exécutées contre les services publics :

| Service | Requête exécutée | Résultat SOAP observé |
| --- | --- | --- |
| Calculator | `Add(intA=15, intB=35)` | `AddResult=50` |
| CountryInfoService | `FullCountryInfo(sCountryISOCode=TN)` | Tunisie, capitale Tunis, indicatif 216, devise TND et langue Arabic |

Les réponses proviennent des appels effectués dans SoapUI, et non des exemples illustratifs de `reponses/`. Les projets SoapUI exportés permettent de retrouver les requêtes.

**Preuves locales :** [Calculator — réponse](preuves/TP02-Calculator-Add-Success.png), [Calculator — projet SoapUI](preuves/TP02-Calculator-soapui-project.xml), [CountryInfoService — réponse](preuves/TP02-CountryInfoService-Success.png) et [CountryInfoService — projet SoapUI](preuves/TP02-CountryInfoService-soapui-project.xml).

Ces résultats locaux complètent sans remplacer les observations CI. Les autres opérations WSDL sont inventoriées, mais n'ont pas toutes été exécutées manuellement.

## Conclusion
L'import des contrats WSDL, la préparation des enveloppes SOAP et l'exécution manuelle des opérations `Add` et `FullCountryInfo` ont été réalisés, avec réponses authentiques et preuves exportées.
