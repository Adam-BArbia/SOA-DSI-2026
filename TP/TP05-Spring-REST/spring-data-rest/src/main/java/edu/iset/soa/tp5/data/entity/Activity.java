package edu.iset.soa.tp5.data.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
public class Activity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String name;
  @Column(length = 2000) private String description;
  private LocalDate localDate;
  private String location;
  private int capacity;
  private double price;
  @Enumerated(EnumType.STRING) private ActivityStatus status;
  @ManyToOne @JoinColumn(name = "category_id") private Category category;
  public Activity() { }
  public Long getId() { return id; } public void setId(Long id) { this.id=id; }
  public String getName() { return name; } public void setName(String name) { this.name=name; }
  public String getDescription() { return description; } public void setDescription(String description) { this.description=description; }
  public LocalDate getLocalDate() { return localDate; } public void setLocalDate(LocalDate value) { localDate=value; }
  public String getLocation() { return location; } public void setLocation(String value) { location=value; }
  public int getCapacity() { return capacity; } public void setCapacity(int value) { capacity=value; }
  public double getPrice() { return price; } public void setPrice(double value) { price=value; }
  public ActivityStatus getStatus() { return status; } public void setStatus(ActivityStatus value) { status=value; }
  public Category getCategory() { return category; } public void setCategory(Category value) { category=value; }
}
