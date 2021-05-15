package work.authorisation;

import org.apache.ibatis.jdbc.SQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class usersCreate extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private Vector<String> rolesVec;
    private JComboBox roles;
    private JButton enter;
    private JButton back;

    public usersCreate(Connection conn, String option){
        super(option);
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loginField = new JTextField(20);
        Color colorLog = loginField.getCaretColor();
        loginField.setForeground(Color.LIGHT_GRAY);
        loginField.setText("Введите логин...");
        loginField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event){
                loginField.setForeground(colorLog);
                if (loginField.getText().equals("Введите логин..."))
                    loginField.setText("");
            }
            @Override
            public void focusLost(FocusEvent event) {
                if (loginField.getText().isEmpty()) {
                    loginField.setForeground(Color.LIGHT_GRAY);
                    loginField.setText("Введите логин...");
                }
            }
        });

        passwordField = new JPasswordField(20);
        Color colorPas = passwordField.getCaretColor();
        passwordField.setForeground(Color.LIGHT_GRAY);
        passwordField.setText("пароль");
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event){
                passwordField.setForeground(colorPas);
                if (passwordField.getText().equals("пароль"))
                    passwordField.setText(null);
            }
            @Override
            public void focusLost(FocusEvent event) {
                if (passwordField.getPassword().toString().isEmpty()) {
                    loginField.setForeground(Color.LIGHT_GRAY);
                    loginField.setText("пароль");
                }
            }
        });

        rolesVec = new Vector<>();
        JPanel rolesPanel = new JPanel();

        if (option.equals("Регистрация")){
            rolesVec.add("администратор БД");
            rolesVec.add("администратор аэропорта");
            rolesVec.add("тех. работник");
            rolesVec.add("кассир");
            rolesVec.add("пассажир");
            roles = new JComboBox(rolesVec);
            rolesPanel.add(roles);
        }

        enter = new JButton("Подключиться");
        back = new JButton("Назад");

        addActionListeners(conn, option);

        JPanel loginPanel = new JPanel();
        loginPanel.add(loginField);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordField);
        JPanel buttons = new JPanel();
        buttons.add(back);
        buttons.add(enter);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(loginPanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(rolesPanel);
        mainPanel.add(buttons);
    }
    private void addActionListeners(Connection conn, String option){
        if (option.equals("Авторизация")){
            String select = "SELECT password_my, role_my FROM users_myusers WHERE(name_my = " + loginField.getText() + ")";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(select);
                ResultSet tmp = preparedStatement.executeQuery();
                String password = tmp.getString(1);
                String role = tmp.getString(2);
                System.out.println(password + " " + role);
            } catch (SQLException exception){

            }
        } else {

        }
    }
}
