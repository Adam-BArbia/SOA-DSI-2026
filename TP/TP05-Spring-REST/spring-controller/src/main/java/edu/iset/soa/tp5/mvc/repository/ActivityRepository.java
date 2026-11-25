package edu.iset.soa.tp5.mvc.repository;
import edu.iset.soa.tp5.mvc.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ActivityRepository extends JpaRepository<Activity,Long> {
  List<Activity> findByStatus(ActivityStatus status);
  List<Activity> findByLocation(String location);
  List<Activity> findByCategory(Category category);
  List<Activity> findByCategoryId(Long categoryId);
  List<Activity> findByNameContainingIgnoreCase(String name);
}
