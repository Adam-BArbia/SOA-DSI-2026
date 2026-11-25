package edu.iset.soa.tp5.jersey.web;
import edu.iset.soa.tp5.jersey.entity.*;
import edu.iset.soa.tp5.jersey.repository.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.springframework.stereotype.Component;
import java.util.List;
@Component @Path("/activities") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
public class ActivityRestApi {
  private final ActivityRepository activities;
  private final CategoryRepository categories;
  public ActivityRestApi(ActivityRepository activities, CategoryRepository categories) { this.activities=activities; this.categories=categories; }
  @GET public List<Activity> getAllActivities() { return activities.findAll(); }
  @GET @Path("/{id}") public Response getActivityById(@PathParam("id") Long id) {
    return activities.findById(id).map(a->Response.ok(a).build()).orElseGet(()->Response.status(404).build());
  }
  @POST public Response createActivity(Activity incoming) {
    if (incoming == null || incoming.getCategory()==null || incoming.getCategory().getId()==null) return Response.status(400).build();
    var category=categories.findById(incoming.getCategory().getId());
    if (category.isEmpty()) return Response.status(400).entity("Unknown category").build();
    incoming.setId(null); incoming.setCategory(category.get());
    var saved=activities.save(incoming);
    return Response.status(Response.Status.CREATED).entity(saved).build();
  }
  @PUT @Path("/{id}") public Response updateActivity(@PathParam("id") Long id, Activity incoming) {
    if (!activities.existsById(id)) return Response.status(404).build();
    if (incoming==null || incoming.getCategory()==null || incoming.getCategory().getId()==null) return Response.status(400).build();
    var category=categories.findById(incoming.getCategory().getId());
    if (category.isEmpty()) return Response.status(400).build();
    incoming.setId(id); incoming.setCategory(category.get());
    return Response.ok(activities.save(incoming)).build();
  }
  @DELETE @Path("/{id}") public Response deleteActivity(@PathParam("id") Long id) {
    if (!activities.existsById(id)) return Response.status(404).build();
    activities.deleteById(id); return Response.noContent().build();
  }
  @GET @Path("/status/{status}") public List<Activity> getActivitiesByStatus(@PathParam("status") ActivityStatus status) { return activities.findByStatus(status); }
  @GET @Path("/category/{categoryId}") public List<Activity> getActivitiesByCategory(@PathParam("categoryId") Long categoryId) { return activities.findByCategoryId(categoryId); }
  @GET @Path("/search/{name}") public List<Activity> searchByName(@PathParam("name") String name) { return activities.findByNameContainingIgnoreCase(name); }
  @GET @Path("/location/{location}") public List<Activity> getByLocation(@PathParam("location") String location) { return activities.findByLocation(location); }
}
