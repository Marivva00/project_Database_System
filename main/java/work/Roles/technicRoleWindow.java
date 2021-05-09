package work.Roles;

import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class technicRoleWindow extends JFrame {
    private JButton tiInspection;
    private JButton requests;
    private JButton back;

    public technicRoleWindow(Connection conn){
        super("Действия тех. работника в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tiInspection = new JButton("добавить ТО");
        requests = new JButton("запросы в информационной системе");
        back = new JButton("Назад");

        addActionListeners(conn);

        JPanel tiInspectionPanel = new JPanel();
        tiInspectionPanel.add(tiInspection);
        JPanel requestsPanel = new JPanel();
        requestsPanel.add(requests);
        JPanel backPanel = new JPanel();
        backPanel.add(back);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(tiInspectionPanel);
        mainPanel.add(requestsPanel);
        mainPanel.add(backPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListeners(Connection conn){
        tiInspection.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "technicalInspection", role.technic);
        });

        back.addActionListener((e)->{
            setVisible(false);
            new roleWindow(conn);
        });
    }
}
