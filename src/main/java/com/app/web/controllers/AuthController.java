package com.app.web.controllers;

import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.auth.PasswordHasher;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public final class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showLoginPage() {
        return "login"; // вернет login.jsp
    }

    @PostMapping
    public String processAuth(
            @RequestParam String action,
            @RequestParam String login,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        if ("register".equals(action)) {
            if (userRepository.findByLogin(login).isPresent()) {
                model.addAttribute("error", "Логин уже занят.");
            } else {
                User user = new User();
                user.setLogin(login);
                user.setPasswordHash(PasswordHasher.sha512(password));
                userRepository.save(user);
                model.addAttribute("message", "Регистрация успешна! Теперь войдите.");
            }
            return "login";
        }

        if ("login".equals(action)) {
            return userRepository.findByLogin(login)
                    .filter(u -> u.getPasswordHash().equals(PasswordHasher.sha512(password)))
                    .map(u -> {
                        session.setAttribute("user", login);
                        return "redirect:/routes";
                    })
                    .orElseGet(() -> {
                        model.addAttribute("error", "Неверный логин или пароль.");
                        return "login";
                    });
        }
        return "login";
    }
}