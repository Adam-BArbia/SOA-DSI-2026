package edu.iset.soa.tp6.service;
import edu.iset.soa.tp6.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WeatherService {
  private final RestClient restClient;
  private final String apiKey;
  public WeatherService(RestClient.Builder restClientBuilder,
    @Value("${weather.api.url}") String apiUrl,
    @Value("${weather.api.key:}") String apiKey) {
      this.restClient = restClientBuilder.baseUrl(apiUrl).build();
      this.apiKey = apiKey;
  }
  public WeatherResponse getWeather(String city) {
    if (apiKey == null || apiKey.isBlank()) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "OpenWeatherMap API key not configured");
    }
    return restClient.get()
        .uri(builder -> builder.queryParam("q",city).queryParam("appid",apiKey)
                .queryParam("units","metric").queryParam("lang","fr").build())
        .retrieve().body(WeatherResponse.class);
  }
}
