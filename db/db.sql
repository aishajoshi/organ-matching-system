CREATE DATABASE IF NOT EXISTS oms;
use oms;
create table user
(
    user_id    VARCHAR(36) PRIMARY KEY,
    full_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NULL,
    phone      VARCHAR(15)  NULL,
    password   VARCHAR(255) NOT NULL,
    user_type  varchar(30)  NOT NULL,
    role       VARCHAR(30)  NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
create table donor
(
    donor_id    VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    dob         DATE         NULL,
    age         INT          NOT NULL,
    blood_group CHAR(10)      NULL,
    email       VARCHAR(100) NULL,
    phone       VARCHAR(15)  NULL,
    status      VARCHAR(50)  NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

);
CREATE TABLE recipient
(
    recipient_id   VARCHAR(36) PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    dob            DATE NULL,
    age            INT NOT NULL,
    blood_group    CHAR(10) NOT NULL,
    required_organ VARCHAR(50) NULL,
    email          VARCHAR(100) NULL,
    phone          VARCHAR(15) NULL,
    urgency_level  VARCHAR(50) NULL,
    status         VARCHAR(50) NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


);
CREATE TABLE organ
(
    organ_id      VARCHAR(36) PRIMARY KEY,
    type          VARCHAR(50)  NOT NULL,
    description   VARCHAR(100) NULL,
    status        VARCHAR(50)  NULL,
    donor_id      VARCHAR(36),
    donated_date  TIMESTAMP    NULL,
    recipient_id  VARCHAR(36),
    received_date TIMESTAMP    NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (donor_id) REFERENCES donor (donor_id)
);

