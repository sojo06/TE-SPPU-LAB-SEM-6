-- MySQL setup script
-- 1) Create database
CREATE DATABASE IF NOT EXISTS ebookdb;
USE ebookdb;

-- 2) Create table
DROP TABLE IF EXISTS ebookshop;
CREATE TABLE ebookshop (
  book_id INT PRIMARY KEY,
  book_title VARCHAR(100) NOT NULL,
  book_author VARCHAR(100) NOT NULL,
  book_price DECIMAL(10,2) NOT NULL,
  quantity INT NOT NULL
);

-- 3) Insert sample data
INSERT INTO ebookshop (book_id, book_title, book_author, book_price, quantity) VALUES
(1, 'Java Basics', 'John Doe', 350.00, 10),
(2, 'Servlet & JSP', 'Jane Smith', 450.00, 7),
(3, 'Database Systems', 'A. Kumar', 550.00, 5);
