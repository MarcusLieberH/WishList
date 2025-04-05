package com.wishlist.controller;

import com.wishlist.model.Wishlist;
import com.wishlist.model.WishlistItem;
import com.wishlist.service.WishlistItemService;
import com.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/wishlist/{wishlistId}")
public class WishlistItemController {
    private final WishlistItemService wishlistItemService;
    private final WishlistService wishlistService;

    public WishlistItemController(WishlistItemService wishlistItemService, WishlistService wishlistService) {
        this.wishlistItemService = wishlistItemService;
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public String showWishlistItems(@PathVariable int wishlistId, Model model) {
        Optional<Wishlist> wishlist = wishlistService.getWishlistById(wishlistId);
        List<WishlistItem> items = wishlistItemService.getItemsByWishlistId(wishlistId);

        if (wishlist.isPresent()) {
            model.addAttribute("wishlist", wishlist.get());
            model.addAttribute("wishlistItems", items);
            return "wishlistItems";
        }
        return "redirect:/wishlist";
    }

    @PostMapping("/add-item")
    public String addWishlistItem(@PathVariable int wishlistId, @RequestParam String name, @RequestParam(required = false) String link) {
        wishlistItemService.addWishlistItem(wishlistId, name, link);
        return "redirect:/wishlist/" + wishlistId;
    }
    @PostMapping("/items/{itemId}/delete")
    public String deleteWishlistItem(@PathVariable int wishlistId, @PathVariable int itemId) {
        wishlistItemService.deleteWishlistItem(itemId);
        return "redirect:/wishlist/" + wishlistId;
    }
}

