package work.authorisation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class GetLogInfo extends JFrame {
    //private JLabel login;
    //private JLabel password;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton enter;
    private JButton back;
    public GetLogInfo(Connection conn, String url){
        super("Log in info");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loginField = new JTextField(20);
        Color colorLog = loginField.getCaretColor();
        loginField.setForeground(Color.LIGHT_GRAY);
        loginField.setText("Enter your login here...");
        loginField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event){
                loginField.setForeground(colorLog);
                if (loginField.getText().equals("Enter your login here..."))
                    loginField.setText("");
            }
            @Override
            public void focusLost(FocusEvent event) {
                if (loginField.getText().isEmpty()) {
                    loginField.setForeground(Color.LIGHT_GRAY);
                    loginField.setText("Enter your login here...");
                }
            }
        });

        passwordField = new JPasswordField(20);
        Color colorPas = passwordField.getCaretColor();
        passwordField.setForeground(Color.LIGHT_GRAY);
        passwordField.setText("password");
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event){
                passwordField.setForeground(colorPas);
                if (passwordField.getText().equals("password"))
                    passwordField.setText(null);
            }
            @Override
            public void focusLost(FocusEvent event) {
                if (passwordField.getPassword().toString().isEmpty()) {
                    loginField.setForeground(Color.LIGHT_GRAY);
                    loginField.setText("password");
                }
            }
        });

        enter = new JButton("Enter");
        back = new JButton("Back");

        JPanel authorisationPanel = new JPanel();
        authorisationPanel.setLayout(new BoxLayout(authorisationPanel, BoxLayout.PAGE_AXIS));

        JPanel loginPanel = new JPanel();
        loginPanel.add(loginField);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordField);
        JPanel buttons = new JPanel();
        buttons.add(back);
        buttons.add(enter);

        enter.addActionListener((e)->{
            String loginStr = loginField.getText();                 //18209_Pogrebnikova_Airport - для университетского; mari - для локального
            String passwordStr = new String(passwordField.getPassword());   //somepassword123 для университетского
            Properties props = new Properties();
            props.setProperty("user", loginStr);
            props.setProperty("password", passwordStr);
            TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
            TimeZone.setDefault(timeZone);
            Locale.setDefault(Locale.ENGLISH);
            getConnection(conn, url, props);
        });
        back.addActionListener((e)->{
            setVisible(false);
            new AuthorisationWindow(conn);
        });

        authorisationPanel.add(loginPanel);
        authorisationPanel.add(passwordPanel);
        authorisationPanel.add(buttons);

        add(authorisationPanel);
        setVisible(true);
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
