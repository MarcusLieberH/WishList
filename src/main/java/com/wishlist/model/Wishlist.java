package com.wishlist.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Wishlist {
    private int id;
    private String name;
    private LocalDateTime createdAt;
    private Integer userId;
    private User user;
    public Wishlist() {
    }

    public Wishlist(int id, String name, LocalDateTime createdAt, Integer userId) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    // Brug denne hvis du stadig har kode, der bruger constructor uden userId
    public Wishlist(int id, String name, LocalDateTime createdAt) {
        this(id, name, createdAt, null);
    }

    // Getters og setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user; }

    public void setUser(User user) {
        this.user = user; }
}

