CREATE TABLE carriage( car_id NUMBER(20) PRIMARY KEY, dep_id NUMBER(15) NOT NULL, FOREIGN KEY (dep_id) REFERENCES department (dep_id))