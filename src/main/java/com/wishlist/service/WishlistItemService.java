package com.wishlist.service;

import com.wishlist.model.WishlistItem;
import com.wishlist.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishlistItemService {
    private final WishlistItemRepository wishlistItemRepository;

    public WishlistItemService(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    public List<WishlistItem> getItemsByWishlistId(int wishlistId) {
        return wishlistItemRepository.findByWishlistId(wishlistId);
    }

    public void addWishlistItem(int wishlistId, String name, String link) {
        wishlistItemRepository.addWishlistItem(wishlistId, name, link);
    }

    public void deleteWishlistItem(int id) {
        wishlistItemRepository.deleteWishlistItem(id);
    }
}
