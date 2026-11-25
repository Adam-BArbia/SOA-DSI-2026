package edu.iset.soa.tp3.service;

import edu.iset.soa.tp3.entity.Compte;
import jakarta.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService(endpointInterface = "edu.iset.soa.tp3.service.BanqueService", serviceName = "BanqueService")
public class BanqueServiceImpl implements BanqueService {
    @Override public double conversionEuroToDinar(double montant) { return montant * 3.5; }
    @Override public Compte getCompte(long code) { return new Compte(code, 1000 + code * 100, new Date()); }
    @Override public List<Compte> getComptes() {
        List<Compte> comptes = new ArrayList<>();
        comptes.add(new Compte(1, 2000, new Date()));
        comptes.add(new Compte(2, 3500, new Date()));
        comptes.add(new Compte(3, 8700, new Date()));
        return comptes;
    }
}
