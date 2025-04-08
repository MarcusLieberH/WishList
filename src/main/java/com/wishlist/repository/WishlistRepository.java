package com.wishlist.repository;
import com.wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> new Wishlist(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getTimestamp("created_at") != null
                    ? rs.getTimestamp("created_at").toLocalDateTime()
                    : null
    );


    public List<Wishlist> findAll() {
        return jdbcTemplate.query("SELECT * FROM wishlist", wishlistRowMapper);
    }

    public Optional<Wishlist> findById(int id) {
        List<Wishlist> results = jdbcTemplate.query("SELECT * FROM wishlist WHERE id = ?", wishlistRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public void addWishlist(String name) {
        try {
            System.out.println("✅ Tilføjer ønskeliste: " + name);
            // If you set DEFAULT GETDATE() in your schema, you can just insert name
            int rows = jdbcTemplate.update("INSERT INTO wishlist (name) VALUES (?)", name);
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

}

