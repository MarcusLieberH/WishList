package com.wishlist.service;

import com.wishlist.model.WishlistItem;
import com.wishlist.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistItemService {
    private final WishlistItemRepository wishlistItemRepository;

    public WishlistItemService(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    public List<WishlistItem> getItemsByWishlistId(int wishlistId) {
        return wishlistItemRepository.findByWishlistId(wishlistId);
    }

    public void addWishlistItem(int wishlistId, String name, String link, String imageUrl, String comment) {
        wishlistItemRepository.addWishlistItem(wishlistId, name, link, imageUrl, comment);
    }


    public void deleteWishlistItem(int id) {
        wishlistItemRepository.deleteWishlistItem(id);
    }
    public void updateWishlistItem(int itemId, String name, String link) {
        wishlistItemRepository.updateWishlistItem(itemId, name, link);
    }
    public Optional<WishlistItem> getItemById(int itemId) {
        return wishlistItemRepository.findById(itemId);
    }
    public List<WishlistItem> getAllWishlistItems() {
        return wishlistItemRepository.findAll();
    }

}
