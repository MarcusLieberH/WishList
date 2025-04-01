package com.wishlist.repository;

import com.wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) ->
            new Wishlist(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("created_at").toLocalDateTime());

    public List<Wishlist> findAll() {
        return jdbcTemplate.query("SELECT * FROM wishlist", wishlistRowMapper);
    }

    public void addWishlist(String name) {
        String checkQuery = "SELECT COUNT(*) FROM wishlist WHERE name = ?";
        int count = jdbcTemplate.queryForObject(checkQuery, Integer.class, name);

        if (count == 0) { // Kun indsæt, hvis det ikke allerede findes
            jdbcTemplate.update("INSERT INTO wishlist (name) VALUES (?)", name);
        }
    }
}