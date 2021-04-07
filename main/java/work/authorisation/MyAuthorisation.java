package work.authorisation;

import work.authorisation.AuthorisationWindow;

import java.sql.Connection;
import java.sql.SQLException;

public class MyAuthorisation {
    public Connection conn;
    public MyAuthorisation() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver is not found");
            e.printStackTrace();
        }
        AuthorisationWindow authorisationWindow = new AuthorisationWindow(conn);
    }
}
