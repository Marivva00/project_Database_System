BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE seq_inc_ti'; EXCEPTION  WHEN OTHERS THEN IF SQLCODE != -2289 THEN  RAISE; END IF; END;