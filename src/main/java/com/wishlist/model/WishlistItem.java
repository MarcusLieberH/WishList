package com.wishlist.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;


public class WishlistItem {
    private int id;
    private int wishlistId;
    private String name;
    private String link;
    private String imageUrl;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    public WishlistItem() {}

    public WishlistItem(int id, int wishlistId, String name, String link, String imageUrl, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.wishlistId = wishlistId;
        this.name = name;
        this.link = link;
        this.imageUrl = imageUrl;
        this.comment = comment;
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
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    }


