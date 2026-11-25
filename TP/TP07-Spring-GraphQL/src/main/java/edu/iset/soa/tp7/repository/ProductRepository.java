package edu.iset.soa.tp7.repository;
import edu.iset.soa.tp7.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, String> {
  List<Product> findByCategoryId(Long categoryId);
}
