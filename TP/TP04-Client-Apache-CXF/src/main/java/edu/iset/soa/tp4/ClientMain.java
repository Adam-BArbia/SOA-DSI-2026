package edu.iset.soa.tp4;

import edu.iset.soa.tp4.generated.BanqueServiceImplService;
import edu.iset.soa.tp4.generated.BanqueService;
import edu.iset.soa.tp4.generated.Compte;
import jakarta.xml.ws.BindingProvider;
import java.util.List;

public final class ClientMain {
    public static void main(String[] args) {
        BanqueServiceImplService service = new BanqueServiceImplService();
        BanqueService port = service.getBanqueServiceImplPort();
        ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8081/BanqueWS");
        Compte c = port.getCompte(1);
        System.out.println("Compte : code=" + c.getCode() + ", solde=" + c.getSolde());
        List<Compte> comptes = port.getComptes();
        System.out.println("Nombre de comptes : " + comptes.size());
        double resultat = port.conversionEuroToDinar(100.0);
        System.out.println("Résultat de conversion = " + resultat);
    }
}
