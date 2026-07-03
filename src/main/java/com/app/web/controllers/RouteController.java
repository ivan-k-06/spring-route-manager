package com.app.web.controllers;

import com.app.model.*;
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
        if (user == null) return "redirect:/auth";

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
            @RequestParam double coordX, @RequestParam int coordY,
            @RequestParam long fromX, @RequestParam int fromY, @RequestParam float fromZ,
            @RequestParam long toX, @RequestParam int toY, @RequestParam float toZ,
            @RequestParam float distance,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String user = (String) session.getAttribute("user");
        if (user == null) return "redirect:/auth";

        Route route = new Route();
        route.setName(name);
        route.setCoordinates(new Coordinates(coordX, coordY));
        route.setFrom(new Location(fromX, fromY, fromZ));
        route.setTo(new Location(toX, toY, toZ));
        route.setDistance(distance);
        route.setOwner(user);

        routeRepository.save(route);

        redirectAttributes.addFlashAttribute("toastSuccess", "Маршрут успешно добавлен!");
        return "redirect:/routes";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoute(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String user = (String) session.getAttribute("user");
        if (user == null) return "redirect:/auth";

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
}