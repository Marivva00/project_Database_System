package work.authorisation;

import javax.swing.*;
import java.sql.Connection;

public class errorMessageWindow extends JFrame {
    private JLabel messageLabel;
    private JButton ok;

    public errorMessageWindow(Connection conn, String message, Integer what){
        super("Ошибка");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        messageLabel = new JLabel(message);
        ok = new JButton("Повторить");

        ok.addActionListener((e)->{
            setVisible(false);
            if (what == 1)
                new usersCreate(conn, "Авторизация");
            else
                new usersCreate(conn, "Регистрация");
        });

        JPanel labelPanel = new JPanel();
        labelPanel.add(messageLabel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ok);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(messageLabel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }
}
