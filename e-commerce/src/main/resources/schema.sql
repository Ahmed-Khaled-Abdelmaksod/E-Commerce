-- ======================================================
-- SWEET DELIGHTS E-COMMERCE DATABASE
-- ======================================================

DROP DATABASE IF EXISTS sweet_delights;

CREATE DATABASE sweet_delights;

USE sweet_delights;

-- ======================================================
-- USERS TABLE
-- ======================================================

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    
    full_name VARCHAR(100) NOT NULL,
    
    email VARCHAR(150) NOT NULL UNIQUE,
    
    password_hash VARCHAR(255) NOT NULL,
    
    phone VARCHAR(20),
    
    birthday DATE,
    
    address VARCHAR(255),
    
    role ENUM('ADMIN','CUSTOMER') DEFAULT 'CUSTOMER',
    
    credit_balance DECIMAL(10,2) DEFAULT 0,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================================================
-- CATEGORIES TABLE
-- ======================================================

CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    
    name VARCHAR(100) NOT NULL UNIQUE,
    
    description TEXT
);

-- ======================================================
-- PRODUCTS TABLE
-- ======================================================

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    
    name VARCHAR(150) NOT NULL,
    
    description TEXT,
    
    price DECIMAL(10,2) NOT NULL,
    
    stock_quantity INT DEFAULT 0,
    
    image_url VARCHAR(255),
    
    category_id INT NOT NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (category_id)
    REFERENCES categories(category_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- ======================================================
-- CART TABLE
-- ======================================================

CREATE TABLE carts (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    
    user_id INT UNIQUE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE
);

-- ======================================================
-- CART ITEMS TABLE
-- ======================================================

CREATE TABLE cart_items (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    
    cart_id INT NOT NULL,
    
    product_id INT NOT NULL,
    
    quantity INT NOT NULL DEFAULT 1,
    
    FOREIGN KEY (cart_id)
    REFERENCES carts(cart_id)
    ON DELETE CASCADE,
    
    FOREIGN KEY (product_id)
    REFERENCES products(product_id)
    ON DELETE RESTRICT
);

-- ======================================================
-- ORDERS TABLE
-- ======================================================

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    
    user_id INT NOT NULL,
    
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    total_price DECIMAL(10,2) NOT NULL,
    
    status ENUM('PENDING','PAID','COMPLETED','CANCELLED') DEFAULT 'PENDING',
    
    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE
);

-- ======================================================
-- ORDER ITEMS TABLE
-- ======================================================

CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    
    order_id INT NOT NULL,
    
    product_id INT NOT NULL,
    
    quantity INT NOT NULL,
    
    price DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (order_id)
    REFERENCES orders(order_id)
    ON DELETE CASCADE,
    
    FOREIGN KEY (product_id)
    REFERENCES products(product_id)
    ON DELETE RESTRICT
);

-- ======================================================
-- INDEXES (FOR PERFORMANCE)
-- ======================================================

CREATE INDEX idx_products_category
ON products(category_id);

CREATE INDEX idx_orders_user
ON orders(user_id);

CREATE INDEX idx_cart_items_cart
ON cart_items(cart_id);

CREATE INDEX idx_order_items_order
ON order_items(order_id);

-- ======================================================
-- SAMPLE DATA
-- ======================================================

INSERT INTO categories (name, description) VALUES
('Cupcakes','Delicious handmade cupcakes'),
('Cakes','Fresh baked cakes'),
('Cookies','Chocolate and butter cookies'),
('Macarons','French macarons'),
('Donuts','Sweet donuts');

INSERT INTO users (full_name,email,password_hash,phone,address,role,credit_balance)
VALUES
('Admin User','admin@sweetdelights.com','hashedpassword','5551111','Admin Address','admin',0),
('Jane Doe','jane@example.com','hashedpassword','5550200','456 Dessert Lane','customer',500);

INSERT INTO products (name,description,price,stock_quantity,image_url,category_id)
VALUES
('Strawberry Cupcake','Fresh strawberry cupcake',4.99,50,'/images/cupcake.jpg',1),
('French Macarons','Colorful macarons',12.99,30,'/images/macarons.jpg',4),
('Chocolate Cake','Rich chocolate cake',24.99,15,'/images/cake.jpg',2),
('Chocolate Chip Cookies','Crispy cookies',8.99,40,'/images/cookies.jpg',3),
('Strawberry Donut','Sweet donut',3.49,60,'/images/donut.jpg',5);

-- ======================================================
-- CREATE CART FOR SAMPLE CUSTOMER
-- ======================================================

INSERT INTO carts (user_id) VALUES (2);

-- ======================================================
-- SAMPLE ORDER
-- ======================================================

INSERT INTO orders (user_id,total_price,status)
VALUES (2,46.97,'completed');

INSERT INTO order_items (order_id,product_id,quantity,price)
VALUES
(1,2,1,12.99),
(1,3,1,24.99),
(1,4,1,8.99);

-- ======================================================
-- END OF SCHEMA
-- ======================================================