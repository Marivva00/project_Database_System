package work.authorisation;

import work.GUI.MainMenuWindow;
import work.Roles.*;

import javax.swing.*;
import java.sql.Connection;

public class messageWindow extends JFrame {
    private JButton ok;
    private JLabel info;

    public messageWindow(Connection conn, String message, String role){
        super("Информация");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        info = new JLabel(message);
        ok = new JButton("Продолжить...");
        ok.addActionListener((e)->{
            setVisible(false);
            if (message.equals("Авторизация прошла успешно")){
                switch (role){
                    case "admin_bd": new MainMenuWindow(conn, work.Roles.role.adminBD); break;
                    case "admin_hr": new adminRoleWindow(conn); break;
                    case "cashier_r": new cashierRoleWindow(conn); break;
                    case "technic": new technicRoleWindow(conn); break;
                    case "passenger": new passengerRoleWindow(conn); break;
                }
            } else
                new MainMenuWindow(conn, work.Roles.role.adminBD);
        });

        JPanel infoPanel = new JPanel();
        infoPanel.add(info);
        JPanel okPanel = new JPanel();
        okPanel.add(ok);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(infoPanel);
        mainPanel.add(okPanel);

        add(mainPanel);
        setVisible(true);
    }
}
