/*
CREATE TABLE users (
                       id INT PRIMARY KEY IDENTITY(1,1),
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at DATETIME
);
CREATE TABLE wishlist (
                          id INT PRIMARY KEY IDENTITY(1,1),
                          name VARCHAR(255) NOT NULL,
                          created_at DATETIME,
                          user_id INT NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE wishlist_item (
                               id INT PRIMARY KEY IDENTITY(1,1),
                               wishlist_id INT NOT NULL,
                               name VARCHAR(255) NOT NULL,
                               link VARCHAR(255),
                               created_at DATETIME,
                               image_url NVARCHAR(MAX),
    comment NVARCHAR(MAX),
    FOREIGN KEY (wishlist_id) REFERENCES wishlist(id)
);
*/