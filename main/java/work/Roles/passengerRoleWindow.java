package work.Roles;

import work.Roles.Actions.getTicketInfo;
import work.lookOnTable.lookOnTableView;
import work.requests.requestsWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Vector;

public class passengerRoleWindow extends JFrame {
    private JButton timetable;
    private JButton request;

    public passengerRoleWindow(Connection conn){
        super("Действия пассажира в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        timetable = new JButton("посмотреть расписание рейсов");
        request = new JButton("запросы в информационной системе");

        addActionListeners(conn);

        JPanel timetablePanel = new JPanel();
        timetablePanel.add(timetable);
        JPanel requestPanel = new JPanel();
        requestPanel.add(request);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(timetablePanel);
        mainPanel.add(requestPanel);

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
            Vector strings = new Vector();
            Vector tmp1 = new Vector();
            tmp1.add("1");
            tmp1.add("Получить перечень и общее число pейсов по указанному маpшpуту, по цене билета и по всем этим кpитеpиям сpазу.");
            strings.add(tmp1);
            Vector tmp2 = new Vector();
            tmp2.add("2");
            tmp2.add("Получить перечень и общее число отмененных pейсов полностью, в указанном напpавлении, по указанному маpшpуту, по количеству невостpебованных мест, по пpоцентному соотношению невостpебованных мест.");
            strings.add(tmp2);
            Vector tmp3 = new Vector();
            tmp3.add("3");
            tmp3.add("Получить перечень и общее число задеpжанных pейсов полностью, по указанной пpичине, по указанному маpшpуту.");
            strings.add(tmp3);
            new requestsWindow(conn, role.passenger, strings);
        });
    }
}
