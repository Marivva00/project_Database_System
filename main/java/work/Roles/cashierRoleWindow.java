package work.Roles;

import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class cashierRoleWindow extends JFrame{
    private JButton tickets;
    private JButton reserveTickets;
    private JButton requests;
    private JButton back;

    public cashierRoleWindow(Connection conn) {
        super("Действия кассира в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tickets = new JButton("продать билет/вернуть билет");
        reserveTickets = new JButton("забронировать билет");
        requests = new JButton("запросы в информационной системе");
        back = new JButton("Назад");

        addActionListeners(conn);

        JPanel ticketsPanel = new JPanel();
        ticketsPanel.add(tickets);
        JPanel reserveTicketsPanel = new JPanel();
        reserveTicketsPanel.add(reserveTickets);
        JPanel requestsPanel = new JPanel();
        requestsPanel.add(requests);
        JPanel backPanel = new JPanel();
        backPanel.add(back);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(ticketsPanel);
        mainPanel.add(reserveTicketsPanel);
        mainPanel.add(requestsPanel);
        mainPanel.add(backPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListeners(Connection conn){
        tickets.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "tickets", role.cashier);
        });
        reserveTickets.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "reserveTickets", role.cashier);
        });

        back.addActionListener((e)->{
            setVisible(false);
            new roleWindow(conn);
        });
    }
}
