package edu.iset.soa.tp7.repository;
import edu.iset.soa.tp7.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category, Long> { }
