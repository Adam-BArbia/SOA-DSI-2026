package edu.iset.soa.tp3;

import edu.iset.soa.tp3.service.BanqueServiceImpl;
import jakarta.xml.ws.Endpoint;

public final class BanqueServer {
    public static void main(String[] args) {
        String url = "http://localhost:8080/BanqueWS";
        Endpoint.publish(url, new BanqueServiceImpl());
        System.out.println("Web Service déployé sur : " + url);
        System.out.println("WSDL disponible sur : " + url + "?wsdl");
    }
}
