package work;

import work.Tables.*;

import javax.swing.*;
import java.sql.Connection;

public class MainMenuWindow extends JFrame {
    private JLabel someInfo;
    private JButton department;
    private JButton medical;
    private JButton carriage;
    private JButton gender;
    private JButton workers;
    public MainMenuWindow(Connection conn){
        super("Main menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        someInfo = new JLabel("There are tables from less dependent to the most dependent");
        department = new JButton("table department");
        medical = new JButton("table medical");
        carriage = new JButton("table carriage");
        gender = new JButton("table gender");
        workers = new JButton("table workers");

        addActionListenersToButtons(conn);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel someInfoPanel = new JPanel();
        JPanel genderPanel = new JPanel();
        JPanel departmentPanel = new JPanel();
        JPanel medicalPanel = new JPanel();
        JPanel carriagePanel = new JPanel();
        JPanel workersPanel = new JPanel();

        someInfoPanel.add(someInfo);
        genderPanel.add(gender);
        departmentPanel.add(department);
        medicalPanel.add(medical);
        carriagePanel.add(carriage);
        workersPanel.add(workers);

        mainPanel.add(someInfoPanel);
        mainPanel.add(genderPanel);
        mainPanel.add(departmentPanel);
        mainPanel.add(medicalPanel);
        mainPanel.add(carriagePanel);
        mainPanel.add(workersPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListenersToButtons(Connection conn){
        gender.addActionListener((e)->{
            setVisible(false);
            new genderTable(conn);
        });
        department.addActionListener((e)->{
            setVisible(false);
            new departmentTable(conn);
        });
        medical.addActionListener((e)->{
            setVisible(false);
            new medicalTable(conn);
        });
        carriage.addActionListener((e)->{
            setVisible(false);
            new carriageTable(conn);
        });
        workers.addActionListener((e)->{
            setVisible(false);
            new workersTable(conn);
        });
    }
}
