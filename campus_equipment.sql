CREATE DATABASE IF NOT EXISTS campus_equipment;
USE campus_equipment;

DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('STUDENT','ADMIN') NOT NULL
);

CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE equipment (
    equipment_id INT PRIMARY KEY AUTO_INCREMENT,
    equipment_name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    total_quantity INT NOT NULL,
    available_quantity INT NOT NULL,
    status VARCHAR(30) DEFAULT 'Available',
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    equipment_id INT NOT NULL,
    booking_date DATE NOT NULL,
    return_date DATE NOT NULL,
    quantity INT NOT NULL,
    status ENUM('Pending','Approved','Rejected','Cancelled','Returned') DEFAULT 'Pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id)
);

INSERT INTO users(name,email,password,role) VALUES
('Administrator','admin@campus.com','admin123','ADMIN'),
('Student One','student@campus.com','student123','STUDENT');

INSERT INTO categories(category_name) VALUES
('Electronics'),('Robotics'),('Photography'),('Audio Visual');

INSERT INTO equipment(equipment_name,category_id,total_quantity,available_quantity,status) VALUES
('Laptop',1,10,10,'Available'),
('Arduino Uno',2,15,15,'Available'),
('DSLR Camera',3,5,5,'Available'),
('Projector',4,6,6,'Available'),
('Microphone',4,8,8,'Available');
