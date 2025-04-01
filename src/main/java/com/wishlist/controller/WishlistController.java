package com.wishlist.controller;

import com.wishlist.model.Wishlist;
import com.wishlist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WishlistController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Map<String, Object>> getWishlist() {
        return jdbcTemplate.queryForList("SELECT * FROM wishlist");
    }

    @DeleteMapping("/{id}")
    public void deleteWishlist(@PathVariable int id) {
        jdbcTemplate.update("DELETE FROM wishlist WHERE id = ?", id);
    }
}
