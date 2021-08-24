DROP TABLE IF EXISTS Articles;

CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE Articles (
    id INT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    description VARCHAR(250),
    price DECIMAL NOT NULL
);
