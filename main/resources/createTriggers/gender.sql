CREATE OR REPLACE TRIGGER tr_inc_gender BEFORE INSERT ON gender FOR EACH ROW BEGIN SELECT seq_inc_gender.NextVal INTO :new.GEN_ID FROM dual; END;