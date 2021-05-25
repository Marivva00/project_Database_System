package work.Roles;

import work.authorisation.usersCreate;
import work.GUI.lookOnTable.lookOnTableView;
import work.requests.getInfoAboutRequest;
import work.requests.requestsWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Vector;

public class passengerRoleWindow extends JFrame {
    private JButton getMyTicket;
    private JButton timetable;
    private JButton request;
    private JButton logout;

    public passengerRoleWindow(Connection conn){
        super("Действия пассажира в информационной системе");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getMyTicket = new JButton("посмтореть мои билеты");
        timetable = new JButton("посмотреть расписание рейсов");
        request = new JButton("запросы в информационной системе");
        logout = new JButton("выйти из системы");
        addActionListeners(conn);

        JPanel ticketPanel = new JPanel();
        ticketPanel.add(getMyTicket);
        JPanel timetablePanel = new JPanel();
        timetablePanel.add(timetable);
        JPanel requestPanel = new JPanel();
        requestPanel.add(request);
        JPanel logoutPanel = new JPanel();
        logoutPanel.add(logout);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(ticketPanel);
        mainPanel.add(timetablePanel);
        mainPanel.add(requestPanel);
        mainPanel.add(logoutPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListeners(Connection conn){
        getMyTicket.addActionListener((e)->{
            setVisible(false);
            new getInfoAboutRequest(conn, role.passenger, null, 3);
        });
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
            tmp2.add("Получить перечень и общее число pейсов и их статус.");
            strings.add(tmp2);
            new requestsWindow(conn, role.passenger, strings);
        });
        logout.addActionListener((e)->{
            setVisible(false);
            new usersCreate(conn, "Авторизация");
        });
    }
}
