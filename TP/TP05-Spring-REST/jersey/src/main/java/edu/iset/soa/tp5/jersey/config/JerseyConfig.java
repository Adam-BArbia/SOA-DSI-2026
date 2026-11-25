package edu.iset.soa.tp5.jersey.config;
import edu.iset.soa.tp5.jersey.web.ActivityRestApi;
import edu.iset.soa.tp5.jersey.web.CategoryRestApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
@Configuration
public class JerseyConfig extends ResourceConfig {
  public JerseyConfig() { register(ActivityRestApi.class); register(CategoryRestApi.class); }
}
