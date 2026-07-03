package com.app.service;

import com.app.web.api.CityDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeocodingService {

    private final RestClient restClient;

    public GeocodingService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader("User-Agent", "WebRouteManager/1.0 (Student Project)")
                .build();
    }

    public List<CityDto> searchCities(String query) {
        try {
            JsonNode response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("format", "json")
                            .queryParam("limit", "5")
                            .queryParam("q", query)
                            .build())
                    .retrieve()
                    .body(JsonNode.class);

            List<CityDto> cities = new ArrayList<>();

            if (response != null && response.isArray()) {
                for (JsonNode node : response) {
                    String name = node.get("display_name").asText();
                    Double lat = Double.parseDouble(node.get("lat").asText());
                    Double lon = Double.parseDouble(node.get("lon").asText());

                    cities.add(new CityDto(name, lat, lon));
                }
            }
            return cities;

        } catch (Exception e) {
            System.err.println("Ошибка при запросе к Nominatim: " + e.getMessage());
            return List.of();
        }
    }
}