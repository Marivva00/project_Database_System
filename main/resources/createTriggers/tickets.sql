CREATE OR REPLACE TRIGGER tr_inc_tickets BEFORE INSERT ON tickets FOR EACH ROW BEGIN SELECT seq_inc_tickets.NextVal INTO :new.TICKET_ID FROM dual; END;