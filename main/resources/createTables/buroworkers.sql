CREATE TABLE buroworkers( buroworker_id NUMBER(20) PRIMARY KEY, buroworker_lastname VARCHAR(60) NOT NULL, buroworker_firstname VARCHAR(60) NOT NULL, buroworker_middlename VARCHAR(60) NULL, buroworker_age NUMBER(2) NOT NULL, gen_id NUMBER(1) NULL, buroworker_child_count NUMBER(2) NULL, car_id NUMBER(20) NULL, stress_resistance NUMBER(1) NOT NULL CONSTRAINT stress_resistance_check CHECK(stress_resistance BETWEEN 0 AND 1), is_manager NUMBER(1) NOT NULL CONSTRAINT is_manager_check_b CHECK(is_manager BETWEEN 0 AND 1), FOREIGN KEY (gen_id) REFERENCES gender (gen_id), FOREIGN KEY (car_id) REFERENCES carriage (car_id))