package work.errorInDelete;

import work.Roles.role;
import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class errorInDeleteRecord extends JFrame {
    private JLabel message1;
    private JLabel message2;
    private JButton back;

    private role userRole;
    public errorInDeleteRecord(Connection conn, String tableName, Integer dep, role userRole){
        super("Работа с '" + tableName + "' таблицей");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;
        message1 = new JLabel("Ошибка при удалении записи из '" + tableName + "' таблицы.");
        message2 = new JLabel("Записи в других таблицах зависят от удаляемой. Выберите другую...");
        back = new JButton("Продолжить");

        back.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, dep, tableName, userRole);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 5, 5));
        JPanel button = new JPanel();

        button.add(back);
        panel.add(message1);
        panel.add(message2);
        panel.add(button);

        add(panel);
        setVisible(true);
    }
}
