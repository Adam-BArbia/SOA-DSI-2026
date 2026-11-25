package edu.iset.soa.tp7;
import edu.iset.soa.tp7.entities.Category;
import edu.iset.soa.tp7.entities.Product;
import edu.iset.soa.tp7.repository.CategoryRepository;
import edu.iset.soa.tp7.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;
import java.util.Random;
import java.util.UUID;
@SpringBootApplication
public class InventoryApplication {
  public static void main(String[] args) { SpringApplication.run(InventoryApplication.class, args); }
  @Bean CommandLineRunner sampleData(CategoryRepository categories, ProductRepository products) {
    return args -> {
      if (categories.count() != 0) return;
      Random random = new Random(42);
      for (String categoryName : List.of("Computer", "Printer", "Smartphone")) {
        Category category = new Category(); category.setName(categoryName); category = categories.save(category);
        for (int i=1; i<=10; i++) {
          Product product = new Product(); product.setId(UUID.randomUUID().toString());
          product.setName(categoryName + " " + i); product.setPrice(100 + random.nextDouble() * 5000);
          product.setQuantity(random.nextInt(100)); product.setCategory(category);
          products.save(product);
        }
      }
    };
  }
}
