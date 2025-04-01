package com.wishlist.repository;

import com.wishlist.model.WishlistItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
