package edu.iset.soa.tp5.jersey.web;
import edu.iset.soa.tp5.jersey.entity.Category;
import edu.iset.soa.tp5.jersey.repository.CategoryRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.springframework.stereotype.Component;
import java.util.List;
@Component @Path("/categories") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
public class CategoryRestApi {
  private final CategoryRepository repository;
  public CategoryRestApi(CategoryRepository repository) { this.repository=repository; }
  @GET public List<Category> getAll() { return repository.findAll(); }
  @GET @Path("/{id}") public Response get(@PathParam("id") Long id) { return repository.findById(id).map(c->Response.ok(c).build()).orElseGet(()->Response.status(404).build()); }
  @GET @Path("/name/{name}") public Response byName(@PathParam("name") String name) { return repository.findByName(name).map(c->Response.ok(c).build()).orElseGet(()->Response.status(404).build()); }
  @POST public Response add(Category category) { category.setId(null); return Response.status(201).entity(repository.save(category)).build(); }
}
