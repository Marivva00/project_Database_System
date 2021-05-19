package work.authorisation;

import work.MainMenuWindow;
import work.Roles.role;

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
    private JLabel login;
    private JLabel password;

    private Integer error = 0;

    public usersCreate(Connection conn, String option){
        super(option);
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        login = new JLabel("Логин:");
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

        password = new JLabel("Пароль:");
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

        enter = new JButton("ОК");
        if (option.equals("Регистрация"))
            back = new JButton("Назад");

        addActionListeners(conn, option);

        JPanel loginPanel = new JPanel();
        loginPanel.add(login);
        loginPanel.add(loginField);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(password);
        passwordPanel.add(passwordField);
        JPanel buttons = new JPanel();
        if (option.equals("Регистрация"))
            buttons.add(back);
        buttons.add(enter);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(loginPanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(rolesPanel);
        mainPanel.add(buttons);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListeners(Connection conn, String option){
        if (option.equals("Регистрация"))
            back.addActionListener((e)->{
                setVisible(false);
                new MainMenuWindow(conn, role.adminBD);
            });
        enter.addActionListener((e)->{
            if (option.equals("Авторизация")){
                String select = "SELECT password_my, role_my FROM users_myusers WHERE(name_my = '" + loginField.getText() + "')";
                String password = null;
                String role = null;
                PreparedStatement preparedStatement = null;
                ResultSet tmp = null;
                try {
                    preparedStatement = conn.prepareStatement(select);
                    tmp = preparedStatement.executeQuery();
                    while(tmp.next()) {
                        password = tmp.getString(1);
                        role = tmp.getString(2);
                    }
                } catch (SQLException exception){
                    exception.printStackTrace();
                }
                if (password.equals(new String(passwordField.getPassword()))){
                    setVisible(false);
                    try {
                        preparedStatement.close();
                        tmp.close();
                    } catch (SQLException exception){
                        exception.printStackTrace();
                    }
                    new messageWindow(conn, "Авторизация прошла успешно", role);
                } else
                    new errorMessageWindow(conn, "Ошибка авторизации, попробуйте снова...", 1);
            } else {
                String userName = loginField.getText();
                String userPassword = new String(passwordField.getPassword());
                String tmp = roles.getSelectedItem().toString();
                String userRole = null;
                switch (tmp){
                    case "администратор БД": userRole = "admin_bd"; break;
                    case "администратор аэропорта": userRole = "admin_hr"; break;
                    case "тех. работник": userRole = "technic"; break;
                    case "кассир": userRole = "cashier_r"; break;
                    case "пассажир": userRole = "passenger"; break;
                }
                String insert = "INSERT INTO users_myusers(name_my, password_my, role_my) VALUES ('" + userName + "', '" + userPassword + "', '" + userRole + "')";
                String createUser = "CREATE USER \"" + userName + "\" IDENTIFIED BY \"" + userPassword + "\" DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP";
                String connection = "GRANT connect to \"" + userName+ "\"";
                String setRole = "GRANT " + userRole + " to \"" + userName + "\"";
                try {
                    PreparedStatement preparedStatementI = conn.prepareStatement(insert);
                    preparedStatementI.executeUpdate(insert);
                    preparedStatementI.close();

                    PreparedStatement preparedStatementCr = conn.prepareStatement(createUser);
                    preparedStatementCr.executeUpdate(createUser);
                    preparedStatementCr.close();

                    PreparedStatement preparedStatementCo = conn.prepareStatement(connection);
                    preparedStatementCo.executeUpdate(connection);
                    preparedStatementCo.close();

                    PreparedStatement preparedStatementS = conn.prepareStatement(setRole);
                    preparedStatementS.executeUpdate(setRole);
                    preparedStatementS.close();
                    conn.commit();
                    setVisible(false);
                    new messageWindow(conn, "Регистрация прошла успешно", userRole);
                } catch (SQLException exception){
                    exception.printStackTrace();
                    setVisible(false);
                    new errorMessageWindow(conn, "Ошибка при регистрации", 2);
                }
            }
        });
    }
}
