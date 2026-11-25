package edu.iset.soa.tp3.service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class BanqueServiceImplTest {
    private final BanqueService service = new BanqueServiceImpl();
    @Test void conversion() { assertEquals(350.0, service.conversionEuroToDinar(100), 0.001); }
    @Test void account() { assertEquals(1, service.getCompte(1).getCode()); }
    @Test void accounts() { assertEquals(3, service.getComptes().size()); }
}
