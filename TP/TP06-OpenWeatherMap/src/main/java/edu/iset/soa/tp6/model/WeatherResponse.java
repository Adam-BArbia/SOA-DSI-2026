package edu.iset.soa.tp6.model;
import java.util.List;
public record WeatherResponse(String name, Main main, List<Weather> weather, Wind wind) { }
