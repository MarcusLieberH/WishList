package com.wishlist.wishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wishlist")
public class WishlistApplication {
	public static void main(String[] args) {
		SpringApplication.run(WishlistApplication.class, args);
	}
}
