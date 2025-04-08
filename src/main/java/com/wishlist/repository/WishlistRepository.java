package com.wishlist.repository;
import com.wishlist.model.User;
import com.wishlist.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;  // Remove final and use field injection instead

    @Autowired
    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> {
        int userId = rs.getInt("user_id");

        Wishlist wishlist = new Wishlist(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                userId
        );

        // Only try to find user if userId is valid (greater than 0)
        if (userId > 0) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                wishlist.setUser(user.get());
            } else {
                System.err.println("❌ No user found with ID: " + userId);
            }
        } else {
            System.err.println("⚠️ Wishlist has invalid user_id: " + userId);
        }

        return wishlist;
    };
    public List<Wishlist> findAll() {
        return jdbcTemplate.query("SELECT * FROM wishlist", wishlistRowMapper);
    }

    public Optional<Wishlist> findById(int id) {
        List<Wishlist> results = jdbcTemplate.query("SELECT * FROM wishlist WHERE id = ?", wishlistRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public void addWishlist(String name, Integer userId) {
        try {
            System.out.println("✅ Tilføjer ønskeliste: " + name + " for user ID: " + userId);

            // Always use the version with user_id, even if it's null
            String sql = "INSERT INTO wishlist (name, user_id) VALUES (?, ?)";
            int rows = jdbcTemplate.update(sql, name, userId);

            System.out.println("✅ Rækker indsat: " + rows);
        } catch (Exception e) {
            System.err.println("❌ Fejl under tilføjelse: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    public void deleteWishlist(int id) {
        jdbcTemplate.update("DELETE FROM wishlist WHERE id = ?", id);
    }
    public void updateWishlistUser(int wishlistId, int userId) {
        try {
            String sql = "UPDATE wishlist SET user_id = ? WHERE id = ?";
            int rows = jdbcTemplate.update(sql, userId, wishlistId);
            System.out.println("✅ Updated wishlist " + wishlistId + " with user ID " + userId + ", rows affected: " + rows);
        } catch (Exception e) {
            System.err.println("❌ Error updating wishlist user: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
