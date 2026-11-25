package edu.iset.soa.tp7.web;
import edu.iset.soa.tp7.dto.ProductRequestDTO;
import edu.iset.soa.tp7.entities.*;
import edu.iset.soa.tp7.repository.*;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Controller
public class ProductGraphQLController {
  private final ProductRepository products;
  private final CategoryRepository categories;
  public ProductGraphQLController(ProductRepository products, CategoryRepository categories) { this.products=products; this.categories=categories; }
  @QueryMapping public List<Product> productList() { return products.findAll(); }
  @QueryMapping public Product productById(@Argument String id) { return products.findById(id).orElse(null); }
  @QueryMapping public List<Category> categories() { return categories.findAll(); }
  @QueryMapping public Category categoryById(@Argument Long id) { return categories.findById(id).orElse(null); }
  @SchemaMapping(typeName = "Category", field = "products")
  public List<Product> products(Category category) { return products.findByCategoryId(category.getId()); }
  private Category requireCategory(Long id) {
    return categories.findById(id).orElseThrow(() -> new IllegalArgumentException("Unknown categoryId: " + id));
  }
  private static void check(ProductRequestDTO request) {
    if (request.name() == null || request.name().isBlank()) throw new IllegalArgumentException("Name required");
    if (request.price() < 0 || request.quantity() < 0) throw new IllegalArgumentException("Price/quantity must be >= 0");
  }
  @MutationMapping @Transactional public Product saveProduct(@Argument("product") ProductRequestDTO request) {
    check(request);
    Product item = new Product(); item.setId(UUID.randomUUID().toString());
    item.setName(request.name()); item.setPrice(request.price()); item.setQuantity(request.quantity());
    item.setCategory(requireCategory(request.categoryId())); return products.save(item);
  }
  @MutationMapping @Transactional public Product updateProduct(@Argument String id, @Argument("product") ProductRequestDTO request) {
    check(request);
    Product item = products.findById(id).orElse(null);
    if (item == null) return null;
    item.setName(request.name()); item.setPrice(request.price()); item.setQuantity(request.quantity());
    item.setCategory(requireCategory(request.categoryId())); return products.save(item);
  }
  @MutationMapping @Transactional public String deleteProduct(@Argument String id) {
    if (!products.existsById(id)) return "NOT_FOUND";
    products.deleteById(id); return "DELETED";
  }
}
