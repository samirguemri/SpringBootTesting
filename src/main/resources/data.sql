DROP TABLE IF EXISTS Articles;

CREATE TABLE Articles (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    description VARCHAR(250),
    price LONG NOT NULL
);

INSERT INTO Articles (id, name, description, price) VALUES
    (1, 'Apple MacBookAir M1', 'Laptop i7 8Go 512SSD 13"', '1399'),
    (2, 'Huawei MateBook D14', 'Laptop i7 16Go 512SSD 14"', '1249'),
    (3, 'Lenovo ThinkBook 15 G2', 'Laptop Ryzen5 8Go 512SSD 15.6"', '899');
