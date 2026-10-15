package com.app.web.controllers;

import com.app.model.User;
import com.app.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String login,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        return userRepository.findByLogin(login)
                .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .map(u -> {
                    session.setAttribute("user", u.getLogin());
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
    public String processRegister(@RequestParam String login,
                                  @RequestParam String password,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        if (login.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Логин и пароль не могут быть пустыми.");
            return "register";
        }

        if (userRepository.findByLogin(login).isPresent()) {
            model.addAttribute("error", "Этот логин уже занят. Придумайте другой.");
            return "register";
        }

        User user = new User();
        user.setLogin(login);
        user.setPasswordHash(passwordEncoder.encode(password));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("message", "Регистрация успешна! Теперь вы можете войти.");
        return "redirect:/login";
    }
}
