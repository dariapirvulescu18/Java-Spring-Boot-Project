
CREATE TABLE IF NOT EXISTS HotelPackage (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    breakfast_included BOOLEAN,
    spa_included BOOLEAN,
    pool_included BOOLEAN
);

CREATE TABLE IF NOT EXISTS Room (
    id INT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(20) NOT NULL,
    price DOUBLE NOT NULL,
    capacity INT NOT NULL


);

CREATE TABLE IF NOT EXISTS Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    phone_number VARCHAR(20) NOT NULL
);


CREATE TABLE Employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50)
);

CREATE TABLE EmployeeRoom (
    employee_id INT NOT NULL,
    room_id INT NOT NULL,
    PRIMARY KEY (employee_id, room_id),
    CONSTRAINT fk_employee_room_employee
        FOREIGN KEY (employee_id)
        REFERENCES Employee(id),
    CONSTRAINT fk_employee_room_room
        FOREIGN KEY (room_id)
        REFERENCES Room(id)
);

CREATE TABLE Reservation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    room_id INT NOT NULL,
    customer_id INT NOT NULL,
    hotel_package_id INT DEFAULT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_reservation_room
        FOREIGN KEY (room_id)
        REFERENCES Room(id),
    CONSTRAINT fk_reservation_customer
        FOREIGN KEY (customer_id)
        REFERENCES Customer(id),

    CONSTRAINT fk_room_package
        FOREIGN KEY (hotel_package_id)
        REFERENCES HotelPackage(id)
);

CREATE TABLE Review (
    id INT PRIMARY KEY AUTO_INCREMENT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(500),
    date DATE NOT NULL,
    room_id INT NOT NULL,
    customer_id INT NOT NULL,
    CONSTRAINT fk_review_room
        FOREIGN KEY (room_id)
        REFERENCES Room(id),
    CONSTRAINT fk_review_customer
        FOREIGN KEY (customer_id)
        REFERENCES Customer(id)
);

INSERT INTO Room (number, price, capacity) VALUES ('101', 150.0, 2);
INSERT INTO Room (number, price, capacity) VALUES ('102', 200.0, 3);
INSERT INTO Room (number, price, capacity) VALUES ('103', 180.0, 2);
INSERT INTO Room (number, price, capacity) VALUES ('104', 220.0, 4);
INSERT INTO Room (number, price, capacity) VALUES ('105', 250.0, 3);

INSERT INTO HotelPackage (name, breakfast_included, spa_included, pool_included)
VALUES ('Standard Package', TRUE, FALSE, FALSE);
INSERT INTO HotelPackage (name, breakfast_included, spa_included, pool_included)
VALUES ('Relax Package', TRUE, TRUE, FALSE);
INSERT INTO HotelPackage (name, breakfast_included, spa_included, pool_included)
VALUES ('Luxury Package', TRUE, TRUE, TRUE);
INSERT INTO HotelPackage (name, breakfast_included, spa_included, pool_included)
VALUES ('Poolside Package', FALSE, FALSE, TRUE);
INSERT INTO HotelPackage (name, breakfast_included, spa_included, pool_included)
VALUES ('Business Package', TRUE, FALSE, FALSE);


INSERT INTO Employee (name, role) VALUES ('John Smith', 'Receptionist');
INSERT INTO Employee (name, role) VALUES ('Maria Popescu', 'Housekeeping');
INSERT INTO Employee (name, role) VALUES ('Alex Ionescu', 'Manager');
INSERT INTO Employee (name, role) VALUES ('Ioana Georgescu', 'Receptionist');
INSERT INTO Employee (name, role) VALUES ('Robert Enache', 'Housekeeping');


INSERT INTO Customer (name, email, phone_number) VALUES ('Ana Popescu', 'ana.popescu@gmail.com', '+40741234567');
INSERT INTO Customer (name, email, phone_number) VALUES ('Mihai Ionescu', 'mihai.ionescu@yahoo.com', '+40741234568');
INSERT INTO Customer (name, email, phone_number) VALUES ('Ioana Vasilescu', 'ioana.vasilescu@gmail.com', '+40741234569');
INSERT INTO Customer (name, email, phone_number) VALUES ('Andrei Georgescu', 'andrei.georgescu@hotmail.com', '+40741234570');
INSERT INTO Customer (name, email, phone_number) VALUES ('Cristina Dumitrescu', 'cristina.dumitrescu@gmail.com', '+40741234571');

