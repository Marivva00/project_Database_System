CREATE OR REPLACE TRIGGER tr_inc_workers BEFORE INSERT ON workers FOR EACH ROW BEGIN SELECT seq_inc_workers.NextVal INTO :new.WORKER_ID FROM dual; END;