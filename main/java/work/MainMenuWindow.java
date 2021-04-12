package work;

import work.Tables.*;
import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainMenuWindow extends JFrame {
    private JLabel independentTablesLabel;
    private JLabel dependentTablesLabel;
    private JLabel requestLabel;

    private JButton requests;
    private JButton department;
    private JButton medical;
    private JButton aviacompany;
    private JButton ticketClass;
    private JButton tripStatus;
    private JButton tripType;
    private JButton airport;
    private JButton gender;            ////////////////////
    private JButton carriage;
    private JButton workers;
    private JButton technicalInspection;
    private JButton planes;
    private JButton trips;
    private JButton timetable;
    private JButton tickets;
    private JButton reserveTickets;
    private JButton passengers;

    public MainMenuWindow(Connection conn){
        super("Main menu");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        independentTablesLabel = new JLabel("This is independent tables:");
        department = new JButton("department");
        medical = new JButton("medical");
        aviacompany = new JButton("aviacompany");
        ticketClass = new JButton("ticket class");
        tripStatus = new JButton("trip status");
        tripType = new JButton("trip type");
        airport = new JButton("airport");
        gender = new JButton("gender");

        dependentTablesLabel = new JLabel("This is dependent tables:");
        carriage = new JButton("carriage");
        workers = new JButton("workers");
        technicalInspection = new JButton("technical inspection");
        planes = new JButton("planes");
        trips = new JButton("trips");
        tickets = new JButton("tickets");
        reserveTickets = new JButton("reserve tickets");
        passengers = new JButton("passengers");
        timetable = new JButton("timetable");

        requestLabel = new JLabel("Here you can find requests in this info system:");
        requests = new JButton("REQUESTS");

        addActionListenersToButtons(conn);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel info1 = new JPanel();
        info1.setLayout(new BoxLayout(info1, BoxLayout.PAGE_AXIS));
        JPanel independent = new JPanel();
        independent.setLayout(new GridLayout(2, 0, 5, 5));
        JPanel info2 = new JPanel();
        info2.setLayout(new BoxLayout(info2, BoxLayout.PAGE_AXIS));
        JPanel dependent = new JPanel();
        dependent.setLayout(new GridLayout(2, 0, 5, 5));

        independentTablesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        info1.add(independentTablesLabel);
        dependentTablesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        info2.add(dependentTablesLabel);

        independent.add(department);
        independent.add(medical);
        independent.add(aviacompany);
        independent.add(ticketClass);
        independent.add(tripStatus);
        independent.add(tripType);
        independent.add(airport);
        independent.add(gender);
        dependent.add(carriage);
        dependent.add(workers);
        dependent.add(technicalInspection);
        dependent.add(planes);
        dependent.add(trips);
        dependent.add(timetable);
        dependent.add(tickets);
        dependent.add(reserveTickets);
        dependent.add(passengers);

        main.add(info1);
        main.add(independent);
        main.add(info2);
        main.add(dependent);
        requestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(requestLabel);
        requests.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(requests);

        add(main);
        setVisible(true);
    }
    private void addActionListenersToButtons(Connection conn){
        department.addActionListener((e)->{
            action(conn, 0,"department");
        });
        medical.addActionListener((e)->{
            action(conn, 0, "medical");
        });
        aviacompany.addActionListener((e)->{
            action(conn, 0, "aviacompany");
        });
        ticketClass.addActionListener((e)->{
            action(conn, 0, "ticketClass");
        });
        tripStatus.addActionListener((e)->{
            action(conn, 0, "tripStatus");
        });
        tripType.addActionListener((e)->{
            action(conn, 0, "tripType");
        });
        airport.addActionListener((e)->{
            action(conn, 0, "airport");
        });
        gender.addActionListener((e)->{
            action(conn, 0, "gender");
        });

        carriage.addActionListener((e)->{
            action(conn, 1, "carriage");
        });
        workers.addActionListener((e)->{
            action(conn, 1, "workers");
        });
        technicalInspection.addActionListener((e)->{
            action(conn, 1, "technicalInspection");
        });
        planes.addActionListener((e)->{
            action(conn, 1, "planes");
        });
        trips.addActionListener((e)->{
            action(conn, 1, "trips");
        });
        timetable.addActionListener((e)->{
            action(conn, 1, "timetable");
        });
        tickets.addActionListener((e)->{
            action(conn, 1, "tickets");
        });
        reserveTickets.addActionListener((e)->{
            action(conn, 1, "reserveTickets");
        });
        passengers.addActionListener((e)->{
            action(conn, 1, "passengers");
        });
    }
    private void action(Connection conn, Integer dep, String tableName){
        setVisible(false);
        new lookOnTableView(conn, dep, tableName);
    }
}
