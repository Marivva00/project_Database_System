package work.Roles;

import work.lookOnTable.lookOnTableView;
import work.requests.requestsWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Vector;

public class adminRoleWindow extends JFrame {
    private JButton trips;
    private JButton timetable;
    private JButton workers;
    private JButton requests;

    public adminRoleWindow(Connection conn) {
        super("Действия администрации в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        trips = new JButton("работа с рейсами");
        timetable = new JButton("работа с расписанием");
        workers = new JButton("работа с работниками аэропорта");
        requests = new JButton("запросы в информационной системе");

        addActionListeners(conn);

        JPanel tripsPanel = new JPanel();
        tripsPanel.add(trips);
        JPanel timetablePanel = new JPanel();
        timetablePanel.add(timetable);
        JPanel workersPanel = new JPanel();
        workersPanel.add(workers);
        JPanel requestsPanel = new JPanel();
        requestsPanel.add(requests);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(tripsPanel);
        mainPanel.add(timetablePanel);
        mainPanel.add(workersPanel);
        mainPanel.add(requestsPanel);

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
        requests.addActionListener((e)->{
            setVisible(false);
            Vector strings = new Vector();
            Vector tmp1 = new Vector();
            tmp1.add("1");
            tmp1.add("Получить список и общее число всех pаботников аэpопоpта, начальников отделов, pаботников указанного отдела, половому пpизнаку, возpасту, пpизнаку наличия и количеству детей.");
            strings.add(tmp1);
            Vector tmp2 = new Vector();
            tmp2.add("2");
            tmp2.add("Получить перечень и общее число pаботников в бpигаде, по всем отделам, в указанном отделе, обслуживающих конкретный pейс, по возpасту.");
            strings.add(tmp2);
            Vector tmp3 = new Vector();
            tmp3.add("3");
            tmp3.add("Получить перечень и общее число пилотов, пpошедших медосмотp либо не пpошедших его в указанный год, по половому пpизнаку, возpасту.");
            strings.add(tmp3);
            new requestsWindow(conn, role.admin, strings);
        });
    }
}
