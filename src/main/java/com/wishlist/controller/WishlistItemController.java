package com.wishlist.controller;
import com.wishlist.model.Wishlist;
import com.wishlist.model.WishlistItem;
import com.wishlist.service.WishlistItemService;
import com.wishlist.service.WishlistService;
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
    public String addWishlistItem(
            @PathVariable int wishlistId,
            @RequestParam String name,
            @RequestParam(required = false) String link,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) String comment
    ) {
        wishlistItemService.addWishlistItem(wishlistId, name, link, imageUrl, comment);
        return "redirect:/wishlist/" + wishlistId;
    }


    @PostMapping("/items/{itemId}/delete")
    public String deleteWishlistItem(@PathVariable int wishlistId, @PathVariable int itemId) {
        wishlistItemService.deleteWishlistItem(itemId);
        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/items/{itemId}/edit")
    public String showEditForm(@PathVariable int wishlistId, @PathVariable int itemId, Model model) {
        Optional<WishlistItem> item = wishlistItemService.getItemById(itemId);

        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            model.addAttribute("wishlistId", wishlistId);
            return "editWishlistItem";
        }

        return "redirect:/wishlist/" + wishlistId;
    }

    @PostMapping("/items/{itemId}/edit")
    public String editWishlistItem(
            @PathVariable int wishlistId,
            @PathVariable int itemId,
            @RequestParam String name,
            @RequestParam(required = false) String link,
            @RequestParam(required = false) String comment
    ) {
        Optional<WishlistItem> existingItem = wishlistItemService.getItemById(itemId);
        if (existingItem.isPresent()) {
            WishlistItem item = existingItem.get();
            // Preserve imageUrl while updating name, link, and comment
            wishlistItemService.updateWishlistItem(itemId, name, link, item.getImageUrl(), comment);
        } else {
            // If item doesn't exist, just update with the provided values
            wishlistItemService.updateWishlistItem(itemId, name, link, null, comment);
        }
        return "redirect:/wishlist/" + wishlistId;
    }
}
