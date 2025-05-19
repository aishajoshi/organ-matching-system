create  database oms;
use oms;
create table user
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type ENUM('Donor', 'Recipient', 'Admin') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
create table donor
(
    DonorID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Age INT NOT NULL,
    BloodType CHAR(5) NOT NULL,
    Contact VARCHAR(100) NOT NULL,
    Status VARCHAR(50) NOT NULL

);
CREATE TABLE recipients (
RecipientID INT PRIMARY KEY AUTO_INCREMENT,
Name VARCHAR(100) NOT NULL,
Age INT NOT NULL,
BloodType CHAR(5) NOT NULL,
RequiredOrgan VARCHAR(50) NOT NULL,
Contact VARCHAR(100) NOT NULL,
UrgencyLevel VARCHAR(50) NOT NULL


);
CREATE TABLE Organ (
 OrganID INT PRIMARY KEY AUTO_INCREMENT,
 Type VARCHAR(50) NOT NULL,
 AvailabilityStatus VARCHAR(50) NOT NULL,
 DonorID INT,
 FOREIGN KEY (DonorID) REFERENCES Donor(DonorID)
);







