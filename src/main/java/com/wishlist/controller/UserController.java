package com.wishlist.controller;

import com.wishlist.model.User;
import com.wishlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String username = request.get("username");
            String email = request.get("email");
            String password = request.get("password");

            User registeredUser = userService.registerUser(username, email, password);

            response.put("success", true);
            response.put("message", "Bruger oprettet med succes");
            response.put("userId", registeredUser.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Der opstod en fejl ved oprettelse af bruger");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
