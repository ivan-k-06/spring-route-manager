package com.app.web.api;

import com.app.service.GeocodingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityRestController {

    private final GeocodingService geocodingService;

    public CityRestController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/search")
    public List<CityDto> search(@RequestParam("q") String query) {
        if (query == null || query.trim().length() < 2) {
            return List.of();
        }
        return geocodingService.searchCities(query);
    }
}