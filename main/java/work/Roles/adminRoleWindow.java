package work.Roles;

import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class adminRoleWindow extends JFrame {
    private JButton trips;
    private JButton timetable;
    private JButton workers;
    private JButton requests;
    private JButton back;

    public adminRoleWindow(Connection conn) {
        super("Действия администрации в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        trips = new JButton("работа с рейсами");
        timetable = new JButton("работа с расписанием");
        workers = new JButton("работа с работниками аэропорта");
        requests = new JButton("запросы в информационной системе");
        back = new JButton("Назад");

        addActionListeners(conn);

        JPanel tripsPanel = new JPanel();
        tripsPanel.add(trips);
        JPanel timetablePanel = new JPanel();
        timetablePanel.add(timetable);
        JPanel workersPanel = new JPanel();
        workersPanel.add(workers);
        JPanel requestsPanel = new JPanel();
        requestsPanel.add(requests);
        JPanel backPanel = new JPanel();
        backPanel.add(back);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(tripsPanel);
        mainPanel.add(timetablePanel);
        mainPanel.add(workersPanel);
        mainPanel.add(requestsPanel);
        mainPanel.add(backPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListeners(Connection conn){
        trips.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "trips", role.admin);
        });
        timetable.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "timetable", role.admin);
        });
        workers.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "workers", role.admin);
        });

        back.addActionListener((e)->{
            setVisible(false);
            new roleWindow(conn);
        });
    }
}
