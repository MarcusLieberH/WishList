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
import java.util.Optional;

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
    public String showWishlists(Model model, HttpSession session) {
        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        String username = (String) session.getAttribute("username");

        System.out.println("📜 Hentede ønskelister: " + wishlists.size());
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("username", username);
        return "wishlist";
    }

    // In WishlistController
    @PostMapping("/wishlist/add")
    public String createWishlist(@RequestParam String name, HttpSession session) {
        System.out.println("🚀 POST /wishlist/add modtaget! Navn: " + name);
        String username = (String) session.getAttribute("username");

        wishlistService.addWishlist(name, username);

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

    @GetMapping("/wishlist/{id}/share")
    public String shareWishlist(@PathVariable int id, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        // Check if the user is logged in
        if (username == null) {
            return "redirect:/login";
        }

        // Retrieve the wishlist
        Optional<Wishlist> wishlistOptional = wishlistService.getWishlistById(id);
        if (!wishlistOptional.isPresent()) {
            model.addAttribute("errorMessage", "Ønskeliste ikke fundet.");
            return "error";
        }

        Wishlist wishlist = wishlistOptional.get();

        // If the wishlist doesn't have a user, associate it with the current user
        if (wishlist.getUser() == null) {
            try {
                wishlistService.associateWishlistWithUser(id, username);
                // Refresh the wishlist after association
                wishlistOptional = wishlistService.getWishlistById(id);
                if (!wishlistOptional.isPresent()) {
                    model.addAttribute("errorMessage", "Fejl ved opdatering af ønskeliste.");
                    return "error";
                }
                wishlist = wishlistOptional.get();
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Fejl ved tilknytning af bruger: " + e.getMessage());
                return "error";
            }
        }

        // Generate a shareable link
        String shareableLink = wishlistService.generateShareableLink(id);
        model.addAttribute("shareableLink", shareableLink);
        model.addAttribute("wishlistName", wishlist.getName());

        return "shareWishlist";  // This should match the name of your HTML template file
    }
}