package com.app.web.controllers;

import com.app.model.Coordinates;
import com.app.model.Location;
import com.app.model.Route;
import com.app.repository.RouteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteRepository routeRepository;

    public RouteController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping
    public String showRoutes(HttpSession session, Model model) {
        String user = (String) session.getAttribute("user");

        model.addAttribute("routes", routeRepository.findAllByOrderByIdAsc());
        model.addAttribute("currentUser", user);

        model.addAttribute("totalRoutes", routeRepository.countByOwner(user));
        model.addAttribute("totalDistance", String.format("%.1f", routeRepository.sumDistanceByOwner(user)));
        model.addAttribute("maxDistance", String.format("%.1f", routeRepository.maxDistanceByOwner(user)));
        model.addAttribute("minDistance", String.format("%.1f", routeRepository.minDistanceByOwner(user)));
        model.addAttribute("avgDistance", String.format("%.1f", routeRepository.avgDistanceByOwner(user)));

        model.addAttribute("globalTotalRoutes", routeRepository.count());

        return "routes";
    }

    @PostMapping
    public String addRoute(
            @RequestParam String name,
            @RequestParam String fromName, @RequestParam Double fromLat, @RequestParam Double fromLon,
            @RequestParam String toName, @RequestParam Double toLat, @RequestParam Double toLon,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String user = (String) session.getAttribute("user");

        float distance = (float) haversineKm(fromLat, fromLon, toLat, toLon);

        Route route = new Route();
        route.setName(name);
        route.setCoordinates(new Coordinates((fromLat + toLat) / 2, (fromLon + toLon) / 2));
        route.setFrom(new Location(fromName, fromLat, fromLon));
        route.setTo(new Location(toName, toLat, toLon));
        route.setDistance(distance);
        route.setOwner(user);

        routeRepository.save(route);
        redirectAttributes.addFlashAttribute("toastSuccess", "Маршрут успешно добавлен!");
        return "redirect:/routes";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoute(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String user = (String) session.getAttribute("user");

        routeRepository.findById(id).ifPresent(route -> {
            if (route.getOwner().equals(user)) {
                routeRepository.delete(route);
                redirectAttributes.addFlashAttribute("toastSuccess", "Маршрут удален!");
            } else {
                redirectAttributes.addFlashAttribute("toastError", "У вас нет прав на удаление этого маршрута.");
            }
        });

        return "redirect:/routes";
    }

    private static double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        final int earthRadiusKm = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return earthRadiusKm * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
