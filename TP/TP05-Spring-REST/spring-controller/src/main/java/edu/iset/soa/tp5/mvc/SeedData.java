package edu.iset.soa.tp5.mvc;
import edu.iset.soa.tp5.mvc.entity.*;
import edu.iset.soa.tp5.mvc.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
@Configuration
public class SeedData {
  @Bean CommandLineRunner initDatabase(CategoryRepository categories, ActivityRepository activities) {
    return args -> {
      if (categories.count() > 0) return;
      Category sport = new Category(); sport.setName("Sport"); sport.setDescription("Activités sportives"); sport = categories.save(sport);
      Category culture = new Category(); culture.setName("Culture"); culture.setDescription("Ateliers culturels"); culture = categories.save(culture);
      Activity football = new Activity(); football.setName("Football"); football.setDescription("Match de football");
      football.setLocation("Tunis"); football.setLocalDate(LocalDate.of(2026,12,10));
      football.setCapacity(20); football.setPrice(15.0); football.setStatus(ActivityStatus.OPEN); football.setCategory(sport); activities.save(football);
      Activity theatre = new Activity(); theatre.setName("Théâtre"); theatre.setDescription("Atelier théâtre");
      theatre.setLocation("Sousse"); theatre.setLocalDate(LocalDate.of(2026,12,12));
      theatre.setCapacity(35); theatre.setPrice(25.0); theatre.setStatus(ActivityStatus.OPEN); theatre.setCategory(culture); activities.save(theatre);
    };
  }
}
