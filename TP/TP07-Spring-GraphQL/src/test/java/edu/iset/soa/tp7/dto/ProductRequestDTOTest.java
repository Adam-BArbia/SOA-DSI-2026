package edu.iset.soa.tp7.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ProductRequestDTOTest {
  @Test void recordFields() {
    var request = new ProductRequestDTO("PC",100.0,5,1L);
    assertEquals("PC",request.name()); assertEquals(5,request.quantity());
    assertEquals(1L,request.categoryId());
  }
}
