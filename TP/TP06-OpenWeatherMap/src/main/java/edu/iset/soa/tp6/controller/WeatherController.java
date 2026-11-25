package edu.iset.soa.tp6.controller;
import edu.iset.soa.tp6.model.WeatherResponse;
import edu.iset.soa.tp6.service.WeatherService;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/meteo")
public class WeatherController {
  private final WeatherService service;
  public WeatherController(WeatherService service) { this.service=service; }
  @GetMapping("/{city}") public WeatherResponse get(@PathVariable String city) { return service.getWeather(city); }
}
