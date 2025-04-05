package com.wishlist.repository;

import com.wishlist.model.WishlistItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WishlistItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishlistItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<WishlistItem> wishlistItemRowMapper = (rs, rowNum) ->
            new WishlistItem(
                    rs.getInt("id"),
                    rs.getInt("wishlist_id"),
                    rs.getString("name"),
                    rs.getString("link"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );

    public List<WishlistItem> findByWishlistId(int wishlistId) {
        return jdbcTemplate.query("SELECT * FROM wishlist_item WHERE wishlist_id = ?", wishlistItemRowMapper, wishlistId);
    }

    public void addWishlistItem(int wishlistId, String name, String link) {
        jdbcTemplate.update("INSERT INTO wishlist_item (wishlist_id, name, link) VALUES (?, ?, ?)",
                wishlistId, name, link);
    }

    public void deleteWishlistItem(int id) {
        jdbcTemplate.update("DELETE FROM wishlist_item WHERE id = ?", id);
    }
    public void updateWishlistItem(int id, String name, String link) {
        jdbcTemplate.update("UPDATE wishlist_item SET name = ?, link = ? WHERE id = ?", name, link, id);
    }
    public Optional<WishlistItem> findById(int itemId) {
        List<WishlistItem> results = jdbcTemplate.query("SELECT * FROM wishlist_item WHERE id = ?", wishlistItemRowMapper, itemId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    public List<WishlistItem> findAll() {
        return jdbcTemplate.query("SELECT * FROM wishlist_item", wishlistItemRowMapper);
    }

}
