-- Create the database
CREATE DATABASE IF NOT EXISTS EventManagement;

-- Use the created database
USE EventManagement;

-- Create the Events table with necessary columns
CREATE TABLE IF NOT EXISTS Events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(15),
    event_date DATE NOT NULL,
    event_time TIME NOT NULL,
    expected_people INT,
    event_venue VARCHAR(50),
    event_theme VARCHAR(50),
    extra_programs VARCHAR(50),
    event_type VARCHAR(50),
    dress_code VARCHAR(50),
    food_type VARCHAR(50),
    cuisine_type VARCHAR(50),
    transportation_for_event VARCHAR(50),
    transportation_for_guests VARCHAR(50),
    accommodation_for_guests VARCHAR(50),
    payment_method VARCHAR(50),
    event_status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS CLIENTS (
    Client_ID INT AUTO_INCREMENT PRIMARY KEY,
    Client_Name VARCHAR(100) NOT NULL,
    Contact_Number VARCHAR(15) NOT NULL UNIQUE
);


