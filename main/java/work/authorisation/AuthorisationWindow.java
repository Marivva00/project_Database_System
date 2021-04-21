package work.authorisation;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class AuthorisationWindow extends JFrame {
    private JLabel connectTo;
    private JButton toLocalBase;
    private JButton toUniBase;
    public AuthorisationWindow(Connection conn){
        super("Авторизация");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        connectTo = new JLabel("Подключиться к:");
        toLocalBase = new JButton("локальному серверу");
        toUniBase = new JButton("серверу НГУ");

        JPanel authorisationPanel = new JPanel();
        authorisationPanel.setLayout(new BoxLayout(authorisationPanel, BoxLayout.PAGE_AXIS));
        JPanel connectionPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        connectionPanel.add(connectTo);
        buttonsPanel.add(toLocalBase);
        buttonsPanel.add(toUniBase);

        authorisationPanel.add(connectionPanel);
        authorisationPanel.add(buttonsPanel);

        add(authorisationPanel);
        setVisible(true);
        initListeners(conn);
    }
    private void initListeners(Connection conn){
        toLocalBase.addActionListener((e)->{
            setVisible(false);
            String url = "jdbc:oracle:thin:@0.0.0.0:1521:";
            new GetLogInfo(conn, url);
        });
        toUniBase.addActionListener((e)->{
            setVisible(false);
            String url = "jdbc:oracle:thin:@84.237.50.81:1521:XE";
            Properties props = new Properties();
            props.setProperty("user", "18209_Pogrebnikova_Airport");
            props.setProperty("password", "somepassword123");
            TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
            TimeZone.setDefault(timeZone);
            Locale.setDefault(Locale.ENGLISH);
            getConnection(conn, url, props);
        });
    }
    private void getConnection(Connection conn, String url, Properties props) {
        int error = 0;
        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException exception) {
            error = 1;
            exception.printStackTrace();
            setVisible(false);
            new ErrorAuthorisationWindow(conn, url);
        }
        if (error == 0) {
            setVisible(false);
            SuccessConnectionInfoWindow success = new SuccessConnectionInfoWindow(conn);
        }
    }
}
