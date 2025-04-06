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

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) ->
            new Wishlist(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );

    public List<Wishlist> findAll() {
        return jdbcTemplate.query("SELECT * FROM wishlist", wishlistRowMapper);
    }

    public Optional<Wishlist> findById(int id) {
        List<Wishlist> results = jdbcTemplate.query("SELECT * FROM wishlist WHERE id = ?", wishlistRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public void addWishlist(String name) {
        jdbcTemplate.update("INSERT INTO wishlist (name, created_at) VALUES (?, NOW())", name);
        jdbcTemplate.queryForObject("SELECT * FROM wishlist WHERE name = ? ORDER BY id DESC LIMIT 1",
                wishlistRowMapper, name);
    }

    public void deleteWishlist(int id) {
        jdbcTemplate.update("DELETE FROM wishlist WHERE id = ?", id);
    }

}

