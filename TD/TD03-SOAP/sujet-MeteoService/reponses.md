# TD3 — WSDL du service météo : six réponses

**1. Où se trouve le service ?** Dans l'élément `<service name="MeteoService">`, la section `<port name="MeteoServicePort">` associe le binding au point d'accès `<soap:address location="http://www.mymeteo.com/ws/meteo"/>`. Il s'agit de l'adresse **décrite par le document**, sans supposer qu'elle est réellement disponible en ligne.

**2. Opérations décrites :** une opération : `releveMeteo`. Son entrée est le message `tns:releveMeteo`, dont la partie `partLieu` pointe vers l'élément XML `ns1:lieu` (avec `ville`). Sa sortie est le message `tns:releveMeteoResponse`, dont `partReleve` pointe vers `ns1:releve` (avec `temperature{valeur,unite}` et `lieu{ville}`).

**3. Parties du document :**
- `definitions` et les `xmlns` : noms des espaces XML et définition du service ; `tns` identifie le contrat, `ns1` ses types métier.
- `types` / `xs:schema` : types complexes `temperature`, `lieu`, `releve` et éléments globaux.
- `message` : entrées/sorties abstraites et leurs parties.
- `portType` : interface abstraite et opération.
- `binding` : implémentation de l'interface en SOAP sur HTTP, style `document` et corps `literal`.
- `service` / `port` : association du binding et de l'adresse réseau.
- `wsam:Action` : métadonnée de traitement/adressage des messages ; `soapAction` est vide dans le binding fourni.

**4. Rôle de SOAP :** protocole de messages XML avec règles d'enveloppe, corps, en-têtes facultatifs et fautes structurées. HTTP est ici le transport ; SOAP décrit la structure des messages indépendamment de la technologie des implémentations.

**5. Structure d'un message SOAP :** un `Envelope` contenant éventuellement un `Header`, puis un `Body` (données utiles et éventuellement `Fault`). Les espaces de noms différencient protocole et données métier.

**6. Exemple :** [`exemples/releveMeteo-request.xml`](exemples/releveMeteo-request.xml) envoie la ville `Tunis`, et [`exemples/releveMeteo-response.xml`](exemples/releveMeteo-response.xml) représente une réponse **illustrative et fictive** de `21.5 C`.

**Note sur l'énoncé :** la dernière balise imprimée est `</definition>` (singulier), alors que la racine ouverte est `<definitions>` ; le WSDL extrait du PDF contient aussi des retours de ligne au milieu d'URI. Le fichier `exemples/meteo.wsdl` est une reconstruction **corrigée pour l'illustration**, pas une copie à l'identique ni une preuve d'existence d'un serveur distant.
