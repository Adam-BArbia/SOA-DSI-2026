package edu.iset.soa.tp6.service;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.hamcrest.Matchers.containsString;
class WeatherServiceTest {
  @Test void mapsOpenWeatherResponse() {
    var builder = RestClient.builder();
    var mock = MockRestServiceServer.bindTo(builder).build();
    mock.expect(requestTo(containsString("q=Tunis"))).andRespond(withSuccess(
       "{\"name\":\"Tunis\",\"main\":{\"temp\":20.5,\"humidity\":60},\"weather\":[{\"description\":\"clair\"}],\"wind\":{\"speed\":4.1}}", MediaType.APPLICATION_JSON));
    var result = new WeatherService(builder,"https://example.test/weather","fake-key").getWeather("Tunis");
    assertEquals("Tunis",result.name());
    assertEquals(20.5,result.main().temp());
    assertEquals("clair",result.weather().get(0).description());
    mock.verify();
  }
}
