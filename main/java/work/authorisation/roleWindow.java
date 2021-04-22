package work.authorisation;

import work.MainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class roleWindow extends JFrame {
    private JButton adminBD;
    private JButton admin;
    private JButton cashier;
    private JButton passenger;
    private JButton technic;

    public roleWindow(Connection conn){
        super("Роль в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        adminBD = new JButton("Администратор БД");
        admin = new JButton("Администрация");
        cashier = new JButton("Кассир");
        technic = new JButton("Тех. работник");
        passenger = new JButton("Пассажир");

        adminBD.addActionListener((e)->{
            actionDo(conn, role.adminBD);
        });
        admin.addActionListener((e)->{
            actionDo(conn, role.admin);
        });
        cashier.addActionListener((e)->{
            actionDo(conn, role.cashier);
        });
        technic.addActionListener((e)->{
            actionDo(conn, role.technic);
        });
        passenger.addActionListener((e)->{
            actionDo(conn, role.passenger);
        });

        JPanel adminBDPanel = new JPanel();
        adminBDPanel.add(adminBD);
        JPanel adminPanel = new JPanel();
        adminPanel.add(admin);
        JPanel cashierPanel = new JPanel();
        cashierPanel.add(cashier);
        JPanel technicPanel = new JPanel();
        technicPanel.add(technic);
        JPanel passengerPanel = new JPanel();
        passengerPanel.add(passenger);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(adminBDPanel);
        mainPanel.add(adminPanel);
        mainPanel.add(cashierPanel);
        mainPanel.add(technicPanel);
        mainPanel.add(passengerPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void actionDo(Connection conn, role userRole){
        setVisible(false);
        new MainMenuWindow(conn, userRole);
    }
}
