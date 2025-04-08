package com.wishlist.service;

import com.wishlist.model.User;
import com.wishlist.model.Wishlist;
import com.wishlist.repository.UserRepository;
import com.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private UserRepository userRepository;
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public Optional<Wishlist> getWishlistById(int id) {
        return wishlistRepository.findById(id);
    }

    public void addWishlist(String name, String username) {
        if (username != null) {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                wishlistRepository.addWishlist(name, user.get().getId());
            } else {
                throw new RuntimeException("User not found");
            }
        } else {
            // For backward compatibility, allow adding wishlist without user
            wishlistRepository.addWishlist(name, null);
        }
    }

    // For backward compatibility
    public void addWishlist(String name) {
        addWishlist(name, null);
    }

    public void deleteWishlist(int id) {
        wishlistRepository.deleteWishlist(id);
    }
    public String generateShareableLink(int wishlistId) {
        return "https://wishlist2025-dqaeh9gnbya3eafp.westeurope-01.azurewebsites.net/wishlist/" + wishlistId;
    }
    public void associateWishlistWithUser(int wishlistId, String username) {
        // Get user ID from username
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            // Update the wishlist with the user ID using the repository
            wishlistRepository.updateWishlistUser(wishlistId, user.get().getId());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}

