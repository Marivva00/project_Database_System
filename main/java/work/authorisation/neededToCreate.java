package work.authorisation;

import javax.swing.*;
import java.sql.Connection;

public class neededToCreate extends JFrame {
    private JButton registration;
    private JButton authorisation;

    public neededToCreate(Connection conn){
        super("Авторизоваться или войти");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        registration = new JButton("зарегистрироваться");
        authorisation = new JButton("авторизоваться");

        registration.addActionListener((e)->{
            setVisible(false);
            new usersCreate(conn, "Регистрация");
        });
        authorisation.addActionListener((e)->{
            setVisible(false);
            new usersCreate(conn, "Авторизация");
        });

        JPanel registrationPanel = new JPanel();
        registrationPanel.add(registration);
        JPanel authorisationPanel = new JPanel();
        authorisationPanel.add(authorisation);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(registrationPanel);
        mainPanel.add(authorisationPanel);

        add(mainPanel);
        setVisible(true);
    }
}
