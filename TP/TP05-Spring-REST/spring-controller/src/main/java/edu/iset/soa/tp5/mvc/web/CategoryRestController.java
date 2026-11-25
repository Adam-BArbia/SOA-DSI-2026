package edu.iset.soa.tp5.mvc.web;
import edu.iset.soa.tp5.mvc.entity.Category;
import edu.iset.soa.tp5.mvc.repository.CategoryRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/categories")
public class CategoryRestController {
  private final CategoryRepository repository;
  public CategoryRestController(CategoryRepository repository) { this.repository=repository; }
  @GetMapping public List<Category> all() { return repository.findAll(); }
  @GetMapping("/{id}") public ResponseEntity<Category> get(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build()); }
  @GetMapping("/name/{name}") public ResponseEntity<Category> byName(@PathVariable String name) { return repository.findByName(name).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build()); }
  @PostMapping public ResponseEntity<Category> add(@RequestBody Category category) { category.setId(null); return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(category)); }
}
