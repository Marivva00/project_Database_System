CREATE TABLE planes ( plane_id NUMBER(20) PRIMARY KEY, plane_type VARCHAR(30) NOT NULL, ti_id NUMBER(20) NOT NULL, plane_passengers_max NUMBER(4) NOT NULL, airport_id NUMBER(5) NOT NULL, trip_count NUMBER(4) NULL, repairing_count NUMBER(4) NULL, plane_age NUMBER(2) NOT NULL, FOREIGN KEY (ti_id) REFERENCES technicalInspection (ti_id), FOREIGN KEY (airport_id) REFERENCES airport (airport_id))