package com.app.web.controllers;

import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.auth.PasswordHasher;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String login, @RequestParam String password, HttpSession session, Model model) {
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

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String login, @RequestParam String password, RedirectAttributes redirectAttributes, Model model) {
        if (userRepository.findByLogin(login).isPresent()) {
            model.addAttribute("error", "Этот логин уже занят. Придумайте другой.");
            return "register"; // Оставляем на странице регистрации с ошибкой
        }

        User user = new User();
        user.setLogin(login);
        user.getPasswordHash();
        user.setPasswordHash(PasswordHasher.sha512(password));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("message", "Регистрация успешна! Теперь вы можете войти.");
        return "redirect:/login";
    }
}