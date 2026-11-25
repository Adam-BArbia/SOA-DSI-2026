package edu.iset.soa.tp7.entities;
import jakarta.persistence.*;
@Entity
public class Product {
  @Id private String id;
  private String name;
  private double price;
  private int quantity;
  @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "category_id") private Category category;
  public Product() { }
  public String getId() { return id; } public void setId(String id) { this.id=id; }
  public String getName() { return name; } public void setName(String name) { this.name=name; }
  public double getPrice() { return price; } public void setPrice(double price) { this.price=price; }
  public int getQuantity() { return quantity; } public void setQuantity(int quantity) { this.quantity=quantity; }
  public Category getCategory() { return category; } public void setCategory(Category category) { this.category=category; }
}
