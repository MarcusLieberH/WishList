package com.wishlist.service;

import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String password) {
        // Validate input
        validateRegistrationInput(username, email, password);

        // Create user with hashed password
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // Save to database
        return userRepository.save(user);
    }

    private void validateRegistrationInput(String username, String email, String password) {
        // Check required fields
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Brugernavn er påkrævet");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email er påkrævet");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Adgangskode er påkrævet");
        }

        // Validate username length
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Brugernavn skal være mellem 3 og 50 tegn");
        }

        // Validate email format
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email format er ikke gyldigt");
        }

        // Validate password length
        if (password.length() < 6) {
            throw new IllegalArgumentException("Adgangskode skal være mindst 6 tegn");
        }

        // Check if username already exists
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Brugernavnet er allerede taget");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email adressen er allerede i brug");
        }
    }
    public User authenticate(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

}
