CREATE OR REPLACE TRIGGER tr_inc_planes BEFORE INSERT ON planes FOR EACH ROW BEGIN SELECT seq_inc_planes.NextVal INTO :new.PLANE_ID FROM dual; END;