INSERT INTO wishlist (name)
SELECT * FROM (SELECT 'Christmas Wishlist') AS tmp
WHERE NOT EXISTS (SELECT name FROM wishlist WHERE name = 'Christmas Wishlist') LIMIT 1;

INSERT INTO wishlist (name)
SELECT * FROM (SELECT 'Birthday Wishlist') AS tmp
WHERE NOT EXISTS (SELECT name FROM wishlist WHERE name = 'Birthday Wishlist') LIMIT 1;

INSERT INTO wishlist (name)
SELECT * FROM (SELECT 'Vacation Wishlist') AS tmp
WHERE NOT EXISTS (SELECT name FROM wishlist WHERE name = 'Vacation Wishlist') LIMIT 1;

