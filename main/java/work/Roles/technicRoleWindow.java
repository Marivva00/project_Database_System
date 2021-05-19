package work.Roles;

import work.lookOnTable.lookOnTableView;
import work.requests.requestsWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Vector;

public class technicRoleWindow extends JFrame {
    private JButton tiInspection;
    private JButton requests;

    public technicRoleWindow(Connection conn){
        super("Действия тех. работника в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tiInspection = new JButton("добавить ТО");
        requests = new JButton("запросы в информационной системе");

        addActionListeners(conn);

        JPanel tiInspectionPanel = new JPanel();
        tiInspectionPanel.add(tiInspection);
        JPanel requestsPanel = new JPanel();
        requestsPanel.add(requests);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(tiInspectionPanel);
        mainPanel.add(requestsPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListeners(Connection conn){
        tiInspection.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "technicalInspection", role.technic);
        });
        requests.addActionListener((e)->{
            setVisible(false);
            Vector strings = new Vector();
            Vector tmp1 = new Vector();
            tmp1.add("1");
            tmp1.add("Получить перечень и общее число самолетов приписанных к аэpопоpту, по количеству совеpшенных pейсов.");
            strings.add(tmp1);
            Vector tmp2 = new Vector();
            tmp2.add("2");
            tmp2.add("Получить перечень и общее число самолетов, пpошедших техосмотp за определенный пеpиод вpемени, отпpавленных в pемонт в указанное вpемя, pемонтиpованных заданное число pаз, по количеству совеpшенных pейсов, по возpасту самолета.");
            strings.add(tmp2);
            new requestsWindow(conn, role.technic, strings);
        });
    }
}
