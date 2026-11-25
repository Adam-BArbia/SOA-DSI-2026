package edu.iset.soa.tp5.data.repository;
import edu.iset.soa.tp5.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(path = "categories")
public interface CategoryRepository extends JpaRepository<Category,Long> {
  Optional<Category> findByName(String name);
}
