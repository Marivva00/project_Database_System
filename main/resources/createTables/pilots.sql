CREATE TABLE pilots( pilot_id NUMBER(20) PRIMARY KEY, pilot_lastname VARCHAR(60) NOT NULL, pilot_firstname VARCHAR(60) NOT NULL, pilot_middlename VARCHAR(60) NULL, pilot_age NUMBER(2) NOT NULL, gen_id NUMBER(1) NOT NULL, pilot_child_count NUMBER(2) NULL, car_id NUMBER(20) NOT NULL, med_id NUMBER(20) NOT NULL, is_manager NUMBER(1) NOT NULL CONSTRAINT is_manager_check_p CHECK(is_manager BETWEEN 0 AND 1), FOREIGN KEY (gen_id) REFERENCES gender (gen_id), FOREIGN KEY (car_id) REFERENCES carriage (car_id), FOREIGN KEY (med_id) REFERENCES medical (med_id))