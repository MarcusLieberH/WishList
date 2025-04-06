package com.wishlist.controller;
import com.wishlist.model.Wishlist;
import com.wishlist.repository.WishlistRepository;
import com.wishlist.service.WishlistItemService;
import com.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;
    public final WishlistItemService wishlistItemService;
    public final WishlistRepository wishlistRepository;

    public WishlistController(WishlistService wishlistService, WishlistItemService wishlistItemService, WishlistRepository wishlistRepository) {
        this.wishlistService = wishlistService;
        this.wishlistItemService = wishlistItemService;
        this.wishlistRepository = wishlistRepository;
    }

    @GetMapping("/wishlist")
    public String showWishlists(Model model,HttpSession session) {
        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        String username = (String) session.getAttribute("username");

        System.out.println("📜 Hentede ønskelister: " + wishlists.size());
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("username", username);
        return "wishlist";
    }

    @PostMapping("/wishlist/add")
    public String createWishlist(@RequestParam String name) {
        System.out.println("🚀 POST /wishlist/add modtaget! Navn: " + name);
        wishlistService.addWishlist(name);
        System.out.println("🔄 Redirecting to /wishlist...");
        return "redirect:/wishlist";
    }

    @PostMapping("/wishlist/{id}/delete")
    public String deleteWishlist(@PathVariable int id) {
        wishlistService.deleteWishlist(id);
        return "redirect:/wishlist";
    }
    @PostMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("🚪 Bruger logget ud"); // Debug
        return "redirect:/";
    }

}


