package com.wishlist.service;

import com.wishlist.model.Wishlist;
import com.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public Optional<Wishlist> getWishlistById(int id) {
        return wishlistRepository.findById(id);
    }

    public void addWishlist(String name) {
        wishlistRepository.addWishlist(name);
    }

    public void deleteWishlist(int id) {
        wishlistRepository.deleteWishlist(id);
    }

}
