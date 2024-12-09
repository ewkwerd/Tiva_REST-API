CREATE DATABASE IF NOT EXISTS tiva_db;
USE tiva_db;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE categories (
  id INT(10) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO categories (id, name) VALUES
(1, 'Men\'s Suits'),
(2, 'Women\'s Evening Dresses'),
(3, 'Men\'s Formal Wear'),
(4, 'Women\'s Cocktail Dresses'),
(5, 'Elegant Accessories');

CREATE TABLE products (
  id INT(10) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(100),
  price DECIMAL(10,2),
  category_id INT(10) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_products_category_id (category_id),
  CONSTRAINT fk_products_category_id FOREIGN KEY (category_id) REFERENCES categories (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO products (id, name, description, price, category_id) VALUES
(1, 'Blue Suit', 'Elegant men\'s suit in blue color', 250.00, 1),
(2, 'Black Dress', 'Evening dress for women in black color', 200.00, 2),
(3, 'White Shirt', 'Formal men\'s shirt in white color', 50.00, 3),
(4, 'Red Dress', 'Women\'s cocktail dress in red color', 150.00, 4),
(5, 'Pearl Necklace', 'Elegant necklace with crystal pendant', 80.00, 5);

CREATE TABLE product_sizes (
  id INT(10) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO product_sizes (id, name) VALUES
(1, 'XS'),
(2, 'S'),
(3, 'M'),
(4, 'L'),
(5, 'XL');

CREATE TABLE stocks (
  id INT(10) NOT NULL AUTO_INCREMENT,
  product_id INT(10) NOT NULL,
  product_size_id INT(10) NOT NULL,
  quantity INT(10),
  PRIMARY KEY (id),
  KEY fk_stock_product_id (product_id),
  KEY fk_stock_product_size_id (product_size_id),
  CONSTRAINT fk_stocks_product_id FOREIGN KEY (product_id) REFERENCES products (id),
  CONSTRAINT fk_stocks_product_size_id FOREIGN KEY (product_size_id) REFERENCES product_sizes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE orders (
  id INT(10) NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE order_details (
  id INT(10) NOT NULL AUTO_INCREMENT,
  order_id INT(10) NOT NULL,
  stock_id INT(10) NOT NULL,
  quantity INT(10) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_order_details_order_id (order_id),
  KEY fk_order_details_stock_id (stock_id),
  CONSTRAINT fk_order_details_stock_id FOREIGN KEY (stock_id) REFERENCES stocks (id),
  CONSTRAINT fk_order_details_order_id FOREIGN KEY (order_id) REFERENCES orders (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

COMMIT;