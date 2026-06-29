package com.app.web.controllers;

import com.app.model.*;
import com.app.repository.RouteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/routes")
public final class RouteController {

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
        return "routes"; // вернет routes.jsp
    }

    @PostMapping
    public String addRoute(
            @RequestParam String name,
            @RequestParam double coordX, @RequestParam int coordY,
            @RequestParam long fromX, @RequestParam int fromY, @RequestParam float fromZ,
            @RequestParam long toX, @RequestParam int toY, @RequestParam float toZ,
            @RequestParam float distance,
            HttpSession session) {

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

        return "redirect:/routes";
    }
}