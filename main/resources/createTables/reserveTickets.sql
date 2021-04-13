CREATE TABLE reserveTickets ( reserve_id NUMBER(20) PRIMARY KEY, ticket_id NUMBER(15) NOT NULL, is_paid NUMBER(1) NOT NULL, paid_date DATE NULL, CONSTRAINT paid CHECK (is_paid BETWEEN 0 AND 1), FOREIGN KEY (ticket_id) REFERENCES tickets (ticket_id))