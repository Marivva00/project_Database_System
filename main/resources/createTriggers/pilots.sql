CREATE OR REPLACE TRIGGER tr_inc_pilots BEFORE INSERT ON pilots FOR EACH ROW BEGIN SELECT seq_inc_pilots.NextVal INTO :new.PILOT_ID FROM dual; END;