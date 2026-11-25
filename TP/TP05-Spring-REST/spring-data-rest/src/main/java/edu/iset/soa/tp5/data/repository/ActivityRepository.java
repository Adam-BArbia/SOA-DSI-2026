package edu.iset.soa.tp5.data.repository;
import edu.iset.soa.tp5.data.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(path = "activities")
public interface ActivityRepository extends JpaRepository<Activity,Long> {
  List<Activity> findByStatus(ActivityStatus status);
  List<Activity> findByLocation(String location);
  List<Activity> findByCategory(Category category);
  List<Activity> findByCategoryId(Long categoryId);
  List<Activity> findByNameContainingIgnoreCase(String name);
}
