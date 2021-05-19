package work.Roles;

import work.lookOnTable.lookOnTableView;
import work.requests.requestsWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Vector;

public class cashierRoleWindow extends JFrame{
    private JButton tickets;
    private JButton reserveTickets;
    private JButton addPassenger;
    private JButton requests;

    public cashierRoleWindow(Connection conn) {
        super("Действия кассира в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tickets = new JButton("продать билет/вернуть билет");
        reserveTickets = new JButton("забронировать билет");
        addPassenger = new JButton("добавить пассажира");
        requests = new JButton("запросы в информационной системе");

        addActionListeners(conn);

        JPanel ticketsPanel = new JPanel();
        ticketsPanel.add(tickets);
        JPanel reserveTicketsPanel = new JPanel();
        reserveTicketsPanel.add(reserveTickets);
        JPanel passengerPanel = new JPanel();
        passengerPanel.add(addPassenger);
        JPanel requestsPanel = new JPanel();
        requestsPanel.add(requests);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(ticketsPanel);
        mainPanel.add(reserveTicketsPanel);
        mainPanel.add(passengerPanel);
        mainPanel.add(requestsPanel);

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
        addPassenger.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "passengers", role.cashier);
        });
        requests.addActionListener((e)->{
            setVisible(false);
            Vector strings = new Vector();
            Vector tmp1 = new Vector();
            tmp1.add("1");
            tmp1.add("Получить перечень и общее число авиаpейсов указанной категоpии, в определенном напpавлении, с указанным типом самолета.");
            strings.add(tmp1);
            Vector tmp2 = new Vector();
            tmp2.add("2");
            tmp2.add("Получить перечень и общее число пассажиpов на данном pейсе, улетевших в указанный день, улетевших за гpаницу в указанный день, по пpизнаку сдачи вещей в багажное отделение, по половому пpизнаку, по возpасту.");
            strings.add(tmp2);
            Vector tmp3 = new Vector();
            tmp3.add("3");
            tmp3.add("Получить перечень и общее число свободных и забpониpованных мест на указанном pейсе, на опреденный день, по указанному маpшpуту, по цене, по вpемени вылета.");
            strings.add(tmp3);
            new requestsWindow(conn, role.cashier, strings);
        });
    }
}
