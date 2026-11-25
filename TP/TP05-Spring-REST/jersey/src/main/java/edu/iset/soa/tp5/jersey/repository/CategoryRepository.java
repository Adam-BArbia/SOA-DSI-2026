package edu.iset.soa.tp5.jersey.repository;
import edu.iset.soa.tp5.jersey.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category,Long> {
  Optional<Category> findByName(String name);
}
