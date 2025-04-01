package com.wishlist.controller;

import com.wishlist.model.Wishlist;
import com.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public String showWishlists(Model model) {
        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        model.addAttribute("wishlists", wishlists);
        return "wishlist"; // Lader Thymeleaf vise wishlist.html
    }

    @PostMapping("/add")
    public String addWishlist(@RequestParam String name) {
        wishlistService.addWishlist(name);
        return "redirect:/wishlist";
    }

    @PostMapping("/{id}/delete")
    public String deleteWishlist(@PathVariable int id) {
        wishlistService.deleteWishlist(id);
        return "redirect:/wishlist";
    }
}
