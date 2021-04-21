package work.authorisation;

import javax.swing.*;
import java.sql.Connection;

public class ErrorAuthorisationWindow extends JFrame {
    JLabel message;
    JButton ok;
    public ErrorAuthorisationWindow(Connection conn, String url){
        super("Ошибка");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        message = new JLabel("Ошибка во время подключения, попробуйте еще раз...");
        ok = new JButton("Продолжить");

        ok.addActionListener((e)->{
            setVisible(false);
            new GetLogInfo(conn, url);
        });

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));

        JPanel label = new JPanel();
        label.add(message);
        JPanel button = new JPanel();
        button.add(ok);

        main.add(label);
        main.add(button);
        add(main);
        setVisible(true);
    }
}
