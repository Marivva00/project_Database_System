CREATE OR REPLACE TRIGGER tr_inc_carriage BEFORE INSERT ON carriage FOR EACH ROW BEGIN SELECT seq_inc_carriage.NextVal INTO :new.CAR_ID FROM dual; END;