package com.wishlist.controller;

import com.wishlist.model.User;
import com.wishlist.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.authenticate(username, password);

        if (user != null) {
            model.addAttribute("user", user);
            return "redirect:/wishlist"; // Redirect til ønskelister
        } else {
            model.addAttribute("error", "Forkert brugernavn eller adgangskode");
            return "index"; // Tilbage til login-siden med fejl
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            User newUser = userService.registerUser(username, email, password);
            model.addAttribute("success", "Bruger oprettet! Log nu ind.");
            return "index";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

}

