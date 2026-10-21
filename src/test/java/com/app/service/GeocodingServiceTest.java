package com.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class GeocodingServiceTest {
    private MockRestServiceServer server;
    private GeocodingService service;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        service = new GeocodingService(builder);
    }

    @Test
    void convertsCoordinatesFromExternalResponse() {
        server.expect(requestTo("https://nominatim.openstreetmap.org/search?format=json&limit=5&q=London"))
                .andRespond(withSuccess("[{\"display_name\":\"London\",\"lat\":\"51.5\",\"lon\":\"-0.12\"}]", MediaType.APPLICATION_JSON));
        var cities = service.searchCities("London");
        assertThat(cities).hasSize(1);
        assertThat(cities.get(0)).extracting("name", "lat", "lon").containsExactly("London", 51.5, -0.12);
        server.verify();
    }

    @Test
    void unavailableProviderReturnsEmptyResult() {
        server.expect(anything()).andRespond(withServerError());
        assertThat(service.searchCities("London")).isEmpty();
        server.verify();
    }

    @Test
    void invalidCoordinatesDoNotEscapeAsException() {
        server.expect(anything()).andRespond(withSuccess("[{\"display_name\":\"London\",\"lat\":\"invalid\",\"lon\":\"0\"}]", MediaType.APPLICATION_JSON));
        assertThat(service.searchCities("London")).isEmpty();
        server.verify();
    }
}
