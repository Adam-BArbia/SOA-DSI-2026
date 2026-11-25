package edu.iset.soa.tp5.mvc.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Category {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String name;
  private String description;
  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  @com.fasterxml.jackson.annotation.JsonIgnore
  private List<Activity> activities = new ArrayList<>();
  public Category() { }
  public Long getId() { return id; } public void setId(Long id) { this.id=id; }
  public String getName() { return name; } public void setName(String name) { this.name=name; }
  public String getDescription() { return description; } public void setDescription(String description) { this.description=description; }
  public List<Activity> getActivities() { return activities; } public void setActivities(List<Activity> items) { this.activities=items; }
}
