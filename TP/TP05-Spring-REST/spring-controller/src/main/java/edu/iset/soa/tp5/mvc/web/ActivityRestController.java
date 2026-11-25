package edu.iset.soa.tp5.mvc.web;
import edu.iset.soa.tp5.mvc.entity.*;
import edu.iset.soa.tp5.mvc.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/activities")
public class ActivityRestController {
  private final ActivityRepository activities;
  private final CategoryRepository categories;
  public ActivityRestController(ActivityRepository activities, CategoryRepository categories) { this.activities=activities; this.categories=categories; }
  @GetMapping public List<Activity> all() { return activities.findAll(); }
  @GetMapping("/{id}") public ResponseEntity<Activity> get(@PathVariable Long id) { return activities.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build()); }
  @PostMapping public ResponseEntity<?> create(@RequestBody Activity incoming) {
    var category=resolveCategory(incoming); if (category==null) return ResponseEntity.badRequest().body("Unknown category");
    incoming.setId(null); incoming.setCategory(category);
    return ResponseEntity.status(HttpStatus.CREATED).body(activities.save(incoming));
  }
  @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Activity incoming) {
    if (!activities.existsById(id)) return ResponseEntity.notFound().build();
    var category=resolveCategory(incoming); if (category==null) return ResponseEntity.badRequest().body("Unknown category");
    incoming.setId(id); incoming.setCategory(category); return ResponseEntity.ok(activities.save(incoming));
  }
  @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!activities.existsById(id)) return ResponseEntity.notFound().build();
    activities.deleteById(id); return ResponseEntity.noContent().build();
  }
  @GetMapping("/status/{status}") public List<Activity> byStatus(@PathVariable ActivityStatus status) { return activities.findByStatus(status); }
  @GetMapping("/category/{categoryId}") public List<Activity> byCategory(@PathVariable Long categoryId) { return activities.findByCategoryId(categoryId); }
  @GetMapping("/search/{name}") public List<Activity> byName(@PathVariable String name) { return activities.findByNameContainingIgnoreCase(name); }
  @GetMapping("/location/{location}") public List<Activity> byLocation(@PathVariable String location) { return activities.findByLocation(location); }
  private Category resolveCategory(Activity activity) {
    if(activity==null || activity.getCategory()==null || activity.getCategory().getId()==null) return null;
    return categories.findById(activity.getCategory().getId()).orElse(null);
  }
}
