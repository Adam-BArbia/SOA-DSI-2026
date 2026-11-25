package edu.iset.soa.tp5.jersey;
import edu.iset.soa.tp5.jersey.entity.*;
import edu.iset.soa.tp5.jersey.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ActivityRepositoryTest {
  @Autowired ActivityRepository activities;
  @Autowired CategoryRepository categories;
  @Test void findByCategoryAndStatus() {
    Category c = new Category(); c.setName("Sport"); c=categories.save(c);
    Activity a = new Activity(); a.setName("Football"); a.setCategory(c); a.setStatus(ActivityStatus.OPEN); activities.save(a);
    assertEquals(1, activities.findByCategoryId(c.getId()).size());
    assertEquals(1, activities.findByStatus(ActivityStatus.OPEN).size());
  }
}
