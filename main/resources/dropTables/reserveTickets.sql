BEGIN    EXECUTE IMMEDIATE 'DROP TABLE reserveTickets';EXCEPTION   WHEN OTHERS THEN      IF SQLCODE != -942 THEN         RAISE;      END IF;END;