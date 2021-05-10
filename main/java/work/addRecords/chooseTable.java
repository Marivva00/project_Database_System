package work.addRecords;

import work.Roles.role;
import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.sql.Connection;

public class chooseTable extends JFrame {
    private JButton pilot;
    private JButton dispetcher;
    private JButton tehworker;
    private JButton cashier;
    private JButton security;
    private JButton buroworker;
    private JButton back;

    public chooseTable(Connection conn, role userRole){
        super("Добавить работника");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pilot = new JButton("добавить пилота");
        dispetcher = new JButton("добавить диспетчера");
        tehworker = new JButton("добавить тех.работника");
        cashier = new JButton("добавить кассира");
        security = new JButton("добавить охранника");
        buroworker = new JButton("добавить служащего бюро");
        back = new JButton("Назад");

        addActionListeners(conn, userRole);

        JPanel pilotPanel = new JPanel();
        pilotPanel.add(pilot);
        JPanel dispetcherPanel = new JPanel();
        dispetcherPanel.add(dispetcher);
        JPanel tehworkerPanel = new JPanel();
        tehworkerPanel.add(tehworker);
        JPanel cashierPanel = new JPanel();
        cashierPanel.add(cashier);
        JPanel securityPanel = new JPanel();
        securityPanel.add(security);
        JPanel buroworkerPanel = new JPanel();
        buroworkerPanel.add(buroworker);
        JPanel backPanel = new JPanel();
        backPanel.add(back);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(pilotPanel);
        mainPanel.add(dispetcherPanel);
        mainPanel.add(tehworkerPanel);
        mainPanel.add(cashierPanel);
        mainPanel.add(securityPanel);
        mainPanel.add(buroworkerPanel);
        mainPanel.add(backPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListeners(Connection conn, role userRole){
        back.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "workers", userRole);
        });
        pilot.addActionListener((e)->{
            setVisible(false);
            new addRecordsToTable(conn, 1, "pilots", userRole);
        });
        dispetcher.addActionListener((e)->{
            setVisible(false);
            new addRecordsToTable(conn, 1, "dispetchers", userRole);
        });
        tehworker.addActionListener((e)->{
            setVisible(false);
            new addRecordsToTable(conn, 1, "tehworkers", userRole);
        });
        cashier.addActionListener((e)->{
            setVisible(false);
            new addRecordsToTable(conn, 1, "cashiers", userRole);
        });
        security.addActionListener((e)->{
            setVisible(false);
            new addRecordsToTable(conn, 1, "securities", userRole);
        });
        buroworker.addActionListener((e)->{
            setVisible(false);
            new addRecordsToTable(conn, 1, "buroworkers", userRole);
        });
    }
}
