package work.requests;

import work.Roles.role;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class infoRequestWindow extends JFrame {
    private JButton back;
    private JLabel info;

    private role userRole;

    public infoRequestWindow(Connection conn, role userRole){
        super("Справка");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;

        back = new JButton("Назад");
        back.addActionListener((e)->{
            setVisible(false);
            new requestsWindow(conn, userRole);
        });

        info = new JLabel("Выберите интересующий вас запрос в списке, нажмите 'Выполнить'");

        JPanel infoPanel = new JPanel();
        infoPanel.add(info);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(back);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(infoPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }
}
