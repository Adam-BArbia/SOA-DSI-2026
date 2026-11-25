package edu.iset.soa.tp4;

import edu.iset.soa.tp4.generated.BanqueService;
import edu.iset.soa.tp4.generated.Compte;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;
import java.util.List;

/** Service local fixture : indépendant de TP3. */
@WebService(endpointInterface = "edu.iset.soa.tp4.generated.BanqueService", name = "BanqueService",
  serviceName = "BanqueServiceImplService", portName = "BanqueServiceImplPort",
  targetNamespace = "http://service.tp4.soa.iset.edu/")
public class LocalBanqueFixture implements BanqueService {
    public static void main(String[] args) {
        String address = "http://localhost:8081/BanqueWS";
        Endpoint.publish(address, new LocalBanqueFixture());
        System.out.println("SOAP fixture disponible : " + address + "?wsdl");
    }
    @Override public double conversionEuroToDinar(double montant) { return montant * 3.5; }
    @Override public Compte getCompte(long code) {
        Compte c = new Compte(); c.setCode(code); c.setSolde(2000.0); return c;
    }
    @Override public List<Compte> getComptes() {
        return List.of(getCompte(1), getCompte(2), getCompte(3));
    }
}
