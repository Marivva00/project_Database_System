package work.Roles;

import work.Roles.Actions.getTicketInfo;
import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class passengerRoleWindow extends JFrame {
    private JButton timetable;
    private JButton request;
    private JButton back;

    public passengerRoleWindow(Connection conn){
        super("Действия пассажира в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        timetable = new JButton("посмотреть расписание рейсов");
        request = new JButton("посмотреть свой билет");
        back = new JButton("Назад");

        addActionListeners(conn);

        JPanel timetablePanel = new JPanel();
        timetablePanel.add(timetable);
        JPanel requestPanel = new JPanel();
        requestPanel.add(request);
        JPanel backPanel = new JPanel();
        backPanel.add(back);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(timetablePanel);
        mainPanel.add(requestPanel);
        mainPanel.add(backPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListeners(Connection conn){
        timetable.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, 1, "timetable", role.passenger);
        });
        request.addActionListener((e)->{
            setVisible(false);
            new getTicketInfo(conn);
        });
        back.addActionListener((e)->{
            setVisible(false);
            new roleWindow(conn);
        });
    }
}
