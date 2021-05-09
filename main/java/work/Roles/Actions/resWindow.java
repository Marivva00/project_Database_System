package work.Roles.Actions;

import work.Roles.passengerRoleWindow;
import work.requests.requestsWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Vector;

public class resWindow extends JFrame {
    private JButton back;

    public resWindow(Connection conn, Vector columnNames, Vector strings){
        super("Информация о билете");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTable table = new JTable(strings,columnNames);

        back = new JButton("Назад");
        back.addActionListener((e)->{
            setVisible(false);
            new passengerRoleWindow(conn);
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(back);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel);
        add(mainPanel);
        setVisible(true);
    }
}
