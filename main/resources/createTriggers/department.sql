CREATE OR REPLACE TRIGGER tr_inc_department BEFORE INSERT ON department FOR EACH ROW BEGIN SELECT seq_inc_department.NextVal INTO :new.DEP_ID FROM dual; END;