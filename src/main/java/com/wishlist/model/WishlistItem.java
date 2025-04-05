package com.wishlist.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Optional;

public class WishlistItem {
    private int id;
    private int wishlistId;
    private String name;
    private String link;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    public WishlistItem() {}

    public WishlistItem(int id, int wishlistId, String name, String link, LocalDateTime createdAt) {
        this.id = id;
        this.wishlistId = wishlistId;
        this.name = name;
        this.link = link;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getWishlistId() { return wishlistId; }
    public void setWishlistId(int wishlistId) { this.wishlistId = wishlistId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    }


