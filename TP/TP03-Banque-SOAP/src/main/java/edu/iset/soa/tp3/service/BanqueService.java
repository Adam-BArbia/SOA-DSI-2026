package edu.iset.soa.tp3.service;

import edu.iset.soa.tp3.entity.Compte;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService(name = "BanqueService")
public interface BanqueService {
    @WebMethod(operationName = "conversionEuroToDinar")
    double conversionEuroToDinar(@WebParam(name = "montant") double montant);
    @WebMethod(operationName = "getCompte")
    Compte getCompte(@WebParam(name = "code") long code);
    @WebMethod(operationName = "getComptes")
    List<Compte> getComptes();
}
